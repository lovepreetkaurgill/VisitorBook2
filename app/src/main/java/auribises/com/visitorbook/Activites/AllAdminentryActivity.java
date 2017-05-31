package auribises.com.visitorbook.Activites;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import auribises.com.visitorbook.Adapters.AdminentryAdapter;
import auribises.com.visitorbook.Adapters.VisitorentryAdapter;
import auribises.com.visitorbook.Class.Adminentry;
import auribises.com.visitorbook.Class.Visitorentry;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class AllAdminentryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

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
    void Admin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout ");
        builder.setMessage("Do you wish to Logout?");
        builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.putExtra("exitSignal",1);
                setResult(302,intent);
                finish();
                Log.i("test","----1");
            }

        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        Admin();
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

                    adapter = new AdminentryAdapter(AllAdminentryActivity.this,R.layout.adminentry_list_item, adminentryList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(AllAdminentryActivity.this);

                    progressDialog.dismiss();

                }catch (Exception e){
                    Log.i("test",response.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AllAdminentryActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(AllAdminentryActivity.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }

    void retrieveFromDB(){
        //1. Retrieve Data from DB
        //2. Convert Each Record into an Object of Type visitorentry
        //3. Put the objects into ArrayList

        adminentryList = new ArrayList<>();

        String[] projection = {Util.COL_IDVISITOR,Util.COL_NAMEVISITOR,Util.COL_PHONEVISITOR,Util.COL_EMAILVISITOR,Util.COL_GENDERVISITOR,Util.COL_ADDRESSVISITOR,Util.COL_PURPOSEVISITOR,Util.COL_DATEVISITOR,Util.COL_TIMEVISITOR,Util.COL_ADMINVISITOR,Util.COL_IDPROOFVISITOR,Util.COL_IDPROOFNUBERVISITOR,Util.COL_VEHICLEVISITOR,Util.COL_VEHICLENUMBERVISITOR};

        Cursor cursor = resolver.query(Util.VISITORENTRY_URI,projection,null,null,null);

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

            adapter = new AdminentryAdapter(this,R.layout.adminentry_list_item, adminentryList);
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
        String[] items ={"View","Update","Delete"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i){
                    case 0:
                        showAdminentry();
                        break;

                    case 1:
                        Intent intent = new Intent(AllAdminentryActivity.this,AdminEntryActivity.class);
                        intent.putExtra("keyAdminentry",adminentry);
                        startActivity(intent);
                        break;

                    case 2:
                        deleteAdminentry();
                        break;
                }

            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode==Util.UPREQCODE&&resultCode==Util.UPRESCODE){
//            Teacher teacher=(Teacher)data.getSerializableExtra(Util.keyresult);
//            teacherList.set(pos,teacher);
//            adapter.notifyDataSetChanged();
//        }
    }

    void showAdminentry(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details of "+ adminentry.getName());
        builder.setMessage(adminentry.toString());
        builder.setPositiveButton("Done",null);
        builder.create().show();
    }

    void deleteAdminentry(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ adminentry.getName());
        builder.setMessage("Do you wish to Delete?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                deleteFromCloud();

            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.create().show();
    }

    void deleteFromCloud(){
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Util.DELETE_ADMINENTRY_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if(success == 1){
                        adminentryList.remove(pos);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(AllAdminentryActivity.this,message,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(AllAdminentryActivity.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AllAdminentryActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AllAdminentryActivity.this,"Some Volley Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("id",String.valueOf(adminentry.getId()));
                return map;
            }
        };

        requestQueue.add(request); // Execution of HTTP Request

    }

}

