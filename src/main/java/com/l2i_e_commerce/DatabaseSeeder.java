package com.l2i_e_commerce;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.dao.DataIntegrityViolationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.net.*;
import java.net.http.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import java.util.stream.Collectors;

import com.meilisearch.sdk.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;



@Component
@EnableAsync
public class DatabaseSeeder {

    @Autowired
    private final ItemService<Book, ?> itemService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private final CategoryService categoryService;
    
    @Autowired
    private ObjectMapper objectMapper;
    

    @Autowired
    public DatabaseSeeder(ItemService<Book, ?> itemService, BookService bookService, 
                          CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.itemService = itemService; 
    }
    
    @Transactional
    public void indexItemsInMeiliSearch() {
        System.err.println("Entrée dans la fonction d'indexation...");
        try {
            String url = "https://ms-700518c9264c-3155.fra.meilisearch.io/indexes/items/documents";
            String apiKey = "f536358532bd64afb90653535c616d81c18239d6";
            Config config = new Config(url, apiKey);

            List<Book> books = this.bookService.findAll();
            System.err.println("XXX NBR " + books.size());
            StringBuffer sbJson = new StringBuffer();
            sbJson.append("[");
            Boolean isFirstItemTreated = false;
            for(Item item : books) {
                System.err.println("sbJson is doing the loop ...");

                if (!isFirstItemTreated) {
                    isFirstItemTreated = true;
                } else {
                    sbJson.append(",");
                }
                if (item instanceof Book) {
                    Book currentBook = (Book) item;
                    String bookJson = objectMapper.writeValueAsString(currentBook);
                    sbJson.append(bookJson);
                    System.err.println("booksJson: XXXXXXXXX" + bookJson);
                }
            }
            sbJson.append("]");
            System.err.println("sbJson ajouté XXXXXXXX: " + sbJson.toString());

			/*
			 * String jsonStringWithPrimaryKey = "{\"primaryKey\": \"id\", \"documents\": "
			 * + sbJson.toString() + "}";
			 */

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(sbJson.toString()))
					/*
					 * .PUT(HttpRequest.BodyPublishers.ofString(jsonStringWithPrimaryKey))
					 */                    
                    .build();

            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = httpResponse.statusCode();
            String responseBody = httpResponse.body();

            if (statusCode >= 400) {
  
            } else {
                System.err.println("MeiliSearch API response status code: " + statusCode);
                System.err.println("MeiliSearch API response body: " + responseBody);

                if (statusCode == 202) {
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        int taskUid = jsonResponse.getInt("taskUid");

                        Thread.sleep(Duration.ofSeconds(2).toMillis());

                        checkTaskStatus("items", taskUid);
                    } catch (JSONException | InterruptedException e) {
                        System.err.println("Error parsing JSON or waiting for task status: " + responseBody);
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("XXXXXXXX Erreur: " + e.getMessage());
        }
    }


    public void checkTaskStatus(String indexUid, int taskUid) {
        try {
            String url = "https://ms-700518c9264c-3155.fra.meilisearch.io/indexes/" + indexUid + "/tasks/" + taskUid;
            String apiKey = "f536358532bd64afb90653535c616d81c18239d6";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + apiKey)
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = httpResponse.statusCode();
            String responseBody = httpResponse.body();

            System.out.println("MeiliSearch Task API response status code: " + statusCode);
            System.out.println("MeiliSearch Task API response body: " + responseBody);

        } catch (Exception e) {
            System.err.println("Error checking task status: " + e.getMessage());
        }
    }

    @PostConstruct
    @Profile("dev")
    public void seedDatabaseForDevelopment() {
        seedDatabase();
        indexItemsInMeiliSearch();
    }

	/*
	 * @PostConstruct
	 * 
	 * @Profile("prod") public void seedDatabaseForProduction() { seedDatabase();
	 * indexItemsInMeiliSearch(); }
	 */
    
