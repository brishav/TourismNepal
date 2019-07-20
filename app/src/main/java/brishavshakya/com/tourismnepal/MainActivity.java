package brishavshakya.com.tourismnepal;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import brishavshakya.com.tourismnepal.Adapter.ProvinceAdapter;
import brishavshakya.com.tourismnepal.Controller.AppController;
import brishavshakya.com.tourismnepal.Model.ProvinceModel;

public class MainActivity extends AppCompatActivity {
    private static final String dataUrlObj = "http://lijeshshakya.000webhostapp.com/api/v1/provinces?token=";
    ArrayList<ProvinceModel> dataModels;
    private Button cancel;
    ListView listView;
    private ProgressDialog pDialog;
    private static ProvinceAdapter adapter;
    String token;
    private  static  String  objUrl="";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.province_list_main);
        Intent intent = getIntent();
        token = intent.getStringExtra("tokenprovince");
        listView  = findViewById(R.id.list);
        cancel = findViewById(R.id.cancelButton);
        objUrl=dataUrlObj+token;
        Log.d("tokenprovince",objUrl);
        sharedPreferences = getSharedPreferences("userToken",0);

        getProvinceData();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });


//        dataModels = new ArrayList<>();
//        dataModels.add(new ProvinceModel(1,"Province 1","#117A65"));
//        dataModels.add(new ProvinceModel(2,"Province 2","#873600"));
//        dataModels.add(new ProvinceModel(3,"Province 3","#85C1E9"));
//        dataModels.add(new ProvinceModel(4,"Province 4","#633974"));
//        dataModels.add(new ProvinceModel(5,"Province 5","#5D6D7E"));
//        dataModels.add(new ProvinceModel(6,"Province 6","#2C3E50"));
//        dataModels.add(new ProvinceModel(7,"Province 7","#512E5F"));
//        adapter= new ProvinceAdapter(dataModels,getApplicationContext());
//        listView.setAdapter(adapter);
    }

    private void getProvinceData() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,dataUrlObj+token,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dataModels = new ArrayList<>();
                        try {

                            JSONArray province = response.getJSONArray("data");
                            for (int i=0;i<province.length();i++){

                                JSONObject finalobject = province.getJSONObject(i);
                                dataModels.add(new ProvinceModel(Integer.parseInt(finalobject.getString("id")),finalobject.getString("name"),"#117A65"));

                            }
                            adapter= new ProvinceAdapter(dataModels,getApplicationContext());
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            Log.i("Err", String.valueOf(error));
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

                                editor.clear();
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

