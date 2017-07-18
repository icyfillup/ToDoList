package com.sargent.mark.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sargent.mark.todolist.data.Contract;
import com.sargent.mark.todolist.data.ToDoItem;

import java.util.ArrayList;

/**
 * Created by mark on 7/4/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener clickListener;
    private String TAG = "todolistadapter";

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    //NOTE[Philip]: to make sure the category value is part of the the todo's update
    public interface ItemClickListener {
        void onItemClick(int pos, String description, String duedate, String categoryItem, long id);
    }

    public ToDoListAdapter(Cursor cursor, ItemClickListener clickListener) {
        this.cursor = cursor;
        this.clickListener = clickListener;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView descr;
        TextView due;
        TextView category;

        String duedate;
        String description;
        String categoryItem;

        long id;

        ItemHolder(View view) {
            super(view);
            descr = (TextView) view.findViewById(R.id.description);
            due = (TextView) view.findViewById(R.id.dueDate);
            //NOTE[Philip]: adding a Textview to show the what category the todos belongs to on the ui
            category = (TextView) view.findViewById(R.id.categoryItem);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));
            Log.d(TAG, "deleting id: " + id);

            duedate = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE));
            description = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION));
            //NOTE[Philip]: getting the category value from the db
            categoryItem = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY_ITEM));

            descr.setText(description);
            due.setText(duedate);
            //NOTE[Philip]: setting the category value onto the category textview ui
            category.setText(categoryItem);

            holder.itemView.setTag(id);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            clickListener.onItemClick(pos, description, duedate, categoryItem, id);
        }

        @Override
        public boolean onLongClick(View v) {
            //NOTE[Philip]: this method changes the background color of the itemView to
            //              green to indicate when todos are conpleted with a long click
            int pos = getAdapterPosition();
            this.itemView.setBackgroundColor(Color.GREEN);
            return true;
        }
    }

}
