package dk.niclashorstad.pocketmoneybysaxo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {

    private static final String TOKEN = "eyJhbGciOiJFUzI1NiIsIng1dCI6IkQ0QUU4MjQ2RDYyNTBFMTY5Njg4NDFCREY4Nzc2MTI4NUMwNUJCMUYifQ.eyJvYWEiOiI3Nzc3MCIsImlzcyI6Im9hIiwiYWlkIjoiMTA5IiwidWlkIjoia29GbGxXbXNMTjVEejRvOVJZb1FuQT09IiwiY2lkIjoia29GbGxXbXNMTjVEejRvOVJZb1FuQT09IiwiaXNhIjoiRmFsc2UiLCJ0aWQiOiIyMDAyIiwic2lkIjoiNjk5NTE5OGNmMDY2NDcwYTg4NTM0OWNlYjVkN2ZhZDIiLCJkZ2kiOiI4NCIsImV4cCI6IjE1MDczNjA5ODcifQ.MaMUmWkNeLmONzD5l1CADdBvXjYocM1FBatWDTrL_MmyhcG-m79QIdGoQTiw-7QbRUYCcCcP2HcVOki7rokrnA";
    private static final String URL_USERS = "https://gateway.saxobank.com/sim/openapi/port/v1/users/me";
    private static final String URL_ACCOUNTS = "https://gateway.saxobank.com/sim/openapi/port/v1/accounts/me";
    private String userKey;
    private String userName;

    private TextView txt_welcome = (TextView) findViewById(R.id.txt_welcome);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Step 1. Launch get request with token
        

        // Step 2. Extract name
        // Step 3. Override txt_welcome
    }
}
