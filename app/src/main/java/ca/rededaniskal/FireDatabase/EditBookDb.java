package ca.rededaniskal.FireDatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.EntityClasses.Book_Instance;

public class EditBookDb {


    public EditBookDb() {


    }

    public boolean EditBookData(Book_Instance bookInstance) throws NullPointerException{
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("book-instances")
                .child(user)
                .child(bookInstance.getBookID());
        return  bookRef.setValue(bookInstance).isSuccessful();


    }


    public void DeleteBook(String node, String ISBN){
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference("book-instances")
                .child(user)
                .child(node);
        bookRef.removeValue();
        DatabaseReference masterRef = FirebaseDatabase.getInstance().getReference()
                .child("master-books")
                .child(ISBN)
                .child("instances")
                .child(user)
                .child(node);
                masterRef.setValue(null);



    }
}