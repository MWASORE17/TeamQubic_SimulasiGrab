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
import com.qubic.grabsimulation.api.model.session.SessionManager;
import com.qubic.grabsimulation.view.activity.AuthActivity;
import com.qubic.grabsimulation.view.activity.MainActivity;

import butterknife.ButterKnife;

public class LoginFragment extends Fragment {
    private TextView register;
    private EditText username, password;
    private Button login;
    private TextInputLayout usernameContainer, passwordContainer;
    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        init(view);
        event();
        return view;
    }
    private void init(View view) {
        register = (TextView) view.findViewById(R.id.login_register);
        username = (EditText) view.findViewById(R.id.login_username);
        password = (EditText) view.findViewById(R.id.login_password);
        login = (Button) view.findViewById(R.id.login_login);
        usernameContainer = (TextInputLayout) view.findViewById(R.id.login_username_container);
        passwordContainer = (TextInputLayout) view.findViewById(R.id.login_password_container);
    }
    private void event(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthActivity)getActivity()).changeFragment(R.id.fragment_container,new RegisterFragment(),false, "");
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;
                usernameContainer.setErrorEnabled(false);
                passwordContainer.setErrorEnabled(false);

                if (TextUtils.isEmpty(username.getText())) {
                    isValid = false;
                    usernameContainer.setErrorEnabled(true);
                    usernameContainer.setError("Email is required");
                } else if (username.getText().length()<7) {
                    isValid = false;
                    usernameContainer.setErrorEnabled(true);
                    usernameContainer.setError("Email is not valid");
                } else if (TextUtils.isEmpty(password.getText())) {
                    isValid = false;
                    passwordContainer.setErrorEnabled(true);
                    passwordContainer.setError("Password is required");
                }

                if (isValid) {
                    Boolean isRegistered = User.isRegistered(username.getText().toString());
                    User user = User.getMatchUser(username.getText().toString(), password.getText().toString());

                    if (!isRegistered) {
                        usernameContainer.setErrorEnabled(true);
                        usernameContainer.setError("Email is not registered as a user.");
                    } else if (null == user) {
                        passwordContainer.setErrorEnabled(true);
                        passwordContainer.setError("Password is wrong.");
                    }

                    if (isRegistered && null != user) {
                        SessionManager.with(getContext()).CreateSession(user);
                        User.setCurrentUser(getContext(), user);
                        ((AuthActivity) getActivity()).doChangeActivity(getContext(), MainActivity.class);
                    }
                }
            }
        });
    }
}
