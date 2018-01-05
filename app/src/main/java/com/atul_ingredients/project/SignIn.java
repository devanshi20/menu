package com.atul_ingredients.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atul_ingredient.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    public static final String TAG = SignIn.class.getSimpleName();

    EditText edtPhone,edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        edtPhone= (EditText)findViewById(R.id.edtphone);
        btnSignIn= (Button)findViewById(R.id.btnSignIn);

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View view) {
                table_user.addValueEventListener(new ValueEventListener() {

                        //ProgressDialog mDialog=new ProgressDialog(SignIn.this);
                       // mDialog.show(SignIn.this,"Please waiting...","Progressing.");
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {

                           // mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
                                Toast.makeText(SignIn.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignIn.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(SignIn.this, "User not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getDetails());
                    }
                });

            }
        });

    }
}

