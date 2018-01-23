package com.hillman.testbooklibrary.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hillman.testbooklibrary.R;
import com.hillman.testbooklibrary.models.Book;
import com.hillman.testbooklibrary.utils.BookManager;
import com.hillman.testbooklibrary.utils.RealmHelper;

import java.util.List;

import io.realm.Realm;

/**
 * Created by hllman on 21.01.18.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    List<Book> books;
    BookManager manager;
    Activity context;
    boolean isLongClick = false;


    public BooksAdapter(List<Book> books, Activity context) {
        this.context = context;
        this.books=books;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_book, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BooksAdapter.ViewHolder holder, final int position) {
        final Book book = books.get(position);
        holder.title.setText(book.getTitle());
        holder.desc.setText(book.getDescription());
        holder.author.setText(book.getAuthor());

        holder.deleteButton.setOnClickListener(view -> {
            manager.remooveBook(book);
            notifyDataSetChanged();
            holder.deleteButton.setVisibility(View.GONE);
        });

        holder.card.setOnLongClickListener(view -> {
            holder.deleteButton.setVisibility(View.VISIBLE);
            isLongClick = true;
            return false;
        });

        holder.card.setOnClickListener((View view) -> {
            if (!isLongClick){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View content = inflater.inflate(R.layout.edit_item, null);
            final EditText editTitle =  content.findViewById(R.id.title);
            final EditText editAuthor =  content.findViewById(R.id.author);
            final EditText editDescription =  content.findViewById(R.id.desc);

            editTitle.setText(book.getTitle());
            editAuthor.setText(book.getAuthor());
            editDescription.setText(book.getDescription());

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(content)
                    .setTitle("Edit Book")
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                       Realm realm = RealmHelper.with(context).getRealm();
                       realm.beginTransaction();
                       book.setTitle(editTitle.getText().toString());
                       book.setAuthor(editAuthor.getText().toString());
                       book.setDescription(editDescription.getText().toString());
                       realm.copyToRealmOrUpdate(book);
                       realm.commitTransaction();
                       BooksAdapter.this.notifyDataSetChanged();
                    })
                    .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();

        }
         isLongClick = false;
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, desc, author;
        private ImageView deleteButton;
        private CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_books_title);
            desc = itemView.findViewById(R.id.text_books_description);
            author = itemView.findViewById(R.id.text_books_author);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            card = itemView.findViewById(R.id.card_books);
            manager = new BookManager(context);
        }
    }
}
