/* TYPE:
 * Logic
 *
 * PURPOSE:
 * Logic relevent to adding a book
 *
 * ISSUES:
 */
package ca.rededaniskal.BusinessLogic;
/*author : Skye */
//NOT IMPLEMENTED FOR PROJECT PART 4: CODE FOR VALIDATING THE BOOK INSTANCE FIELDS

//TODO: validate book id, validate against master, validate 10-digit and 13-digit isbn

//import ca.rededaniskal.Database.Add_Book_Db;
import java.lang.String;


import ca.rededaniskal.Database.Add_Book_Db;
import ca.rededaniskal.Database.EditBookDb;
import ca.rededaniskal.EntityClasses.Book_Instance;

import static com.google.android.gms.common.util.ArrayUtils.contains;

public class AddBookLogic {

    private String title;
    private String author;
    private String ISBN;

    private String titleError;
    private String authorError;
    private String ISBNError;

    //Constructor
    public AddBookLogic(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    //Validate the title of the new book
    public void validateTitle() {
        if (this.title.isEmpty()) {
            titleError = "Required Field";
        }
        else {
            String[] words = this.title.split("/s");
            char[] let = words[0].toCharArray();
            let[0] = Character.toUpperCase(let[0]);
            words[0] = let.toString();

            for (int i = 1; i < words.length; i++) {
                String[] joins = {"the", "and", "or", "if", "a", "an", "at"};
                char[] letters = words[i].toCharArray();
                if (!contains(joins, words[i])) {
                    letters[0] = Character.toUpperCase(letters[0]);
                }
                words[i] = letters.toString();


            }
            title = String.join(" ", words);
        }


    }

    //Validate the author of the new book
    public void validateAuthor() {
        if (this.author.isEmpty()) {
            authorError = "Required Field";
        }

        else if (!this.author.equals("bell hooks")) {

            String[] words = this.author.split("/s");
            for (int i = 0; i < words.length; i++) {
                char[] letters = words[i].toCharArray();
                letters[0] = Character.toUpperCase(letters[0]);
                words[i] = letters.toString();

            }
            this.author = String.join(" ", words);
        }

    }

    //Validate the ISBN
    public void validateISBN() {

        int how_many=0;
        int sum;

        String[] nums = ISBN.split("-|/s");
        int even = 0;

        for (String n: nums){

            char[] num = n.toCharArray();
            how_many+=num.length;

            for (char digit: num){
                if (!Character.isDigit(digit)){

                    this.ISBNError =   "Illegal character '"+digit+"'.";
                }
            }
        }
        if (how_many!=13 && how_many!=10){
            this.ISBNError = "Not a valid ISBN";
        }


    }

    //Returns Whether the book is valid
    public String isValid() {
        //Currently raises no errors, businessLogic is always Valid
        //TODO: implement this when testing stages are done for basic functionality
        validateTitle();
        validateAuthor();
        validateISBN();

        return "";
    }



    public String saveInformation(Book_Instance book){
        Add_Book_Db db  = new Add_Book_Db();
        return db.addBook(book);

    }

    public void updateInformation(Book_Instance book_instance){
        EditBookDb db = new EditBookDb();
        db.EditBookData(book_instance);
    }

    public void delete(String bookId, String isbn){

        EditBookDb db = new EditBookDb();
        db.DeleteBook(bookId, isbn);


    }





}