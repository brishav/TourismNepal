package brishavshakya.com.tourismnepal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import brishavshakya.com.tourismnepal.Controller.AppController;

public class DemoActivity extends AppCompatActivity {

    //Province Number Transferred from Province Activity
    int ProvinceNumber;
    //api linkname
    String dataUrlObj = "https://demo8059979.mockable.io/areas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demmy);

        SharedPreferences sharedPreferences = getSharedPreferences("userToken", MODE_PRIVATE);
        Log.d("tokenview",sharedPreferences.getString("token",null));

        Intent intent = getIntent();
//        ProvinceNumber = Integer.parseInt(intent.getStringExtra("ProvinceNumber"));
        Log.d("ProvinceNumber", String.valueOf(ProvinceNumber));
        makeJsonObjectRequest();
    }

    private void makeJsonObjectRequest() {
//        JsonArrayRequest req = new JsonObjectRequest(dataUrlObj,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response1) {
//                        Log.d("Response", response1.toString());
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        VolleyLog.d("Boley", "Error: " + error.getMessage());
//                        Log.d("Error message", error.getMessage());
//                        Toast.makeText(getApplicationContext(),
//                                error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(dataUrlObj, null, responseObj, errorListener));
    }


    public Response.Listener<JSONObject> responseObj = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.i("response", "response:" + response);
            try {
                JSONArray dataArray = response.getJSONArray("data");
                String nameValue = dataArray.getJSONObject(0).getJSONArray("districts").getJSONObject(0).getJSONArray("locations").getJSONObject(0).getString("name");
                for ( int i=0;i<dataArray.length();i++) {
                    String districtname = dataArray.getJSONObject(0).getJSONArray("districts").getJSONObject(i).getString("name");
                    Log.i("name", "Name:" + nameValue);
                    Log.i("district", "Name:" + districtname);

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

};
    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };
}


