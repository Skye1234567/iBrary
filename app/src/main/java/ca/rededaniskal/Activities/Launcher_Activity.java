/* TYPE:
 * Activity
 *
 * PURPOSE:
 * Used to test stuff, not a part of the app itself
 *
 * ISSUES:
 *
 */
package ca.rededaniskal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.rededaniskal.Barcode.Barcode_Scanner_Activity;
import ca.rededaniskal.R;

public class Launcher_Activity extends AppCompatActivity{

    private Button buttonScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.go_home_button);
        buttonScanner = findViewById(R.id.go_to_scanner_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome(v);
            }
        });


    }

    private void goToHome(View v){

//        Intent intent = new Intent(v.getContext(), Book_Details_Activity.class);
//        startActivity(intent);
//        this.finish();

        Intent intent = new Intent(v.getContext(), Login_Activity.class);
        startActivity(intent);
        this.finish();

    }
    private void goToScanner(View v){
        Intent intent = new Intent(v.getContext(), Barcode_Scanner_Activity.class);
        startActivity(intent);
        this.finish();
    }
}
