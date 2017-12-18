package com.example.priya.updatecontactcp;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText mname,mphone;
    Button mupdatecontact,mview;
    TextView mdisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mname = (EditText)findViewById(R.id.name);
        mphone = (EditText)findViewById(R.id.phone);
        mupdatecontact = (Button) findViewById(R.id.updatecontact);
        mdisplay = (TextView)findViewById(R.id.dispaly);

        mupdatecontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // to get values from edittext

                String name = mname.getText().toString();
                String phone =mphone.getText().toString();

                //validating if fields are empty or not
                if(name.equals("")&&phone.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                updateContact(name,phone);
            }

        });

    }


// update contact method
    public void updateContact (String name, String phone){

        ContentResolver cr = getContentResolver();
        String where = ContactsContract.Data.DISPLAY_NAME+" = ? ";
        String[] params = new String[] {name};

        // Creates a new array of ContentProviderOperation objects.

        ArrayList<ContentProviderOperation> ops =
                new ArrayList<ContentProviderOperation>();

        // Builds the operation and update it to the array of operations
        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where,params)

                // updates the specified Phone data row and sets phone number
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                // Builds the operation and adds it to the array of operations
                .build());

       //Applies the array of ContentProviderOperation objects in batch. The results are discarded.
        try {
            cr.applyBatch(ContactsContract.AUTHORITY, ops);
            Toast.makeText(getBaseContext(), "Contacts Updated sucessfully", Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }

        Toast.makeText(this,"Contact Updated"+name,Toast.LENGTH_SHORT).show();

    }
}
