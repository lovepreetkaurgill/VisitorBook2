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

import auribises.com.visitorbook.Class.GuardLogin;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class GuardForgetPasswordActivity extends AppCompatActivity {

    @InjectView(R.id.txtEmailg)
    EditText TxtEmail;

    @InjectView(R.id.buttonSubmitg)
    Button btnSubmit;

    RequestQueue requestQueue;

    GuardLogin guardlogin;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_forget_password1);
        ButterKnife.inject(this);

        requestQueue = Volley.newRequestQueue(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);

        guardlogin = new GuardLogin();
    }

    boolean isNetworkConected() {

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());

    }

    public void OnChangeGuard(View view) {
        if (view.getId() == R.id.buttonSubmitg) {
            guardlogin.setUsername(TxtEmail.getText().toString().trim());

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

        url = Util.GUARD_FORGETPASSWORD_PHP;

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test", response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success == 1) {
                        Toast.makeText(GuardForgetPasswordActivity.this, message, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(GuardForgetPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.i("test", e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(GuardForgetPasswordActivity.this, "Some Exception", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test", error.toString());
                progressDialog.dismiss();
                Toast.makeText(GuardForgetPasswordActivity.this, "Some Error" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", guardlogin.getUsername());

                Log.i("test", guardlogin.toString());
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
        if (guardlogin.getUsername().isEmpty()) {
            flag = false;
            TxtEmail.setError("Please Enter Username");
        }

        return flag;
    }
}