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
import auribises.com.visitorbook.Adapters.AdminentryAdapter;
import auribises.com.visitorbook.Class.Adminentry;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class Admin1Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @InjectView(R.id.listView)
    ListView listView;

    @InjectView(R.id.editTextSearch)
    EditText eTxtSearch;

    ArrayList<Adminentry> adminentryList;

    ContentResolver resolver;

    AdminentryAdapter adapter;

    Adminentry adminentry;
    int pos;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_adminentry);

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

        adminentryList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.RETRIEVE_ADMINENTRY_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("adminentry");

                    int id=0;
                    String n="",p="",e="",g="",a="",pu="",d="",ti="",ad="",idp="",idpn="",v="",vn="";
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        id = jObj.getInt("id");
                        n = jObj.getString("name");
                        p = jObj.getString("phone");
                        e = jObj.getString("email");
                        g = jObj.getString("gender");
                        a = jObj.getString("address");
                        pu = jObj.getString("purpose");
                        d = jObj.getString("date");
                        ti = jObj.getString("time");
                        ad = jObj.getString("admin");
                        idp = jObj.getString("idproof");
                        idpn = jObj.getString("idproofnumber");
                        v = jObj.getString("vehicle");
                        vn = jObj.getString("vehiclenumber");

                        adminentryList.add(new Adminentry(id,n,p,e,g,a,pu,d,ti,ad,idp,idpn,v,vn));
                    }

                    adapter = new AdminentryAdapter(Admin1Activity.this,R.layout.admin1_list_item, adminentryList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(Admin1Activity.this);

                    progressDialog.dismiss();

                }catch (Exception e){
                    Log.i("test",e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(Admin1Activity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(Admin1Activity.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }

    void retrieveFromDB(){
        //1. Retrieve Data from DB
        //2. Convert Each Record into an Object of Type Teacher
        //3. Put the objects into ArrayList

        adminentryList = new ArrayList<>();

        String[] projection = {Util.COL_IDVISITOR,Util.COL_NAMEVISITOR,Util.COL_PHONEVISITOR,Util.COL_EMAILVISITOR,Util.COL_GENDERVISITOR,Util.COL_ADDRESSVISITOR,Util.COL_PURPOSEVISITOR,Util.COL_DATEVISITOR,Util.COL_TIMEVISITOR,Util.COL_ADMINVISITOR,Util.COL_IDPROOFVISITOR,Util.COL_IDPROOFNUBERVISITOR,Util.COL_VEHICLEVISITOR,Util.COL_VEHICLENUMBERVISITOR};

        Cursor cursor = resolver.query(Util.ADMINENTRY_URI,projection,null,null,null);

        if(cursor!=null){

            int i=0;
            String n="",p="",e="",g="",a="",pu="",d="",ti="",ad="",idp="",idpn="",v="",vn="";

            while (cursor.moveToNext()){
                i = cursor.getInt(cursor.getColumnIndex(Util.COL_IDVISITOR));
                n = cursor.getString(cursor.getColumnIndex(Util.COL_NAMEVISITOR));
                p = cursor.getString(cursor.getColumnIndex(Util.COL_PHONEVISITOR));
                e = cursor.getString(cursor.getColumnIndex(Util.COL_EMAILVISITOR));
                g = cursor.getString(cursor.getColumnIndex(Util.COL_GENDERVISITOR));
                a = cursor.getString(cursor.getColumnIndex(Util.COL_ADDRESSVISITOR));
                pu = cursor.getString(cursor.getColumnIndex(Util.COL_PURPOSEVISITOR));
                d = cursor.getString(cursor.getColumnIndex(Util.COL_DATEVISITOR));
                ti = cursor.getString(cursor.getColumnIndex(Util.COL_TIMEVISITOR));
                ad = cursor.getString(cursor.getColumnIndex(Util.COL_ADMINVISITOR));
                idp = cursor.getString(cursor.getColumnIndex(Util.COL_IDPROOFVISITOR));
                idpn = cursor.getString(cursor.getColumnIndex(Util.COL_IDPROOFNUBERVISITOR));
                v = cursor.getString(cursor.getColumnIndex(Util.COL_VEHICLEVISITOR));
                vn = cursor.getString(cursor.getColumnIndex(Util.COL_VEHICLENUMBERVISITOR));

                adminentryList.add(new Adminentry(i,n,p,e,g,a,pu,d,ti,ad,idp,idpn,v,vn));
            }

            adapter = new AdminentryAdapter(this,R.layout.admin1_list_item, adminentryList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos = i;
        adminentry = adminentryList.get(i);
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
                        showAdminentry();
                        break;

                    case 1:
                        Intent intent = new Intent(Admin1Activity.this,Admin1Activity.class);
                        intent.putExtra("keyAdminentry",adminentry);
                        startActivity(intent);
                        break;
                }
            }
        });
        builder.create().show();
    }

    void showAdminentry(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details of "+ adminentry.getName());
        builder.setMessage(adminentry.toString());
        builder.setPositiveButton("Done",null);
        builder.create().show();
    }
}
