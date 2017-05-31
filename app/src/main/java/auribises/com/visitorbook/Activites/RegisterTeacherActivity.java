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

import auribises.com.visitorbook.Class.RegisterTeacher;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterTeacherActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

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

    @InjectView(R.id.passwordt)
    EditText eTxtPassword;

    @InjectView(R.id.radioButtonMale)
    RadioButton rbMale;

    @InjectView(R.id.radioButtonFemale)
    RadioButton rbFemale;

    ArrayAdapter<String> adapter;

    @InjectView(R.id.buttonRegister)
    Button btnSubmit;

    RegisterTeacher registerteacher, rcvRegisterTeacher;

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
        setContentView(R.layout.activity_teacher_register);

        ButterKnife.inject(this);
        eTxtName = (EditText)findViewById(R.id.editTextName);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        registerteacher = new RegisterTeacher();

        rbMale.setOnCheckedChangeListener(this);
        rbFemale.setOnCheckedChangeListener(this);

        resolver = getContentResolver();

        requestQueue = Volley.newRequestQueue(this);

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyregisterteacher");


        if(updateMode){
            rcvRegisterTeacher = (RegisterTeacher) rcv.getSerializableExtra("keyregisterteacher");
            //Log.i("test", Register_Teacher.toString());
            eTxtName.setText(rcvRegisterTeacher.getName());
            eTxtPhone.setText(rcvRegisterTeacher.getPhone());
            eTxtEmail.setText(rcvRegisterTeacher.getEmail());
            eTxtBirthdate.setText(rcvRegisterTeacher.getBirthdate());
            eTxtAddress.setText(rcvRegisterTeacher.getAddress());
            eTxtQualification.setText(rcvRegisterTeacher.getQualification());
            eTxtExperience.setText(rcvRegisterTeacher.getExperience());
            eTxtPassword.setText(rcvRegisterTeacher.getPassword());

            if(rcvRegisterTeacher.getGender().equals("Male")){
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
        //Log.i("insertIntoCloud", Register_Teacher.toString() );
        return (networkInfo!=null && networkInfo.isConnected());

    }

    public void clickHandler(View view){
        if(view.getId() == R.id.buttonRegister){
            registerteacher.setName(eTxtName.getText().toString().trim());
            registerteacher.setPhone(eTxtPhone.getText().toString().trim());
            registerteacher.setEmail(eTxtEmail.getText().toString().trim());
            registerteacher.setBirthdate(eTxtBirthdate.getText().toString().trim());
            registerteacher.setAddress(eTxtAddress.getText().toString().trim());
            registerteacher.setQualification(eTxtQualification.getText().toString().trim());
            registerteacher.setExperience(eTxtExperience.getText().toString().trim());
            registerteacher.setPassword(eTxtPassword.getText().toString().trim());

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
            url = Util.INSERT_REGISTERTEACHER_PHP;
        }else{
            url = Util.UPDATE_REGISTERTEACHER_PHP;
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
                        Toast.makeText(RegisterTeacherActivity.this,message,Toast.LENGTH_LONG).show();

                        if(updateMode)
                            finish();

                    }else{
                        Toast.makeText(RegisterTeacherActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("test",e.toString());
                    progressDialog.dismiss();
                    Toast.makeText(RegisterTeacherActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("test",error.toString());
                Toast.makeText(RegisterTeacherActivity.this,"Some Error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                //Log.i("test", Register_Teacher.toString() );
                if(updateMode)
                    map.put("id",String.valueOf(rcvRegisterTeacher.getId()));

                map.put("name", registerteacher.getName());
                map.put("phone", registerteacher.getPhone());
                map.put("email", registerteacher.getEmail());
                map.put("gender", registerteacher.getGender());
                map.put("birthdate", registerteacher.getBirthdate());
                map.put("address", registerteacher.getAddress());
                map.put("qualification", registerteacher.getQualification());
                map.put("experience", registerteacher.getExperience());
                map.put("password", registerteacher.getPassword());
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
                registerteacher.setGender("Male");
            } else {
                registerteacher.setGender("Female");
            }
        }
    }

    void insertIntoDB(){

        ContentValues values = new ContentValues();

        values.put(Util.COL_NAMEREGISTERTEACHER, registerteacher.getName());
        values.put(Util.COL_PHONEREGISTERTEACHER, registerteacher.getPhone());
        values.put(Util.COL_EMAILREGISTERTEACHER, registerteacher.getEmail());
        values.put(Util.COL_BIRTHDATEREGISTERTEACHER, registerteacher.getBirthdate());
        values.put(Util.COL_GENDERREGISTERTEACHER, registerteacher.getGender());
        values.put(Util.COL_ADDRESSREGISTERTEACHER, registerteacher.getAddress());
        values.put(Util.COL_QUALIFICATIONREGISTERTEACHER, registerteacher.getQualification());
        values.put(Util.COL_EXPERIENCEREGISTERTEACHER, registerteacher.getExperience());
        values.put(Util.COL_PASSWORDREGISTERTEACHER, registerteacher.getPassword());

        if(!updateMode){
            Uri dummy = resolver.insert(Util.REGISTERTEACHER_URI,values);
            Toast.makeText(this, registerteacher.getName()+ " Registered Successfully "+dummy.getLastPathSegment(),Toast.LENGTH_LONG).show();

            // Log.i("Insert", Register_Teacher.toString());

            clearFields();
        }else{
            String where = Util.COL_IDREGISTERTEACHER + " = "+ rcvRegisterTeacher.getId();
            int i = resolver.update(Util.REGISTERTEACHER_URI,values,where,null);
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

        menu.add(0,101,0,"All Teacher");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            Intent i = new Intent(RegisterTeacherActivity.this, AllTeacherRegisterActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    boolean validateFields(){
        boolean flag = true;

        if(registerteacher.getName().isEmpty()){
            flag = false;
            eTxtName.setError("Please Enter Name");
        }

        if(registerteacher.getPhone().isEmpty()){
            flag = false;
            eTxtPhone.setError("Please Enter Phone");
        }else{
            if(registerteacher.getPhone().length()<10){
                flag = false;
                eTxtPhone.setError("Please Enter 10 digits Phone Number");
            }
        }

        if(registerteacher.getEmail().isEmpty()){
            flag = false;
            eTxtEmail.setError("Please Enter Email");
        }else{
            if(!(registerteacher.getEmail().contains("@") && registerteacher.getEmail().contains("."))){
                flag = false;
                eTxtEmail.setError("Please Enter correct Email");
            }
        }
        if(registerteacher.getAddress().isEmpty()){
            flag = false;
            eTxtAddress.setError("Please Enter Address");
        }

        if(registerteacher.getQualification().isEmpty()){
            flag = false;
            eTxtQualification.setError("Please Enter Qualification");
        }

        if(registerteacher.getExperience().isEmpty()){
            flag = false;
            eTxtExperience.setError("Please Enter Experience");
        }else{
            if(registerteacher.getPhone().length()<2){
                flag = false;
                eTxtExperience.setError("Please Enter 2 digits Experience");
            }
        }

        if(registerteacher.getPassword().isEmpty()  ){
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


