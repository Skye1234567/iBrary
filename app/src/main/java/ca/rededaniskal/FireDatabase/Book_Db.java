package ca.rededaniskal.FireDatabase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.EntityClasses.Book_Instance;

abstract class Book_Db {
    public final String TAG = "Add_Book_Db";
    FirebaseDatabase db;
    DatabaseReference bookRef;
    DatabaseReference masterRef;
    String success;
    Book_Instance book_instance;
    ValueEventListener masterListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                updateMaster();
            }
            else{
                addMaster();
            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    Book_Db() {
        this.db = FirebaseDatabase.getInstance();
    }

    public abstract String addBook(Book_Instance bookInstance) throws NullPointerException;

    public abstract void updateMaster();

    public abstract void addMaster();
}
