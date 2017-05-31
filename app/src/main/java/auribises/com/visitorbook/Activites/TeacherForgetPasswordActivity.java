package auribises.com.visitorbook.Activites;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import auribises.com.visitorbook.Class.TeacherLogin;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class TeacherForgetPasswordActivity extends AppCompatActivity {

    @InjectView(R.id.txtEmailt)
    EditText TxtEmail;

    @InjectView(R.id.buttonSubmitt)
    Button btnSubmit;

    RequestQueue requestQueue;

    TeacherLogin teacherLogin;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_forget_password);
        ButterKnife.inject(this);

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        teacherLogin = new TeacherLogin();
    }

    boolean isNetworkConected() {

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();


        return (networkInfo != null && networkInfo.isConnected());

    }

    public void OnChangePass(View view) {
        if (view.getId() == R.id.buttonSubmitt) {
            teacherLogin.setUsername(TxtEmail.getText().toString().trim());

            if (validateFields()) {
                if (isNetworkConected())
                    UpdateIntoCloud();
                else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please correct Input", Toast.LENGTH_LONG).show();
            }
        }
    }
    void UpdateIntoCloud() {

        String url = "";
        progressDialog.show();

        url = Util.TEACHER_FORGETPASSWORD_PHP;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test", response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success == 1) {
                        Toast.makeText(TeacherForgetPasswordActivity.this, message, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(TeacherForgetPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.i("test", e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(TeacherForgetPasswordActivity.this, "Some Exception", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test", error.toString());
                progressDialog.dismiss();
                Toast.makeText(TeacherForgetPasswordActivity.this, "Some Error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", teacherLogin.getUsername());

                Log.i("test", teacherLogin.toString());
                return map;

            }
        };
        requestQueue.add(request); // execute the request, send it to server

    }

    void clearFields(){
        TxtEmail.setText("");
    }

    boolean validateFields() {
        boolean flag = true;
        if (teacherLogin.getUsername().isEmpty()) {
            flag = false;
            TxtEmail.setError("Please Enter Username");
        }

        return flag;
    }
}