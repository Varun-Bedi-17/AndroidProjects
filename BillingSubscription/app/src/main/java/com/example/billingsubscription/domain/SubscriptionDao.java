package com.example.billingsubscription.domain;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.billingsubscription.data.SubscriptionEntity;

import java.util.List;

@Dao
public interface SubscriptionDao {
    @Query("SELECT * FROM subscriptions")
    List<SubscriptionEntity> getAllSubscriptions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubscriptions(List<SubscriptionEntity> subscriptions);
}
