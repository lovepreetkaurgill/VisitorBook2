package auribises.com.visitorbook.Activites;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import auribises.com.visitorbook.Class.Adminentry;
import auribises.com.visitorbook.Class.Visitorentry;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class AdminEntryActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {


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

    @InjectView(R.id.editTextAddress)
    EditText eTxtAddress;

    @InjectView(R.id.editTextPurpose)
    EditText eTxtPurpose;

    @InjectView(R.id.editTextDate)
    EditText eTxtDate;

    @InjectView(R.id.editTextTime)
    EditText eTxtTime;

    @InjectView(R.id.editTextAdmin)
    EditText eTxtAdmin;

    @InjectView(R.id.spinnerIDProof)
    Spinner spIDProof;
    ArrayAdapter<String> adapter1;

    @InjectView(R.id.editTextIDproofNumber)
    EditText eTxtIDProofNumber;

    @InjectView(R.id.spinnerVehicle)
    Spinner spVehicle;
    ArrayAdapter<String> adapter2;

    @InjectView(R.id.editTextVehicleNumber)
    EditText eTxtVehicleNumber;

    @InjectView(R.id.buttonSubmit)
    Button btnSubmit;

    Adminentry adminentry, rcvAdminentry;

    ContentResolver resolver;

    boolean updateMode;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    DatePickerDialog datePickerDialog;

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            eTxtDate.setText(i+"/"+(i1+1)+"/"+i2);

        }
    };

    TimePickerDialog timePickerDialog;
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            eTxtTime.setText(i+" : "+i1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_entry);

        ButterKnife.inject(this);

        preferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        editor = preferences.edit();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        adminentry = new Adminentry();

        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapter1.add("--Select IDProof--");
        adapter1.add("Aadhar Card");
        adapter1.add("License");
        adapter1.add("PAN Card");
        adapter1.add("Voter Card");

        spIDProof.setAdapter(adapter1);

        spIDProof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    adminentry.setIDProof(adapter1.getItem(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapter2.add("--Vehicle Type--");
        adapter2.add("None");
        adapter2.add("Car");
        adapter2.add("Bike");
        adapter2.add("Activa");


        spVehicle.setAdapter(adapter2);

        spVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    adminentry.setVehicle(adapter2.getItem(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rbMale.setOnCheckedChangeListener(this);
        rbFemale.setOnCheckedChangeListener(this);

        resolver = getContentResolver();

        requestQueue = Volley.newRequestQueue(this);

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyAdminentry");


        if(updateMode){
            rcvAdminentry = (Adminentry)rcv.getSerializableExtra("keyAdminentry");
            eTxtName.setText(rcvAdminentry.getName());
            eTxtPhone.setText(rcvAdminentry.getPhone());
            eTxtEmail.setText(rcvAdminentry.getEmail());
            eTxtAddress.setText(rcvAdminentry.getAddress());
            eTxtPurpose.setText(rcvAdminentry.getPurpose());
            eTxtDate.setText(rcvAdminentry.getDate());
            eTxtTime.setText(rcvAdminentry.getTime());
            eTxtAdmin.setText(rcvAdminentry.getAdmin());
            eTxtIDProofNumber.setText(rcvAdminentry.getIDProofnumber());
            eTxtVehicleNumber.setText(rcvAdminentry.getVehiclenumber());

            if(rcvAdminentry.getGender().equals("Male")){
                rbMale.setChecked(true);
            }else{
                rbFemale.setChecked(true);
            }

            int q = 0;
            for(int i=0;i<adapter1.getCount();i++){
                if(adapter1.getItem(i).equals(rcvAdminentry.getIDProof())){
                    Log.i("TEST2",adapter1.getItem(i)+" - "+rcvAdminentry.getIDProof());
                    q = i;
                    break;
                }
            }
            spIDProof.setSelection(q);

            int r = 0;
            for(int i=0;i<adapter2.getCount();i++){
                if(adapter2.getItem(i).equals(rcvAdminentry.getVehicle())){
                    Log.i("TEST3",adapter2.getItem(i)+" - "+rcvAdminentry.getVehicle());
                    r = i;
                    break;
                }
            }
            spVehicle.setSelection(r);

            btnSubmit.setText("Update");
        }
    }

    boolean isNetworkConected(){

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        Log.i("test",adminentry.toString());

        return (networkInfo!=null && networkInfo.isConnected());

    }

    public void onclickadmin(View view){
        if(view.getId() == R.id.buttonSubmit) {
            btnSubmit.setOnClickListener(this);

            adminentry.setName(eTxtName.getText().toString().trim());
            adminentry.setPhone(eTxtPhone.getText().toString().trim());
            adminentry.setEmail(eTxtEmail.getText().toString().trim());
            adminentry.setAddress(eTxtAddress.getText().toString().trim());
            adminentry.setPurpose(eTxtPurpose.getText().toString().trim());
            adminentry.setDate(eTxtDate.getText().toString().trim());
            adminentry.setTime(eTxtTime.getText().toString().trim());
            adminentry.setAdmin(eTxtAdmin.getText().toString().trim());
            adminentry.setIDProofnumber(eTxtIDProofNumber.getText().toString().trim());
            adminentry.setVehiclenumber(eTxtVehicleNumber.getText().toString().trim());

            //insertIntoDB();

            if(validateFields()) {
                if (isNetworkConected())
                    insertIntoCloud();
            }else
                Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
        }
    }


    void insertIntoCloud(){

        String url="";

        if(!updateMode){
            url = Util.INSERT_ADMINENTRY_PHP;
        }else{
            url = Util.UPDATE_ADMINENTRY_PHP;
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
                        Toast.makeText(AdminEntryActivity.this,message,Toast.LENGTH_LONG).show();
//
                        if(!updateMode){

                            editor.putString(Util.KEY_NAME, adminentry.getName());
                            editor.putString(Util.KEY_PHONE, adminentry.getPhone());
                            editor.putString(Util.KEY_EMAIL, adminentry.getEmail());
                            editor.putString(Util.KEY_ADDRESS, adminentry.getAddress());
                            editor.putString(Util.KEY_PURPOSE, adminentry.getPurpose());
                            editor.putString(Util.KEY_DATE, adminentry.getDate());
                            editor.putString(Util.KEY_TIME, adminentry.getTime());
                            editor.putString(Util.KEY_ADMIN, adminentry.getAdmin());
                            editor.putString(Util.KEY_IDPROOF, adminentry.getIDProof());
                            editor.putString(Util.KEY_IDPROOFNUMBER, adminentry.getIDProofnumber());
                            editor.putString(Util.KEY_VEHICLE, adminentry.getVehicle());
                            editor.putString(Util.KEY_VEHICLENUMBER, adminentry.getVehiclenumber());

                            editor.commit();

//                            Intent home = new Intent(VisitorEntryActivity.this,VisitorEntryActivity.class);
//                            startActivity(home);
//                            finish();
                        }

                        if(updateMode)
                            finish();

                    }else{
                        Toast.makeText(AdminEntryActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AdminEntryActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AdminEntryActivity.this,"Some Error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                Log.i("test",adminentry.toString());

                if(updateMode)
                    map.put("id",String.valueOf(rcvAdminentry.getId()));

                map.put("name", adminentry.getName());
                map.put("phone", adminentry.getPhone());
                map.put("email", adminentry.getEmail());
                map.put("gender", adminentry.getGender());
                map.put("address", adminentry.getAddress());
                map.put("purpose", adminentry.getPurpose());
                map.put("date", adminentry.getDate());
                map.put("time", adminentry.getTime());
                map.put("admin", adminentry.getAdmin());
                map.put("idproof", adminentry.getIDProof());
                map.put("idproofnumber", adminentry.getIDProofnumber());
                map.put("vehicle", adminentry.getVehicle());
                map.put("vehiclenumber", adminentry.getVehiclenumber());
                return map;
            }
        };

        requestQueue.add(request); // execute the request, send it ti server

        clearFields();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();

        if(b) {
            if (id == R.id.radioButtonMale) {
                adminentry.setGender("Male");
            } else {
                adminentry.setGender("Female");
            }
        }
    }

    void insertIntoDB(){

        ContentValues values = new ContentValues();

        values.put(Util.COL_NAMEVISITOR, adminentry.getName());
        values.put(Util.COL_PHONEVISITOR, adminentry.getPhone());
        values.put(Util.COL_EMAILVISITOR, adminentry.getEmail());
        values.put(Util.COL_GENDERVISITOR, adminentry.getGender());
        values.put(Util.COL_ADDRESSVISITOR, adminentry.getAddress());
        values.put(Util.COL_PURPOSEVISITOR, adminentry.getPurpose());
        values.put(Util.COL_DATEVISITOR, adminentry.getDate());
        values.put(Util.COL_TIMEVISITOR, adminentry.getTime());
        values.put(Util.COL_ADMINVISITOR, adminentry.getAdmin());
        values.put(Util.COL_IDPROOFVISITOR, adminentry.getIDProof());
        values.put(Util.COL_IDPROOFNUBERVISITOR, adminentry.getIDProofnumber());
        values.put(Util.COL_VEHICLEVISITOR, adminentry.getVehicle());
        values.put(Util.COL_VEHICLENUMBERVISITOR, adminentry.getVehiclenumber());

        if(!updateMode){
            Uri dummy = resolver.insert(Util.VISITORENTRY_URI,values);
            Toast.makeText(this, adminentry.getName()+ " Registered Successfully "+dummy.getLastPathSegment(),Toast.LENGTH_LONG).show();

            Log.i("insertintocloud", adminentry.toString());

            clearFields();
        }else{
            String where = Util.COL_IDVISITOR + " = "+ rcvAdminentry.getId();
            int i = resolver.update(Util.ADMINENTRY_URI,values,where,null);
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
        eTxtAddress.setText("");
        eTxtPurpose.setText("");
        eTxtDate.setText("");
        eTxtTime.setText("");
        eTxtAdmin.setText("");
        spIDProof.setSelection(0);
        eTxtIDProofNumber.setText("");
        spVehicle.setSelection(0);
        eTxtVehicleNumber.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0,101,0,"All Visitors");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            Log.i("test","---0");
            Intent i = new Intent(AdminEntryActivity.this,AllAdminentryActivity.class);
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

        if(adminentry.getName().isEmpty()){
            flag = false;
            eTxtName.setError("Please Enter Name");
        }

        if(adminentry.getPhone().isEmpty()){
            flag = false;
            eTxtPhone.setError("Please Enter Phone");
        }else{
            if(adminentry.getPhone().length()<10){
                flag = false;
                eTxtPhone.setError("Please Enter 10 digits Phone Number");
            }
        }

        if(adminentry.getEmail().isEmpty()){
            flag = false;
            eTxtEmail.setError("Please Enter Email");
        }else{
            if(!(adminentry.getEmail().contains("@") && adminentry.getEmail().contains("."))){
                flag = false;
                eTxtEmail.setError("Please Enter correct Email");
            }
        }
        if(adminentry.getAddress().isEmpty()){
            flag = false;
            eTxtAddress.setError("Please Enter Address");
        }
        if(adminentry.getPurpose().isEmpty()){
            flag = false;
            eTxtPurpose.setError("Please Enter Purpose");
        }
        if(adminentry.getDate().isEmpty()){
            flag = false;
            eTxtDate.setError("Please Enter Date");
        }
        if(adminentry.getTime().isEmpty()){
            flag = false;
            eTxtTime.setError("Please Enter Time");
        }
        if(adminentry.getName().isEmpty()){
            flag = false;
            eTxtAdmin.setError("Please Enter Admin name");
        }
        if(adminentry.getName().isEmpty()){
            flag = false;
            eTxtIDProofNumber.setError("Please Enter IDProofNumber");
        }
        if(adminentry.getName().isEmpty()){
            flag = false;
            eTxtVehicleNumber.setError("Please Enter VehicleNumber");
        }
        return flag;

    }

    @Override
    public void onClick(View v) {

    }

    public void showDatePicker(View view){

        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(this,dateSetListener,yy,mm,dd);
        datePickerDialog.show();

    }

    public void showTimePicker(View view){
        Calendar calendar = Calendar.getInstance();
        int hh = calendar.get(Calendar.HOUR_OF_DAY);
        int mm = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(this,timeSetListener,hh,mm,true);
        timePickerDialog.show();
    }
}



