package com.qubic.grabsimulation.view.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.api.model.entity.PaymentMethod;
import com.qubic.grabsimulation.api.model.entity.TopUpType;
import com.qubic.grabsimulation.api.model.entity.User;
import com.qubic.grabsimulation.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class TopUpCreditDetailFragment extends Fragment {

    private TopUpType topUpType;
    private String topUpValue;
    private PaymentMethod selectedPaymentMethod;
    private boolean isValidTopUpAmount;
    private boolean isValidTopUpMethod;

    public TopUpCreditDetailFragment() {
        // Required empty public constructor
        isValidTopUpAmount = false;
        isValidTopUpMethod = false;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param topUpType TopUpType Param.
     * @return A new instance of fragment TopUpCreditDetailFragment.
     */
    public static TopUpCreditDetailFragment newInstance(TopUpType topUpType,
                                                        PaymentMethod selectedPaymentMethod,
                                                        String topUpValue) {
        TopUpCreditDetailFragment fragment = new TopUpCreditDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("TopUpType", topUpType);
        args.putParcelable("SelectedPaymentMethod", selectedPaymentMethod);
        args.putString("TopUpValue", topUpValue);
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
        View view = inflater.inflate(R.layout.fragment_top_up_credit_detail, container, false);
        Bundle args = this.getArguments();
        ButterKnife.bind(this, view);

        if (null != args) {
            this.topUpType = args.getParcelable("TopUpType");
            this.selectedPaymentMethod = args.getParcelable("SelectedPaymentMethod");

            if (null != this.selectedPaymentMethod) {
                isValidTopUpMethod = true;
            }

            this.topUpValue = args.getString("TopUpValue");
        }

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(topUpType.getName());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        validateSummaryAndSubmitButton();

        FrameLayout paymentMethod = (FrameLayout) getView().findViewById(R.id.top_up_payment_method);

        if (null == selectedPaymentMethod) {
            TextView paymentMethodHint = new TextView(getContext());
            paymentMethodHint.setTextColor(((TextInputEditText) getView()
                    .findViewById(R.id.top_up_value)).getHintTextColors());
            paymentMethodHint.setText(R.string.payment_method_hint);
            paymentMethod.addView(paymentMethodHint,
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT));
        } else {
            LinearLayout paymentMethodView = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                    R.layout.payment_method_item, null, false);
            paymentMethodView.setPadding(0, 0, 0, 0);
            paymentMethodView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));

            TextView paymentMethodName = (TextView) paymentMethodView.findViewById(R.id.payment_method_name);
            //convert 5dp to px
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
            ((ViewGroup.MarginLayoutParams) paymentMethodName.getLayoutParams()).setMargins((int) px, 0, 0, 0);

            ImageView paymentMethodImage = (ImageView) paymentMethodView.findViewById(R.id.payment_method_image);
            paymentMethodName.setText(selectedPaymentMethod.getName());
            paymentMethodImage.setImageResource(getResources().getIdentifier(
                    selectedPaymentMethod.getImageName(), "drawable", getContext().getPackageName()));
            paymentMethod.addView(paymentMethodView,
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));
        }

        TextInputEditText topUpValueEditText = (TextInputEditText) getView().findViewById(R.id.top_up_value);
        topUpValueEditText.setText(this.topUpValue);
    }

    @OnClick(R.id.first_top_up_value)
    public void setFirstTopUpValue()
    {
        TextInputEditText topUpValueEditText = (TextInputEditText) getView().findViewById(R.id.top_up_value);
        topUpValueEditText.setText("50000");
    }

    @OnClick(R.id.second_top_up_value)
    public void setSecondTopUpValue()
    {
        TextInputEditText topUpValueEditText = (TextInputEditText) getView().findViewById(R.id.top_up_value);
        topUpValueEditText.setText("100000");
    }

    @OnClick(R.id.third_top_up_value)
    public void setThirdTopUpValue()
    {
        TextInputEditText topUpValueEditText = (TextInputEditText) getView().findViewById(R.id.top_up_value);
        topUpValueEditText.setText("200000");
    }

    @OnTextChanged(R.id.top_up_value)
    public void validateTopUpValue()
    {
        TextInputEditText topUpValue = (TextInputEditText) getView().findViewById(R.id.top_up_value);
        TextInputLayout topUpValueInputLayout = (TextInputLayout) getView().findViewById(R.id.top_up_value_layout);
        String topUpValueText = topUpValue.getText().toString();
        List<Button> topUpValueButtons = new ArrayList<>();
        String[] topUpValues = new String[] {"50000", "100000", "200000"};

        topUpValueButtons.add(((Button) getView().findViewById(R.id.first_top_up_value)));
        topUpValueButtons.add(((Button) getView().findViewById(R.id.second_top_up_value)));
        topUpValueButtons.add(((Button) getView().findViewById(R.id.third_top_up_value)));

        if (topUpValueText.equals("")) {
            isValidTopUpAmount = false;
            topUpValueInputLayout.setError(null);
        } else if (Integer.parseInt(topUpValueText) < 50000 ||
            Integer.parseInt(topUpValueText) >= 1000000) {
            isValidTopUpAmount = false;
            topUpValueInputLayout.setError("Invalid Top-up amount");
        } else {
            isValidTopUpAmount = true;
            topUpValueInputLayout.setError(null);
        }

        for (int i = 0; i < topUpValueButtons.size(); i++) {
            if (topUpValues[i].equals(topUpValueText)) {
                topUpValueButtons.get(i).setBackgroundColor(ContextCompat
                        .getColor(getContext(), R.color.grey_400));
            } else {
                topUpValueButtons.get(i).setBackgroundColor(ContextCompat
                        .getColor(getContext(), R.color.white));
            }
        }

        validateSummaryAndSubmitButton();
    }

    @OnClick(R.id.top_up_payment_method_container)
    public void pickPaymentMethodList()
    {
        TextInputEditText topUpValueEditText = (TextInputEditText) getView().findViewById(R.id.top_up_value);
        ((MainActivity) getActivity()).changeFragment(R.id.fragment_container,
                PaymentMethodListFragment.newInstance(topUpType, selectedPaymentMethod,
                        topUpValueEditText.getText().toString()), true, "");
    }

    private void validateSummaryAndSubmitButton()
    {
        Button submitButton = (Button) getView().findViewById(R.id.top_up_submit);
        TextView summaryText = (TextView) getView().findViewById(R.id.summary_text);
        LinearLayout summaryContainer = (LinearLayout) getView().findViewById(R.id.summary_container);

        if (isValidTopUpAmount && isValidTopUpMethod) {
            TextInputEditText topUpValueEditText = (TextInputEditText) getView().findViewById(R.id.top_up_value);
            summaryText.setVisibility(View.VISIBLE);
            summaryContainer.setVisibility(View.VISIBLE);

            TextView payValueText = (TextView) getView().findViewById(R.id.summary_pay_value);
            payValueText.setText(topUpValueEditText.getText());
            TextView totalValueText = (TextView) getView().findViewById(R.id.summary_total_value);
            totalValueText.setText(topUpValueEditText.getText());

            submitButton.setEnabled(true);
            submitButton.setBackgroundColor(ContextCompat.getColor(getContext(),
                    R.color.primary_dark));
        } else {
            summaryText.setVisibility(View.GONE);
            summaryContainer.setVisibility(View.GONE);
            submitButton.setEnabled(false);
            submitButton.setBackgroundColor(ContextCompat.getColor(getContext(),
                    R.color.grey_600));
        }
    }

    @OnClick(R.id.top_up_submit)
    public void submitTopUp()
    {
        TextInputEditText topUpValueEditText = (TextInputEditText) getView().findViewById(R.id.top_up_value);
        User.getCurrentUser().setCredit(User.getCurrentUser().getCredit() +
                Double.parseDouble(topUpValueEditText.getText().toString()));
        //remove previous top up credit detail fragment
        getActivity().getSupportFragmentManager().popBackStack();
        ((MainActivity) getActivity()).changeFragment(R.id.fragment_container,
                new TopUpCreditFragment(), false, "");
    }
}
