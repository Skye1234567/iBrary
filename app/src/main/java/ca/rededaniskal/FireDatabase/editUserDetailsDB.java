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

import ca.rededaniskal.Activities.Edit_Profile_Activity;
import ca.rededaniskal.EntityClasses.User;

import static android.content.ContentValues.TAG;

public class editUserDetailsDB{
    private FirebaseAuth mAuth;
    private String email;
    private String username;
    private String location;
    private String phone;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private List<User> userList;
    private String key;
    private boolean failed;
    private Edit_Profile_Activity parent;


    public editUserDetailsDB(Edit_Profile_Activity par) {
        parent = par;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userList = new ArrayList<>();
        if (user != null) {
            email = user.getEmail();
            getUserDetails();
            failed = false;

        } else {
            failed = true;

        }
    }

    public boolean getFailed(){return failed;}


    public void saveNewDetails(User user){
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(key).setValue(user);

    }


    private void getUserDetails(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("email")
                .equalTo(email);

        Log.d(TAG, "*********----->"+email);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            userList.clear();
            Log.d(TAG, "*********----->onDataChange");
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Log.d(TAG, "*********----->"+snapshot.getValue());
                    key = snapshot.getKey();
                    Log.d(TAG, "*********----->ID: "+key);
                    User user = snapshot.getValue(User.class);

                    parent.setTexts(user.getEmail(), user.getPhoneNumber(), user.getLocation(), user.getUserName());
                }
            }
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };
}
