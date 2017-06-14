package com.qubic.grabsimulation.view.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.api.model.entity.PaymentMethod;
import com.qubic.grabsimulation.api.model.entity.TopUpType;
import com.qubic.grabsimulation.listener.PaymentMethodRVTouchListener;
import com.qubic.grabsimulation.view.activity.MainActivity;
import com.qubic.grabsimulation.adapter.PaymentMethodRVAdapter;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodListFragment extends Fragment {
    private TopUpType topUpType;
    private String topUpValue;
    private PaymentMethod selectedPaymentMethod;

    public PaymentMethodListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param topUpType TopUpType Param.
     * @return A new instance of fragment TopUpCreditDetailFragment.
     */
    public static PaymentMethodListFragment newInstance(TopUpType topUpType,
                                                        PaymentMethod selectedPaymentMethod,
                                                        String topUpValue) {
        PaymentMethodListFragment fragment = new PaymentMethodListFragment();
        Bundle args = new Bundle();
        args.putParcelable("TopUpType", topUpType);
        args.putString("TopUpValue", topUpValue);
        args.putParcelable("SelectedPaymentMethod", selectedPaymentMethod);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_method_list, container, false);
        Bundle args = this.getArguments();

        if (null != args) {
            this.topUpType = args.getParcelable("TopUpType");
            this.selectedPaymentMethod = args.getParcelable("SelectedPaymentMethod");
            this.topUpValue = args.getString("TopUpValue");
        }

        final List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Select a Payment Method");

        paymentMethods.add(new PaymentMethod("ATM Bersama", "atm_bersama"));
        paymentMethods.add(new PaymentMethod("ATM BCA", "atm_bca"));
        paymentMethods.add(new PaymentMethod("ATM BNI", "atm_bni"));
        paymentMethods.add(new PaymentMethod("ATM BRI", "atm_bri"));

        PaymentMethodRVAdapter adapter = new PaymentMethodRVAdapter(getContext(), paymentMethods);
        final RecyclerView paymentMethodRv = (RecyclerView) view.findViewById(R.id.payment_method_rv);
        paymentMethodRv.setAdapter(adapter);
        paymentMethodRv.setHasFixedSize(true);
        paymentMethodRv.setLayoutManager(new LinearLayoutManager(getContext()));
        paymentMethodRv.addOnItemTouchListener(new PaymentMethodRVTouchListener(getContext(),
                paymentMethodRv, new PaymentMethodRVTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //remove previous top up credit detail fragment
                getActivity().getSupportFragmentManager().popBackStack();
                ((MainActivity) getActivity()).changeFragment(R.id.fragment_container,
                        TopUpCreditDetailFragment.newInstance(topUpType,
                                paymentMethods.get(position), topUpValue), false, "TopUpCreditDetail");
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        return view;
    }
}
