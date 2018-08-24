package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class groupchat extends MainActivity {
    private Button butt;
    private FirebaseDatabase mData;
    private DatabaseReference mData2;
    private DatabaseReference mStor;
    private ListView lview;
    private Groupchatadapter mGroupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouplist);
        Button butt = (Button) findViewById(R.id.creategroupbutton);
        mData = FirebaseDatabase.getInstance();
        mData2 = mData.getReference();
        final List<String> gcarray = new ArrayList<>();
        lview = (ListView) findViewById(R.id.messageListView2);
        mGroupAdapter = new Groupchatadapter(groupchat.this, R.layout.activity_groupchat ,gcarray);
        lview.setAdapter(mGroupAdapter);
        //DataSnapshot dat = mData2.child("Group Chat 0");
        mData2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String parent) {
                    if (dataSnapshot.getKey().startsWith("Group Chat ")) {
                        if (!gcarray.contains(dataSnapshot.getKey())) {
                            gcarray.add(dataSnapshot.getKey());
                        }

                    }
                mGroupAdapter = new Groupchatadapter(groupchat.this, R.layout.activity_groupchat ,gcarray);
                lview.setAdapter(mGroupAdapter);
                }

            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            public void onChildChanged (@NonNull DataSnapshot dataSnapshot, String parent) {
                String s = dataSnapshot.getKey();

                if (dataSnapshot.hasChild(s)) {
                    gcarray.add(s);

                }
            }

            public void onChildMoved (@NonNull DataSnapshot dataSnapshot, String parent) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(groupchat.this,"MMMMMMMMMM TOASTY", Toast.LENGTH_LONG).show();
            }
        });

        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = "Group Chat ";
                String b = String.valueOf(gcarray.size());
                a = a.concat(b);
                gcarray.add(a);
                mStor = FirebaseDatabase.getInstance().getReference().child(gcarray.get(gcarray.size() - 1));
                FriendlyMessage msg = new FriendlyMessage("Welcome to the chat!",FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), "", FirebaseAuth.getInstance().getCurrentUser().getUid());
               mStor.push().setValue(msg);
               mGroupAdapter = new Groupchatadapter(groupchat.this, R.layout.activity_groupchat, gcarray);
               lview.setAdapter(mGroupAdapter);
            }
        });

    }

}