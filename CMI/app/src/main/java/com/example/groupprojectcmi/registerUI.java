package com.example.groupprojectcmi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class registerUI extends AppCompatActivity {

    EditText rUser, rPassword, rComfirmPassword, rEmail, rPlateNumber, rPhoneNumber;
    Button rButton;

    //declaration for authenthication
    FirebaseAuth fAuth;

    FirebaseDatabase fdatarootNode;
    DatabaseReference reference;





    TextView rRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_u_i);

        //where to get the data from
        rUser = findViewById(R.id.register_Username); //used
        rPassword = findViewById(R.id.register_Password); //used
        rComfirmPassword = findViewById(R.id.register_ConfirmPassword);
        rEmail = findViewById(R.id.register_Email); //used
        rPlateNumber = findViewById(R.id.register_PlateNum); //used
        rButton = findViewById(R.id.register_Button); //used
        rPhoneNumber = findViewById(R.id.register_PhoneNum);

        //instantiate fire authenthecation
        fAuth = FirebaseAuth.getInstance();
        //instantiate fire database from its root
        fdatarootNode = FirebaseDatabase.getInstance();


       //this is if user is registered i will bring them straight into my app
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }




        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //declaration of where i get the item from it
                String email = rEmail.getText().toString().trim();
                String password = rPassword.getText().toString().trim();
                String confirmPassword = rComfirmPassword.getText().toString().trim();
                String user = rUser.getText().toString().trim();
                String phoneNumber = rPhoneNumber.getText().toString().trim();
                String plateNumber = rPlateNumber.getText().toString().trim();


                //check if email is enter or not
                if (TextUtils.isEmpty(email)) {
                    rEmail.setError("Email is Required");
                    return;
                }

                //check if password is enter or not
                if (TextUtils.isEmpty(password)) {
                    rPassword.setError("Password is Required");
                    return;
                }

                //check if password is more than 6 characters
                if (password.length() < 6) {
                    rPassword.setError("Password Must be >= 6 characters");
                    return;
                }

                //check if password is not of equal length
                if (password.length() != confirmPassword.length()) {
                    rComfirmPassword.setError("Password not the same");
                    return;
                }

                //check if user is keyed in or not
                if (TextUtils.isEmpty(user)) {
                    rUser.setError("user is Required");
                    return;
                }

                //check if phonenumber is keyed it or not
                if (TextUtils.isEmpty(phoneNumber)) {
                    rUser.setError("phone Number is Required");
                    return;
                }

                //check if user is keyed in or not
                if (TextUtils.isEmpty(plateNumber)) {
                    rPlateNumber.setError("plateNumber is Required");
                    return;
                }

                //saving data in firebase real-time database
                //this is to with reference to firedatabase of where its path is where in this case is userStorage in firebase database
                reference = fdatarootNode.getReference("userStorage");
                //get all the values of the user
                registerUIFirebaseDataBase rFirebaseDataBase = new registerUIFirebaseDataBase(email, password, user,phoneNumber, plateNumber);
                reference.child(user).setValue(rFirebaseDataBase);


                //register the user in firebase authenthication
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if everything is successful from checking the password requirments to plate number
                        if(task.isSuccessful()){
                            Toast.makeText(com.example.groupprojectcmi.registerUI.this, "User Created", Toast.LENGTH_SHORT).show();
                            //this start activity will bring the user to the main page when everything is fine
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(com.example.groupprojectcmi.registerUI.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });



            }
        });


    }

}