/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View the details of a book if it is not your book
 * Send a request for a book if it is not your book
 * View the requests for this book if it is your book
 * Navigate to editing the book if it is your book
 *
 * ISSUES:
 * Needs to DB support
 *
 */
package ca.rededaniskal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.BorrowRequestAdapter;
import ca.rededaniskal.FireDatabase.requestsOnBookDB;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;
import ca.rededaniskal.FireDatabase.BookDetailsdb;

/**
 * This activity lets the user view a book's details.
 * If they own the book, it also lets them go to the edit book activity.
 */

//Author: Revan
public class Book_Details_Activity extends AppCompatActivity {

    TextView DisplayTitle;
    TextView DisplayAuthor;
    TextView DisplayISBN;
    TextView DisplayOwner;
    TextView DisplayStatus;
    TextView DisplayDescription;

    ImageView BookCover;

    Button GoToForum;
    Button Request_Cancel;
    Button Edit;

    RecyclerView viewRequests;
    private Book_Instance book;

    BorrowRequestAdapter requestAdapter;
    ArrayList<BorrowRequest> l;

    boolean isRequested; //Auxillary variable for keeping track of where we need to go

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__details_);

        //Set the attributes to their corresponding views
        DisplayTitle = (TextView) findViewById(R.id.DisplayTitle);
        DisplayAuthor = (TextView) findViewById(R.id.DisplayAuthor);
        DisplayISBN = (TextView) findViewById(R.id.DisplayISBN);
        DisplayOwner = (TextView) findViewById(R.id.DisplayOwner);
        DisplayStatus = (TextView) findViewById(R.id.DisplayStatus);
        DisplayDescription = (TextView) findViewById(R.id.editDescription);

        BookCover = (ImageView) findViewById(R.id.BookCover);

        GoToForum = (Button) findViewById(R.id.GoToForum); //TODO: GO TO ACTIVITy
        Request_Cancel = (Button) findViewById(R.id.request_cancel);
        Edit = findViewById(R.id.Edit);

        viewRequests = (RecyclerView) findViewById(R.id.viewRequests);


        //Get what was passed in and display it
        Intent intent = getIntent();
        book = (Book_Instance) intent.getSerializableExtra("book"); //Get the book

        DisplayTitle.setText(book.getTitle());
        DisplayAuthor.setText(book.getAuthor());
        DisplayISBN.setText(book.getISBN());
        DisplayOwner.setText(book.getOwner());
        DisplayStatus.setText(book.getStatus());
        //DisplayDescription.setText(book.get); TODO: Descriptions?

        //TODO: Make this the actual user
        String globalUser = FirebaseAuth.getInstance().getUid();

        //Set the visibility of Edit + cardView
        if (globalUser.equals(book.getOwner())) {
            Edit.setVisibility(View.VISIBLE); //SHOW the button
            Request_Cancel.setVisibility(View.INVISIBLE);
            viewRequests.setHasFixedSize(true);
            viewRequests.setLayoutManager(new LinearLayoutManager(this));

            //for Testing
//            BorrowRequest br = new BorrowRequest("revan", "revan", "123", "123");
//            BorrowRequest br2 = new BorrowRequest("revan", "revan", "123", "123");
//            BorrowRequest br3 = new BorrowRequest("revan", "revan", "123", "123");


            l = new ArrayList<>();
//            l.add(br);
//            l.add(br2);
//            l.add(br3);


            requestAdapter = new BorrowRequestAdapter(this, l);
            viewRequests.setAdapter(requestAdapter);
            requestAdapter.notifyDataSetChanged();
            requestsOnBookDB db = new requestsOnBookDB(this);
            if (db.getFailed()){returnToLogin();}

        }else{

            viewRequests.setVisibility(viewRequests.INVISIBLE);
        }
        BookDetailsdb db = new BookDetailsdb();

        db.bookInUserRequests(book.getBookID());

        //Set appropriate text for the button at the bottom
        if (book.getStatus() == "Requested" && isRequested) {
            Request_Cancel.setText(R.string.cancel_request);

        } else {
            Request_Cancel.setText(R.string.request_book);

        }

        //Set On-Click listeners

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Edit_Book_Instance_Activity.class);
                intent.putExtra("book", book);
                startActivity(intent);
                finish();
            }
        });

        //TODO: Make this go to forum
        /*
        GoToForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ForumActivity.class);
                intent.putExtra("activity","Edit");
                startActivity(intent);
            }
        });
        */

        final Book_Details_Activity thisone = this;

        //TODO: DB
        Request_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Case cancel book request
                if (isRequested){
                    Request_Cancel.setText(R.string.request_book);
                    isRequested = false;
                    //TODO: remove from database
                }else{
                    //Case Request book
                    Request_Cancel.setText(R.string.cancel_request);
//                    BorrowRequest request = new Request(globalUser.getUserName(), ,"borrow" );
                    isRequested = true;
                    requestsOnBookDB db = new requestsOnBookDB(book, thisone);
                    //TODO: add request to database
                }
            }
        });

    }

    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange2");
//            l.clear();
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    BorrowRequest request = snapshot.getValue(BorrowRequest.class);
                    l.add(request);

                }

                Log.d(TAG, "*********----->" + l);
                l.add(new BorrowRequest());
                requestAdapter.notifyDataSetChanged();

                Log.d(TAG, "*********----->length" + l.size());
//                    requestAdapter.notifyDataSetChanged();
            }

        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public void setTrue() {
        this.isRequested = true;

    }

    public String getBookISBN(){return book.getISBN();}

    public void listClear(){l.clear(); return;}

    public void append(BorrowRequest r){l.add(r);return;}

    public int getLSize(){return l.size();}

    public void notifyRequest(){requestAdapter.notifyDataSetChanged();return;}



    //---------ENCLOSED DATABASE CLASS-----------------------//

    private void returnToLogin() {
        startActivity(new Intent(this, Login_Activity.class));
    }

}



