package dtu.library.app;

import java.util.ArrayList;
import java.util.List;

public class LibraryApp {

    private boolean isLoggedIn = false;
    private ArrayList<Book> bookList = new ArrayList<>();


    //----Admin features----//

    public boolean adminLoggedIn() {
        return isLoggedIn;
    }
    public boolean adminLogin(String password) {
        this.isLoggedIn = password.equals("adminadmin");
        return isLoggedIn;
    }
    public void adminLogout(){
        isLoggedIn = false;
    }

    //----BookList Features----//

    public void addBook(Book book) throws OperationNotAllowedException {
        if(isLoggedIn) bookList.add(book);
        else throw new OperationNotAllowedException("Administrator login required");
    }

    public ArrayList<Book> getBooks(){
        return bookList;
    }

    public List<Book> search(String searchText) {
        List<Book> tempList = new ArrayList<>();

        for(Book book : bookList){
            if(
                    book.getTitle().contains(searchText)
                    || book.getSignature().contains(searchText)
                    || book.getAuthor().contains(searchText)
            ) tempList.add(book);
        }
        return tempList;

    }
}
