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

import java.util.ArrayList;
import java.util.List;

import ca.rededaniskal.EntityClasses.Request;
import ca.rededaniskal.EntityClasses.User;

import static android.content.ContentValues.TAG;

public class getUserRequestsDB{
    private FirebaseAuth mAuth;
    private String email;
    private String username;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private List<Request> requestList;
    private Boolean failed;


    //This function retrieves the users requests from the database.

    public getUserRequestsDB() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        requestList = new ArrayList<Request>();
        if (user != null) {
            email = user.getEmail();
            getUserDetails();
            getUserRequestSender();
            getUserRequestRecipent();
            failed = false;


        } else {
            failed = true;

        }
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    //This function gets the user's details

    private void getUserDetails(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("email")
                .equalTo(email);

        Log.d(TAG, "*********----->"+email);
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    //This function updates the notifications if the database changes.

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange");
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Log.d(TAG, "*********----->"+snapshot.getValue());
                    User user = snapshot.getValue(User.class);
                    username = user.getUserName();
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    //Returns who sent the request

    private void getUserRequestSender(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        Query query = FirebaseDatabase.getInstance().getReference("Requests")
                .orderByChild("senderUserName")
                .equalTo(username);

        Log.d(TAG, "*********----->"+username);
        query.addListenerForSingleValueEvent(valueEventListener1);

    }

    //Keeps track of when users get updated in database.

    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange1");
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Request request = snapshot.getValue(Request.class);
                    requestList.add(request);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    //Gets the recipient of the request

    private void getUserRequestRecipent(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Requests");
        Query query = FirebaseDatabase.getInstance().getReference("Requests")
                .orderByChild("recipientUserName")
                .equalTo(username);

        Log.d(TAG, "*********----->"+username);
        query.addListenerForSingleValueEvent(valueEventListener2);

    }

    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange2");
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Request request = snapshot.getValue(Request.class);
                    requestList.add(request);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}