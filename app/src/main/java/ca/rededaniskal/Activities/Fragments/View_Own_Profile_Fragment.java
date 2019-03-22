/* TYPE:
 * Fragment
 *
 * PURPOSE:
 * View your own info
 * Navigate to editing your profile, viewing library, seeing borrowed and requested books and friends
 *
 * ISSUES:
 *
 */
package ca.rededaniskal.Activities.Fragments;
import ca.rededaniskal.FireDatabase.currentUserDetailsDB;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import ca.rededaniskal.Activities.Edit_Profile_Activity;
import ca.rededaniskal.Activities.Login_Activity;
import ca.rededaniskal.Activities.View_All_Books_Activity;
import ca.rededaniskal.Activities.View_All_Requests_Activity;
import ca.rededaniskal.Activities.View_All_Users_Activity;
import ca.rededaniskal.Activities.View_Borrowed_Requested_Activity;
import ca.rededaniskal.Activities.View_Friends_Activity;
import ca.rededaniskal.Activities.View_My_Library_Activity;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;

/**
 * This fragment is for viewing one's own profile.
 * It also acts as a portal to many other activities, including: logout, edit profile,
 * friends list, view requested/borrowed books, view all book requests, my library, view all
 * users, view all books.
 *
 * Todo for part 5
 * Remove the "view all books" and "view all users" buttons when search gets implemented
 * add user profile picture from database
 * make the buttons fit on the screen better
 *
 * What we have so far:
 * all of the buttons lead to the appropriate activity.
 */


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link View_Own_Profile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class View_Own_Profile_Fragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private currentUserDetailsDB db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v;

//    private OnFragmentInteractionListener mListener;

    public View_Own_Profile_Fragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment View_Own_Profile_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static View_Own_Profile_Fragment newInstance(String param1, String param2) {
        View_Own_Profile_Fragment fragment = new View_Own_Profile_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "*********----->onCreateView");


        v = inflater.inflate(R.layout.fragment_personal_profile, container, false);
        db = new currentUserDetailsDB(this);
        if (db.getFailed()){
            returnToLogin();
        }

        Button editButton = v.findViewById(R.id.edit_user);
        Button viewLibrary = (Button) v.findViewById(R.id.my_library);
        Button viewBorrowedRequested = (Button) v.findViewById(R.id.borrowed_requested_books);
        Button viewFriends = (Button) v.findViewById(R.id.friends_listbutton);
        Button logout = (Button) v.findViewById(R.id.logout);
        Button viewAllRequests = (Button) v.findViewById(R.id.view_all_requests);
        Button viewAllUsers = (Button) v.findViewById(R.id.viewUsers);
        Button viewAllBooks = (Button) v.findViewById(R.id.viewBooks);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_Profile_Activity.class);
                startActivity(intent);
            }
        });

        viewLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_My_Library_Activity.class);
                startActivity(intent);
            }
        });

        viewBorrowedRequested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_Borrowed_Requested_Activity.class);
                startActivity(intent);
            }
        });

        viewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_Friends_Activity.class);
                startActivity(intent);
            }
        });

        viewAllRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_All_Requests_Activity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login_Activity.class);
                startActivity(intent);
            }
        });

        viewAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_All_Users_Activity.class);
                startActivity(intent);
            }
        });

        viewAllBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_All_Books_Activity.class);
                startActivity(intent);
            }
        });

        return v;
    }


    public void setTexts(String email, String phone, String location, String username){
        Log.d(TAG, "*********----->setTexts");

        TextView e = v.findViewById(R.id.DisplayEmail);
        TextView p = v.findViewById(R.id.DisplayPhoneNumber);
        TextView l = v.findViewById(R.id.DisplayLocation);
        TextView u = v.findViewById(R.id.DisplayUserName);

        e.setText(email);
        p.setText(phone);
        l.setText(location);
        u.setText(username);
    }

    private void returnToLogin() {
        startActivity(new Intent(getActivity(), Login_Activity.class));
    }

}
