package info.duhovniy.maxim.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class Editor extends Activity {

    public static final String EDITOR_CODE = "note";
    private EditText mHeader, mNoteText;
    private Button mSaveButton;
    private Note mNote;
    private int mPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mHeader = (EditText) findViewById(R.id.headerText);
        mNoteText = (EditText) findViewById(R.id.noteText);
        mSaveButton = (Button) findViewById(R.id.buttonSave);

        final Intent intent = getIntent();

        if (intent.getExtras() != null) {
            mPosition = intent.getIntExtra(MainActivity.POS, -1);
            mNote = (Note) intent.getSerializableExtra(EDITOR_CODE);
            if (mNote != null) {
                mHeader.setText(mNote.getHeader());
                mNoteText.setText(mNote.getBody());
            }
        } else {
            mNote = new Note();
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNote.setHeader(String.valueOf(mHeader.getText()));
                mNote.setBody(String.valueOf(mNoteText.getText()));
                intent.putExtra(MainActivity.MAIN_CODE, (Serializable) mNote);
                intent.putExtra(MainActivity.POS, mPosition);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
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

}
