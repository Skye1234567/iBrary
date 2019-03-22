package ca.rededaniskal.FireDatabase;

/*author: Skye*/

//SCRATCH PAD FOR DATABASE-------> Not real, just figuring things out.

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;

public class Get_All_Books_Data {
    boolean continuous ;
    Book_List all_books_data;

    public Get_All_Books_Data() {
        continuous =true;
        all_books_data = new Book_List();
        synchronizeBooks();


    }

    public void synchronizeBooks(){
        final Book_List loaded_books = new Book_List();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("book-instances//all-books");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    loaded_books.addBook(d.getValue(Book_Instance.class));
                }


               setAll_books_data(loaded_books);
                loadSampleData(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }

    public Book_List getAll_books_data() {
        return all_books_data;
    }

    public void setAll_books_data(Book_List all_books_data) {

        this.all_books_data = all_books_data;
    }

    private void loadSampleData(boolean continuous) {
        while(continuous) {
            synchronizeBooks();
        }
    }

    public void quit(){
        continuous=false;
    }


}

