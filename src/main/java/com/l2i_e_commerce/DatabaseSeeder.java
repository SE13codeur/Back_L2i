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

@Component
@EnableAsync
public class DatabaseSeeder {

    private final ItemService itemService;
    private final BookService bookService;
    private final CategoryService categoryService;


    @Autowired
    public DatabaseSeeder(ItemService itemService, BookService bookService, 
                          EditorService editorService, CategoryService categoryService) {
        this.itemService = itemService;
        this.bookService = bookService;
        this.categoryService = categoryService;


    }

    @PostConstruct
    @Profile("dev")
    public void seedDatabaseForDevelopment() {
        // données pour l'environnement de développement
    	/*
    	 * public void seedDatabasePerso() { //test pour un livre déjà existant avec une
    	 * version différente Category categoryIT = new Category();
    	 * categoryIT.setName("IT"); categoryIT = categoryService.save(categoryIT);
    	 * 
    	 * Set<Author> authors1 = new HashSet<>(); authors1.add(new Author("Joyce Kay",
    	 * "Avila"));
    	 * 
    	 * Book book1 = Book.builder() .title("Snowflake: The Definitive Guide")
    	 * .authors(authors1) .editor(new Editor("Penguin House")) .category(categoryIT)
    	 * .isbn13("9781098103828") .pages("450") .year("2023") .version(3) .build();
    	 * book1 = bookService.save(book1); itemService.save(new Item(book1,
    	 * "https://itbook.store/img/books/9781098103828.png", "", new
    	 * BigDecimal("58.90"), true, "English")); }
    	 */
        seedDatabase();
    }

    @PostConstruct
    @Profile("prod")
    public void seedDatabaseForProduction() {
    	seedDatabase();
    }
    
    @PostConstruct
    public void seedDatabase() {
    	// Créer et enregistrer un objet Category
    	Category category = new Category();
    	category.setName("Livres");
    	try {
            categoryService.save(category);
		} catch (DataIntegrityViolationException e) {
            System.err.println("Catégorie déjà présente en base avec ce nom : " + category.getName());

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
            System.err.println("Error parsing JSON in parse method: " + responseBody);
            e.printStackTrace();
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
                .thenApply(this::parseBookDetails)
                .join();
    }

    public String parseBookDetails(String responseBody) {
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
            Book book = new Book();
            book.setTitle(title);
            book.setSubtitle(subtitle);
            book.setAuthors(authorsSplitString);
            book.setEditor(editor);
            book.setIsbn13(isbn13);
            book.setPages(pages);
            book.setYear(year);
            book.setSummary(description);
            book.setVersion(ThreadLocalRandom.current().nextInt(1, 5));
            book.setCategory(this.categoryService.findById(1l));
            try {
            	bookService.save(book);
            } catch (DataIntegrityViolationException e) {
                System.err.println("Livre déjà présent en base avec cet isbn13 : " + isbn13);
            }

            // Créer et enregistrer un objet Item
            Item item = new Item();
            item.setBook(book);
            BigDecimal price = new BigDecimal(priceString.replace("$", "").trim());
            item.setRegularPrice(price);
            item.setImageUrl(imageUrl);
            item.setDescription(description);
            // Autres attributs si nécessaire
            itemService.save(item);

        } catch (JSONException e) {
            System.err.println("Error parsing JSON in parseBookDetails method: " + responseBody);
            e.printStackTrace();
        }
        return null;
    }
}
        

            
            
   
