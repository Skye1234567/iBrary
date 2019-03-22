package ca.rededaniskal.Database;
/*author Skye*/
//Interacts with the Firebase when a user adds a book to ther library
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.FirebaseDatabase;

        import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Master_Book;

public class Add_Book_Db extends Book_Db {

    public static final String MASTER_BOOKS = "master-books";
    public static final String BOOK = "book";
    public static final String INSTANCES = "instances";
    public static final String BOOK_INSTANCES = "book-instances";

    public Add_Book_Db() {
        super();
        //Creates a new reference to the correct path in the Firebase
        //Book instances are stored under there unique id, under my-books,
        //under unique user Uid, under book-instances.

        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.bookRef = db.getReference().child(BOOK_INSTANCES)
                .child(user);

    }

    @Override
    public String addBook(Book_Instance bookInstance) throws NullPointerException{
        this.book_instance = bookInstance;


        success =bookRef.push().getKey();
        bookInstance.setBookID(success);
        Log.d(TAG, "***********---->" +bookInstance.getBookID());


        //Stores value
        //TODO: update master-book
        masterRef= FirebaseDatabase.getInstance().getReference().child(MASTER_BOOKS).child(bookInstance.getISBN());

        masterRef.addListenerForSingleValueEvent(masterListener);




        if (bookRef.child(success).setValue(bookInstance).isSuccessful()){
            return success;

        }
        else return null;





    }

    @Override
    public void updateMaster(){
        masterRef.child(INSTANCES).child(book_instance.getOwner()).child(book_instance.getBookID()).setValue(true);


    }

    @Override
    public void addMaster(){
        Master_Book mb = new Master_Book(book_instance.getTitle(), book_instance.getAuthor(), book_instance.getISBN());
        masterRef.child(BOOK).setValue(mb);
        masterRef.child(INSTANCES).child(book_instance.getOwner()).child(book_instance.getBookID()).setValue(true);


    }

}
