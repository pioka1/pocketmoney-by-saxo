package dk.niclashorstad.pocketmoneybysaxo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ViewBalanceActivity extends AppCompatActivity {

    private static final String URL_BALANCES = "https://gateway.saxobank.com/sim/openapi/port/v1/balances";
    private String token;
    private String user_key;
    TextView textView_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_balance);

        Intent main_menu_intent = getIntent();

        token = main_menu_intent.getStringExtra("TOKEN");
        user_key = main_menu_intent.getStringExtra("USER_KEY");

        new FetchAccountBalance().execute(URL_BALANCES, user_key, token);
    }


    private class FetchAccountBalance extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                // params[0] = URL_USERS, params[1] = TOKEN
                URL url = new URL(params[0] + "?AccountKey=" + params[1] + "&ClientKey=" + params[1]);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "bearer " + params[2]);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json_stringified){
            super.onPostExecute(json_stringified);
            Log.d("JSON DATA", json_stringified);
            try {
                JSONObject json_obj = new JSONObject(json_stringified);
                textView_balance = (TextView) findViewById(R.id.textView_balance);
                textView_balance.setText(json_obj.getString("Currency") + " " + json_obj.getString("CashBalance"));
                Log.d("CashBalance", json_obj.getString("CashBalance"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
