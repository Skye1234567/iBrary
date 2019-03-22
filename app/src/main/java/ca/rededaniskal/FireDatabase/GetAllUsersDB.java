package ca.rededaniskal.FireDatabase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.Activities.View_All_Users_Activity;

import static android.content.ContentValues.TAG;

public class GetAllUsersDB {
    DatabaseReference mDatabase;
    View_All_Users_Activity parent;

    public GetAllUsersDB(View_All_Users_Activity p) {

        parent = p;
        getUserQuery();
    }

    private void getUserQuery(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            parent.Usersclear();
            Log.d(TAG, "*********----->onDataChange");
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Log.d(TAG, "*********----->"+snapshot.getValue());
                    User user = snapshot.getValue(User.class);
                    parent.Usersadd(user);
                }
                parent.notifyData();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}