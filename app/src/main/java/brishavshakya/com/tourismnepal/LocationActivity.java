package brishavshakya.com.tourismnepal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import brishavshakya.com.tourismnepal.Adapter.LocationAdapter;
import brishavshakya.com.tourismnepal.Controller.AppController;
import brishavshakya.com.tourismnepal.Model.LocationModel;

public class LocationActivity extends AppCompatActivity {
    private static final String dataUrlObj = "https://demo8059979.mockable.io/areas";
    private ArrayList<LocationModel> location_data;
    private GridView gridView;
    int ProvinceNumber,DistrictNumber;
    LocationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.district_layout);
        gridView = (GridView) findViewById(R.id.gridView);
        location_data = new ArrayList<>();
        Intent intent = getIntent();
        ProvinceNumber = Integer.parseInt(intent.getStringExtra("ProvinceNumber"));
        DistrictNumber = Integer.parseInt(intent.getStringExtra("DistrictNumber"));
        Toast.makeText(getApplicationContext(),
                "Province Number is: "+ProvinceNumber+" and District Number is: "+DistrictNumber,Toast.LENGTH_LONG).show();
        getLocationData();
    }

    private void getLocationData() {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,dataUrlObj,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Response from the server
                        //Log.i("Response123", String.valueOf(response.length()));
                        Log.d("DistrictNumber", String.valueOf(DistrictNumber));
                        try {
                            JSONArray provinceArray = response.getJSONArray("data");
                            String nameValue = provinceArray.getJSONObject(0).getJSONArray("districts").getJSONObject(0)
                                    .getJSONArray("locations").getJSONObject(0).getString("name");

                            for ( int i=0;i<=provinceArray.length();i++) {

                                    JSONObject dataobject = provinceArray.getJSONObject(i);
                                    JSONArray districtArray = dataobject.getJSONArray("districts");
                                    for (int j = 0; j <= districtArray.length(); j++) {
                                        if (i+1 == ProvinceNumber){

                                            JSONObject locationObject = districtArray.getJSONObject(j);
                                            JSONArray locationArray = locationObject.getJSONArray("locations");
                                            Log.d("LocationNumber", String.valueOf(locationArray.length()));
                                            for (int k = 0; k <locationArray.length();k++){

                                                if (j+1 == DistrictNumber) {
                                                    Log.d("Sample","  "+provinceArray.getJSONObject(i).getString("id"));
                                                String locationName = provinceArray.getJSONObject(i).getJSONArray("districts")
                                                        .getJSONObject(j).getJSONArray("locations").getJSONObject(k).getString("name");
                                                String locationId = provinceArray.getJSONObject(i).getJSONArray("districts")
                                                        .getJSONObject(j).getJSONArray("locations").getJSONObject(k).getString("id");
                                                String locationPicture = provinceArray.getJSONObject(i).getJSONArray("districts")
                                                        .getJSONObject(j).getJSONArray("locations").getJSONObject(k).getString("picture_url");
                                                String locationDescription = provinceArray.getJSONObject(i).getJSONArray("districts")
                                                        .getJSONObject(j).getJSONArray("locations").getJSONObject(k).getString("description");
                                                Log.i("district", "Name:" +locationName);

                                                LocationModel listData = new LocationModel(locationId, locationName, locationPicture,locationDescription, ProvinceNumber, DistrictNumber);
                                                location_data.add(listData);

                                            }
                                            adapter = new LocationAdapter(getApplicationContext(), R.layout.grid_list, location_data);
                                            gridView.setAdapter(adapter);
                                            }


                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
        AppController.getInstance().addToRequestQueue(req);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                new AlertDialog.Builder(this)
                        .setTitle("Log Out")
                        .setMessage("Are you sure you wanna log out?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
                break;
            case R.id.setting:
                Toast.makeText(getApplicationContext(),"Setting menu",Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
