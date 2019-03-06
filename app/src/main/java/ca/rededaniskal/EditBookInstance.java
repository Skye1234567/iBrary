package ca.rededaniskal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditBookInstance extends AppCompatActivity {
    BookInstance bookInstance;
    FirebaseDatabase database;
    DatabaseReference bookRef;
    Button saveChanges;
    EditText Title, Author, ISBN, Owner, Status, Description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_instance);
        database = FirebaseDatabase.getInstance();
        bookRef = database.getReference().child("bookInstance");

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });








    }
}
