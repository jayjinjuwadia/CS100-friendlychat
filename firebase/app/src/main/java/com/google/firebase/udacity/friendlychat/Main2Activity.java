package com.google.firebase.udacity.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = (TextView) (findViewById(R.id.textViewprof));
        textView.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
    }
}

