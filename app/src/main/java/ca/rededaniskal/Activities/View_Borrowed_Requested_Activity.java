/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View all your requested and borrowed books
 *
 * ISSUES:
 * Needs DB support
 *
 */
package ca.rededaniskal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Switch;

import ca.rededaniskal.BusinessLogic.BookAdapter;

import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.R;
import ca.rededaniskal.FireDatabase.ReadBookDB;

//Author: Revan, Skye
public class View_Borrowed_Requested_Activity extends AppCompatActivity {

    private Book_List BL;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private Switch toggleRequested;
    private Switch toggleBorrowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__borrowed__requested_);

        recyclerView = (RecyclerView) findViewById(R.id.ViewBooks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter(this, BL);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();

        ReadBookDB db = new ReadBookDB(this);
        db.update();
    }


    public void updateBookView(Book_List book_list){
        bookAdapter = new BookAdapter(this, book_list);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
    }

    //***Enclosed Database helper class***
    }