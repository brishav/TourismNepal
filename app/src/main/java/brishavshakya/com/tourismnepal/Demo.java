package brishavshakya.com.tourismnepal;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
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

import java.util.ArrayList;

import brishavshakya.com.tourismnepal.Adapter.DistrictAdapter;
import brishavshakya.com.tourismnepal.Model.DistrictModel;

public class Demo extends AppCompatActivity{
    private static final String HI ="https://uniqueandrocode.000webhostapp.com/hiren/androidweb.php" ;
    private ArrayList<DistrictModel>list_data;
    private GridView gridView;
    DistrictAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.district_layout);
        gridView=(GridView)findViewById(R.id.gridView);
        list_data=new ArrayList<>();

        // adapter=new DistrictAdapter(getApplicationContext(),list_data);
        getData();
    }

    private void getData() {
        StringRequest stringRequest =new StringRequest(Request.Method.GET, HI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=0; i<array.length(); i++){
                        JSONObject ob=array.getJSONObject(i);
                        DistrictModel listData=new DistrictModel(ob.getString("name"),ob.getString("imageurl"));
                        list_data.add(listData);
                    }
                    adapter=new DistrictAdapter(getApplicationContext(),R.layout.grid_list,list_data);
                    gridView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
