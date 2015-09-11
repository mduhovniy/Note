package info.duhovniy.maxim.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.LinkedList;

public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LinkedList<Note> myDataset = new LinkedList<>();
    private ImageButton addButton;
    private final int CODE = 222;
    public static final String MAIN_CODE = "main";
    public static final String POS = "position number in list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        if(myDataset.isEmpty()) {
            //mRecyclerView.
        }

        addButton = (ImageButton) findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Editor.class);
                startActivityForResult(intent, CODE);
            }
        });

        ((MyAdapter)mAdapter).setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, Editor.class);
                intent.putExtra(Editor.EDITOR_CODE, (Serializable) myDataset.get(position));
                intent.putExtra(POS, position);
                startActivityForResult(intent, CODE);
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public void onTouchEvent(RecyclerView recycler, MotionEvent event) {
                // Handle on touch events here
                if(event.getAction() == MotionEvent.ACTION_BUTTON_PRESS)
                    Toast.makeText(getBaseContext(), "sending", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recycler, MotionEvent event) {
                return false;
            }

        });


    }

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE && resultCode == RESULT_OK) {
            if(data.getIntExtra(POS, -1) == -1) {
                myDataset.add((Note) data.getSerializableExtra(MAIN_CODE));
            } else {
                myDataset.set(data.getIntExtra(POS, 0), (Note) data.getSerializableExtra(MAIN_CODE));
            }
            mAdapter.notifyDataSetChanged();
        }
    }
}