    @PostConstruct
    public void seedDatabase() {
    	Category category = new Category();
    	category.setName("Livres");
    	try {
            categoryService.save(category);
		} catch (DataIntegrityViolationException e) {
			/*
			 * System.err.println("Catégorie déjà présente en base avec ce nom : " +
			 * category.getName());
			 */		}
    	
        for (int i = 1; i <= 8; i++) {
            fetchData(i);
        }
    }

    @Async
    public void fetchData(int pageNumber) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.itbook.store/1.0/search/mongodb/" + pageNumber))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::parse);
    }

    public String parse(String responseBody) {
        try {
            JSONObject json = new JSONObject(responseBody);
            JSONArray books = json.getJSONArray("books");
            for (int i = 0; i < books.length(); i++) {
                JSONObject book = books.getJSONObject(i);
                String isbn13 = book.getString("isbn13");
                getBookDetails(isbn13);
            }
        } catch (JSONException e) {
			/*
			 * System.err.println("Error parsing JSON in parse method: " + responseBody);
			 * e.printStackTrace();
			 */
        }
        return null;
    }

    public void getBookDetails(String isbn13) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.itbook.store/1.0/books/" + isbn13))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(t -> {
					try {
						return parseBookDetails(t);
					} catch (Exception e) {
						/*
						 * System.err.println(e.getMessage());
						 */					}
					return t;
				})
                .join();
    }

    public String parseBookDetails(String responseBody) throws Exception {
    	try {
            JSONObject json = new JSONObject(responseBody);

         // Extraire les informations du livre à partir du JSON
            String title = json.getString("title");
            String subtitle = json.getString("subtitle");
            String authors = json.getString("authors");
            String publisher = json.getString("publisher");
            String isbn13 = json.getString("isbn13");
            String pages = json.getString("pages");
            String year = json.getString("year");
            String description = json.getString("desc");
            int maxLength = 404;

            if (description.length() > maxLength) {
                description = description.substring(0, maxLength - 3) + "...";
            }

            String priceString = json.getString("price");
            String imageUrl = json.getString("image");

            Set<Author> authorsSplitString = Arrays.stream(authors.split(","))
                    .map(String::trim)
                    .map(fullName -> {
                        String[] nameParts = fullName.split(" ");
                        String firstName = nameParts[0];
                        String lastName = nameParts.length > 1 ? nameParts[1] : "";
                        Author author = new Author();
                        author.setFirstName(firstName);
                        author.setLastName(lastName);
                        return author;
                    })
                    .collect(Collectors.toSet());
            
            // Créer un objet Editor à partir du nom de l'éditeur
            Editor editor = new Editor();
            editor.setName(publisher);
            
            // Créer et enregistrer un objet Book
            Book book = Book.builder()
                    .title(title)
                    .subtitle(subtitle)
                    .authors(authorsSplitString)
                    .editor(editor)
                    .isbn13(isbn13)
                    .pages(pages)
                    .year(year)
                    .summary(description)
                    .version(ThreadLocalRandom.current().nextInt(1, 5))
                    .category(this.categoryService.findById(1l))
                    .build();

            try {
            	bookService.save(book);
            } catch (DataIntegrityViolationException e) {
				/*
				 * System.err.println("Livre déjà présent en base avec cet isbn13 : " + isbn13);
				 */            }

            // Créer et enregistrer un objet Item
            Item item = new Book();
            BigDecimal price = new BigDecimal(priceString.replace("$", "").trim());
            item.setRegularPrice(price);
            item.setImageUrl(imageUrl);
            item.setDescription(description);
            // Autres attributs si nécessaire
            itemService.save((Book)item);

        } catch (JSONException e) {
			/*
			 * System.err.println("Error parsing JSON in parseBookDetails method: " +
			 * responseBody); e.printStackTrace();
			 */
        }
        return null;
    }
}
        

            
            
   
