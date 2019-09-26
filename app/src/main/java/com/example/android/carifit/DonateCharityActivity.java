package com.example.android.carifit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class DonateCharityActivity extends AppCompatActivity
{

    TextView m_response;

    EditText editText;

    String amount = "0.00";

    double amount_double=0.00;
    //static Cart m_cart;

    PayPalConfiguration m_configuration;
    // the id is the link to the paypal account, we have to create an app and get its id
    String m_paypalClientId = "AbJ3_gPfbF4aAhKaNfDKLlZTWptYNklmz7sSkvAH6Pq87_FfZrA27bqIsl5-T96TuIW395StsN0QCZww";
    Intent m_service;
    int m_paypalRequestCode = 999; // or any number you want

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        //LinearLayout list = (LinearLayout) findViewById(R.id.list);

        m_response = (TextView) findViewById(R.id.transactionStatus);



        m_configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // sandbox for test, production for real
                .clientId(m_paypalClientId);

        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration); // configuration above
        startService(m_service); // paypal service, listening to calls to paypal app

        //m_cart = new Cart();

        /*Product products[] =
                {
                        new Product("product 1", 15.20),
                        new Product("product 2", 19.30),
                        new Product("product 3", 8.13),
                        new Product("product 4", 55.42),
                        new Product("product 5", 23.99),
                        new Product("product 6", 15.42),
                        new Product("product 7", 99.33)
                };

        for(int i = 0 ; i < products.length ; i++)
        {
            Button button = new Button(this);
            button.setText(products[i].getName() + " --- " + products[i].getValue() + " $");
            button.setTag(products[i]);

            // display
            button.setTextSize(40);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    200, Gravity.CENTER);
            layoutParams.setMargins(20, 50, 20, 50);
            button.setLayoutParams(layoutParams);

            button.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    Button button = (Button) view;
                    Product product = (Product) button.getTag();

                    m_cart.addToCart(product);
                    m_response.setText("Total cart value = " + String.format("%.2f", m_cart.getValue()) + " $");
                }
            });

            list.addView(button);
        }*/
    }

    void makeDonation(View view)
    {

        editText =  (EditText)findViewById(R.id.donationAmount);

        amount=editText.getText().toString();

        amount = amount.trim();

        if (amount.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DonateCharityActivity.this);
            builder.setMessage("Enter a valid amount.")
                    .setTitle("Incorrect Amount")
                    .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        else {

            amount_double = Double.parseDouble(amount);

            PayPalPayment cart = new PayPalPayment(new BigDecimal(amount_double), "USD", "Cart",
                    PayPalPayment.PAYMENT_INTENT_SALE);

            Intent intent = new Intent(this, PaymentActivity.class); // it's not paypalpayment, it's paymentactivity !
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, cart);
            startActivityForResult(intent, m_paypalRequestCode);

        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == m_paypalRequestCode)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                // we have to confirm that the payment worked to avoid fraud
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirmation != null)
                {
                    String state = confirmation.getProofOfPayment().getState();

                    if(state.equals("approved")) // if the payment worked, the state equals approved
                        m_response.setText("payment approved");
                    else
                        m_response.setText("error in the payment");
                }
                else
                    m_response.setText("confirmation is null");
            }
        }
    }
}
