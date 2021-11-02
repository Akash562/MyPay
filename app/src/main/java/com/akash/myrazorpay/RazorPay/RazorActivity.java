package com.akash.myrazorpay.RazorPay;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akash.myrazorpay.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.Random;

public class RazorActivity extends Activity implements PaymentResultListener {

    TextView orderno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razor);

        Checkout.preload(getApplicationContext());

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);

        orderno=findViewById(R.id.orderno);
        orderno.setText(String.valueOf(n));

        findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

        findViewById(R.id.txt_privacy_policy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("https://razorpay.com/sample-application/")));
            }
        });

    }

    public void startPayment() {
        try {
            JSONObject options = new JSONObject();
            options.put("name", R.string.app_name);
            options.put("description", "Amount Chargeable");
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "100");
            options.put("prefill.email", "abc@gmail.com");
            options.put("prefill.contact","0123456789");
            new Checkout().open(RazorActivity.this,options);
        } catch (Exception e) {
            Toast.makeText(RazorActivity.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("TAG", "startPayment: **************"+e);
        }

    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onPaymentSuccess: ******************"+razorpayPaymentID);
        } catch (Exception e) {
            Log.e("Success :", "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onPaymentError: ****************"+ code + " " + response);
        } catch (Exception e) {
            Log.e("Error :", "Exception in onPaymentError", e);
        }
    }


}

