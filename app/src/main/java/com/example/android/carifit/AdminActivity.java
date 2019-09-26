package com.example.android.carifit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }

    public void validate(View view){

        EditText editText1 = (EditText) findViewById (R.id.usernameField);
        EditText editText2 = (EditText) findViewById (R.id.passwordField);

        String un = editText1.getText().toString();
        String pw = editText2.getText().toString();

        un = un.trim();
        pw = pw.trim();

        if(un.equals("GAPP") && pw.equals("GAPP123")){
            Intent intent = new Intent(this,CharityActivity.class);
            startActivity(intent);
        }
        else{

            AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
            builder.setMessage("Incorrect username and password")
                    .setTitle("Restricted Access")
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
