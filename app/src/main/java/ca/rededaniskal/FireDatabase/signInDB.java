package ca.rededaniskal.FireDatabase;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.Activities.Login_Activity;

import static android.content.ContentValues.TAG;

public class signInDB {

    // To read or write from the database, a database reference is needed
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private boolean success;
    private FirebaseUser newUser;
    private Login_Activity parent;

    public signInDB(Login_Activity par){
        parent = par;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        success = false;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void signInUser(String email, String password){

        Log.d(TAG, "*********-----> IN signInUser");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(parent, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(parent.getApplicationContext(), "Sign In Success", Toast.LENGTH_SHORT).show();
                            parent.nextActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                updateUI(null);
                            success = false;
                            Toast.makeText(parent.getApplicationContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                        }
                        Log.d(TAG, "*********----->COMPLETED");
                    }
                });

    }

    public FirebaseUser getNewUser() {
        return newUser;
    }


    private void setNewUser(FirebaseUser newUser) {
        this.newUser = newUser;
    }
}