package info.duhovniy.maxim.note;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private DBHandler handler;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final int CODE = 222;
    public static final String POS = "position number in list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new DBHandler(MainActivity.this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        final View coordinatorLayoutView = findViewById(R.id.snackbarPosition);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        final Cursor cursor = handler.listAllNotes();

        mAdapter = new MyAdapter(cursor);
        mRecyclerView.setAdapter(mAdapter);
/*
        if(!cursor.moveToNext()) {
            ImageView i = new ImageView(this);
            i.setImageResource(R.drawable.ic_star_border_black_24dp);
            i.setAdjustViewBounds(true); // set the ImageView bounds to match the Drawable's dimensions
            i.setLayoutParams(new Gallery.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT));
            mRecyclerView.addView(i);
        }*/

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, Editor.class);
                cursor.moveToPosition(position);
                intent.putExtra(POS, position);
                startActivityForResult(intent, CODE);
            }

            @Override
            public void onItemLongClick(View view, final int position) {

                final Cursor c = handler.listAllNotes();
                c.moveToPosition(position);

                final View.OnClickListener clickListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        handler.delNote(c.getInt(0));
                        ((MyAdapter)mAdapter).setNotes(handler.listAllNotes());
                        mAdapter.notifyDataSetChanged();
                    }
                };

                Snackbar.make(coordinatorLayoutView, "Delete " + c.getString(1) + "?", Snackbar.LENGTH_LONG)
                        .setAction("Ok", clickListener).show();

            }
        }));

/*
        if(myDataset.isEmpty()) {
            mRecyclerView.
        }
*/

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.myFAB);

        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Editor.class);
//                intent.putExtra(HANDLER, (CharSequence) handler);
                startActivityForResult(intent, CODE);
            }
        });

/*        ((MyAdapter) mAdapter).setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, Editor.class);
                cursor.moveToPosition(position);
                intent.putExtra(POS, position);
                startActivityForResult(intent, CODE);
            }
        });*/


    }

    @Override
    public void onResume() {

        super.onResume();

        ((MyAdapter)mAdapter).setNotes(handler.listAllNotes());
        mAdapter.notifyDataSetChanged();


    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE && resultCode == RESULT_OK) {
            if (data.getIntExtra(POS, -1) == -1) {
                //               myDataset.add((Note) data.getSerializableExtra(MAIN_CODE));
            } else {
                //               myDataset.set(data.getIntExtra(POS, 0), (Note) data.getSerializableExtra(MAIN_CODE));
            }
        }

    }
}
