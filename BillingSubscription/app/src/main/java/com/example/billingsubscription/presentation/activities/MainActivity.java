package com.example.billingsubscription.presentation.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.example.billingsubscription.R;
import com.example.billingsubscription.data.SubscriptionEntity;
import com.example.billingsubscription.domain.SubscriptionDao;
import com.example.billingsubscription.domain.SubscriptionDatabase;
import com.example.billingsubscription.presentation.adapters.SubscriptionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PurchasesUpdatedListener {

    String TAG = "BiilingApp";
    private List<SubscriptionEntity> subscriptionsList;
    private BillingClient billingClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*This data can be loaded in splash screen of the application. */
        // Load subscription IDs from the Google Play Console or hardcoded for testing
        List<SubscriptionEntity> subscriptions = new ArrayList<>();
        subscriptions.add(new SubscriptionEntity("monthly_subscription_id", "Monthly Subscription", "120"));
        subscriptions.add(new SubscriptionEntity("quarterly_subscription_id", "Quarterly Subscription", "450"));
        subscriptions.add(new SubscriptionEntity("yearly_subscription_id", "Yearly Subscription", "1700"));
        subscriptions.add(new SubscriptionEntity("one_time_purchase_id", "One time Subcription", "3500"));
        // Save the subscription IDs to the Room database
        SubscriptionDatabase database = SubscriptionDatabase.getInstance(this);
        SubscriptionDao subscriptionDao = database.subscriptionDao();

        // To do background thread work
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            subscriptionDao.insertSubscriptions(subscriptions);
            subscriptionsList = subscriptionDao.getAllSubscriptions();
            //UI Thread work here
            handler.post(this::setUpRecyclerView);
        });

        setContentView(R.layout.activity_main);
        TextView continueButton = findViewById(R.id.buttonBuy);
        continueButton.setOnClickListener(this);

    }


    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SubscriptionAdapter adapter = new SubscriptionAdapter(subscriptionsList);
        recyclerView.setAdapter(adapter);
    }

    private void setUpGooglePlayBilling() {
        // Connect to Google Play Billing service
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // BillingClient is ready. You can query purchases here.
                    querySkuDetails();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Handle connection failure or try to reconnect.
                setUpGooglePlayBilling();
                Log.i(TAG, "onBillingServiceDisconnected");
            }
        });
    }


    private void querySkuDetails() {
        SkuDetailsParams.Builder paramsBuilder = SkuDetailsParams.newBuilder();
        List<String> skuList= getSkuList();
        paramsBuilder.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);

        billingClient.querySkuDetailsAsync(paramsBuilder.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                    SkuDetails skuDetails = skuDetailsList.get(0); // Assuming you have only one SKU detail for the given SKU.
                    if (skuDetails != null) {
                        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                .setSkuDetails(skuDetails)
                                .build();
                        BillingResult responseCode = billingClient.launchBillingFlow(MainActivity.this, flowParams);
                    }
                }
            }
        });
    }

    private List<String> getSkuList() {
        List<String> skuList = new ArrayList<>();
        for (SubscriptionEntity subscription : subscriptionsList) {
            String sku = subscription.getId();
            skuList.add(sku);
        }
        return skuList;
    }


    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (Purchase purchase : purchases) {
                // Handle the purchase details and acknowledge the purchase if necessary.
                acknowledgeSubscription(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle user cancellation.
        } else {
            // Handle other errors.
        }
    }

    private void acknowledgeSubscription(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
            AcknowledgePurchaseParams params = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.getPurchaseToken())
                    .build();

            billingClient.acknowledgePurchase(params, new AcknowledgePurchaseResponseListener() {
                @Override
                public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
                    // Handle the acknowledgment response.
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        if (billingClient != null && billingClient.isReady()) {
            billingClient.endConnection();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonBuy){// Initialize the BillingClient
            billingClient = BillingClient.newBuilder(this)
                    .setListener(this)
                    .enablePendingPurchases()
                    .build();
            setUpGooglePlayBilling();
        }
    }
}