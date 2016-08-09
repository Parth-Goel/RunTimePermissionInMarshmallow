package com.tt.png.permissionchecking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PermissionCheck extends AppCompatActivity {

    Button btn;
    Button btnMsg;
    EditText et;
    Intent callInt;
    Intent msgInt;
    private  static  final int CALL_PERMISSION_CODE=1;
    private  static  final int SMS_PERMISSION_CODE=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_check);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        callIntent();
        MsgIntent();

    }

    private void MsgIntent()
    {
        btnMsg=(Button)findViewById(R.id.button2);
        et=(EditText)findViewById(R.id.editText);
        btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                msgInt=new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + et.getText().toString()));


                if (et.getText().toString().equals("")||(et.getText().toString().length()!=10))
                    Toast.makeText(getApplicationContext(),"Please, Enter number/valid number",Toast.LENGTH_SHORT).show();
                else if(ContextCompat.checkSelfPermission(PermissionCheck.this,Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(PermissionCheck.this,new String[] {Manifest.permission.SEND_SMS},SMS_PERMISSION_CODE);
                else if(et.getText().length()==10)
                 //
                {
                    msgInt.putExtra("address", et.getText().toString());
                    msgInt.putExtra("sms_body","Hi..How are you?");
                    Toast.makeText(getApplicationContext(), "default", Toast.LENGTH_SHORT).show();
                    startActivity(msgInt);

                }

            }
        });

    }
   private void callIntent()
    {

        btn=(Button)findViewById(R.id.button);
        et=(EditText)findViewById(R.id.editText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callInt=new Intent(Intent.ACTION_CALL, Uri.parse("tel: +91"+et.getText().toString()));
                if (et.getText().toString().equals("")||(et.getText().toString().length()!=10))
                    Toast.makeText(getApplicationContext(),"Please, Enter number/valid number",Toast.LENGTH_SHORT).show();
                else if(ContextCompat.checkSelfPermission(PermissionCheck.this,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(PermissionCheck.this,new String[] {Manifest.permission.CALL_PHONE},CALL_PERMISSION_CODE);
                else
                startActivity(callInt);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==CALL_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getApplicationContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();
                startActivity(callInt);

            } else {

                Toast.makeText(getApplicationContext(), "Permission Denied..", Toast.LENGTH_SHORT).show();


            }
        }
                else if(requestCode==SMS_PERMISSION_CODE) {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();
                    msgInt.putExtra("address", et.getText().toString());
                    msgInt.putExtra("sms_body", "Hi..How are you?");

                    startActivity(msgInt);

                } else {

                    Toast.makeText(getApplicationContext(), "Permission Denied..", Toast.LENGTH_SHORT).show();


                }
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_permission_check, menu);
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
