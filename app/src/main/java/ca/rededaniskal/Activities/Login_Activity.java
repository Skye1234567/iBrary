/* TYPE:
 * Activity
 *
 * PURPOSE:
 * Log in to the app
 *
 * ISSUES:
 *
 */
package ca.rededaniskal.Activities;

import ca.rededaniskal.FireDatabase.signInDB;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import ca.rededaniskal.BusinessLogic.Log_In_Logic;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;

/**
 * This lets the user enter their login information and log in, or lets them go to the register
 * activity.
 */

public class Login_Activity extends AppCompatActivity {
    private Button loginButton;
    private Button RegisterButton;
    private EditText email;
    private EditText password;
    private Log_In_Logic logic;
    private signInDB db;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        //Set the buttons and Edit Texts
        loginButton = (Button) findViewById(R.id.button);
        RegisterButton = (Button) findViewById(R.id.button2);
        email  = (EditText) findViewById(R.id.editText5);
        password = (EditText)findViewById(R.id.editText6);
        logo = (ImageView) findViewById(R.id.Logo);

        logo.setImageResource(R.mipmap.ic_launcher);

        //Set on click listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
                finalPass();
            }
        });

        //Set on click listeners
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Signup_Activity.class);
                startActivity(intent);
            }
        });

    }


    private void validateFields(){
        String em = email.getText().toString();
        String pass = password.getText().toString();
        logic = new Log_In_Logic(em, pass);

        String error = logic.validatePassword();
        if(!error.equals("")){
            password.setError(error);
        }

        String error1 = logic.validateEmail();
        if(!error1.equals("")){
            email.setError(error1);
        }
    }


    public void finalPass() {
        if (logic.isValid()) {
            db = new signInDB(this);
            String em = email.getText().toString();
            String pass = password.getText().toString();
            Log.d(TAG, "*********----->"+pass);
            db.signInUser(em, pass);
        }
    }


    public void nextActivity(){

        startActivity(new Intent(Login_Activity.this,Main_Activity.class));
    }


//    Enclosed database class

}
