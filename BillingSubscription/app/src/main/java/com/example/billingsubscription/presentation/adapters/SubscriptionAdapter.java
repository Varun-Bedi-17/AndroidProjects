package com.example.billingsubscription.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billingsubscription.R;
import com.example.billingsubscription.data.SubscriptionEntity;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder> {

    private final List<SubscriptionEntity> subscriptions;

    public SubscriptionAdapter(List<SubscriptionEntity> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_premium_list, parent, false);
        return new SubscriptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {
        SubscriptionEntity subscription = subscriptions.get(position);
        holder.bind(subscription);
    }

    @Override
    public int getItemCount() {
        return subscriptions.size();
    }

    static class SubscriptionViewHolder extends RecyclerView.ViewHolder {
        private final TextView subscriptionTitle;

        SubscriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            subscriptionTitle = itemView.findViewById(R.id.product_title);
        }

        void bind(SubscriptionEntity subscription) {
            String priceAndName = subscription.getSubscriptionPrice() + "/" + subscription.getSubscriptionName();
            subscriptionTitle.setText(priceAndName);
        }
    }

}

