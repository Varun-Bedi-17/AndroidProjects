package com.blockermode.focusmode.uibilllingjava;

import android.app.Activity;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.blockermode.focusmode.uibilllingjava.model.PremiumSelectionDetail;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PurchasesUpdatedListener {
    RecyclerView recyclerView;
    Activity activity;

    PremiumAdapter adapter;
    ArrayList<PremiumSelectionDetail> list = new ArrayList<>();
    PremiumSelectionDetail premiumSelectionDetail;
    BillingClient billingClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        findViewById(R.id.button_close).setOnClickListener(view -> onBackPressed());
        recyclerViewSetup();
        billingClient = BillingClient.newBuilder(this).setListener(this).enablePendingPurchases().build();


        findViewById(R.id.buttonBuy).setOnClickListener(view -> {

            if (adapter.getSelected() == null) {
                Toast.makeText(activity, "No Selection", Toast.LENGTH_SHORT).show();
                return;
            }

            premiumSelectionDetail = adapter.getSelected();
            setUpGooglePlayBilling();
        });
    }

    private void recyclerViewSetup() {

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list.add(new PremiumSelectionDetail("BlockerMode Monthly Subscription", "₹120 / Monthly", "bm_monthly_subscription", "bm-monthly-subscription", "subscription"));

        list.add(new PremiumSelectionDetail("BlockerMode Quarterly Subscription", "₹350 / Quarterly", "bm_quarterly_subscription", "bm-quarterly-subscription", "subscription"));

        list.add(new PremiumSelectionDetail("BlockerMode Yearly Subscription", "₹1,200 / Yearly", "bm_yearly_subscription", "bm-yearly-subscription", "subscription"));

        list.add(new PremiumSelectionDetail("BlockerMode One Time Purchase Subscription", "₹5,500 / One Time Purchase", "bm_one_time_purchase_subscription", "purchase", "purchase"));

        adapter = new PremiumAdapter(activity, list);
        recyclerView.setAdapter(adapter);

    }

    private void setUpGooglePlayBilling() {
        // Connect to Google Play Billing service
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // BillingClient is ready. You can query purchases here.
                    querySkuDetails(callback);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Handle connection failure or try to reconnect.
                Toast.makeText(MainActivity.this, "Biiling disconnected", Toast.LENGTH_SHORT).show();
                Log.i("Billing", "onBillingServiceDisconnected");
                setUpGooglePlayBilling();
            }
        });
    }


    private void querySkuDetails(ProductDetailsCallback callback) {
        QueryProductDetailsParams paramsBuilder = QueryProductDetailsParams
                .newBuilder()
                .setProductList(
                        ImmutableList.of(QueryProductDetailsParams.Product.newBuilder()
                                .setProductId(premiumSelectionDetail.getBasePlanId())
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build()
                        )
                )
                .build();

        billingClient.queryProductDetailsAsync(paramsBuilder, new ProductDetailsResponseListener() {
                    public void onProductDetailsResponse(BillingResult billingResult,
                                                         List<ProductDetails> productDetailsList) {

                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            callback.onProductDetailsReceived(productDetailsList);
                        } else {
                            callback.onQueryFailed(billingResult);
                        }
                    }
                }
        );
    }


    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (Purchase purchase : purchases) {
                // Handle the purchase details and acknowledge the purchase if necessary.
                acknowledgeSubscription(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            Log.i("Billing", "user cancelled");
        } else {
            Log.i("Billing", "other error");
        }
    }

    private void acknowledgeSubscription(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
            AcknowledgePurchaseParams params = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();

            billingClient.acknowledgePurchase(params, new AcknowledgePurchaseResponseListener() {
                @Override
                public void onAcknowledgePurchaseResponse(BillingResult billingResult) {
                    // Handle the acknowledgment response.
                }
            });
        }
    }

    ProductDetailsCallback callback = new ProductDetailsCallback() {
        @Override
        public void onProductDetailsReceived(List<ProductDetails> productDetailsList) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImmutableList productDetailsParamsList =
                            ImmutableList.of(
                                    BillingFlowParams.ProductDetailsParams.newBuilder()
                                            // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                                            .setProductDetails(productDetailsList.get(0))
                                            .build()
                            );

                    BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                            .setProductDetailsParamsList(productDetailsParamsList)
                            .build();

                    // Launch the billing flow
                    BillingResult billingResult = billingClient.launchBillingFlow(activity, billingFlowParams);
                }
            });
        }

        @Override
        public void onQueryFailed(BillingResult billingResult) {
            // Handle query failure on the main thread
            Log.i("Billing", "query failed");
        }
    };
}

interface ProductDetailsCallback {
    void onProductDetailsReceived(List<ProductDetails> productDetailsList);

    void onQueryFailed(BillingResult billingResult);
}