package com.hillman.testbooklibrary.utils;

import android.app.Activity;
import com.hillman.testbooklibrary.models.Book;
import java.util.List;

/**
 * Created by hllman on 20.01.18.
 */

public class BookManager {

    Activity context;

    public BookManager(Activity context){
        this.context = context;
    }

    public void addBooks(List<Book> books){
        RealmHelper.with(context).addBooks(books);
    }

    public List<Book> getBooks(){
        return RealmHelper.with(context).getBooks();
    }

    public void updateBook(Book book){
        RealmHelper.with(context).updateBook(book);
    }

    public void remooveBook(Book book){
        RealmHelper.with(context).removeBook(book);
    }

//    public List<Book> getBooksFromApi(){
//
//        return new ArrayList<>();
//    }

//    public void saveFromApiToRealm(){
//        RealmHelper.with(context).addBooks(getBooksFromApi());
//    }
}
