package com.example.ritesh.jointapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class PaypalWebviewActivity extends AppCompatActivity {

    WebView PayPalWV;
    String payKey = "", result = "", json = "", webPayPalID = "";
    ProgressDialog progressDialog;
    double webpaymentAmount = 0;
    double webpaymentAmountTwo = 0;
    double webpaymentAmountfinaltransffred = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_webview);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);


        PayPalWV = (WebView) findViewById(R.id.PayPalWebView);

        Bundle extra = getIntent().getExtras();
        webpaymentAmount = Double.parseDouble(extra.getString("PaymentAmount"));
        webPayPalID = extra.getString("paypalID");
//        paymentAmount = Double.parseDouble(String.valueOf(50.25));
//        webpaymentAmountTwo = webpaymentAmount - 20;
//        webpaymentAmountTwo = webpaymentAmount - 20;
//        webpaymentAmountTwo = (int)(webpaymentAmount *(3.20f/100.00f));  //Using Domestic fee rate of 2.9% + $.30 ($0 to $3000) = $ 3.20
        webpaymentAmountTwo = (int)(3.0f * webpaymentAmount) / 100.0f;  // after cutting 3% of entered amount rest is transfered
        //Using Domestic fee rate of 2.9% + $.30 ($0 to $3000) = $ 3.20
        webpaymentAmountfinaltransffred = webpaymentAmount - webpaymentAmountTwo;




        Log.e("Both Amount AND KEY", "\n" +
                "webpaymentAmount  " + "" + webpaymentAmount
                + ">>>>>" + "\n" +
                "webpaymentAmountTwo  " + "" + webpaymentAmountTwo
                + ">>>>>" + "\n" +
                "webpaymentAmountfinaltransffred  " + "" + webpaymentAmountfinaltransffred
                + ">>>>>" + "\n"
                + "webPayPalID Key  " + "" + webPayPalID);

        new JsonPaypal().execute();

    }

    @SuppressWarnings("deprecation")
    private class JsonPaypal extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            Log.e("JsonPaypal onPreExecute :", "OK");
            Log.e("JsonPaypal onPreExecute :", "OK");

            Log.e("JsonPaypal webPayPalID ID :", "" + webPayPalID);
            Log.e("JsonPaypal PyaPal Amount ONE :", "" + webpaymentAmount);
            Log.e("JsonPaypal PyaPal Amount TWO :", "" + webpaymentAmountTwo);

            super.onPreExecute();
            progressDialog.show();
        }

        /*first method (Start)*/
        @Override
        protected String doInBackground(String... paramss) {

            Log.e("JsonPaypal doInBackground :", "OK");
            Log.e("JsonPaypal doInBackground :", "OK");

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("https://svcs.sandbox.paypal.com/AdaptivePayments/Pay");

            try {

                Log.e("JsonPaypal try Block ", "OK");
                Log.e("JsonPaypal try Block ", "OK");

                post.setHeader("X-PAYPAL-SECURITY-USERID", "riteshkumar.bussiness_api1.gmail.com"); // jiske account se paypal integrate hua hai
                post.setHeader("X-PAYPAL-SECURITY-PASSWORD", "8QKN6YJJC5EVAVBU");
                post.setHeader("X-PAYPAL-SECURITY-SIGNATURE", "AFcWxV21C7fd0v3bYYYRCpSSRl31Av21JIEsc7vYb8Jm2nPTuX3bKWUG");
                post.setHeader("X-PAYPAL-REQUEST-DATA-FORMAT", "NV");
                post.setHeader("X-PAYPAL-RESPONSE-DATA-FORMAT", "JSON");
                post.setHeader("X-PAYPAL-APPLICATION-ID", "APP-80W284485P519543T");

                List<NameValuePair> nameValuePairs = new ArrayList<>();

                nameValuePairs.add(new BasicNameValuePair("actionType", "PAY"));
                /*params.add(new BasicNameValuePair("senderEmail", "gaurav.technorizen.personal@gmail.com"));*/

                nameValuePairs.add(new BasicNameValuePair("currencyCode", "USD"));
                nameValuePairs.add(new BasicNameValuePair("feesPayer", "EACHRECEIVER"));

//                params.add(new BasicNameValuePair("receiverList.receiver(0).amount", "1500"));
                nameValuePairs.add(new BasicNameValuePair("receiverList.receiver(0).amount", String.valueOf(webpaymentAmount)));
                nameValuePairs.add(new BasicNameValuePair("receiverList.receiver(0).email", "riteshkumar.bussiness@gmail.com")); // jiske account me commission jayega
                nameValuePairs.add(new BasicNameValuePair("receiverList.receiver(0).primary", "true"));

                nameValuePairs.add(new BasicNameValuePair("receiverList.receiver(1).amount", String.valueOf(webpaymentAmountfinaltransffred)));
//                params.add(new BasicNameValuePair("receiverList.receiver(1).amount", "1499"));
                nameValuePairs.add(new BasicNameValuePair("receiverList.receiver(1).email", "riteshkumar.personl@gmail.com")); // jiske account me paise bhejna hai
                nameValuePairs.add(new BasicNameValuePair("receiverList.receiver(1).primary", "false"));

                nameValuePairs.add(new BasicNameValuePair("memo", "Testing"));
                nameValuePairs.add(new BasicNameValuePair("requestEnvelope.errorLanguage", "en_US"));
                nameValuePairs.add(new BasicNameValuePair("returnUrl", "http://www.shopinhome.com/"));
                nameValuePairs.add(new BasicNameValuePair("cancelUrl", "http://technorizen.com/company/"));

                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String obj = EntityUtils.toString(response.getEntity());
                Log.e("************ All INCOMING PayPal DATA VALUE ************", "" + obj);
                JSONObject ParentObject = new JSONObject(obj);
                String alldata = ParentObject.getString("responseEnvelope");
                JSONObject EnvelopeObject = new JSONObject(alldata);
                String ack = EnvelopeObject.getString("ack");
                Log.e("************Json All data*******************", "" + alldata);

                if (ParentObject.has("payKey")) {
                    payKey = ParentObject.getString("payKey");
                    Log.e("payKey :", "" + payKey);
                    Log.e("payKey :", "" + payKey);
                    Log.e("payKey :", "" + payKey);

                }

                return ack;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {

                progressDialog.dismiss();
                Toast.makeText(PaypalWebviewActivity.this, "Server Problem", Toast.LENGTH_SHORT).show();
                finish();

            } else if (result.equalsIgnoreCase("Failure")) {

                progressDialog.dismiss();
                Toast.makeText(PaypalWebviewActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                finish();

            } else if (result.equalsIgnoreCase("Success")) {

                PayPalWV.getSettings().setJavaScriptEnabled(true);
                PayPalWV.loadUrl("https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_ap-payment&paykey=" + payKey);
                PayPalWV.setWebViewClient(new WebViewClient() {

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        Log.e("Check WV", "Redirecting URL " + url);

                        if (url.startsWith("http://www.shopinhome.com/")) {

                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("Result", "Success");
                            returnIntent.putExtra("PayKey", payKey);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();

//                            new JsonPaypalDetail().execute();

                            return true;

                        } else if (url.startsWith("http://technorizen.com/company/")) {

                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("Result", "Fail");
                            returnIntent.putExtra("PayKey", payKey);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                            return true;

                        }

                        return false;
                    }

                    @Override
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        super.onReceivedError(view, errorCode, description, failingUrl);

                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);


                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);

                        progressDialog.dismiss();

                    }


                });
            }

            Log.e("InPost", "yes");

        }

    }


}