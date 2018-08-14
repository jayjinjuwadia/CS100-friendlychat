package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.File;
import java.io.InputStream;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import  java.lang.Exception;
import  java.lang.annotation.Annotation;
public class Main2Activity extends MainActivity {
    private FirebaseStorage mFirebaseStorage2;
   private FirebaseAuth auth;
    private StorageReference mProfilePhotosStorageReference;
    TextView textView;
    ImageButton button;
    ImageView imview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseStorage2 = FirebaseStorage.getInstance();
        mProfilePhotosStorageReference = mFirebaseStorage2.getReference().child("profile_photos").child(auth.getCurrentUser().getUid());
        setContentView(R.layout.activity_main2);
        textView = (TextView) (findViewById(R.id.textViewprof));
            textView.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            button = (ImageButton) (findViewById(R.id.Imbutton));
            imview = (ImageView) (findViewById(R.id.imageView));
            if (!mProfilePhotosStorageReference.child(auth.getCurrentUser().getUid() + ".png").getDownloadUrl().toString().isEmpty()) {

                imview.setImageURI(mProfilePhotosStorageReference.child(auth.getCurrentUser().getUid() + ".png").getDownloadUrl().getResult());
        }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                    // TODO: Fire an intent to show an image picker
                }
            });

        }
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

               //FirebaseUser user = auth.getCurrentUser();
               // String userid = user.getUid();
                    Uri selectedImageUri = data.getData();

                    //imview.setImageURI(selectedImageUri);
                   // imview.setAdjustViewBounds(true);
                    final StorageReference photoRef = mProfilePhotosStorageReference.child(auth.getCurrentUser().getUid() + ".png");
                    photoRef.putFile(selectedImageUri).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Main2Activity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                        }
                    })
                            .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Uri dlUri = uri;
                                            imview.setImageURI(dlUri);
                                            imview.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                            });

        }

}

