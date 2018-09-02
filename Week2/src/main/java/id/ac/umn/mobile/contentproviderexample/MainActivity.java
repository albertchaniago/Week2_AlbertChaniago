package id.ac.umn.mobile.contentproviderexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button readContactActivity = (Button) findViewById(R.id.read_contacts_button);
        readContactActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ReadContactsActivity.class));
            }
        });

        Button createContactActivity = (Button) findViewById(R.id.create_contact_button);
        createContactActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateContactActivity.class));
            }
        });

    }
}