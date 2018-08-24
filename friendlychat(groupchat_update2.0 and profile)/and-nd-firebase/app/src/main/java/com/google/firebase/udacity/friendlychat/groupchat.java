package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;


public class groupchat extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);

        final Button mcs100_close = (Button)findViewById(R.id.cs100_close);
        mcs100_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcs100_close.setText("it is closed");
            }
        });

        final Button mcs180_close = (Button)findViewById(R.id.cs180_close);
        mcs180_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcs180_close.setText("it is closed");
            }
        });

        final Button mihateDT_close = (Button)findViewById(R.id.ihate_close);
        mihateDT_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mihateDT_close.setText("it is closed");
            }
        });

        Button btn = (Button)findViewById(R.id.cs100_chat);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(groupchat.this, CS100GROUP.class));
            }
        });


        Button btn3 = (Button)findViewById(R.id.ihateDT_chat);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(groupchat.this, ihateDTGROUP.class));
            }
        });

        Button btn4 = (Button)findViewById(R.id.cs180_chat);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cs180 = new Intent(groupchat.this, CS180GROUP.class);
                startActivity(cs180);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.groupchat_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.messageboard_menu:
                Intent intent = new Intent(groupchat.this, MainActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
