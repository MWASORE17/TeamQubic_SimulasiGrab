package com.qubic.grabsimulation.view.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.view.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dennyho on 4/15/17.
 */

public class GrabPayFragment extends Fragment {

    public GrabPayFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Grab Pay");
        View view = inflater.inflate(R.layout.fragment_grab_pay, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @OnClick (R.id.top_up)
    public void showTopUp()
    {
        TopUpCreditFragment topUpCreditFragment = new TopUpCreditFragment();
        ((MainActivity) getActivity()).changeFragment(R.id.fragment_container,
                topUpCreditFragment, true, "");
    }

    @OnClick (R.id.add_payment_method)
    public void showAddPaymentMethod()
    {
        Toast.makeText(getActivity(), "Add Payment Method Clicked !", Toast.LENGTH_SHORT).show();
    }
}
