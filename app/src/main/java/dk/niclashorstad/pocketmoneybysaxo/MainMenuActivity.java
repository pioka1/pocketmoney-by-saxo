package dk.niclashorstad.pocketmoneybysaxo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainMenuActivity extends AppCompatActivity {

    private static final String TOKEN = "eyJhbGciOiJFUzI1NiIsIng1dCI6IkQ0QUU4MjQ2RDYyNTBFMTY5Njg4NDFCREY4Nzc2MTI4NUMwNUJCMUYifQ.eyJvYWEiOiI3Nzc3MCIsImlzcyI6Im9hIiwiYWlkIjoiMTA5IiwidWlkIjoia29GbGxXbXNMTjVEejRvOVJZb1FuQT09IiwiY2lkIjoia29GbGxXbXNMTjVEejRvOVJZb1FuQT09IiwiaXNhIjoiRmFsc2UiLCJ0aWQiOiIyMDAyIiwic2lkIjoiNjk5NTE5OGNmMDY2NDcwYTg4NTM0OWNlYjVkN2ZhZDIiLCJkZ2kiOiI4NCIsImV4cCI6IjE1MDczNjA5ODcifQ.MaMUmWkNeLmONzD5l1CADdBvXjYocM1FBatWDTrL_MmyhcG-m79QIdGoQTiw-7QbRUYCcCcP2HcVOki7rokrnA";
    private static final String URL_USERS = "https://gateway.saxobank.com/sim/openapi/port/v1/users/me";
    private static final String URL_ACCOUNTS = "https://gateway.saxobank.com/sim/openapi/port/v1/accounts/me";
    private String userKey;
    private String userName;

    private TextView txt_welcome;

    private Button btn_view_balance;
    private Button btn_buy_currencies;
    private Button btn_sell_currencies;
    private Button btn_view_leaderboard;
    private Button btn_log_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        new JsonTask().execute(URL_USERS, TOKEN);

        btn_view_balance = (Button) findViewById(R.id.btn_view_balance);
        btn_view_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private class JsonTask extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                // params[0] = URL_USERS, params[1] = TOKEN
                URL url = new URL(params[0]);
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "bearer " + params[1]);

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
                txt_welcome = (TextView) findViewById(R.id.txt_welcome);
                txt_welcome.setText("Welcome " + json_obj.getString("Name") + "!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
