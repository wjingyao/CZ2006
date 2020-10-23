package com.example.groupprojectcmi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class loginUI extends AppCompatActivity {

    EditText lUser, lPassword, lEmail;
    TextView lNewUser;
    Button lButton;

    //FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_u_i);


        lEmail =findViewById(R.id.login_Email);
        lPassword = findViewById(R.id.login_Password);
        lNewUser = findViewById(R.id.login_NewUser);
        lButton = findViewById(R.id.login_Button);


        //instantiate fire authenthecation
        //fAuth = FirebaseAuth.getInstance();

        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //declaration of where i get the item from it
                String email = lEmail.getText().toString().trim();
                String password = lPassword.getText().toString().trim();


                //check if email is enter or not
                if (TextUtils.isEmpty(email)) {
                    lUser.setError("Email is Required");
                    return;
                }

                //check if password is enter or not
                if (TextUtils.isEmpty(password)) {
                    lPassword.setError("Password is Required");
                    return;
                }

                //check if password is more than 6 characters
                if (password.length() < 6) {
                    lPassword.setError("Password Must be >= 6 characters");
                    return;
                }



                //authenticate the user
                //fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   // @Override
                   // public void onComplete(@NonNull Task<AuthResult> task) {
                   //     if(task.isSuccessful()){
                   //         Toast.makeText(loginUI.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                   //         startActivity(new Intent(getApplicationContext(),MainActivity.class));
                  //      }
                  //      else {
                  //          Toast.makeText(loginUI.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    //    }
                  //  }
              //  });


            }
        });

        //when user click on -New User?, it will bring them to register page
        lNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),registerUI.class));
            }
        });

    }
}