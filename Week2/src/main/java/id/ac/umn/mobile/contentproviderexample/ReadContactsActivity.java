package id.ac.umn.mobile.contentproviderexample;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ReadContactsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_contacts);

        updateList("");

        final EditText searchEdit = (EditText) findViewById(R.id.search_edit);
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateList(searchEdit.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        };

        Cursor profiles = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        ListView profilesList = (ListView) findViewById(R.id.profiles_list);
        profilesList.setAdapter(new SimpleCursorAdapter(
                ReadContactsActivity.this,
                android.R.layout.simple_list_item_1,
                profiles,
                new String[]{
                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
                },
                new int[]{
                        android.R.id.text1
                },
                0
        ));

    }

    private void updateList(String search){
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        };

        String selection = null;
        String[] selectionArgs = null;
        if(!search.equals("")){
            selection = String.format("UPPER(%s) LIKE ?",
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
            selectionArgs = new String[] {String.format("%%%s%%",search.toUpperCase())};
        }

        final Cursor profiles = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );

        ListView profileList = (ListView) findViewById(R.id.profiles_list);
        profileList.setAdapter(new SimpleCursorAdapter(
                ReadContactsActivity.this,
                android.R.layout.simple_list_item_1,
                profiles,
                new String[]{
                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
                },
                new int[]{
                        android.R.id.text1
                },
                0
        ));

        profileList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                profiles.moveToPosition(position);
                Intent intent = new Intent(ReadContactsActivity.this, UpdateContactActivity.class);
                intent.putExtra("_ID", profiles.getString(
                        profiles.getColumnIndex(ContactsContract.Contacts._ID)
                ));
                startActivity(intent);
                return false;

            }
        });

    }
}
