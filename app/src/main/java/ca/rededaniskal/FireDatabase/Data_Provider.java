package ca.rededaniskal.FireDatabase;
//reference :  https://www.youtube.com/watch?v=zVjSnhJu9qw

/*author : Skye*/
//SCRATCH PAD FOR DATABASE-------> Not real, just figuring things out.


import android.content.Context;
import android.support.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;



import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;



public class Data_Provider {

    private Book_Instance book_instance;
    private Book_List book_list;


    HashMap<Integer, String> urls;



    /* Integer MASTER  = 0;
        Integer BOOKINSTANCE = 1;
        Integer AVAILABLE  = 2;
        Integer  REQUESTED = 3;
        Integer BORROWED  = 4;
        Integer ACCEPTED = 5;
        Integer USERBOOKS = 6;
    */
    Context context;

    //Constuctor for getting a Booklist


    public Data_Provider(Integer Switch) {


        this.book_list = new Book_List();


        Data_Map data_map = new Data_Map();
        urls = data_map.getUrls();
        String child="";
        switch (Switch) {
            case 0:
                child = urls.get(0);
                break;
            case 1:
                child = urls.get(1);
                break;
            case 2:
                child = urls.get(2);
                break;
            case 3:
                child = urls.get(3);
                break;
            case 4:
                child = urls.get(4);
                break;
            case 5:
                child = urls.get(5);
                break;
            case 6:
                child = urls.get(6);
                break;


            default:
                throw new IllegalArgumentException("Not valid pathway.");
        }
        String reference = child;
        try{
            startListening(reference);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    //Get the book_list from the DataSnapshot
    public void startListening(String reference)throws Exception{
        final Book_List loaded_book_list = new Book_List();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(reference);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                loaded_book_list.addBook(new Book_Instance("title", "author", "3333333333", "one", "many", "ok","A" ));
                setBook_list(loaded_book_list);
                getBook_list();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                new Exception(databaseError.getMessage());

            }
        });


    }
    public void setBook_list(Book_List book_list) {
        this.book_list = book_list;
    }



    //Constructor for getting a book instance


    public Data_Provider(Integer Switch, final String ItemID) {

        String userid = "666666"; //TODO: get real user id from mauth

        this.book_list = new Book_List();

        Data_Map data_map = new Data_Map();
        urls = data_map.getUrls();
        String child;
        switch (Switch) {
            case 0:
                child = urls.get(0);
                break;
            case 1:
                child = urls.get(1);
                break;
            case 2:
                child = urls.get(2);
                break;
            case 3:
                child = urls.get(3);
                break;
            case 4:
                child = urls.get(4);
                break;
            case 5:
                child = urls.get(5);
                break;
            case 6:
                child = urls.get(6) + "//" + userid;
                break;


            default:
                throw new IllegalArgumentException("Not valid pathway.");
        }
        String reference =child;

        startSingleListening(reference, ItemID);

    }


    //listening for a bookInstance

public void startSingleListening(String reference, final String ItemID){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(reference).child(ItemID);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(ItemID)){
                    Book_Instance book = dataSnapshot.getValue(Book_Instance.class);
                    book.setBookID(ItemID);
                    setBook_instance(book);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                throw databaseError.toException();


            }
        });


    }
    public Book_List getBook_list() {
        return this.book_list;
    }
    public void setBook_instance(Book_Instance book){
        this.book_instance = book;
    }





}


/*
    public Book_Instance getBook(String id){

            DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference().child(url);

           reference.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (dataSnapshot.hasChild(bookID)){
                       Book_Instance book = dataSnapshot.getValue(Book_Instance.class);
                       book.setBookID(bookID);
                       setBook_instance(book);


                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
                   throw databaseError.toException();


               }
           });

        }

        return this.book_instance;


    }





    public  boolean insert(Book_Instance book_instance) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference newRef = databaseReference.child(urls.get(2));
        String id = newRef.push().getKey();
        if (!newRef.child(id).setValue(book_instance).isSuccessful()){
            return false;
        };

        for (Integer i: urls.keySet()){
            newRef = databaseReference.child(urls.get(i));
            if (i%2 ==0 && i>2) {
                if (!newRef.child(id).setValue(book_instance).isSuccessful()){
                    return  false;
                }



                if (i%17 ==0){
                    if(!newRef.child(user).child(id).setValue(book_instance).isSuccessful()){
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public Integer deleteBookInstance(Integer url, String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        for (Integer i: urls.keySet()){
            if (i%2 ==0) {
                DatabaseReference newRef = databaseReference.child(urls.get(i));
                if (i%17 ==0){
                    newRef.child(user).child(id).removeValue();
                }
                else if (i%7 ==0){
                    //TODO
                }
                else{
                    newRef.child(id).removeValue();
                }

            }
            else{
                return null;
            }


        }

        return 1;

    }


    public boolean update(Book_Instance book_instance) {
        String id = book_instance.getBookID();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference newRef = databaseReference.child(urls.get(2));

        if (!newRef.child(id).setValue(book_instance).isSuccessful()){
            return false;
        };

        for (Integer i: urls.keySet()){
            newRef = databaseReference.child(urls.get(i));
            if (i%2 ==0 && i>2) {
                if (!newRef.child(id).setValue(book_instance).isSuccessful()){
                    return  false;
                }



                if (i%17 ==0){
                    newRef.child(user).child(id).setValue(book_instance);


                }


            }


        }






        return true;
    }


*/

