package com.l2i_e_commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class L2iECommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(L2iECommerceApplication.class, args);
	}

}
/*
 * package com.l2i_e_commerce;
 * 
 * import java.math.BigDecimal; import java.util.Set; import java.util.HashSet;
 * 
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.ApplicationArguments; import
 * org.springframework.boot.ApplicationRunner; import
 * org.springframework.boot.SpringApplication; import
 * org.springframework.boot.autoconfigure.SpringBootApplication; import
 * org.springframework.core.env.Environment;
 * 
 * import com.l2i_e_commerce.model.*; import com.l2i_e_commerce.service.*;
 * 
 * import lombok.*;
 * 
 * @SpringBootApplication
 * 
 * @RequiredArgsConstructor public class L2iECommerceApplication implements
 * ApplicationRunner {
 * 
 * private final BookService bookService; private final CategoryService
 * categoryService; private final ItemService itemService;
 * 
 * @Autowired private Environment environment;
 * 
 * public static void main(String[] args) {
 * SpringApplication.run(L2iECommerceApplication.class, args); }
 * 
 * @Override public void run(ApplicationArguments args) throws Exception { if
 * (environment.getProperty("app.seedDatabase", Boolean.class, false)) {
 * seedDatabase(); } }
 * 
 * private void seedDatabase() {
 * 
 * //test pour un livre déjà existant avec une version différente Category
 * categoryIT = new Category(); categoryIT.setName("IT"); categoryIT =
 * categoryService.save(categoryIT);
 * 
 * Set<Author> authors1 = new HashSet<>(); authors1.add(new Author("Joyce Kay",
 * "Avila"));
 * 
 * Book book1 = Book.builder() .title("Snowflake: The Definitive Guide")
 * .authors(authors1) .editor(new Editor("Penguin House")) .category(categoryIT)
 * .isbn13("9781098103828") .pages("450") .year("2023") .version(3) .build();
 * book1 = bookService.save(book1); itemService.save(new Item(book1,
 * "https://itbook.store/img/books/9781098103828.png", new BigDecimal("58.90"),
 * true, "English"));
 * 
 * 
 * Book book2 = Book.builder() .title("Python for Data Analysis, 3rd Edition")
 * .authors(Set.of(new Author("Wes", "McKinney"))) .editor(new
 * Editor("O'Reilly Press")) .category(categoryIT) .isbn13("9781098104030")
 * .pages("350") .year("2010") .version(3) .build(); book2 =
 * bookService.save(book2); itemService.save(new Item(book2,
 * "https://itbook.store/img/books/9781098104030.png", new BigDecimal("37.49"),
 * false, "English"));
 * 
 * Book book3 = Book.builder() .title("Reliable Machine Learning")
 * .summary("Applying SRE Principles to ML in Production") .authors(Set.of(new
 * Author("O'Reilly", "Media"))) .editor(new Editor("Image 3D"))
 * .category(categoryIT) .isbn13("9781098106225") .pages("350") .year("2023")
 * .build(); book3 = bookService.save(book3); itemService.save(new Item(book3,
 * "https://itbook.store/img/books/9781098104030.png", new BigDecimal("37.49"),
 * false, "English"));
 * 
 * 
 * Book book4 = Book.builder()
 * .title("Data Visualization with Python and JavaScript, 2nd Edition")
 * .summary("Scrape, Clean, Explore, and Transform Your Data")
 * .authors(Set.of(new Author("Kyran", "Dale"))) .editor(new
 * Editor("Miro Collecton")) .category(categoryIT) .isbn13("9781098111878")
 * .pages("420") .year("2023") .build(); book4 = bookService.save(book4);
 * itemService.save(new Item(book4,
 * "https://itbook.store/img/books/9781098104030.png", new BigDecimal("37.49"),
 * false, "English"));
 * 
 * 
 * Book book5 = Book.builder() .title("Learning Microsoft Power BI")
 * .summary("Transforming Data into Insights") .authors(Set.of(new
 * Author("Jeremey", "Arnold"))) .editor(new Editor("O'Reilly Media"))
 * .category(categoryIT) .isbn13("9781098112844") .pages("375") .year("2023")
 * .build(); book5 = bookService.save(book5); itemService.save(new Item(book5,
 * "https://itbook.store/img/books/9781098104030.png", new BigDecimal("37.49"),
 * false, "English"));
 * 
 * }
 * 
 * }
 */