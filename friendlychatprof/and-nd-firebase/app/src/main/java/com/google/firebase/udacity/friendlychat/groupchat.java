package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.File;

import java.util.ArrayList;
import java.util.Map;

public class groupchat extends MainActivity {
    private Button butt;
    private FirebaseDatabase mData;
    private DatabaseReference mData2;
    private DatabaseReference mStor;
    private DatabaseReference mStor2;
    private FirebaseStorage mGod;
    private ListView lview;
    private Groupchatadapter mGroupAdapter;
    private int flag = 0;
    public static int god = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouplist);
        Button butt = (Button) findViewById(R.id.creategroupbutton);
        mData = FirebaseDatabase.getInstance();
        mData2 = mData.getReference();
        final List<groupchatname> gcarray = new ArrayList<>();
        lview = (ListView) findViewById(R.id.messageListView2);
     /*  StorageTask f = mGod.getReference().getFile(mGod.getReference().child("god.txt").getDownloadUrl().getResult()).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(groupchat.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
               Toast.makeText(groupchat.this, "Toasty",Toast.LENGTH_LONG).show();
           }
       });*/
        mGroupAdapter = new Groupchatadapter(groupchat.this, R.layout.activity_groupchat ,gcarray);
        lview.setAdapter(mGroupAdapter);
        //DataSnapshot dat = mData2.child("Group Chat 0");

        mData2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String parent) {
                    if (dataSnapshot.getKey().startsWith("Group Chat ")) {
                        for (int i = 0; i < gcarray.size(); i++) {
                            if (gcarray.get(i).getChatname() == dataSnapshot.getKey()) {

                                flag = 1;
                            }
                            else {}
                        }
                        //DataSnapshot f = FirebaseDatabase.getInstance().getReference().child("groupchatuids").child(dataSnapshot.getKey());
                        if (flag != 1 ) {
                            groupchatname gcr = new groupchatname(dataSnapshot.getKey(),0,null);
                            gcarray.add(gcr);
                        }

                    }
                    if (dataSnapshot.getKey().startsWith("groupchat")) {
                        for (int k = 0; k < gcarray.size(); k++) {
                            if (dataSnapshot.child(gcarray.get(k).getChatname()).exists()) {
                                String f = dataSnapshot.child(gcarray.get(k).getChatname()).getValue().toString();
                                f = f.substring(22,50);
                                String str = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                int g = f.compareTo(str);
                                if (f.compareTo(str) != 0) {
                                    gcarray.remove(k);
                                    k--;
                                }
                            }
                        }
                    }
                    flag = 0;
                   /*for (int j = 0; j < gcarray.size(); j++) {
                        if (mappy.get(gcarray.get(j).getChatname()).get(0) != FirebaseAuth.getInstance().getCurrentUser().getUid()) {
                            gcarray.remove(j);
                            j--;
                        }
                    }*/
                mGroupAdapter = new Groupchatadapter(groupchat.this, R.layout.activity_groupchat ,gcarray);
                lview.setAdapter(mGroupAdapter);
                }

            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            public void onChildChanged (@NonNull DataSnapshot dataSnapshot, String parent) {

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
                String b = String.valueOf(god);
                a = a.concat(b);
                god++;
                groupchatname gfr = new groupchatname(a, 0, null);
                gcarray.add(gfr);
                mStor = FirebaseDatabase.getInstance().getReference().child(gcarray.get(gcarray.size() - 1).getChatname());
               // String ud = FirebaseAuth.getInstance().getCurrentUser().getUid();
               // mStor.push().setValue(ud);
                mStor2 = FirebaseDatabase.getInstance().getReference().child("groupchatuids").child(gcarray.get(gcarray.size() - 1).getChatname());
                mStor2.push().setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                FriendlyMessage msg = new FriendlyMessage("Welcome to the chat!",FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), "", FirebaseAuth.getInstance().getCurrentUser().getUid());
               mStor.push().setValue(msg);
               mGroupAdapter = new Groupchatadapter(groupchat.this, R.layout.activity_groupchat, gcarray);
               lview.setAdapter(mGroupAdapter);
            }
        });

    }

}