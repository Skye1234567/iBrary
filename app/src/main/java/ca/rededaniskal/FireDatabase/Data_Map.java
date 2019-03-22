package ca.rededaniskal.FireDatabase;
/*author: Skye*/
//SCRATCH PAD FOR DATABASE-------> Not real, just figuring things out.

import java.util.HashMap;


public class Data_Map {

    private HashMap<Integer, String> urls;

    public Data_Map() {



        Integer MASTER  = 0;
        Integer BOOKINSTANCE = 1;
        Integer AVAILABLE  = 2;
        Integer  REQUESTED = 3;
        Integer BORROWED  = 4;
        Integer ACCEPTED = 5;
        Integer USERBOOKS = 6;


        this.urls = new HashMap<>();
        this.urls.put(MASTER, "master-books");

        this.urls.put(BOOKINSTANCE, "book-instances//all-books");
        this.urls.put(AVAILABLE, "book-status//a");
        this.urls.put(BORROWED, "book-status//b");
        this.urls.put(REQUESTED, "book-status//r");
        this.urls.put(ACCEPTED, "book-status//c");
        this.urls.put(USERBOOKS, "user//user-books");


    }


    public HashMap<Integer, String> getUrls() {
        return urls;
    }
}
