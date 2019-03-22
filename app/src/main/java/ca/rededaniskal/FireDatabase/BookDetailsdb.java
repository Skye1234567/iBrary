package ca.rededaniskal.FireDatabase;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.Activities.Book_Details_Activity;

public class BookDetailsdb{
    Book_Details_Activity parent;
    private boolean failed;

    public void BookDetailsdb(Book_Details_Activity bda){
        parent = bda;
    }



    public void bookInUserRequests(String bookid){

        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference requestBook = FirebaseDatabase.getInstance().getReference("book-instances")
                .child(user)
                .child("my-requests")
                .child(bookid);
        requestBook.addListenerForSingleValueEvent(requestedListener);
        failed = false;


    }

    public boolean getFailed(){return failed;}

    ValueEventListener requestedListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                parent.setTrue();

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            databaseError.toException();

        }
    };



}
