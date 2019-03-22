package ca.rededaniskal.FireDatabase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.Activities.View_My_Library_Activity;

public class ReadMyBookDB {

    private DatabaseReference mdatabase;
    String TAG;
    View_My_Library_Activity parent;

    public ReadMyBookDB(View_My_Library_Activity p){
        update();
        parent = p;

    }


    public void update(){
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mdatabase = FirebaseDatabase.getInstance().getReference("book-instances").child(user);
        mdatabase.addListenerForSingleValueEvent(valueEventListener);
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.d(TAG, "**************---> In OnDataChange");
            if (dataSnapshot.exists()){
                Book_List book_list = new Book_List();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Book_Instance book_instance = snapshot.getValue(Book_Instance.class);
                    Log.d(TAG, "**************--->"+book_instance.getOwner());
                    book_list.addBook(book_instance);
                }
                parent.updateBookView(book_list);
            }
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
}