package info.duhovniy.maxim.note;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Editor extends Activity {

    public static final String EDITOR_CODE = "note";
    private EditText mHeader, mNoteText, mEmail;
    private Button mSaveButton, mBackButton, mDelButton;
    private Note mNote = new Note();
    private int mPosition = -1;
    private DBHandler mHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mHeader = (EditText) findViewById(R.id.headerText);
        mNoteText = (EditText) findViewById(R.id.noteText);
        mEmail = (EditText) findViewById(R.id.emailText);
        mSaveButton = (Button) findViewById(R.id.buttonSave);
        mBackButton = (Button) findViewById(R.id.buttonBack);
        mDelButton = (Button) findViewById(R.id.buttonDel);

        final Intent intent = getIntent();

        if (intent.getExtras() != null) {
            mPosition = intent.getIntExtra(MainActivity.POS, -1);
            Cursor c = mHandler.listAllNotes();

            if (c.moveToPosition(mPosition)) {
                mHeader.setText(c.getString(1));
                mNoteText.setText(c.getString(2));
                mEmail.setText(c.getString(3));
            }

            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNote.setHeader(String.valueOf(mHeader.getText()));
                    mNote.setBody(String.valueOf(mNoteText.getText()));
                    mNote.setmEmail(String.valueOf(mEmail.getText()));

                    if (mPosition != -1) {
                        Cursor c = mHandler.listAllNotes();
                        c.moveToPosition(mPosition);
                        mHandler.replaceNote(c.getInt(0), mNote);
                    } else {
                        mHandler.addNote(mNote);
                    }

                    intent.putExtra(MainActivity.POS, mPosition);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

            mBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            mDelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
/*
                mNote.setHeader(String.valueOf(mHeader.getText()));
                mNote.setBody(String.valueOf(mNoteText.getText()));
                mNote.setmEmail(String.valueOf(mEmail.getText()));
*/
                    if (mPosition != -1) {
                        Cursor c = mHandler.listAllNotes();
                        c.moveToPosition(mPosition);
                        mHandler.delNote(c.getInt(0));
                    }

                    intent.putExtra(MainActivity.POS, mPosition);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        }

    }
}

