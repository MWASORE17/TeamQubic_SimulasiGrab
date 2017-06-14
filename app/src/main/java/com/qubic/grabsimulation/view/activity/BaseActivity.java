package com.qubic.grabsimulation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.qubic.grabsimulation.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by christopher on 5/8/17.
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * replacing fragment container with fragment
     * @param containerViewId current fragment container
     * @param fragment fragment
     * @param isAddToBackStack to specify if current fragment is going to backstack
     */
    public void changeFragment(int containerViewId, Fragment fragment, boolean isAddToBackStack, String tag) {
        FragmentManager f = getSupportFragmentManager();
        FragmentTransaction transaction = f.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down,
                R.anim.slide_up, R.anim.slide_down);
        transaction.replace(containerViewId, fragment, tag);
        if (isAddToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
    public static boolean isEmailValid(String email) {
//        String _expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        String _expression = "^[a-z]([a-z0-9-\\.]+)?+@[a-z]+\\.[a-z]{2,4}+(\\.[a-z]{2,4})?$";
        CharSequence _email = email;
        Pattern _pattern = Pattern.compile(_expression, Pattern.CASE_INSENSITIVE);
        Matcher _mathcer = _pattern.matcher(_email);

        if (_mathcer.matches()) {
            return true;
        }
        return false;
    }
    public static boolean isPasswordValid(String password) {
        String _expression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[@!\\*?$&\\^#-])[\\w@!\\*?$&\\^#-]{8,}$";
        CharSequence _password = password;
        Pattern _pattern = Pattern.compile(_expression, Pattern.CASE_INSENSITIVE);
        Matcher _mathcer = _pattern.matcher(_password);

        if (_mathcer.matches()) {
            return true;
        }
        return false;
    }
    public static void doChangeActivity (Context context, Class destination) {
        Intent _intent = new Intent(context, destination);
        _intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(_intent);
    }
}
