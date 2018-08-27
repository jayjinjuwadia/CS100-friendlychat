package com.google.firebase.udacity.friendlychat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

public class addm extends MainActivity {
    StorageReference storef = FirebaseStorage.getInstance().getReference().child(groupyg+ "uid.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmemberpage);
        final EditText edi = (EditText) findViewById(R.id.messageEditText3);
        final Button sendbutt = (Button) findViewById(R.id.sendButton3);



        edi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendbutt.setEnabled(true);
                } else {
                    sendbutt.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        sendbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                try {
                   final  File fl = File.createTempFile(edi.getText().toString(), "prof.txt");
                    final FileWriter flot = new FileWriter(fl, true);
                    final BufferedWriter flotter = new BufferedWriter(flot);
                    storef.getFile(fl).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            try {
                                flotter.append(edi.getText().toString());
                                flotter.append('\n');
                                FileReader fu = new FileReader(fl);
                                BufferedReader futter = new BufferedReader(fu);
                                String me = futter.readLine();
                                while (me != null) {
                                    Toast.makeText(addm.this, me,Toast.LENGTH_LONG).show();
                                    me = futter.readLine();
                                }

                                Uri ury = android.net.Uri.parse(fl.toURI().toString());
                                storef.putFile(ury).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        //fl.deleteOnExit();
                                        Toast.makeText(addm.this, "Successfully added member", Toast.LENGTH_LONG).show();
                                        edi.setText("");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(addm.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            catch (Exception j) {}
                        }
                    });
                }
                catch (Exception ol ) {}

                // Clear input box

            }
        });

    }
}
