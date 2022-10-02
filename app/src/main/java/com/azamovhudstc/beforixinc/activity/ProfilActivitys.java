package com.azamovhudstc.beforixinc.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.azamovhudstc.beforixinc.R;
import com.azamovhudstc.beforixinc.utils.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivitys extends AppCompatActivity {
    FirebaseFirestore db;
    public static final int PICK_IMAGE=123;
    StorageReference storageReference;
    String imageUriAcsessToken;
    Uri imagePath;
    CircleImageView circleImageView,addicon;
    EditText name, fullname,password;
    FirebaseDatabase firebaseDatabase;

    FirebaseStorage storage;
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_profil);
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        addicon=findViewById(R.id.imageView);
//        databaseList()
        storageReference=storage.getReference();
        firebaseDatabase=FirebaseDatabase.getInstance();
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);
        name = findViewById(R.id.name_developer);
        fullname = findViewById(R.id.full_name);
        auth = FirebaseAuth.getInstance();
        password= (EditText) findViewById(R.id.password);

        db = FirebaseFirestore.getInstance();

        addicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(circleImageView);

            }
        });
    }

    public void uploadImage(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,PICK_IMAGE);


    }
    public void yuborish(View view) {
        String firstName = name.getText().toString();
        String lastName = fullname.getText().toString();
        String passwords = password.getText().toString();
        if (firstName.trim().isEmpty() && lastName.trim().isEmpty() ) {
            Toast.makeText(getApplicationContext(), "Bosh maydonni toldiring !", Toast.LENGTH_SHORT).show();
        } else if (imagePath == null) {
            imageUriAcsessToken="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS54088iJjHpn-y9FCxGAh5NBEdHugwIXewWQ&usqp=CAU";
            sendImageToStorage();
        } else {

            if (firstName.trim().isEmpty() && lastName.trim().toString().isEmpty() && passwords.trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Barch maydonlarni To`ldiring", Toast.LENGTH_SHORT).show();

            } else {
                sendImageToStorage();

            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            imagePath=data.getData();
            circleImageView.setImageURI(imagePath);

        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    private void sendImageToStorage() {
        StorageReference imageRef = storageReference.child("Images").child(auth.getUid()).child("Profile Pic");
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        UploadTask task = imageRef.putBytes(data);
        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUriAcsessToken = uri.toString();

                        sendDataUri();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Image Not Uploaded !"+e.getMessage() ,Toast.LENGTH_SHORT).show();

                    }
                });
                Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Image Not Uploaded !"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void sendDataUri() {
        String firstName = name.getText().toString();
        String lastName = fullname.getText().toString();
        String passwords = password.getText().toString();
        String phoneNumber =getIntent().getStringExtra("phone");
        UserModel userModel=new UserModel(phoneNumber,firstName,lastName,imageUriAcsessToken,passwords,0,0);
        String id =auth.getUid();
        firebaseDatabase.getReference().child("Users").child(id).setValue(userModel);
        Toast.makeText(this, "Success !", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

//        DocumentReference documentReference = db.collection("users")
//                .document(auth.getUid());
//        Map<String, Object> user = new HashMap<>();
//        user.put("name", firstName);
//        user.put("phone", );
//        user.put("lastName", lastName);
//        user.put("image", imageUriAcsessToken);



    }


}
