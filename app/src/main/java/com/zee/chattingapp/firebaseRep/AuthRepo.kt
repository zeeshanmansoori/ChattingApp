package com.zee.chattingapp.firebaseRep

import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthRepo {

    private lateinit var firebaseAuth: FirebaseAuth

    constructor(

    ){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    fun login(phoneNo:String,password:String){
        //firebaseAuth.sign
    }

    fun logout(){


    }
   /* var username: EditText? =
        null, var email:EditText? = null, var password:EditText? = null, var phone:EditText? = null
    var verify: Button? = null
    var checkInternet: checkInternet? = null

    var auth: FirebaseAuth? = null
    var reference: DatabaseReference? = null
    var pd: ProgressBar? = null

    var email: EditText? = null, var password:EditText? = null
    var login: Button? = null
    var signup: TextView? = null, var forgetpass:TextView? = null
    var auth: FirebaseAuth? = null
    var alertDialogBuilder: AlertDialog.Builder? = null*/

        //signin user ka code gh
        /*
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_user);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        forgetpass = findViewById(R.id.forget_password);
        alertDialogBuilder = new AlertDialog.Builder(this);
        auth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(SignInUser.this) == true) {
                    startActivity(new Intent(SignInUser.this, RegisterUser.class));
                }
            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(SignInUser.this) == true) {
                    Toast.makeText(SignInUser.this, "To reset password Please contact developers ", Toast.LENGTH_LONG).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(SignInUser.this) == true) {
                    final ProgressDialog pd = new ProgressDialog(SignInUser.this);
                    pd.setMessage("Please wait...");
                    pd.show();
                    String str_email = email.getText().toString();
                    String str_password = password.getText().toString();
                    if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                        pd.dismiss();
                        Toast.makeText(SignInUser.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    } else {
                        String val1 = email.getText().toString().trim();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                        Query checkUser = reference.orderByChild("email").equalTo(val1);
                        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    email.setError(null);
                                    auth.signInWithEmailAndPassword(str_email, str_password)
                                            .addOnCompleteListener(SignInUser.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {

                                                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                                                .child(auth.getCurrentUser().getUid());

                                                        //putting value into prefs
                                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                        Prefs.put(getApplicationContext(), user);


                                                        reference.addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                pd.dismiss();
                                                                Intent intent = new Intent(SignInUser.this, DashboardActivityy.class);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(intent);
                                                                finish();
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                pd.dismiss();
                                                            }
                                                        });
                                                    } else {
                                                        pd.dismiss();
                                                        Toast.makeText(SignInUser.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                } else {
                                    pd.dismiss();
                                    email.setError("No Such Email Found. Please SignUp");
                                    email.requestFocus();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }
        });
    }
        */

    //signup user ka code
    /*
    *
    *
    *  private Boolean validateusrEmail() {
        String ews = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String val = email.getText().toString();
        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(ews)) {
            email.setError("Invalid Email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }

    }

    private Boolean validatePhone() {
        String val = phone.getText().toString();
        boolean s = false;
        if (val.startsWith("+91")) {
            s = true;
        }
        if (val.length() < 13) {
            phone.setError("Wrong Number");
            return false;
        } else if (!s) {
            phone.setError("Add +91");
            return false;
        } else {
            phone.setError(null);
            return true;
        }

    }

    private Boolean validateName() {

        String val = username.getText().toString();
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            return true;
        }

    }

    private Boolean validatepass() {
        String pass = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        String val = password.getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else if (val.length() <= 4) {
            password.setError("Password too short");
            return false;
        } else if (val.length() >= 15) {
            password.setError("Password too long");
            return false;
        } else if (!val.matches(pass)) {
            password.setError("Password too weak include atleast 1 special character and 1 digit");
            return false;
        } else {
            password.setError(null);
            return true;
        }

    }

    private void checkEmail() {

        String val1 = email.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("email").equalTo(val1);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    email.setError("Email Exists Please Sign In");
                    email.requestFocus();

                } else {
                    email.setError(null);
                    checkPhone();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void checkPhone() {
        String str_username = username.getText().toString();
        String str_fullname = email.getText().toString();
        String str_email = email.getText().toString();
        String str_password = password.getText().toString();
        String str_phone = phone.getText().toString();

        String val1 = phone.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("phone").equalTo(val1);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phone.setError("Phone Number Alrready In Use. Please Sign In");
                    phone.requestFocus();

                } else {
                    phone.setError(null);
                    register(str_username, str_fullname, str_email, str_password, str_phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void register(final String username, final String fullname, String email, String password, String phone) {
        Intent intent = new Intent(getApplicationContext(), OtpVerify.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("username", username);
        intent.putExtra("phone", phone);
        intent.putExtra("fullname", fullname);
        startActivity(intent);

    }
    *
    */

}