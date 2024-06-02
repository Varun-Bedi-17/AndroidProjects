package com.example.billingsubscription.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subscriptions")
public class SubscriptionEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "subscription_name")
    private String subscriptionName;

    @ColumnInfo(name = "subscription_price")
    private String subscriptionPrice;

    // Constructor
    public SubscriptionEntity(@NonNull String id, String subscriptionName, String subscriptionPrice) {
        this.id = id;
        this.subscriptionName = subscriptionName;
        this.subscriptionPrice = subscriptionPrice;
    }

    // Getters and setters
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public String getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(String subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

}
