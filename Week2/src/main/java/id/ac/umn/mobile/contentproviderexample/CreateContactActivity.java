package id.ac.umn.mobile.contentproviderexample;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        final EditText namaLengkapEdit = (EditText) findViewById(R.id.nama_lengkap_edit);
        final EditText telponRumahEdit = (EditText) findViewById(R.id.telpon_rumah_edit);
        final EditText handphoneEdit = (EditText) findViewById(R.id.handphone_edit);

        Button createBtn = (Button) findViewById(R.id.create_btn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaLengkap = namaLengkapEdit.getText().toString();
                String telponRumah = telponRumahEdit.getText().toString();
                String handphone = handphoneEdit.getText().toString();

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, namaLengkap);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, telponRumah);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);

                intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, handphone);
                intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                startActivity(intent);
                finish();

            }
        });
    }
}
