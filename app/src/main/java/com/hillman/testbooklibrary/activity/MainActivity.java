package com.hillman.testbooklibrary.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hillman.testbooklibrary.R;
import com.hillman.testbooklibrary.adapters.BooksAdapter;
import com.hillman.testbooklibrary.models.Book;
import com.hillman.testbooklibrary.utils.BookManager;
import com.hillman.testbooklibrary.utils.RealmHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BooksAdapter adapter;
    RecyclerView recyclerView;
    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BookManager manager = new BookManager(this);

        books = new ArrayList();
        recyclerView = findViewById(R.id.recycler);

        adapter = new BooksAdapter(manager.getBooks(), this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(v -> {
              LayoutInflater inflater = MainActivity.this.getLayoutInflater();
              View content = inflater.inflate(R.layout.edit_item, null);
              final EditText editTitle = content.findViewById(R.id.title);
              final EditText editAuthor = content.findViewById(R.id.author);
              final EditText editDescription = content.findViewById(R.id.desc);

              AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
              builder.setView(content)
                      .setTitle("Add book")
                      .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                          Book book = new Book();
                          book.setId((int) (manager.getBooks().size() + System.currentTimeMillis()));
                          book.setTitle(editTitle.getText().toString());
                          book.setAuthor(editAuthor.getText().toString());
                          book.setDescription(editDescription.getText().toString());

                          if (editTitle.getText() == null || editTitle.getText().toString().equals("") || editTitle.getText().toString().equals(" ")) {
                              Toast.makeText(MainActivity.this, "Entry not saved, missing title", Toast.LENGTH_SHORT).show();
                          } else {
                             manager.updateBook(book);
                             adapter.notifyDataSetChanged();
                             recyclerView.scrollToPosition(manager.getBooks().size() -1);
                          }})
                      .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());

              AlertDialog dialog = builder.create();
              dialog.show();
          });

    }
}
