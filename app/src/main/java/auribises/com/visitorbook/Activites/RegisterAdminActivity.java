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
import auribises.com.visitorbook.Class.RegisterAdmin;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterAdminActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

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

    @InjectView(R.id.passworda)
    EditText eTxtPassword;

    @InjectView(R.id.radioButtonMale)
    RadioButton rbMale;

    @InjectView(R.id.radioButtonFemale)
    RadioButton rbFemale;

    ArrayAdapter<String> adapter;

    @InjectView(R.id.buttonRegister)
    Button btnSubmit;

    RegisterAdmin registeradmin, rcvRegisterAdmin;

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
        setContentView(R.layout.activity_admin_register);

        ButterKnife.inject(this);
        eTxtName = (EditText)findViewById(R.id.editTextName);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        registeradmin = new RegisterAdmin();

        rbMale.setOnCheckedChangeListener(this);
        rbFemale.setOnCheckedChangeListener(this);

        resolver = getContentResolver();

        requestQueue = Volley.newRequestQueue(this);

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyregisteradmin");


        if(updateMode){
            rcvRegisterAdmin = (RegisterAdmin) rcv.getSerializableExtra("keyregisteradmin");
            //Log.i("test", Register_Teacher.toString());
            eTxtName.setText(rcvRegisterAdmin.getName());
            eTxtPhone.setText(rcvRegisterAdmin.getPhone());
            eTxtEmail.setText(rcvRegisterAdmin.getEmail());
            eTxtBirthdate.setText(rcvRegisterAdmin.getBirthdate());
            eTxtAddress.setText(rcvRegisterAdmin.getAddress());
            eTxtQualification.setText(rcvRegisterAdmin.getQualification());
            eTxtExperience.setText(rcvRegisterAdmin.getExperience());
            eTxtPassword.setText(rcvRegisterAdmin.getPassword());

            if(rcvRegisterAdmin.getGender().equals("Male")){
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

    public void onclickHandler(View view){
        if(view.getId() == R.id.buttonRegister){
            registeradmin.setName(eTxtName.getText().toString().trim());
            registeradmin.setPhone(eTxtPhone.getText().toString().trim());
            registeradmin.setEmail(eTxtEmail.getText().toString().trim());
            registeradmin.setBirthdate(eTxtBirthdate.getText().toString().trim());
            registeradmin.setAddress(eTxtAddress.getText().toString().trim());
            registeradmin.setQualification(eTxtQualification.getText().toString().trim());
            registeradmin.setExperience(eTxtExperience.getText().toString().trim());
            registeradmin.setPassword(eTxtPassword.getText().toString().trim());

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
            url = Util.INSERT_REGISTERADMIN_PHP;
        }else{
            url = Util.UPDATE_REGISTERADMIN_PHP;
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
                        Toast.makeText(RegisterAdminActivity.this,message,Toast.LENGTH_LONG).show();

                        if(updateMode)
                            finish();

                    }else{
                        Toast.makeText(RegisterAdminActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("test",e.toString());
                    progressDialog.dismiss();
                    Toast.makeText(RegisterAdminActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("test",error.toString());
                Toast.makeText(RegisterAdminActivity.this,"Some Error"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                //Log.i("test", RegisterGuard.toString() );
                if(updateMode)
                    map.put("id",String.valueOf(rcvRegisterAdmin.getId()));

                map.put("name", registeradmin.getName());
                map.put("phone", registeradmin.getPhone());
                map.put("email", registeradmin.getEmail());
                map.put("birthdate", registeradmin.getBirthdate());
                map.put("gender", registeradmin.getGender());
                map.put("address", registeradmin.getAddress());
                map.put("qualification", registeradmin.getQualification());
                map.put("experience", registeradmin.getExperience());
                map.put("password", registeradmin.getPassword());
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
                registeradmin.setGender("Male");
            } else {
                registeradmin.setGender("Female");
            }
        }
    }

    void insertIntoDB(){

        ContentValues values = new ContentValues();

        values.put(Util.COL_NAMEREGISTERADMIN, registeradmin.getName());
        values.put(Util.COL_PHONEREGISTERADMIN, registeradmin.getPhone());
        values.put(Util.COL_EMAILREGISTERADMIN, registeradmin.getEmail());
        values.put(Util.COL_BIRTHDATEREGISTERADMIN, registeradmin.getBirthdate());
        values.put(Util.COL_GENDERREGISTERADMIN, registeradmin.getGender());
        values.put(Util.COL_ADDRESSREGISTERADMIN, registeradmin.getAddress());
        values.put(Util.COL_QUALIFICATIONREGISTERADMIN, registeradmin.getQualification());
        values.put(Util.COL_EXPERIENCEREGISTERADMIN, registeradmin.getExperience());
        values.put(Util.COL_PASSWORDREGISTERADMIN, registeradmin.getPassword());

        if(!updateMode){
            Uri dummy = resolver.insert(Util.REGISTERADMIN_URI,values);
            Toast.makeText(this, registeradmin.getName()+ " Registered Successfully "+dummy.getLastPathSegment(),Toast.LENGTH_LONG).show();

            // Log.i("Insert", Register_Teacher.toString());

            clearFields();
        }else{
            String where = Util.COL_IDREGISTERADMIN + " = "+ rcvRegisterAdmin.getId();
            int i = resolver.update(Util.REGISTERADMIN_URI,values,where,null);
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

        menu.add(0,101,0,"All Admins");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            Intent i = new Intent(RegisterAdminActivity.this, AllAdminRegisterActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    boolean validateFields(){
        boolean flag = true;

        if(registeradmin.getName().isEmpty()){
            flag = false;
            eTxtName.setError("Please Enter Name");
        }

        if(registeradmin.getPhone().isEmpty()){
            flag = false;
            eTxtPhone.setError("Please Enter Phone");
        }else{
            if(registeradmin.getPhone().length()<10){
                flag = false;
                eTxtPhone.setError("Please Enter 10 digits Phone Number");
            }
        }

        if(registeradmin.getEmail().isEmpty()){
            flag = false;
            eTxtEmail.setError("Please Enter Email");
        }else{
            if(!(registeradmin.getEmail().contains("@") && registeradmin.getEmail().contains("."))){
                flag = false;
                eTxtEmail.setError("Please Enter correct Email");
            }
        }
        if(registeradmin.getAddress().isEmpty()){
            flag = false;
            eTxtAddress.setError("Please Enter Address");
        }

        if(registeradmin.getQualification().isEmpty()){
            flag = false;
            eTxtQualification.setError("Please Enter Qualification");
        }

        if(registeradmin.getExperience().isEmpty()){
            flag = false;
            eTxtExperience.setError("Please Enter Experience");
        }else{
            if(registeradmin.getPhone().length()<2){
                flag = false;
                eTxtExperience.setError("Please Enter 2 digits Experience");
            }
        }

        if(registeradmin.getPassword().isEmpty()  ){
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


