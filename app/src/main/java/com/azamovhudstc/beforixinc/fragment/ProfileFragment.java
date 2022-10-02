package com.azamovhudstc.beforixinc.fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.azamovhudstc.beforixinc.R;
import com.azamovhudstc.beforixinc.utils.UserModel;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    TextInputEditText firstName, lastName, password;
    Button update;
    CircleImageView circleImageView;
    UserModel userModel;
    ArrayList<UserModel> userModelArrayList;
    String string;
    DatabaseReference databaseReference;
    TextView fullName, gbCount, balanceCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        UserModel userModel=new UserModel();
        userModelArrayList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        circleImageView = root.findViewById(R.id.profile_image);
        fullName = root.findViewById(R.id.full_name_profile);
        gbCount = root.findViewById(R.id.mb_count);
        balanceCount = root.findViewById(R.id.balance);
        update = root.findViewById(R.id.update_profile);
        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        Query chekUser =databaseReference.child(FirebaseAuth.getInstance().getUid());
        chekUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String namefromDb=snapshot.child(FirebaseAuth.getInstance().getUid()).child("name").getValue(String.class);
                    String lastname=snapshot.child(FirebaseAuth.getInstance().getUid()).child("lastName").getValue(String.class);
                    String imgUrl=snapshot.child(FirebaseAuth.getInstance().getUid()).child("imageUrl").getValue(String.class);
                    int gbcounts =snapshot.child(FirebaseAuth.getInstance().getUid()).child("mb").getValue(Integer.class);
                    userModel.setName(namefromDb);
                    userModel.setLastName(lastname);
                    userModel.setImageUrl(imgUrl);
                    userModel.setMb(gbcounts);
                }else {
                    Toast.makeText(getContext(), "Foydalanuvchi off", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fullName.setText(userModel.getName().toString());
        Glide.with(getActivity()).load(userModel.getImageUrl()).into(circleImageView);
        return root;

    }
}