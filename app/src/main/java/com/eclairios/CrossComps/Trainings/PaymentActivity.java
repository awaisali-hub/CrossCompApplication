package com.eclairios.CrossComps.Trainings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eclairios.CrossComps.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PaymentActivity extends AppCompatActivity {

    // 10.0.2.2 is the Android emulator's alias to localhost
    private static final String BACKEND_URL = " https://vast-river-10550.herokuapp.com/";
    private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;
    TextView amountTextView;
    EditText card_number,cvc,month,year;
    Button payButton;

    private int keyDel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().hide();



        // Configure the SDK with your Stripe publishable key so it can make requests to Stripe

        amountTextView = findViewById(R.id.tv_price);
        card_number = findViewById(R.id.cardNumberEditText);
        cvc = findViewById(R.id.cardCVCEditText);
        month = findViewById(R.id.cardDateEditText);
        year = findViewById(R.id.cardDateyear);
        payButton  = findViewById(R.id.payButton);

        card_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                card_number.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });
                if (keyDel == 0) {
                    int len = card_number.getText().length();
                    if(len == 4) {
                        card_number.setText(card_number.getText() + " ");
                        card_number.setSelection(card_number.getText().length());
                    }else if(len == 9){
                        card_number.setText(card_number.getText() + " ");
                        card_number.setSelection(card_number.getText().length());
                    }else if(len == 14){
                        card_number.setText(card_number.getText() + " ");
                        card_number.setSelection(card_number.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull("pk_test_51IQtqIFJ4SzIm78RToLW7aCQbOuMP16WPAtaHnZdedDNWtr5wxEb6TiU7uL0oDMtbVeAPYjkZTQJaXPsLapXjsWd00wHiyDGaj")
        );

        startCheckout();

    }

    public void MoveToHelperChat(View view) {
        startActivity(new Intent(PaymentActivity.this,HelperChatActivity.class));
    }


    private void startCheckout() {



        // Create a PaymentIntent by calling the server's endpoint.
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        double amount = Double.valueOf(amountTextView.getText().toString()) * 100;
        Log.e("amount", "onCreate: "+amount );

        Map<String, Object> payMap = new HashMap<>();
        Map<String, Object> itemMap = new HashMap<>();
        List<Map<String, Object>> itemList = new ArrayList<>();
        payMap.put("currency", "usd"); //dont change currency in testing phase otherwise it won't work
        itemMap.put("id", "photo_subscription");
        itemMap.put("amount", amount);
        itemList.add(itemMap);
        payMap.put("items", itemList);
        String json = new Gson().toJson(payMap);


        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "create-payment-intent")
                .post(body)
                .build();
        httpClient.newCall(request)
                .enqueue(new PayCallback(PaymentActivity.this));
        // Hook up the pay button to the card widget and stripe instance

        payButton.setOnClickListener((View view) -> {

            if(TextUtils.isEmpty(card_number.getText().toString()) || TextUtils.isEmpty(cvc.getText().toString()) || TextUtils.isEmpty(month.getText().toString()) || TextUtils.isEmpty(year.getText().toString())){

                Toast.makeText(this, "All Fields are Required!!!", Toast.LENGTH_SHORT).show();
            }else{
                //     CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                //        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
                ////////////////////////////
                CardInputWidget cardInputWidget = new CardInputWidget(this);
                cardInputWidget.setCardNumber(card_number.getText().toString());
                cardInputWidget.setCvcCode(cvc.getText().toString());
                cardInputWidget.setExpiryDate(Integer.parseInt(month.getText().toString()), Integer.parseInt(year.getText().toString()));
                cardInputWidget.setPostalCodeRequired(false);

                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();

                ///////////////////////////////


                if (params != null) {
                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);

                    stripe.confirmPayment(this, confirmParams);
                }
            }

        });
    }
    private void displayAlert(@NonNull String title,
                              @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(PaymentActivity.this,HelperChatActivity.class));
            }
        });

        card_number.setText("");
        cvc.setText("");
        month.setText("");
        year.setText("");
        builder.create().show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(PaymentActivity.this));
    }
    private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );
        paymentIntentClientSecret = responseMap.get("clientSecret");
    }
    private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<PaymentActivity> activityRef;
        PayCallback(@NonNull PaymentActivity activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final PaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
                    ).show()
            );
        }
        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final PaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }
    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<PaymentActivity> activityRef;
        PaymentResultCallback(@NonNull PaymentActivity activity) {
            activityRef = new WeakReference<>(activity);
        }
        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final PaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent)
                );

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage()
                );
            }
        }
        @Override
        public void onError(@NonNull Exception e) {
            final PaymentActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString());
        }
    }
}
