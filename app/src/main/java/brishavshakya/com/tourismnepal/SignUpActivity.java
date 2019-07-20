package brishavshakya.com.tourismnepal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private static final String URL_FOR_REGISTRATION = "https://lijeshshakya.000webhostapp.com/api/v1/register";
    EditText username , password , repassword, email ;
    RadioGroup gender;
    Spinner country;
    Button register;
    String uname , pword , repword , uemail , ugender , ctry;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        username = findViewById(R.id.newUsername);
        password = findViewById(R.id.newPassword);
        repassword = findViewById(R.id.newConfirmPassword);
        country = findViewById(R.id.newCountry);
        email = findViewById(R.id.newEmail);
        register = findViewById(R.id.confirmRegister);
        gender = findViewById(R.id.new_genderradiobutton);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                country.getSelectedItemId();

                submitForm();
            }
        });

    }

    private void submitForm() {

            int selectedId = gender.getCheckedRadioButtonId();
            Log.i("GenderId", String.valueOf(selectedId));
            String gender;
            if(selectedId == R.id.gender_female) {
                gender = "Female";
            }
            else if(selectedId == R.id.gender_male){
                gender = "Male";
            }
            else {
                gender = "Other";
            }
            registerUser(username.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString(),
                    gender,
                    repassword.getText().toString(),
                    (int) country.getSelectedItemId());
        }

        private void registerUser(final String name, final String email, final String password,
                                  final String gender, final String repassword, final int countryId) {
            // Tag used to cancel the request
            String cancel_req_tag = "register";

            progressDialog.setMessage("Adding you ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    URL_FOR_REGISTRATION, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("Response",response);
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        if (!error) {
                            String user = jObj.getJSONObject("user").getString("name");
                            Toast.makeText(getApplicationContext(), "Hi " + user +", You are successfully Added!", Toast.LENGTH_SHORT).show();

                            // Launch login activity
                            Intent intent = new Intent(
                                    SignUpActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i( "Registration Error: " , error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("gender", gender);
                    params.put("confrimation_password", repassword);
                    params.put("coutry_id", String.valueOf(countryId));
                    return params;
                }
            };
            // Adding request to request queue
            AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, cancel_req_tag);
        }

        private void showDialog() {
            if (!progressDialog.isShowing())
                progressDialog.show();
        }

        private void hideDialog() {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }

}
