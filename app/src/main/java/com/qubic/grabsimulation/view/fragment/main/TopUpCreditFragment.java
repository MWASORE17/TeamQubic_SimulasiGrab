package com.qubic.grabsimulation.view.fragment.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.api.model.entity.TopUpType;
import com.qubic.grabsimulation.api.model.entity.User;
import com.qubic.grabsimulation.view.activity.MainActivity;
import com.qubic.grabsimulation.adapter.TopUpAdapter;

import java.util.ArrayList;
import java.util.List;

public class TopUpCreditFragment extends Fragment {

    private View view;
    public TopUpCreditFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Credits");
        view = inflater.inflate(R.layout.fragment_top_up_credit, container, false);

        List<TopUpType> topUpTypes = new ArrayList<>();
        topUpTypes.add(new TopUpType("atm_and_internet_banking", "Atm & Internet Banking"));
        //topUpTypes.add(new TopUpType("minimarket", "Minimarket"));
        //topUpTypes.add(new TopUpType("cards_and_wallets", "Cards And Wallets"));

        TopUpAdapter adapter = new TopUpAdapter(getContext(),
                R.layout.activity_listview,
                topUpTypes);

        ListView listView = (ListView) view.findViewById(R.id.top_up_types);

        LinearLayout listViewHeader = new LinearLayout(getContext());
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        listViewHeader.setPadding((int) px, (int) px, (int) px, 0);
        LinearLayout.LayoutParams layoutParams = new  LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView balance = (TextView) view.findViewById(R.id.balance);
        balance.setText(User.getCurrentUser().getCredit().toString());

        TextView listViewHeaderText = new TextView(getContext());
        listViewHeaderText.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        listViewHeaderText.setText("Top-Up");
        listViewHeaderText.setTypeface(null, Typeface.BOLD);

        listViewHeader.addView(listViewHeaderText, layoutParams);
        listView.addHeaderView(listViewHeader, null, false);

        listView.setAdapter(adapter);

        //click listener for list view item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ((MainActivity) getActivity()).changeFragment(
                        R.id.fragment_container,
                        TopUpCreditDetailFragment
                                .newInstance(
                                        (TopUpType) parent.getAdapter().getItem(position),
                                        null, ""
                                ),
                        true, "TopUpCreditDetail"
                );
            }
        });

        return view;
    }
}
