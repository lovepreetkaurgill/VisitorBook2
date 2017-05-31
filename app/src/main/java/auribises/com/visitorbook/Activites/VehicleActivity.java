package auribises.com.visitorbook.Activites;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import auribises.com.visitorbook.Class.Vehicle;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class VehicleActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    @InjectView(R.id.editTextName)
    EditText eTxtName;

    @InjectView(R.id.editTextPhone)
    EditText eTxtPhone;

    @InjectView(R.id.editTextEmail)
    EditText eTxtEmail;

    @InjectView(R.id.radioButtonMale)
    RadioButton rbMale;

    @InjectView(R.id.radioButtonFemale)
    RadioButton rbFemale;

    @InjectView(R.id.editTextVehicle)
    EditText eTxtVehicle;

    @InjectView(R.id.editTextVehicleNumber)
    EditText eTxtVehicleNumber;

    @InjectView(R.id.buttonSubmit)
    Button btnSubmit;

    Vehicle vehicle, rcvVehicle;

    ContentResolver resolver;

    boolean updateMode;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        ButterKnife.inject(this);

        preferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        editor = preferences.edit();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        vehicle = new Vehicle();

        rbMale.setOnCheckedChangeListener(this);
        rbFemale.setOnCheckedChangeListener(this);

        requestQueue = Volley.newRequestQueue(this);

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyVehicle");

        if(updateMode){
            rcvVehicle = (Vehicle)rcv.getSerializableExtra("keyVehicle");
            eTxtName.setText(rcvVehicle.getName());
            eTxtPhone.setText(rcvVehicle.getPhone());
            eTxtEmail.setText(rcvVehicle.getEmail());
            eTxtVehicle.setText(rcvVehicle.getVehicle());
            eTxtVehicleNumber.setText(rcvVehicle.getVehiclenumber());

            if(rcvVehicle.getGender().equals("Male")){
                rbMale.setChecked(true);
            }else{
                rbFemale.setChecked(true);
            }

            btnSubmit.setText("Update");
        }
    }

    boolean isNetworkConected(){

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        Log.i("insertIntoCloud",vehicle.toString());
        return (networkInfo!=null && networkInfo.isConnected());

    }

    public void clickHandler(View view){
        if(view.getId() == R.id.buttonSubmit){

            vehicle.setName(eTxtName.getText().toString().trim());
            vehicle.setPhone(eTxtPhone.getText().toString().trim());
            vehicle.setEmail(eTxtEmail.getText().toString().trim());
            vehicle.setVehicle(eTxtVehicle.getText().toString().trim());
            vehicle.setVehiclenumber(eTxtVehicleNumber.getText().toString().trim());

            if(validateFields()) {
                if (isNetworkConected())
                    insertIntoCloud();
            } else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
            }
        }

    void insertIntoCloud(){

        String url="";

        if(!updateMode){
            url = Util.INSERT_VEHICLE_PHP;
        }else{
            url = Util.UPDATE_VEHICLE_PHP;
        }

        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            Log.i("test",response.toString());
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if(success == 1){
                        Toast.makeText(VehicleActivity.this,message,Toast.LENGTH_LONG).show();

                        if(updateMode)
                            finish();

                    }else{
                        Toast.makeText(VehicleActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("test",e.toString());
                    progressDialog.dismiss();
                    Toast.makeText(VehicleActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("test",error.toString());
                Toast.makeText(VehicleActivity.this,"Some Error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                Log.i("test",vehicle.toString());
                if(updateMode)
                    map.put("id",String.valueOf(rcvVehicle.getId()));
                map.put("name", vehicle.getName());
                map.put("phone", vehicle.getPhone());
                map.put("email", vehicle.getEmail());
                map.put("gender", vehicle.getGender());
                map.put("vehicle", vehicle.getVehicle());
                map.put("vehiclenumber", vehicle.getVehiclenumber());

                return map;
            }
        };

        requestQueue.add(request);

        clearFields();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();

        if(b) {
            if (id == R.id.radioButtonMale) {
                vehicle.setGender("Male");
            } else {
                vehicle.setGender("Female");
            }
        }
    }

    void insertIntoDB(){

        ContentValues values = new ContentValues();

        values.put(Util.COL_NAMEVEHICLE,vehicle.getName());
        values.put(Util.COL_PHONEVEHICLE,vehicle.getPhone());
        values.put(Util.COL_EMAILVEHICLE,vehicle.getEmail());
        values.put(Util.COL_GENDERVEHICLE,vehicle.getGender());
        values.put(Util.COL_VEHICLEVEHICLE,vehicle.getVehicle());
        values.put(Util.COL_VEHICLENUMBERVEHICLE,vehicle.getVehiclenumber());

         if(!updateMode){
            Uri dummy = resolver.insert(Util.VEHICLE_URI,values);
            Toast.makeText(this,vehicle.getName()+ " Registered Successfully "+dummy.getLastPathSegment(),Toast.LENGTH_LONG).show();

            Log.i("Insert",vehicle.toString());

            clearFields();
        }else{
            String where = Util.COL_IDVEHICLE + " = "+rcvVehicle.getId();
            int i = resolver.update(Util.VEHICLE_URI,values,where,null);
            if(i>0){
                Toast.makeText(this,"Updation Successful",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    void clearFields(){
        eTxtName.setText("");
        eTxtEmail.setText("");
        eTxtPhone.setText("");
        rbMale.setChecked(false);
        rbFemale.setChecked(false);
        eTxtVehicle.setText("");
        eTxtVehicleNumber.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0,101,0,"All Vehicles");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            Log.i("test","---0");
            Intent i = new Intent(VehicleActivity.this,AllVehicleActivity.class);
            startActivityForResult(i,301);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("test","----2");
        if(requestCode==301&&resultCode==302){
            Log.i("test","----1");
            finish();
        }
    }

    boolean validateFields(){
        boolean flag = true;

        if(vehicle.getName().isEmpty()){
            flag = false;
            eTxtName.setError("Please Enter Name");
        }

        if(vehicle.getPhone().isEmpty()){
            flag = false;
            eTxtPhone.setError("Please Enter Phone");
        }else{
            if(vehicle.getPhone().length()<10){
                flag = false;
                eTxtPhone.setError("Please Enter 10 digits Phone Number");
            }
        }

        if(vehicle.getEmail().isEmpty()){
            flag = false;
            eTxtEmail.setError("Please Enter Email");
        }else{
            if(!(vehicle.getEmail().contains("@") && vehicle.getEmail().contains("."))){
                flag = false;
                eTxtEmail.setError("Please Enter correct Email");
            }
        }
        if(vehicle.getVehicle().isEmpty()){
            flag = false;
            eTxtVehicle.setError("Please Enter correct Vehicle");
        }
        if(vehicle.getVehiclenumber().isEmpty()){
            flag = false;
            eTxtVehicleNumber.setError("Please Enter correct VehicleNumber");
        }

        return flag;

    }
}
