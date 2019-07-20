package brishavshakya.com.tourismnepal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private String URLline = "https://lijeshshakya.000webhostapp.com/api/v1/login";

    EditText email,password;
    Button signInButton;
    TextView signUpPage;

    SharedPreferences sharedPreferences;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);


            email = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);
        signInButton = findViewById(R.id.signInButton);
        signUpPage = findViewById(R.id.signUpPage);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginUser();


            }

        });
        signUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
             }
        });

        }

        private void loginUser () {
            final String email1 = email.getText().toString().trim();
            final String pword = password.getText().toString().trim();
            if (email1.isEmpty() || pword.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Empty Fields Detected", Toast.LENGTH_LONG).show();
            } else {
                pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Please Wait...");
                pd.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLline,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                parseData(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "Invalid email/password", Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email1);
                        params.put("password", pword);

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        }

        public void parseData (String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equals("true")) {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "Logged in ", Toast.LENGTH_LONG).show();
                    String token = jsonObject.getString("token");

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("tokenprovince",token);
                    startActivity(intent);
                    finish();


                } else {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "User not found or maybe is not registered ", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        }
}










