package dtu.library.app;

public class Book {

    private String title;
    private String author;
    private String signature;



    public Book(String title, String author, String signature){
        this.title = title;
        this.author = author;
        this.signature = signature;
    }



    //----Getters----//

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getSignature() {
        return signature;
    }
}
