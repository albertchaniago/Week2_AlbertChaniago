package id.ac.umn.mobile.contentproviderexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        final String contactId = getIntent().getStringExtra("_ID");
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        };
        String selection = String.format("%s = ?", ContactsContract.Contacts._ID);
        String[] selectionArgs = new String[]{
                contactId
        };
        final Cursor contact = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
        final EditText namaLengkapEdit = (EditText) findViewById(R.id.nama_lengkap_edit);
        contact.moveToFirst();
        namaLengkapEdit.setText(contact.getString(contact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)));

        String[] phoneProjection = new String[]{
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE
        };

        String phoneSelection = String.format(
                "%s = ? AND %s = ?",
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        );

        final String[] homeSelectionArgs = new String[]{
                Integer.toString(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE),
                contactId
        };

        final Cursor homephones = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                phoneProjection,
                phoneSelection,
                homeSelectionArgs,
                null
        );

        final EditText telponRumahEdit = (EditText) findViewById(R.id.telpon_rumah_edit);
        if(homephones.getCount() >0 ){
            homephones.moveToFirst();
            telponRumahEdit.setText((homephones.getString(homephones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));
        }

        String[] mobileSelectionArgs = new String[]{
                Integer.toString(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE),
                contactId
        };

        Cursor mobilePhones = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                phoneProjection,
                phoneSelection,
                mobileSelectionArgs,
                null
        );
        final EditText handphoneEdit = (EditText) findViewById(R.id.handphone_edit);
        if(mobilePhones.getCount()>1){
            mobilePhones.moveToPosition(1);
            handphoneEdit.setText(mobilePhones.getString(mobilePhones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }

        Button updateBtn = (Button) findViewById(R.id.update_btn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaLengkap = namaLengkapEdit.getText().toString();
                String telponRumah = telponRumahEdit.getText().toString();
                String handphone = handphoneEdit.getText().toString();

                ContentValues contactValues = new ContentValues();
                contactValues.put(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY, namaLengkap);
                String contactWhere = String.format("%s = ?", ContactsContract.Contacts._ID);
                String[] contactWhereArgs = new String[]{
                        contactId
                };
                getContentResolver().update(
                        ContactsContract.Data.CONTENT_URI,
                        contactValues,
                        contactWhere,
                        contactWhereArgs
                );

                String phoneWhere = String.format(
                        "%s = ? AND %s = ? AND %s = ?",
                        ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                        ContactsContract.Data.MIMETYPE
                );
                ContentValues homePhoneValues = new ContentValues();
                homePhoneValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, telponRumah);
                String[] homePhoneWhereArgs = new String[]{
                        Integer.toString(ContactsContract.CommonDataKinds.Phone.TYPE_HOME),
                        contactId,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                };
                getContentResolver().update(
                        ContactsContract.Data.CONTENT_URI,
                        homePhoneValues,
                        phoneWhere,
                        homePhoneWhereArgs
                );

                ContentValues mobilePhoneValues = new ContentValues();
                mobilePhoneValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, handphone);
                String[] mobilePhoneWhereArgs = new String[]{
                        Integer.toString(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE),
                        contactId,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                };
                getContentResolver().update(
                        ContactsContract.Data.CONTENT_URI,
                        mobilePhoneValues,
                        phoneWhere,
                        mobilePhoneWhereArgs
                );



                finish();
            }
        });

    }
}
