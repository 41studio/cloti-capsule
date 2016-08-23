package com.fourtyonestudio.cloticapsuleloopback;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fourtyonestudio.cloticapsuleloopback.model.Users;
import com.fourtyonestudio.cloticapsuleloopback.services.UserRepository;
import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.btn_register)
    Button btnRegister;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_register)
    void btnRegister() {
        if(!username.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")){
            register();
        }else{
            Toast.makeText(this, "Please fill correctly", Toast.LENGTH_LONG).show();
        }


    }

    private void register() {
        Apps app = new Apps();
        RestAdapter adapter = app.getLoopBackAdapter();

        UserRepository repository = adapter.createRepository(UserRepository.class);

        Users model = repository.createObject(ImmutableMap.of("username", username.getText().toString(), "email", email.getText().toString(), "emailVerified", true, "password", password.getText().toString()));

        model.save(new VoidCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(Throwable t) {;
                Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
