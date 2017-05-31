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

import auribises.com.visitorbook.Class.Visitorentry;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class VisitorEntryActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {


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

    @InjectView(R.id.editTextTeacher)
    EditText eTxtTeacher;

    @InjectView(R.id.spinnerBranch)
    Spinner spBranch;
    ArrayAdapter<String> adapter;

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

    Visitorentry visitorentry, rcvVisitorentry;

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
        setContentView(R.layout.activity_visitor_entry);

        ButterKnife.inject(this);

        preferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        editor = preferences.edit();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        visitorentry = new Visitorentry();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapter.add("--Select Branch--");
        adapter.add("Applied Sciences");
        adapter.add("Civil Engineering");
        adapter.add("Electrical Engineering");
        adapter.add("Mechanical Engineering");
        adapter.add("Electroncis & Communication Engineering");
        adapter.add("Computer Science & Engineering");
        adapter.add("Information Technology");
        adapter.add("Production Engineering");
        adapter.add("Business Administration");
        adapter.add("Computer Applications");;

        spBranch.setAdapter(adapter);

        spBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    visitorentry.setBranch(adapter.getItem(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                    visitorentry.setIDProof(adapter1.getItem(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        adapter2.add("--Vehicle Type--");
        adapter2.add("Car");
        adapter2.add("Bike");
        adapter2.add("Activa");
        adapter2.add("Auto");
        adapter2.add("Bus");

        spVehicle.setAdapter(adapter2);

        spVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    visitorentry.setVehicle(adapter2.getItem(i));
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
        updateMode = rcv.hasExtra("keyVisitorentry");


        if(updateMode){
            rcvVisitorentry = (Visitorentry)rcv.getSerializableExtra("keyVisitorentry");
            eTxtName.setText(rcvVisitorentry.getName());
            eTxtPhone.setText(rcvVisitorentry.getPhone());
            eTxtEmail.setText(rcvVisitorentry.getEmail());
            eTxtAddress.setText(rcvVisitorentry.getAddress());
            eTxtPurpose.setText(rcvVisitorentry.getPurpose());
            eTxtDate.setText(rcvVisitorentry.getDate());
            eTxtTime.setText(rcvVisitorentry.getTime());
            eTxtTeacher.setText(rcvVisitorentry.getTeacher());
            eTxtIDProofNumber.setText(rcvVisitorentry.getIDProofnumber());
            eTxtVehicleNumber.setText(rcvVisitorentry.getVehiclenumber());

            if(rcvVisitorentry.getGender().equals("Male")){
                rbMale.setChecked(true);
            }else{
                rbFemale.setChecked(true);
            }

            Log.i("TEST",adapter+" - "+rcvVisitorentry.getBranch()+" - "+rcvVisitorentry.getIDProof()+" - "+rcvVisitorentry.getVehicle());

            int p = 0;
            for(int i=0;i<adapter.getCount();i++){
                if(adapter.getItem(i).equals(rcvVisitorentry.getBranch())){
                    Log.i("TEST1",adapter.getItem(i)+" - "+rcvVisitorentry.getBranch());
                    p = i;
                    break;
                }
            }
            spBranch.setSelection(p);

            int q = 0;
            for(int i=0;i<adapter1.getCount();i++){
                if(adapter1.getItem(i).equals(rcvVisitorentry.getIDProof())){
                    Log.i("TEST2",adapter1.getItem(i)+" - "+rcvVisitorentry.getIDProof());
                    q = i;
                    break;
                }
            }
            spIDProof.setSelection(q);

            int r = 0;
            for(int i=0;i<adapter2.getCount();i++){
                if(adapter2.getItem(i).equals(rcvVisitorentry.getVehicle())){
                    Log.i("TEST3",adapter2.getItem(i)+" - "+rcvVisitorentry.getVehicle());
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
        Log.i("test",visitorentry.toString());

        return (networkInfo!=null && networkInfo.isConnected());

    }

    public void onclickhandler(View view){
        if(view.getId() == R.id.buttonSubmit) {
            btnSubmit.setOnClickListener(this);

            visitorentry.setName(eTxtName.getText().toString().trim());
            visitorentry.setPhone(eTxtPhone.getText().toString().trim());
            visitorentry.setEmail(eTxtEmail.getText().toString().trim());
            visitorentry.setAddress(eTxtAddress.getText().toString().trim());
            visitorentry.setPurpose(eTxtPurpose.getText().toString().trim());
            visitorentry.setDate(eTxtDate.getText().toString().trim());
            visitorentry.setTime(eTxtTime.getText().toString().trim());
            visitorentry.setTeacher(eTxtTeacher.getText().toString().trim());
            visitorentry.setIDProofnumber(eTxtIDProofNumber.getText().toString().trim());
            visitorentry.setVehiclenumber(eTxtVehicleNumber.getText().toString().trim());

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
            url = Util.INSERT_VISITORENTRY_PHP;
        }else{
            url = Util.UPDATE_VISITORENTRY_PHP;
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
                        Toast.makeText(VisitorEntryActivity.this,message,Toast.LENGTH_LONG).show();

                        if(!updateMode){

                            editor.putString(Util.KEY_NAME, visitorentry.getName());
                            editor.putString(Util.KEY_PHONE, visitorentry.getPhone());
                            editor.putString(Util.KEY_EMAIL, visitorentry.getEmail());
                            editor.putString(Util.KEY_ADDRESS, visitorentry.getAddress());
                            editor.putString(Util.KEY_PURPOSE, visitorentry.getPurpose());
                            editor.putString(Util.KEY_DATE, visitorentry.getDate());
                            editor.putString(Util.KEY_TIME, visitorentry.getTime());
                            editor.putString(Util.KEY_TEACHER, visitorentry.getTeacher());
                            editor.putString(Util.KEY_BRANCH, visitorentry.getBranch());
                            editor.putString(Util.KEY_IDPROOF, visitorentry.getIDProof());
                            editor.putString(Util.KEY_IDPROOFNUMBER, visitorentry.getIDProofnumber());
                            editor.putString(Util.KEY_VEHICLE, visitorentry.getVehicle());
                            editor.putString(Util.KEY_VEHICLENUMBER, visitorentry.getVehiclenumber());

                            editor.commit();

                       }

                        if(updateMode)
                            finish();

                    }else{
                        Toast.makeText(VisitorEntryActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(VisitorEntryActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(VisitorEntryActivity.this,"Some Error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                Log.i("test",visitorentry.toString());

                if(updateMode)
                    map.put("id",String.valueOf(rcvVisitorentry.getId()));

                map.put("name", visitorentry.getName());
                map.put("phone", visitorentry.getPhone());
                map.put("email", visitorentry.getEmail());
                map.put("gender", visitorentry.getGender());
                map.put("address", visitorentry.getAddress());
                map.put("purpose", visitorentry.getPurpose());
                map.put("date", visitorentry.getDate());
                map.put("time", visitorentry.getTime());
                map.put("teacher", visitorentry.getTeacher());
                map.put("branch", visitorentry.getBranch());
                map.put("idproof", visitorentry.getIDProof());
                map.put("idproofnumber", visitorentry.getIDProofnumber());
                map.put("vehicle", visitorentry.getVehicle());
                map.put("vehiclenumber", visitorentry.getVehiclenumber());
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
                visitorentry.setGender("Male");
            } else {
                visitorentry.setGender("Female");
            }
        }
    }

    void insertIntoDB(){

        ContentValues values = new ContentValues();

        values.put(Util.COL_NAMEVISITOR, visitorentry.getName());
        values.put(Util.COL_PHONEVISITOR, visitorentry.getPhone());
        values.put(Util.COL_EMAILVISITOR, visitorentry.getEmail());
        values.put(Util.COL_GENDERVISITOR, visitorentry.getGender());
        values.put(Util.COL_ADDRESSVISITOR, visitorentry.getAddress());
        values.put(Util.COL_PURPOSEVISITOR, visitorentry.getPurpose());
        values.put(Util.COL_DATEVISITOR, visitorentry.getDate());
        values.put(Util.COL_TIMEVISITOR, visitorentry.getTime());
        values.put(Util.COL_TEACHERVISITOR, visitorentry.getTeacher());
        values.put(Util.COL_BRANCHVISITOR, visitorentry.getBranch());
        values.put(Util.COL_IDPROOFVISITOR, visitorentry.getIDProof());
        values.put(Util.COL_IDPROOFNUBERVISITOR, visitorentry.getIDProofnumber());
        values.put(Util.COL_VEHICLEVISITOR, visitorentry.getVehicle());
        values.put(Util.COL_VEHICLENUMBERVISITOR, visitorentry.getVehiclenumber());

        if(!updateMode){
            Uri dummy = resolver.insert(Util.VISITORENTRY_URI,values);
            Toast.makeText(this, visitorentry.getName()+ " Registered Successfully "+dummy.getLastPathSegment(),Toast.LENGTH_LONG).show();

            Log.i("insertintocloud", visitorentry.toString());

            clearFields();
        }else{
            String where = Util.COL_IDVISITOR + " = "+ rcvVisitorentry.getId();
            int i = resolver.update(Util.VISITORENTRY_URI,values,where,null);
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
        eTxtTeacher.setText("");
        spBranch.setSelection(0);
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
            Intent i = new Intent(VisitorEntryActivity.this,AllVisitorentryActivity.class);
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

        if(visitorentry.getName().isEmpty()){
            flag = false;
            eTxtName.setError("Please Enter Name");
        }

        if(visitorentry.getPhone().isEmpty()){
            flag = false;
            eTxtPhone.setError("Please Enter Phone");
        }else{
            if(visitorentry.getPhone().length()<10){
                flag = false;
                eTxtPhone.setError("Please Enter 10 digits Phone Number");
            }
        }

        if(visitorentry.getEmail().isEmpty()){
            flag = false;
            eTxtEmail.setError("Please Enter Email");
        }else{
            if(!(visitorentry.getEmail().contains("@") && visitorentry.getEmail().contains("."))){
                flag = false;
                eTxtEmail.setError("Please Enter correct Email");
            }
        }
        if(visitorentry.getAddress().isEmpty()){
            flag = false;
            eTxtAddress.setError("Please Enter Address");
        }
        if(visitorentry.getPurpose().isEmpty()){
            flag = false;
            eTxtPurpose.setError("Please Enter Purpose");
        }
        if(visitorentry.getDate().isEmpty()){
            flag = false;
            eTxtDate.setError("Please Enter Date");
        }
        if(visitorentry.getTime().isEmpty()){
            flag = false;
            eTxtTime.setError("Please Enter Time");
        }
        if(visitorentry.getName().isEmpty()){
            flag = false;
            eTxtTeacher.setError("Please Enter Teacher name");
        }
        if(visitorentry.getName().isEmpty()){
            flag = false;
            eTxtIDProofNumber.setError("Please Enter IDProofNumber");
        }
        if(visitorentry.getName().isEmpty()){
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



