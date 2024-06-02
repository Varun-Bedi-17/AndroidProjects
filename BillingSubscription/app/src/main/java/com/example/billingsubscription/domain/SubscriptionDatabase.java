package com.example.billingsubscription.domain;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.billingsubscription.data.SubscriptionEntity;

@Database(entities = {SubscriptionEntity.class}, version = 1, exportSchema = false)
public abstract class SubscriptionDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "subscription_database";
    private static volatile SubscriptionDatabase instance;

    public abstract SubscriptionDao subscriptionDao();

    public static synchronized SubscriptionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            SubscriptionDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
