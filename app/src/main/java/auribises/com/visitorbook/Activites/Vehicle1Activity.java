package auribises.com.visitorbook.Activites;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import auribises.com.visitorbook.Adapters.VehicleAdapter;
import auribises.com.visitorbook.Class.Vehicle;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class Vehicle1Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @InjectView(R.id.listView)
    ListView listView;

    @InjectView(R.id.editTextSearch)
    EditText eTxtSearch;

    ArrayList<Vehicle> vehiclesList;

    ContentResolver resolver;

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
        resolver = getContentResolver();


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

    void retrieveFromCloud(){

        progressDialog.show();

        vehiclesList = new ArrayList<>();

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

                        vehiclesList.add(new Vehicle(id,n,p,e,g,v,vn));
                    }

                    adapter = new VehicleAdapter(Vehicle1Activity.this,R.layout.vehicle1_list_item, vehiclesList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(Vehicle1Activity.this);

                    progressDialog.dismiss();

                }catch (Exception e){
                    Log.i("test",e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(Vehicle1Activity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(Vehicle1Activity.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }

    void retrieveFromDB(){
        //1. Retrieve Data from DB
        //2. Convert Each Record into an Object of Type Teacher
        //3. Put the objects into ArrayList

        vehiclesList = new ArrayList<>();

        String[] projection = {Util.COL_IDVEHICLE,Util.COL_NAMEVEHICLE,Util.COL_PHONEVEHICLE,Util.COL_EMAILVEHICLE,Util.COL_GENDERVEHICLE,Util.COL_VEHICLEVEHICLE,Util.COL_VEHICLENUMBERVEHICLE};

        Cursor cursor = resolver.query(Util.VEHICLE_URI,projection,null,null,null);

        if(cursor!=null){

            int i=0;
            String n="",p="",e="",g="",v="",vn="";

            while (cursor.moveToNext()){
                i = cursor.getInt(cursor.getColumnIndex(Util.COL_IDVEHICLE));
                n = cursor.getString(cursor.getColumnIndex(Util.COL_NAMEVEHICLE));
                p = cursor.getString(cursor.getColumnIndex(Util.COL_PHONEVEHICLE));
                e = cursor.getString(cursor.getColumnIndex(Util.COL_EMAILVEHICLE));
                g = cursor.getString(cursor.getColumnIndex(Util.COL_GENDERVEHICLE));
                v = cursor.getString(cursor.getColumnIndex(Util.COL_VEHICLEVEHICLE));
                vn = cursor.getString(cursor.getColumnIndex(Util.COL_VEHICLENUMBERVEHICLE));

                vehiclesList.add(new Vehicle(i,n,p,e,g,v,vn));
            }

            adapter = new VehicleAdapter(this,R.layout.vehicle1_list_item, vehiclesList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos = i;
        vehicle = vehiclesList.get(i);
        showOptions();
    }

    void showOptions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items ={"View"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i){
                    case 0:
                        showVehicle();
                        break;

                    case 1:
                        Intent intent = new Intent(Vehicle1Activity.this,Vehicle1Activity.class);
                        intent.putExtra("keyVehicle",vehicle);
                        startActivity(intent);
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
}
