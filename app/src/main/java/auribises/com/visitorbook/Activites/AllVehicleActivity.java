package auribises.com.visitorbook.Activites;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import auribises.com.visitorbook.Adapters.VehicleAdapter;
import auribises.com.visitorbook.Class.Vehicle;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class AllVehicleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @InjectView(R.id.listView)
    ListView listView;

    @InjectView(R.id.editTextSearch)
    EditText eTxtSearch;

    ArrayList<Vehicle> vehicleList;

    VehicleAdapter adapter;

    Vehicle vehicle;
    int pos;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_vehicle);

        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(this);

        eTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if(adapter!=null){
                    adapter.filter(str);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        retrieveFromCloud();
    }
    void Vehicle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Activity Finish");
        builder.setMessage("Do you wish to Finish?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.putExtra("exitSignal",1);
                setResult(302,intent);
                finish();
                Log.i("test","----1");
            }

        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        Vehicle();
    }

    void retrieveFromCloud(){

        progressDialog.show();

        vehicleList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.RETRIEVE_VEHICLE_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("vehicle");

                    int id=0;
                    String n="",p="",e="",g="",v="",vn="";
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        id = jObj.getInt("id");
                        n = jObj.getString("name");
                        p = jObj.getString("phone");
                        e = jObj.getString("email");
                        g = jObj.getString("gender");
                        v = jObj.getString("vehicle");
                        vn = jObj.getString("vehiclenumber");

                        vehicleList.add(new Vehicle(id,n,p,e,g,v,vn));
                    }

                    adapter = new VehicleAdapter(AllVehicleActivity.this,R.layout.vehicle_list_item, vehicleList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(AllVehicleActivity.this);

                    progressDialog.dismiss();

                }catch (Exception e){
                    Log.i("test",e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AllVehicleActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(AllVehicleActivity.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos = i;
        vehicle = vehicleList.get(i);
        showOptions();
    }

    void showOptions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items ={"View","Update","Delete"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i){
                    case 0:
                        showVehicle();
                        break;

                    case 1:
                        Intent intent = new Intent(AllVehicleActivity.this,VehicleActivity.class);
                        intent.putExtra("keyVehicle", vehicle);
                        startActivity(intent);
                        break;

                    case 2:
                        deleteVehicle();
                        break;
                }
            }
        });

        builder.create().show();
    }

    void showVehicle(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details of "+ vehicle.getName());
        builder.setMessage(vehicle.toString());
        builder.setPositiveButton("Done",null);
        builder.create().show();
    }

    void deleteVehicle(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ vehicle.getName());
        builder.setMessage("Do you wish to Delete?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                deleteFromCloud();

            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.create().show();
    }

    void deleteFromCloud(){
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Util.DELETE_VEHICLE_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if(success == 1){
                        vehicleList.remove(pos);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(AllVehicleActivity.this,message,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(AllVehicleActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AllVehicleActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AllVehicleActivity.this,"Some Volley Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",String.valueOf(vehicle.getId()));
                return map;
            }
        };

        requestQueue.add(request);

    }
}
