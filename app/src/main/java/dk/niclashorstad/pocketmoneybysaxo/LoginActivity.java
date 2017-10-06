package dk.niclashorstad.pocketmoneybysaxo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    // Dummy login info
    private static final String CPR = "2212922555";
    private static final String SERVICE_CODE = "1942";
    // Views
    private EditText inputCpr;
    private EditText inputServiceCode;
    private TextView textViewError;
    private View viewAjaxLoader;
    private Button loginButton;
    // Login Task
    private UserLoginTask loginTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Instantiate variables
        inputCpr = (EditText) findViewById(R.id.cpr);
        inputServiceCode = (EditText) findViewById(R.id.service_code);
        textViewError = (TextView) findViewById(R.id.login_error);
        viewAjaxLoader = findViewById(R.id.ajax_loader);
        loginButton = (Button) findViewById(R.id.login_button);

        // If "Enter" is clicked on software keyboard
        inputCpr.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    inputServiceCode.requestFocus();
                    return true;
                }
                return false;
            }
        });
        inputServiceCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    validate();
                    return true;
                }
                return false;
            }
        });

        // If login Button is clicked, run login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }


    private void validate() {

        boolean error_exists = false;

        // Exit if login attempt currently being performed
        if (loginTask != null) {
            return;
        }
        hideKeyboard();
        // Hide error on new attempt
        textViewError.setVisibility(View.GONE);

        // Save user input
        String cpr = inputCpr.getText().toString();
        String service_code = inputServiceCode.getText().toString();

        // Validation
        if (cpr.length() < 10) {
            textViewError.setText(getString(R.string.error_cpr_wrong_length));
            textViewError.setVisibility(View.VISIBLE);
            error_exists = true;
        }
        if (service_code.length() < 4) {
            textViewError.setText(getString(R.string.error_service_code_wrong_length));
            textViewError.setVisibility(View.VISIBLE);
            error_exists = true;
        }
        if (TextUtils.isEmpty(cpr)) {
            textViewError.setText(getString(R.string.error_missing_cpr));
            textViewError.setVisibility(View.VISIBLE);
            error_exists = true;
        }
        if (TextUtils.isEmpty(service_code)) {
            textViewError.setText(getString(R.string.error_missing_service_code));
            textViewError.setVisibility(View.VISIBLE);
            error_exists = true;
        }

        // Launch asynchronous UserLoginTask
        if (!error_exists) {
            showAjaxLoader(true);
            loginTask = new UserLoginTask(cpr, service_code);
            loginTask.execute((Void) null);
        } else {
            inputCpr.requestFocus();
        }

    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String cpr;
        private final String service_code;

        // Assigns the private Strings to the input of the new UserLoginTask instance
        UserLoginTask(String _cpr, String _service_code) {
            cpr = _cpr;
            service_code = _service_code;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            // TODO: attempt authentication against a network service.
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            return (cpr.equals(CPR) && service_code.equals(SERVICE_CODE)); // Sent to onPostExecute

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            loginTask = null;
            showAjaxLoader(false);

            View mainContainerView = findViewById(R.id.main_container);

            if (success) {
                // TODO: redirect to new screen

                //Snackbar allahuSnackbar = Snackbar.make(mainContainerView, "Login success", Snackbar.LENGTH_LONG );
                //allahuSnackbar.setActionTextColor(Color.GREEN).show();

                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                startActivity(intent);
            } else {
                textViewError.setText(getString(R.string.error_invalid_login));
                textViewError.setVisibility(View.VISIBLE);
                inputCpr.requestFocus();
                Snackbar allahuSnackbar = Snackbar.make(mainContainerView, "Login failed", Snackbar.LENGTH_LONG );
                allahuSnackbar.setActionTextColor(Color.RED).show();
            }
        }

        @Override
        protected void onCancelled() {
            loginTask = null;
            showAjaxLoader(false);
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showAjaxLoader(final boolean show) {

            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            viewAjaxLoader.setVisibility(show ? View.VISIBLE : View.GONE);
            viewAjaxLoader.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    viewAjaxLoader.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}