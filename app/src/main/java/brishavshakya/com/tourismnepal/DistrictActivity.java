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

import brishavshakya.com.tourismnepal.Adapter.DistrictAdapter;
import brishavshakya.com.tourismnepal.Controller.AppController;
import brishavshakya.com.tourismnepal.Model.DistrictModel;

public class DistrictActivity extends AppCompatActivity {
    private static final String dataUrlObj = "https://demo8059979.mockable.io/areas";
    private ArrayList<DistrictModel> list_data;
    private GridView gridView;
    DistrictAdapter adapter;
    int ProvinceNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.district_layout);
        gridView = (GridView) findViewById(R.id.gridView);
        list_data = new ArrayList<>();
        Intent intent = getIntent();
        ProvinceNumber = Integer.parseInt(intent.getStringExtra("ProvinceNumber"));
        //Retreive Dsitrict Data
        getDistrictData();
        Log.i("ProvinceNumber", String.valueOf(ProvinceNumber));
    }

    private void getDistrictData() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,dataUrlObj,null,
        new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Response from the server
                Log.i("Response123", String.valueOf(response.length()));
                try {
                    JSONArray provinceArray = response.getJSONArray("data");
                    JSONObject dataobject = provinceArray.getJSONObject(0);
                    JSONArray districtArray = dataobject.getJSONArray("districts");


                    Log.i("Response123", String.valueOf(districtArray.length()));
                    Log.i("Response12", String.valueOf(ProvinceNumber));
                    String nameValue = provinceArray.getJSONObject(0).getJSONArray("districts").getJSONObject(0)
                            .getJSONArray("locations").getJSONObject(0).getString("name");

                    for ( int i=0;i<=provinceArray.length();i++) {
                        for (int j=0;j<districtArray.length();j++){
                            if (i == ProvinceNumber) {
                                String districtname = provinceArray.getJSONObject(j).getJSONArray("districts")
                                        .getJSONObject(j).getString("name");
                                String districtId = provinceArray.getJSONObject(j).getJSONArray("districts")
                                        .getJSONObject(j).getString("id");
                                String districtPicture = provinceArray.getJSONObject(j).getJSONArray("districts")
                                        .getJSONObject(j).getString("picture_url");
                                Log.i("district", "Name:" + districtPicture);
                                DistrictModel listData = new DistrictModel(districtId,districtname, districtPicture,ProvinceNumber);
                                list_data.add(listData);

                            }
                            adapter = new DistrictAdapter(getApplicationContext(), R.layout.grid_list, list_data);
                            gridView.setAdapter(adapter);

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
