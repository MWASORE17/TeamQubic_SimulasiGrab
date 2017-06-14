package com.qubic.grabsimulation.view.fragment.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.view.activity.AuthActivity;
import com.qubic.grabsimulation.view.fragment.auth.LoginFragment;

import butterknife.ButterKnife;

/**
 * Created by christopher on 5/1/17.
 */

public class LoginButtonFragment extends Fragment {
    public LoginButtonFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_button, container, false);
        ButterKnife.bind(this, view);
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        Button loginEmail = (Button) view.findViewById(R.id.btn_email_login);
        loginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthActivity) getActivity()).changeFragment(R.id.fragment_container, new LoginFragment(), true, "");
            }
        });
    }
}
