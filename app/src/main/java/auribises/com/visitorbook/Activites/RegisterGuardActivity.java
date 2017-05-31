package auribises.com.visitorbook.Activites;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import auribises.com.visitorbook.Class.RegisterGuard;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterGuardActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    @InjectView(R.id.editTextName)
    EditText eTxtName;

    @InjectView(R.id.editTextPhone)
    EditText eTxtPhone;

    @InjectView(R.id.editTextEmail)
    EditText eTxtEmail;

    @InjectView(R.id.editTextBirthdate)
    EditText eTxtBirthdate;

    @InjectView(R.id.editTextAddress)
    EditText eTxtAddress;

    @InjectView(R.id.editTextQualification)
    EditText eTxtQualification;

    @InjectView(R.id.editTextExperience)
    EditText eTxtExperience;

    @InjectView(R.id.passwordg)
    EditText eTxtPassword;

    @InjectView(R.id.radioButtonMale)
    RadioButton rbMale;

    @InjectView(R.id.radioButtonFemale)
    RadioButton rbFemale;

    ArrayAdapter<String> adapter;

    @InjectView(R.id.buttonRegister)
    Button btnSubmit;

    RegisterGuard registerguard, rcvRegisterGuard;

    ContentResolver resolver;

    boolean updateMode;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    DatePickerDialog datePickerDialog;

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

            eTxtBirthdate.setText(i+"/"+(i1+1)+"/"+i2);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_register);

        ButterKnife.inject(this);
        eTxtName = (EditText)findViewById(R.id.editTextName);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        registerguard = new RegisterGuard();

        rbMale.setOnCheckedChangeListener(this);
        rbFemale.setOnCheckedChangeListener(this);

        resolver = getContentResolver();

        requestQueue = Volley.newRequestQueue(this);

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyregisterguard");


        if(updateMode){
            rcvRegisterGuard = (RegisterGuard) rcv.getSerializableExtra("keyregisterguard");
            //Log.i("test", Register_Teacher.toString());
            eTxtName.setText(rcvRegisterGuard.getName());
            eTxtPhone.setText(rcvRegisterGuard.getPhone());
            eTxtEmail.setText(rcvRegisterGuard.getEmail());
            eTxtBirthdate.setText(rcvRegisterGuard.getBirthdate());
            eTxtAddress.setText(rcvRegisterGuard.getAddress());
            eTxtQualification.setText(rcvRegisterGuard.getQualification());
            eTxtExperience.setText(rcvRegisterGuard.getExperience());
            eTxtPassword.setText(rcvRegisterGuard.getPassword());

            if(rcvRegisterGuard.getGender().equals("Male")){
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
        //Log.i("insertIntoCloud", RegisterGuard.toString() );
        return (networkInfo!=null && networkInfo.isConnected());

    }

    public void clickHandler(View view){
        if(view.getId() == R.id.buttonRegister){
            registerguard.setName(eTxtName.getText().toString().trim());
            registerguard.setPhone(eTxtPhone.getText().toString().trim());
            registerguard.setEmail(eTxtEmail.getText().toString().trim());
            registerguard.setBirthdate(eTxtBirthdate.getText().toString().trim());
            registerguard.setAddress(eTxtAddress.getText().toString().trim());
            registerguard.setQualification(eTxtQualification.getText().toString().trim());
            registerguard.setExperience(eTxtExperience.getText().toString().trim());
            registerguard.setPassword(eTxtPassword.getText().toString().trim());

            //insertIntoDB();

            if(validateFields()) {
                if (isNetworkConected())
                    insertIntoCloud();
                else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
            }
        }
    }

    void insertIntoCloud(){

        String url="";

        if(!updateMode){
            url = Util.INSERT_REGISTERGUARD_PHP;
        }else{
            url = Util.UPDATE_REGISTERGUARD_PHP;
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
                        Toast.makeText(RegisterGuardActivity.this,message,Toast.LENGTH_LONG).show();

                        if(updateMode)
                            finish();

                    }else{
                        Toast.makeText(RegisterGuardActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("test",e.toString());
                    progressDialog.dismiss();
                    Toast.makeText(RegisterGuardActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("test",error.toString());
                Toast.makeText(RegisterGuardActivity.this,"Some Error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                //Log.i("test", RegisterGuard.toString() );
                if(updateMode)
                    map.put("id",String.valueOf(rcvRegisterGuard.getId()));

                map.put("name", registerguard.getName());
                map.put("phone", registerguard.getPhone());
                map.put("email", registerguard.getEmail());
                map.put("birthdate", registerguard.getBirthdate());
                map.put("gender", registerguard.getGender());
                map.put("address", registerguard.getAddress());
                map.put("qualification", registerguard.getQualification());
                map.put("experience", registerguard.getExperience());
                map.put("password", registerguard.getPassword());
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
                registerguard.setGender("Male");
            } else {
                registerguard.setGender("Female");
            }
        }
    }

    void insertIntoDB(){

        ContentValues values = new ContentValues();

        values.put(Util.COL_NAMEREGISTERGUARD, registerguard.getName());
        values.put(Util.COL_PHONEREGISTERGUARD, registerguard.getPhone());
        values.put(Util.COL_EMAILREGISTERGUARD, registerguard.getEmail());
        values.put(Util.COL_BIRTHDATEREGISTERGUARD, registerguard.getBirthdate());
        values.put(Util.COL_GENDERREGISTERGUARD, registerguard.getGender());
        values.put(Util.COL_ADDRESSREGISTERGUARD, registerguard.getAddress());
        values.put(Util.COL_QUALIFICATIONREGISTERGUARD, registerguard.getQualification());
        values.put(Util.COL_EXPERIENCEREGISTERGUARD, registerguard.getExperience());
        values.put(Util.COL_PASSWORDREGISTERGUARD, registerguard.getPassword());

        if(!updateMode){
            Uri dummy = resolver.insert(Util.REGISTERGUARD_URI,values);
            Toast.makeText(this, registerguard.getName()+ " Registered Successfully "+dummy.getLastPathSegment(),Toast.LENGTH_LONG).show();

            // Log.i("Insert", Register_Teacher.toString());

            clearFields();
        }else{
            String where = Util.COL_IDREGISTERGUARD + " = "+ rcvRegisterGuard.getId();
            int i = resolver.update(Util.REGISTERGUARD_URI,values,where,null);
            if(i>0){
                Toast.makeText(this,"Updation Successful",Toast.LENGTH_LONG).show();
                finish();
            }
        }


    }

    void clearFields(){
        eTxtName.setText("");
        eTxtPhone.setText("");
        eTxtEmail.setText("");
        eTxtBirthdate.setText("");
        eTxtAddress.setText("");
        eTxtQualification.setText("");
        eTxtExperience.setText("");
        eTxtPassword.setText("");
        rbMale.setChecked(false);
        rbFemale.setChecked(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0,101,0,"All Guards");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            Intent i = new Intent(RegisterGuardActivity.this, AllGuardRegisterActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    boolean validateFields(){
        boolean flag = true;

        if(registerguard.getName().isEmpty()){
            flag = false;
            eTxtName.setError("Please Enter Name");
        }

        if(registerguard.getPhone().isEmpty()){
            flag = false;
            eTxtPhone.setError("Please Enter Phone");
        }else{
            if(registerguard.getPhone().length()<10){
                flag = false;
                eTxtPhone.setError("Please Enter 10 digits Phone Number");
            }
        }

        if(registerguard.getEmail().isEmpty()){
            flag = false;
            eTxtEmail.setError("Please Enter Email");
        }else{
            if(!(registerguard.getEmail().contains("@") && registerguard.getEmail().contains("."))){
                flag = false;
                eTxtEmail.setError("Please Enter correct Email");
            }
        }
        if(registerguard.getAddress().isEmpty()){
            flag = false;
            eTxtAddress.setError("Please Enter Address");
        }

        if(registerguard.getQualification().isEmpty()){
            flag = false;
            eTxtQualification.setError("Please Enter Qualification");
        }

        if(registerguard.getExperience().isEmpty()){
            flag = false;
            eTxtExperience.setError("Please Enter Experience");
        }else{
            if(registerguard.getPhone().length()<2){
                flag = false;
                eTxtExperience.setError("Please Enter 2 digits Experience");
            }
        }

        if(registerguard.getPassword().isEmpty()  ){
            flag=false;
            eTxtPassword.setError("Please Enter Password");
        }

        return flag;

    }


    public void showDatePicker(View view){

        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(this,dateSetListener,yy,mm,dd);
        datePickerDialog.show();

    }

}


