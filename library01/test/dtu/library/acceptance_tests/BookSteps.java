package dtu.library.acceptance_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dtu.library.app.Book;
import dtu.library.app.LibraryApp;
import dtu.library.app.OperationNotAllowedException;
//import dtu.library.app.OperationNotAllowedException;

public class BookSteps {

	private LibraryApp libraryApp;

	private Book book;
	private String errorMessage = "";
	private List<Book> books;

	/*
	 * Note that the constructor is apparently never called, but there are no null
	 * pointer exceptions regarding that libraryApp is not set. When creating the
	 * BookSteps object, the Cucumber libraries are using that constructor with an
	 * object of class LibraryApp as the default.
	 *
	 * This also holds for all other step classes that have a similar constructor.
	 * In this case, the <b>same</b> object of class LibraryApp is used as an
	 * argument. This provides an easy way of sharing the same object, in this case
	 * the object of class LibraryApp, among all step classes.
	 *
	 * This principle is called <em>dependency injection</em>. More information can
	 * be found in the "Cucumber for Java" book available online from the DTU Library.
	 */
	public BookSteps(LibraryApp libraryApp) {
		this.libraryApp = libraryApp;
	}

	@Given("^I have a book with title \"([^\"]*)\", author \"([^\"]*)\", and signature \"([^\"]*)\"$")
	public void iHaveABookWithTitleAuthorAndSignature(String title, String author, String signature) throws Exception {
		book = new Book(title,author,signature);
	}

	@Given("^these books are contained in the library$")
	public void theseBooksAreContainedInTheLibrary(List<List<String>> books) throws Exception {
		for (List<String> bookInfo : books) {
			libraryApp.addBook(new Book(bookInfo.get(0), bookInfo.get(1), bookInfo.get(2)));
		}
	}

	@When("^I add the book$")
	public void iAddTheBook() throws Exception {
		try {
			libraryApp.addBook(book);
		} catch (OperationNotAllowedException e) {
			errorMessage = e.getMessage();
		}
	}

	@Then("^the book with title \"([^\"]*)\", author \"([^\"]*)\", and signature \"([^\"]*)\" is added to the library$")
	public void theBookWithTitleAuthorAndSignatureIsAddedToTheLibrary(String title, String author, String signature)
			throws Exception {
		assertEquals(title, book.getTitle());
		assertEquals(author, book.getAuthor());
		assertEquals(signature, book.getSignature());
		assertTrue(libraryApp.getBooks().contains(book));
	}

	@Then("^I get the error message \"([^\"]*)\"$")
	public void iGetTheErrorMessage(String errorMessage) throws Exception {
		assertEquals(errorMessage, this.errorMessage);
	}

	@Given("^the library has a book with title \"([^\"]*)\", author \"([^\"]*)\", and signature \"([^\"]*)\"$")
	public void theLibraryHasABookWithTitleAuthorAndSignature(String title, String author, String signature)
			throws Exception {
		Book book = new Book(title, author, signature);
		libraryApp.addBook(book);
	}

	@When("^I search for the text \"([^\"]*)\"$")
	public void iSearchForTheText(String searchText) throws Exception {
		books = libraryApp.search(searchText);
	}

	@Then("^I find the book with signature \"([^\"]*)\"$")
	public void iFindTheBookWithSignature(String signature) throws Exception {
		assertEquals(1, books.size());
		assertEquals(signature, books.get(0).getSignature());
	}

	@Then("^I don't find any book$")
	public void iDonTFindAnyBook() throws Exception {
		assertTrue(books.isEmpty());
	}

	@Then("^I find a book with signatures \"([^\"]*)\" and \"([^\"]*)\"$")
	public void iFindABookWithSignaturesAnd(String signature1, String signature2) throws Exception {
		assertEquals(2, books.size());
		Book book1 = books.get(0);
		Book book2 = books.get(1);
		assertTrue((book1.getSignature().equals(signature1) && book2.getSignature().equals(signature2))
				|| (book1.getSignature().equals(signature2) && book2.getSignature().equals(signature1)));
	}

}
