package com.l2i_e_commerce;

import com.l2i_e_commerce.dao.*;
import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;



@Component
@EnableAsync
public class DatabaseSeeder {

    @Value("${meilisearch.url}")
    private String meilisearchUrl;

    @Value("${meilisearch.apikey}")
    private String meilisearchApiKey;

    @Value("${meilisearch.indexUid}")
    private String meilisearchIndexUid;


    @Autowired
    private final ItemService<Book, ?> itemService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private EditorRepository editorRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public DatabaseSeeder(ItemService<Book, ?> itemService, BookService bookService, 
                          AuthorRepository authorRepository, EditorRepository editorRepository, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorRepository = authorRepository;
        this.editorRepository = editorRepository;
        this.categoryService = categoryService;
        this.itemService = itemService; 
    }
    
    @Transactional
    public void indexItemsInMeiliSearch() {
        try {

            List<Book> books = this.bookService.findAll();
            System.err.println("NBR ITEMS MYSQL " + books.size());
            StringBuffer sbJson = new StringBuffer();
            sbJson.append("[");
            Boolean isFirstItemTreated = false;
            for(Item item : books) {
                if (!isFirstItemTreated) {
                    isFirstItemTreated = true;
                } else {
                    sbJson.append(",");
                }
                if (item instanceof Book) {
                    Book currentBook = (Book) item;
                    sbJson.append("{");
                    sbJson.append("\"id\"");
                    sbJson.append(":");
                    sbJson.append(currentBook.getId());
                    sbJson.append(",");
                    sbJson.append("\"imageUrl\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getImageUrl() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"description\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getDescription() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"regularPrice\"");
                    sbJson.append(":");
                    sbJson.append(currentBook.getRegularPrice());
                    sbJson.append(",");
                    sbJson.append("\"quantityInStock\"");
                    sbJson.append(":");
                    sbJson.append(currentBook.getQuantityInStock());
                    sbJson.append(",");
                    sbJson.append("\"rating\"");
                    sbJson.append(":");
                    sbJson.append(currentBook.getRating());
                    sbJson.append(",");
                    sbJson.append("\"isNewCollection\"");
                    sbJson.append(":");
                    sbJson.append(currentBook.getIsNewCollection() == 1 ? true : false);
                    sbJson.append(",");
                    sbJson.append("\"language\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getLanguage() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"totalSales\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getTotalSales() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"isbn13\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getIsbn13() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"title\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getTitle() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"subtitle\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getSubtitle() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"pages\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getPages() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"year\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getYear() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"version\"");
                    sbJson.append(":");
                    sbJson.append(currentBook.getVersion());
                    sbJson.append(",");
                    sbJson.append("\"isInStock\"");
                    sbJson.append(":");
                    sbJson.append(currentBook.getIsInStock() == 1 ? true : false);
                    sbJson.append(",");
                    sbJson.append("\"meiliSearchId\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getMeiliSearchId() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"category\"");
                    sbJson.append(":");
                    sbJson.append("{");
                    sbJson.append("\"id\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getBooksCategory().getId() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"name\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getBooksCategory().getName() + "\"");
                    sbJson.append(",");
                    sbJson.append("\"parent_id\"");
                    sbJson.append(":");
                    sbJson.append("\"" + currentBook.getBooksCategory().getParent().getId() + "\"");
                    sbJson.append("}");
                    sbJson.append(",");
                    sbJson.append("\"authors\"");
                    sbJson.append(":");
                    sbJson.append("[");
                    Boolean firstCurrentAuthorTreated = false;
                    Set<Author> authors = authorRepository.findByBooks_Id(currentBook.getId());
                    for(Author currentAuthor :  authors) {
                    	if (firstCurrentAuthorTreated) {
                    		sbJson.append(",");
                    	} else {
                    		firstCurrentAuthorTreated = true;
                    	}
                    	sbJson.append("{");
                    	sbJson.append("\"id\"");
                    	sbJson.append(":");
                    	sbJson.append(currentAuthor.getId());
                    	sbJson.append(",");
                    	sbJson.append("\"firstname\"");
                    	sbJson.append(":");
                    	sbJson.append("\"" + currentAuthor.getFirstName() + "\"");
                    	sbJson.append(",");
                    	sbJson.append("\"lastname\"");
                    	sbJson.append(":");
                    	sbJson.append("\"" + currentAuthor.getLastName() + "\"");
                    	sbJson.append("}");
                    }
                    sbJson.append("]");
                    sbJson.append(",");
                    sbJson.append("\"editor\"");
                    sbJson.append(":");
                    Editor editor = editorRepository.findByBooks_Id(currentBook.getId());
                    	
                    	sbJson.append("{");
                    	sbJson.append("\"id\"");
                    	sbJson.append(":");
                    	sbJson.append(editor.getId());
                    	sbJson.append(",");
                    	sbJson.append("\"name\"");
                    	sbJson.append(":");
                    	sbJson.append("\"" + editor.getName().replace("'","\'") + "\"");
                    	sbJson.append("}");
                    sbJson.append("}");

                }
            }
            sbJson.append("]");
            System.err.println("sbJson ajouté : " + sbJson.toString());

			/*
			 * String jsonStringWithPrimaryKey = "{\"primaryKey\": \"id\", \"documents\": "
			 * + sbJson.toString() + "}";
			 */

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(this.meilisearchUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + this.meilisearchApiKey)
					//.POST(HttpRequest.BodyPublishers.ofString(sbJson.toString()))					
					.PUT(HttpRequest.BodyPublishers.ofString(sbJson.toString()))                 
                    .build();

            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = httpResponse.statusCode();
            String responseBody = httpResponse.body();

            if (statusCode >= 400) {
  
            } else {
                if (statusCode == 202) {
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        int taskUid = jsonResponse.getInt("taskUid");

                        Thread.sleep(Duration.ofSeconds(2).toMillis());

                        checkTaskStatus(this.meilisearchIndexUid, taskUid);
                    } catch (JSONException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }


    public void checkTaskStatus(String indexUid, int taskUid) {
        try {
            String taskUrl = this.meilisearchUrl + "/indexes/" + indexUid + "/tasks/" + taskUid;
            String apiKey = this.meilisearchApiKey;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(taskUrl))
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
        try {
        Category itemsCategory = null;
        if (!categoryService.categoryExists("Articles", null)) {
            itemsCategory = new Category();
            itemsCategory.setName("Articles");
            categoryService.save(itemsCategory);
        }

        Category booksCategory = null;
        if (itemsCategory!= null && !categoryService.categoryExists("Livres", itemsCategory.getId())) {
            booksCategory = new Category();
            booksCategory.setParent(itemsCategory);
            booksCategory.setName("Livres");
            categoryService.save(booksCategory);
        }

        Category moviesCategory = null;
        if (itemsCategory!= null && !categoryService.categoryExists("Vidéos", itemsCategory.getId())) {
            moviesCategory = new Category();
            moviesCategory.setName("Vidéos");
            moviesCategory.setParent(itemsCategory);
            categoryService.save(moviesCategory);
        }

        Category frenchBooksCategory = null;
        if (booksCategory!= null && !categoryService.categoryExists("Langue Française", booksCategory.getId())) {
            frenchBooksCategory = new Category();
            frenchBooksCategory.setName("Langue Française");
            frenchBooksCategory.setParent(booksCategory);
            categoryService.save(frenchBooksCategory);
        }

        Category englishBooksCategory = null;
        if (booksCategory!= null && !categoryService.categoryExists("Langue Anglaise", booksCategory.getId())) {
            englishBooksCategory = new Category();
            englishBooksCategory.setName("Langue Anglaise");
            englishBooksCategory.setParent(booksCategory);
            categoryService.save(englishBooksCategory);
        }

        Category frenchMoviesCategory = null;
        if (moviesCategory!= null && !categoryService.categoryExists("Langue Française", moviesCategory.getId())) {
            frenchMoviesCategory = new Category();
            frenchMoviesCategory.setName("Langue Française");
            frenchMoviesCategory.setParent(moviesCategory);
            categoryService.save(frenchMoviesCategory);
        }

        Category englishMoviesCategory = null;
        if (moviesCategory!= null && !categoryService.categoryExists("Langue Anglaise", moviesCategory.getId())) {
            englishMoviesCategory = new Category();
            englishMoviesCategory.setParent(moviesCategory);
            englishMoviesCategory.setName("Langue Anglaise");
            categoryService.save(englishMoviesCategory);
        }

        } catch (DataIntegrityViolationException e) {
        }

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
					}
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
            String language = json.getString("language");
            String year = json.getString("year");
            String description = json.getString("desc");
            String rating = json.getString("rating");
            int maxLength = 777;

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
                    .version(ThreadLocalRandom.current().nextInt(1, 5))
                    .build();

            // Créer et enregistrer un objet Item
            BigDecimal price = new BigDecimal(priceString.replace("$", "").trim());
            book.setRegularPrice(price);
            book.setImageUrl(imageUrl);
            book.setDescription(description);
            book.setIsNewCollection((short) (Integer.parseInt(year) >= 2022 ?
            						1 : 0)
            		);
            book.setQuantityInStock(ThreadLocalRandom.current().nextInt(0, 333));
            book.setRating((float) Integer.parseInt(rating));
            book.setLanguage(language);
            book.setBooksCategory(language.equalsIgnoreCase("English") ? 
            		this.categoryService.findById(5l) :
            			this.categoryService.findById(4l));
            
            try {
            	bookService.save(book);
            } catch (DataIntegrityViolationException e) {
            }


        } catch (JSONException e) {

        }
        return null;
    }
}
        

            
            
   
