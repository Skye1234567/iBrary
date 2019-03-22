/* TYPE:
 * Activity
 *
 * PURPOSE:
 * Signup for app
 *
 * ISSUES:
 *
 */
package ca.rededaniskal.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;

import ca.rededaniskal.FireDatabase.SignUpDB;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;
import ca.rededaniskal.BusinessLogic.SignUpLogic;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class Signup_Activity extends AppCompatActivity {

    private EditText usernameText;
    private EditText passwordText;
    private EditText confirmText;
    private EditText emailText;
    private EditText phoneText;
    private SignUpLogic businessLogic;
    private User user;

    private FusedLocationProviderClient client;
    TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button button = findViewById(R.id.button_signup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: DB add user to database
                getInfo();
                validateFields();
                finalPass();
            }
        });

        location = findViewById(R.id.location);

        client = LocationServices.getFusedLocationProviderClient(this);

        requestPermission();

        //Set onClick Listeners
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the user location and set the relevent fields

                if (ActivityCompat.checkSelfPermission(Signup_Activity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(Signup_Activity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location2) {
                        if(location2!=null) {
                            //newLocation.setText(location.getLatitude() + " " + location.getLongitude());
                            location.setText(getAddressName(location2.getLatitude(), location2.getLongitude()));
                        }
                    }
                });

            }
        });
    }


    private String getAddressName(double lat, double lon) {
        String address = "";
        Geocoder geocoder = new Geocoder(Signup_Activity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            address = addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[] {ACCESS_FINE_LOCATION}, 1);
    }

    //Make sure the fields are valid before signup
    public void validateFields(){
        String error = businessLogic.validdatePhone();
        if(!error.equals("")){
            phoneText.setError(error);
        }
        String error1 = businessLogic.validateEmail();
        if(!error1.equals("")){
            emailText.setError(error1);
        }
        String error2 = businessLogic.validatePassword();
        if(!error2.equals("")){
            passwordText.setError(error2);
        }
        String error3 = businessLogic.validateConfirm();
        if(!error3.equals("")){
            confirmText.setError(error3);
        }
        String error4 = businessLogic.validateUsername();
        if(!error4.equals("")){
            usernameText.setError(error4);
        }
    }


    public void getInfo(){
        usernameText = findViewById(R.id.input_username);
        passwordText = findViewById(R.id.input_password);
        confirmText = findViewById(R.id.input_confirm_password);
        emailText = findViewById(R.id.input_email);
        phoneText = findViewById(R.id.input_phone);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String confirm = confirmText.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();

        user = new User(username, email, phone, "");
        businessLogic = new SignUpLogic(username, password, confirm, email, phone);

    }


    public void finalPass(){
        if (businessLogic.isValid()){
            SignUpDB db = new SignUpDB(this);
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();

            db.createUser(email, password);
            }
    }


    public void nextActivity(){
        startActivity(new Intent(Signup_Activity.this,Main_Activity.class));
    }

    public String getUserEmail(){return user.getEmail();}

    public User getUser() {
        return user;
    }

    //--------- SIGNUPDB ENCLOSED CLASS ------------ //


}
