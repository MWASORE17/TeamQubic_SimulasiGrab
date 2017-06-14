package com.qubic.grabsimulation.view.fragment.auth;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.api.model.entity.User;
import com.qubic.grabsimulation.view.activity.AuthActivity;

import butterknife.ButterKnife;

public class RegisterFragment extends Fragment {
    private TextView login;
    private TextInputLayout register_username;
    private TextInputLayout register_phone;
    private TextInputLayout register_password;
    private TextInputLayout register_repassword;
    private EditText et_username;
    private EditText et_phone;
    private EditText et_password;
    private EditText et_repassword;
    private Button btn_register;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        init(view);
        event();
        return view;
    }
    private void init(View view) {
        login = (TextView) view.findViewById(R.id.register_login);
        register_username = (TextInputLayout) view.findViewById(R.id.register_username_container);
        register_phone = (TextInputLayout) view.findViewById(R.id.register_phone_container);
        register_password = (TextInputLayout) view.findViewById(R.id.register_password_container);
        register_repassword = (TextInputLayout) view.findViewById(R.id.register_repassword_container);
        btn_register = (Button) view.findViewById(R.id.register_register);
        et_username = (EditText) view.findViewById(R.id.register_username);
        et_phone = (EditText) view.findViewById(R.id.register_phone);
        et_password = (EditText) view.findViewById(R.id.register_password);
        et_repassword = (EditText) view.findViewById(R.id.register_repassword);
    }

    private void event() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthActivity)getActivity()).changeFragment(R.id.fragment_container,
                        new LoginFragment(),false, "");
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean _isvalid = true;
                register_username.setErrorEnabled(false);
                register_phone.setErrorEnabled(false);
                register_password.setErrorEnabled(false);
                register_repassword.setErrorEnabled(false);

                if (TextUtils.isEmpty(et_username.getText())) {
                    _isvalid = false;
                    register_username.setErrorEnabled(true);
                    register_username.setError("Name is required");
                } else if (et_username.getText().length() < 7) {
                    _isvalid = false;
                    register_username.setErrorEnabled(true);
                    register_username.setError("Name minimal 7");

                } else if (TextUtils.isEmpty(et_phone.getText())) {
                    _isvalid = false;
                    register_phone.setErrorEnabled(true);
                    register_phone.setError("Email is required");
                } else if (et_phone.getText().length()<11) {
                    _isvalid = false;
                    register_phone.setErrorEnabled(true);
                    register_phone.setError("Phone Number is not valid");
                } else if (TextUtils.isEmpty(et_password.getText())) {
                    _isvalid = false;
                    register_password.setErrorEnabled(true);
                    register_password.setError("Password is required");
                } else if (!AuthActivity.isPasswordValid(et_password.getText().toString())) {
                    _isvalid = false;
                    register_password.setErrorEnabled(true);
                    register_password.setError("Password is not valid. " +
                            "Password must contains at least 1 lowercase, 1 uppercase, 1 number, " +
                            "1 special character and minimum 8 characters");
                } else if (TextUtils.isEmpty(et_repassword.getText())) {
                    _isvalid = false;
                    register_repassword.setErrorEnabled(true);
                    register_repassword.setError("Re-Password is required");
                } else if (!et_password.getText().toString().equals(et_repassword.getText().toString())) {
                    _isvalid = false;
                    register_repassword.setErrorEnabled(true);
                    register_repassword.setError("Password not match");

                }

                if (_isvalid) {
                    User userNew = new User(et_username.getText().toString(),
                            et_password.getText().toString(), et_phone.getText().toString());
                    User.addNewUser(userNew);
                    ((AuthActivity)getActivity()).changeFragment(R.id.fragment_container,
                            new LoginFragment(),false, "");
                }
            }
        });
    }

}
