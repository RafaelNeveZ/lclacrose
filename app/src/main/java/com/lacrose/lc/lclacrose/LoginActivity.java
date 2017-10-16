package com.lacrose.lc.lclacrose;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.lacrose.lc.lclacrose.Util.MainActivity;


public class LoginActivity extends MainActivity {
    private FirebaseAuth Auth;
    private FirebaseAuth.AuthStateListener AuthListener;
    private final Context context = this;
    private final static String ERROR = "ERROR";
    public EditText et_user,et_pass;
    public TextView tv_forgotPass, tv_about, tv_terms;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_user = (EditText) findViewById(R.id.user_edit_text);
        et_pass = (EditText) findViewById(R.id.pass_edit_text);
        tv_forgotPass = (TextView) findViewById(R.id.pass_forgot);
        tv_about = (TextView) findViewById(R.id.about);
        tv_terms = (TextView) findViewById(R.id.terms_of_user);
        Auth = FirebaseAuth.getInstance();
        loginListener();

    }


    //=================================LOGIN=======================================================\\
    public void loginListener(){
        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(context,WorkActivity.class);
                    context.startActivity(intent);
                    finish();
                    dismissProgress();
                } else {
                    Log.e(ERROR,"NAO LOGOU");
                }
            }
        };
    }

    public void login(View view) {
        if(validateFields()) {
            showProgress(getString(R.string.loging));
            Auth.signInWithEmailAndPassword(et_user.getText().toString(), et_pass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        try {
                            throw task.getException();
                        }catch(FirebaseAuthInvalidCredentialsException e) {
                            Toast.makeText(context,getString(R.string.credential_wrong),Toast.LENGTH_SHORT).show();
                        }catch(FirebaseAuthInvalidUserException e) {
                            Toast.makeText(context,getString(R.string.user_invalid),Toast.LENGTH_SHORT).show();
                        } catch(Exception e) {
                            Toast.makeText(context,getString(R.string.server_error),Toast.LENGTH_SHORT).show();
                        }
                        dismissProgress();
                    }else{
                        dismissProgress();
                    }
                }
            });
        }
    }

    private boolean validateFields() {
        if(et_user.getText().toString().isEmpty()){
            et_user.setError(getString(R.string.empty_field_error));
            return false;
        }
        if(et_pass.getText().toString().isEmpty()){
            et_pass.setError(getString(R.string.empty_field_error));
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        if(Auth!=null) {
            Auth.addAuthStateListener(AuthListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (AuthListener != null) {
            Auth.removeAuthStateListener(AuthListener);
        }
    }

    public void forgotPass(View view) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_forgot_pass);
        dialog.setTitle(getString(R.string.dialog_forgot_pass_title));
        dialog.show();
        final EditText emailToReset = (EditText)dialog.findViewById(R.id.edit_email_reset);
        Button btCancel = (Button) dialog.findViewById(R.id.cancel_forgot);
        Button btSendEmail = (Button) dialog.findViewById(R.id.confirm_send_email);
        btSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailToReset.getText().toString().isEmpty()) {
                    Auth.sendPasswordResetEmail(emailToReset.getText().toString()).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            dialog.dismiss();
                            Toast.makeText(context,getString(R.string.email_sent),Toast.LENGTH_SHORT).show();
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(context,ERROR,Toast.LENGTH_SHORT).show();
                        }

                    });
                }else {
                    emailToReset.setError(getString(R.string.email_miss));
                }
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    //=============================================================================================\\
    public void about(View view) {
        Intent intent = new Intent(this,AboutActivity.class);
        this.startActivity(intent);
    }

    public void terms(View view) {
        Intent intent = new Intent(this,TermsActivity.class);
        this.startActivity(intent);
}

}