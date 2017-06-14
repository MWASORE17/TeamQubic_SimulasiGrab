package com.qubic.grabsimulation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qubic.grabsimulation.api.model.entity.PaymentMethod;

import java.util.List;
import com.qubic.grabsimulation.R;

/**
 * Created by dennyho on 5/9/17.
 */

public class PaymentMethodRVAdapter extends RecyclerView.Adapter {
    private List<PaymentMethod> paymentMethods;
    private Context context;
    public PaymentMethodRVAdapter() {

    }

    public PaymentMethodRVAdapter(Context context, List<PaymentMethod> paymentMethods) {
        this.context = context;
        this.paymentMethods = paymentMethods;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_method_item,
                parent, false);

        return new PaymentMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PaymentMethodRVAdapter.PaymentMethodViewHolder paymentMethodViewHolder =
                (PaymentMethodRVAdapter.PaymentMethodViewHolder) holder;
        final PaymentMethod paymentMethod = this.paymentMethods.get(position);
        ((PaymentMethodViewHolder) holder).name.setText(paymentMethod.getName());
        ((PaymentMethodViewHolder) holder).image.setImageResource(this.context
                .getResources().getIdentifier(paymentMethod.getImageName(), "drawable",
                        this.context.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return this.paymentMethods.size();
    }

    private class PaymentMethodViewHolder extends RecyclerView.ViewHolder {
        protected ImageView image;
        protected TextView name;

        public PaymentMethodViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.payment_method_name);
            image = (ImageView) itemView.findViewById(R.id.payment_method_image);
        }
    }
}

