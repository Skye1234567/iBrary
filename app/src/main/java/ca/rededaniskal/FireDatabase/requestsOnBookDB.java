package ca.rededaniskal.FireDatabase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.User;

import static android.content.ContentValues.TAG;

public class requestsOnBookDB {
    private String email;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String username;
    private DatabaseReference mDatabase;
    private boolean submit;
    private Book_Instance boook;
    private boolean failed;
    private Book_Details_Activity parent;



    public requestsOnBookDB(Book_Details_Activity p) {
        parent = p;
        submit = false;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            getUsername();
            failed = false;

        } else {
            failed = true;
        }
    }


    public requestsOnBookDB(Book_Instance book, Book_Details_Activity p){
        parent = p;
        submit = true;
        this.boook = book;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            email = user.getEmail();
            getUsername();
            failed = false;

        } else {
            failed = true;
        }

    }

    public boolean getFailed(){return failed;}

    private void createRequest(){
//            BorrowRequest requ = new BorrowRequest(username, book., book.getISBN(), book.getBookID());
    }

    private void submitRequest(BorrowRequest request){
        mDatabase = FirebaseDatabase.getInstance().getReference("BorrowRequests");
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(request);
    }

    private void getUsername() {
        Log.d(TAG, "*********----->/Email: "+email);
        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(valueEventListener1);
    }


    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange1");
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User u = snapshot.getValue(User.class);
                    Log.d(TAG, "*********----->username: "+u.getUserName());
                    username = u.getUserName();
                }
                if (submit){
                    createRequest();
                }else {
                    getRequests();
                }
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void getOwnerUserName(String uid){
    }


    private void getRequests() {

        Query query2 = FirebaseDatabase.getInstance().getReference("BorrowRequests")
                .orderByChild("recipientUserName").equalTo(username);

        query2.addListenerForSingleValueEvent(valueEventListener3);
    }

    ValueEventListener valueEventListener3 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange3");
            parent.listClear();
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BorrowRequest request = snapshot.getValue(BorrowRequest.class);
                    if (request.getIsbn().equals(parent.getBookISBN())) {
                        Log.d(TAG, "*********----->Books " + request.getIsbn());
                        parent.append(request);
                    }
                }
                parent.append(new BorrowRequest());
                parent.notifyRequest();

                Log.d(TAG, "*********----->length" + parent.getLSize());
//                    requestAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}