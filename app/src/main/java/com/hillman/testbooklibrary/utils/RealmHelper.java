package com.hillman.testbooklibrary.utils;

/**
 * Created by hllman on 20.01.18.
 */

import android.app.Activity;
import android.app.Application;
import com.hillman.testbooklibrary.models.Book;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;


public class RealmHelper {

    private static RealmHelper instance;
    private final Realm realm;

    public RealmHelper(Application application) {
        Realm.init(application);
        realm = Realm.getDefaultInstance();
    }


    public static RealmHelper with(Activity activity) {

        if (instance == null) {
            instance = new RealmHelper(activity.getApplication());
        }

        return instance;
    }


    public static RealmHelper getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }


    public void refresh() {
        realm.refresh();
    }

    public void addBooks(List<Book> books){
        realm.beginTransaction();
        realm.copyToRealm(books);
        realm.commitTransaction();
    }

    public void removeBook(Book book){

        RealmResults<Book> query = realm.where(Book.class)
                .contains("title", book.getTitle())
                .or().contains("author", book.getTitle())
                .findAll();

        realm.beginTransaction();
        query.deleteAllFromRealm();
        realm.commitTransaction();

    }

    public void updateBook(Book book){
        realm.beginTransaction();
        Book book1 = book;
        realm.copyToRealmOrUpdate(book1);
        realm.commitTransaction();
    }

    public void clearAll() {
        realm.beginTransaction();
        realm.delete(Book.class);
        realm.commitTransaction();
    }


    public RealmResults<Book> getBooks() {
        return realm.where(Book.class).findAll();
    }


    public Book getBook(String id) {
        return realm.where(Book.class)
                .equalTo("id", id).findFirst();
    }

    public RealmResults<Book> queryedBooks() {
        return realm.where(Book.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }
}

