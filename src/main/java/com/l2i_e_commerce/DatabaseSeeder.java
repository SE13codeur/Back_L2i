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

@Component
@EnableAsync
public class DatabaseSeeder {

   /* @Value("${meilisearch.url}")
    private String meilisearchUrl;

    @Value("${meilisearch.apikey}")
    private String meilisearchApiKey;

    @Value("${meilisearch.indexUid}")
    private String meilisearchIndexUid;*/

    @Autowired
    private final ItemService<Book, ?> itemService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private EditorService editorService;

    @Autowired
    private TVAService tvaService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public DatabaseSeeder(ItemService<Book, ?> itemService, BookService bookService,
                          AuthorService authorService, EditorService editorService, CategoryService categoryService, TVAService tvaService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.editorService = editorService;
        this.categoryService = categoryService;
        this.itemService = itemService;
        this.tvaService = tvaService;
    }

//    @PostConstruct
//    @Profile("dev")
//    public void seedDatabaseForDevelopment() {
//        seedDatabase();
//        indexItemsInMeiliSearch();
//    }

    @PostConstruct
    @Profile("prod")
    public void seedDatabaseForProduction() {
        seedDatabase();
    }

    public void seedDatabase() {
        System.err.println("Entrée du seeder");
        try {
            TVA tva20 = new TVA();
            tva20.setTvaType("tva20");
            tva20.setTvaRate(0.2);

            TVA tva10 = new TVA();
            tva10.setTvaType("tva10");
            tva10.setTvaRate(0.1);

            TVA tva5 = new TVA();
            tva5.setTvaType("tva5.5");
            tva5.setTvaRate(0.055);

            TVA tva2 = new TVA();
            tva2.setTvaType("tva2.1");
            tva2.setTvaRate(0.021);


            this.tvaService.save(tva20);
            this.tvaService.save(tva10);
            this.tvaService.save(tva5);
            this.tvaService.save(tva2);

        } catch (Exception ex) {
            System.err.println("Erreur de la sauvegarde de la TVA");
        }

        try {
            Category itemsCategory = null;
            if (!categoryService.categoryExists("Articles", null)) {
                itemsCategory = new Category();
                itemsCategory.setName("Articles");
                categoryService.save(itemsCategory);
            }

            Category booksCategory = null;
            if (itemsCategory != null && !categoryService.categoryExists("Livres", itemsCategory.getId())) {
                booksCategory = new Category();
                booksCategory.setParent(itemsCategory);
                booksCategory.setName("Livres");
                categoryService.save(booksCategory);
            }

            Category moviesCategory = null;
            if (itemsCategory != null && !categoryService.categoryExists("Vidéos", itemsCategory.getId())) {
                moviesCategory = new Category();
                moviesCategory.setName("Vidéos");
                moviesCategory.setParent(itemsCategory);
                categoryService.save(moviesCategory);
            }

            Category frenchBooksCategory = null;
            if (booksCategory != null && !categoryService.categoryExists("Langue Française", booksCategory.getId())) {
                frenchBooksCategory = new Category();
                frenchBooksCategory.setName("Langue Française");
                frenchBooksCategory.setParent(booksCategory);
                categoryService.save(frenchBooksCategory);
            }

            Category englishBooksCategory = null;
            if (booksCategory != null && !categoryService.categoryExists("Langue Anglaise", booksCategory.getId())) {
                englishBooksCategory = new Category();
                englishBooksCategory.setName("Langue Anglaise");
                englishBooksCategory.setParent(booksCategory);
                categoryService.save(englishBooksCategory);
            }

            Category frenchMoviesCategory = null;
            if (moviesCategory != null && !categoryService.categoryExists("Langue Française", moviesCategory.getId())) {
                frenchMoviesCategory = new Category();
                frenchMoviesCategory.setName("Langue Française");
                frenchMoviesCategory.setParent(moviesCategory);
                categoryService.save(frenchMoviesCategory);
            }

            Category englishMoviesCategory = null;
            if (moviesCategory != null && !categoryService.categoryExists("Langue Anglaise", moviesCategory.getId())) {
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
            String description = json.getString("desc").replace("'", "\'");
            String rating = json.getString("rating");

            String priceString = json.getString("price");
            String imageUrl = json.getString("image");

            Set<Author> authorsSet = new HashSet<Author>();
            String[] authorsArray = authors.split(",");
            for (int i = 0; i < authorsArray.length; i++){
                String currentAuthorStr = authorsArray[i];
                String[] currentAuthorArray = currentAuthorStr.split(" ");
                String firstname = currentAuthorArray[0];
                String lastname = currentAuthorArray[1];
                List<Author> authorsList = this.authorService.findByFirstnameAndLastname(firstname, lastname);
                if (authorsList.size() == 0){
                    Author authorNew = new Author();
                    authorNew.setFirstname(firstname);
                    authorNew.setLastname(lastname);
                    this.authorService.save(authorNew);
                    authorsSet.add(authorNew);
                } else {
                    authorsSet.add(authorsList.get(0));
                }
            }

            // Créer un objet Editor à partir du nom de l'éditeur
            Editor editor = new Editor();
            Editor currentEditor = this.editorService.findByName(publisher);
            if(currentEditor != null){
                editor = currentEditor;
            } else {
                editor.setName(publisher);
                this.editorService.save(editor);
            }

            // Créer et enregistrer un objet Book
            Book book = Book.builder()
                    .title(title)
                    .subtitle(subtitle)
                    .authors(authorsSet)
                    .editor(editor)
                    .isbn13(isbn13)
                    .pages(pages)
                    .year(year)
                    .version(ThreadLocalRandom.current().nextInt(1, 5))
                    .onSale((short) 0)
                    .build();

            // Créer et enregistrer un objet Item
            BigDecimal price = new BigDecimal(priceString.replace("$", "").trim());
            book.setRegularPrice(price);
            book.setImageUrl(imageUrl);
            book.setDescription(description);
            book.setQuantityInStock(ThreadLocalRandom.current().nextInt(0, 333));
            book.setRating((float) Integer.parseInt(rating) == 0 ? 5 : (float) Integer.parseInt(rating));
            book.setLanguage(language);
            book.setCategory(language.equalsIgnoreCase("English") ?
                    this.categoryService.findById(4l) :
                    this.categoryService.findById(5l));
            book.setTva(this.tvaService.findById(3l));

            try {
                bookService.save(book);
            } catch (DataIntegrityViolationException e) {
            }


        } catch (JSONException e) {

        }
        return null;
    }
}


//    @Transactional
//    public void indexItemsInMeiliSearch() {
//        try {
//
//            List<Book> books = this.bookService.findAll();
//            System.err.println("NBR ITEMS MYSQL " + books.size());
//            StringBuffer sbJson = new StringBuffer();
//            sbJson.append("[");
//            Boolean isFirstItemTreated = false;
//            for(Item item : books) {
//                if (!isFirstItemTreated) {
//                    isFirstItemTreated = true;
//                } else {
//                    sbJson.append(",");
//                }
//                if (item instanceof Book) {
//                    Book currentBook = (Book) item;
//                    sbJson.append("{");
//                    sbJson.append("\"id\"");
//                    sbJson.append(":");
//                    sbJson.append(currentBook.getId());
//                    sbJson.append(",");
//                    sbJson.append("\"imageUrl\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getImageUrl() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"description\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getDescription() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"regularPrice\"");
//                    sbJson.append(":");
//                    sbJson.append(currentBook.getRegularPrice());
//                    sbJson.append(",");
//                    sbJson.append("\"quantityInStock\"");
//                    sbJson.append(":");
//                    sbJson.append(currentBook.getQuantityInStock());
//                    sbJson.append(",");
//                    sbJson.append("\"rating\"");
//                    sbJson.append(":");
//                    sbJson.append(currentBook.getRating());
//                    sbJson.append(",");
//                    sbJson.append("\"isNewCollection\"");
//                    sbJson.append(":");
//                    sbJson.append(currentBook.getIsNewCollection() == 1 ? true : false);
//                    sbJson.append(",");
//                    sbJson.append("\"language\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getLanguage() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"totalSales\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getTotalSales() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"isbn13\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getIsbn13() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"title\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getTitle() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"subtitle\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getSubtitle() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"pages\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getPages() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"year\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getYear() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"version\"");
//                    sbJson.append(":");
//                    sbJson.append(currentBook.getVersion());
//                    sbJson.append(",");
//                    sbJson.append("\"isInStock\"");
//                    sbJson.append(":");
//                    sbJson.append(currentBook.getIsInStock() == 1 ? true : false);
//                    sbJson.append(",");
//                    sbJson.append("\"meiliSearchId\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getMeiliSearchId() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"category\"");
//                    sbJson.append(":");
//                    sbJson.append("{");
//                    sbJson.append("\"id\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getBooksCategory().getId() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"name\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getBooksCategory().getName() + "\"");
//                    sbJson.append(",");
//                    sbJson.append("\"parent_id\"");
//                    sbJson.append(":");
//                    sbJson.append("\"" + currentBook.getBooksCategory().getParent().getId() + "\"");
//                    sbJson.append("}");
//                    sbJson.append(",");
//                    sbJson.append("\"authors\"");
//                    sbJson.append(":");
//                    sbJson.append("[");
//                    Boolean firstCurrentAuthorTreated = false;
//                    Set<Author> authors = authorRepository.findByBooks_Id(currentBook.getId());
//                    for(Author currentAuthor :  authors) {
//                    	if (firstCurrentAuthorTreated) {
//                    		sbJson.append(",");
//                    	} else {
//                    		firstCurrentAuthorTreated = true;
//                    	}
//                    	sbJson.append("{");
//                    	sbJson.append("\"id\"");
//                    	sbJson.append(":");
//                    	sbJson.append(currentAuthor.getId());
//                    	sbJson.append(",");
//                    	sbJson.append("\"firstname\"");
//                    	sbJson.append(":");
//                    	sbJson.append("\"" + currentAuthor.getFirstname() + "\"");
//                    	sbJson.append(",");
//                    	sbJson.append("\"lastname\"");
//                    	sbJson.append(":");
//                    	sbJson.append("\"" + currentAuthor.getLastname() + "\"");
//                    	sbJson.append("}");
//                    }
//                    sbJson.append("]");
//                    sbJson.append(",");
//                    sbJson.append("\"editor\"");
//                    sbJson.append(":");
//                    Editor editor = editorRepository.findByBooks_Id(currentBook.getId());
//
//                    	sbJson.append("{");
//                    	sbJson.append("\"id\"");
//                    	sbJson.append(":");
//                    	sbJson.append(editor.getId());
//                    	sbJson.append(",");
//                    	sbJson.append("\"name\"");
//                    	sbJson.append(":");
//                    	sbJson.append("\"" + editor.getName().replace("'","\'") + "\"");
//                    	sbJson.append("}");
//                    sbJson.append("}");
//
//                }
//            }
//            sbJson.append("]");
//            System.err.println("sbJson ajouté : " + sbJson.toString());
//
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest httpRequest = HttpRequest.newBuilder()
//                    .uri(URI.create(this.meilisearchUrl))
//                    .header("Content-Type", "application/json")
//                    .header("Authorization", "Bearer " + this.meilisearchApiKey)
//					.POST(HttpRequest.BodyPublishers.ofString(sbJson.toString()))
//					//.PUT(HttpRequest.BodyPublishers.ofString(sbJson.toString()))
//                    .build();
//
//            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//            int statusCode = httpResponse.statusCode();
//            String responseBody = httpResponse.body();
//
//            if (statusCode >= 400) {
//
//            } else {
//                if (statusCode == 202) {
//                    try {
//                        JSONObject jsonResponse = new JSONObject(responseBody);
//                        int taskUid = jsonResponse.getInt("taskUid");
//
//                        Thread.sleep(Duration.ofSeconds(2).toMillis());
//
//                        checkTaskStatus(this.meilisearchIndexUid, taskUid);
//                    } catch (JSONException | InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("Erreur: " + e.getMessage());
//        }
//    }


/*
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
*/
            
   
