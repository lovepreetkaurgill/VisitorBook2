package auribises.com.visitorbook.Activites;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import auribises.com.visitorbook.Class.RegisterAdmin;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class AdminChangePasswordActivity extends AppCompatActivity {

    @InjectView(R.id.textNewUsernamead)
    TextView TxtNewUsername;

    @InjectView(R.id.textNewPasswordad)
    TextView TxtNewPassword;

    @InjectView(R.id.buttonSubmit)
    Button btnSubmit;

    RequestQueue requestQueue;

    RegisterAdmin registeradmin;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_password);
        ButterKnife.inject(this);

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        registeradmin = new RegisterAdmin();

        btnSubmit.setText("Submit");
    }

    boolean isNetworkConected() {

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();


        return (networkInfo != null && networkInfo.isConnected());

    }

    public void OnChangeAdmin(View view) {
        if (view.getId() == R.id.buttonSubmit) {
            registeradmin.setEmail(TxtNewUsername.getText().toString().trim());
            registeradmin.setPassword(TxtNewPassword.getText().toString().trim());

//            Intent i = new Intent(AdminChangePasswordActivity.this, AdminloginActivity.class);
//            startActivity(i);
//            finish();

            if (validateFields()) {
                if (isNetworkConected())
                    insertIntoCloud();
                else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
            }


        }
    }


    void insertIntoCloud() {

        String url = "";
        progressDialog.show();

        url = Util.ADMINCHANGEPASSWORD_PHP;


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test", response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success == 1) {
                        Toast.makeText(AdminChangePasswordActivity.this, message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(AdminChangePasswordActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.i("test", e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AdminChangePasswordActivity.this, "Some Exception", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test", error.toString());
                progressDialog.dismiss();
                Toast.makeText(AdminChangePasswordActivity.this, "Some Error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", registeradmin.getEmail());
                map.put("password", registeradmin.getPassword());
                Log.i("test", registeradmin.toString());
                return map;

            }
        };
        requestQueue.add(request); // execute the request, send it to server


    }
    void clearFields(){
        TxtNewUsername.setText("");
        TxtNewPassword.setText("");
    }

    Boolean validateFields() {
        boolean flag = true;
        if (registeradmin.getEmail().isEmpty()) {
            flag = false;
            TxtNewUsername.setError("Please Enter Username");
        }

        if (registeradmin.getPassword().isEmpty()) {
            flag = false;
            TxtNewPassword.setError("Please Enter Password");
        }
        return flag;


    }

}