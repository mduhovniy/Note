package info.duhovniy.maxim.note;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Cursor mNotes;
    /*****
     * Creating OnItemClickListener
     *****/

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView headerText, noteText;
        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.headerText = (TextView) itemView.findViewById(R.id.note_header);
            this.noteText = (TextView) itemView.findViewById(R.id.note_text);
            this.messageButton = (Button) itemView.findViewById(R.id.message_button);

            // Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }

    public MyAdapter(Cursor notes) {
        mNotes = notes;
    }

    public void setNotes(Cursor notes) {
        mNotes = notes;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout

        // Return a new holder instance

        return new ViewHolder(inflater.inflate(R.layout.row, parent, false));
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        mNotes.moveToPosition(position);
        Note note = new Note(mNotes.getString(1), mNotes.getString(2), mNotes.getString(3));

        // Set item views based on the data model
        viewHolder.headerText.setText(note.getHeader());
        viewHolder.noteText.setText(note.getBody());

        Button button = viewHolder.messageButton;

        if (note.getmEmail() != null) {
            button.setText(R.string.sendText);
            button.setEnabled(true);
        } else {
            button.setText(R.string.offlineText);
            button.setEnabled(false);
        }

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mNotes.getCount();
    }
}



