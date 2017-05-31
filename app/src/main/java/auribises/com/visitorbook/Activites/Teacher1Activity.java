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

import auribises.com.visitorbook.Adapters.TeacherAdapter;
import auribises.com.visitorbook.Class.Teacher;
import auribises.com.visitorbook.R;
import auribises.com.visitorbook.Util;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class Teacher1Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @InjectView(R.id.listView)
    ListView listView;

    @InjectView(R.id.editTextSearch)
    EditText eTxtSearch;

    ArrayList<Teacher> teacherList;

    ContentResolver resolver;

    TeacherAdapter adapter;

    Teacher teacher;
    int pos;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_teachers);

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


        //retrieveFromDB();

        retrieveFromCloud();
    }

    void retrieveFromCloud(){

        progressDialog.show();

        teacherList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.RETRIEVE_TEACHER_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.i("test",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("teacher");

                    int id=0;
                    String n="",p="",e="",g="",a="",pu="",d="",t="",r="";
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
                        t = jObj.getString("time");
                        r = jObj.getString("room");

                        teacherList.add(new Teacher(id,n,p,e,g,a,pu,d,t,r));
                    }

                    adapter = new TeacherAdapter(Teacher1Activity.this,R.layout.teacher1_list_item, teacherList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(Teacher1Activity.this);

                    progressDialog.dismiss();

                }catch (Exception e){
                    Log.i("test",e.toString());
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(Teacher1Activity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("test",error.toString());
                progressDialog.dismiss();
                Toast.makeText(Teacher1Activity.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }

    void retrieveFromDB(){
        //1. Retrieve Data from DB
        //2. Convert Each Record into an Object of Type Teacher
        //3. Put the objects into ArrayList

        teacherList = new ArrayList<>();

        String[] projection = {Util.COL_IDTEACHER,Util.COL_NAMETEACHER,Util.COL_PHONETEACHER,Util.COL_EMAILTEACHER,Util.COL_GENDERTEACHER,Util.COL_ADDRESSTEACHER,Util.COL_PURPOSETEACHER,Util.COL_DATETEACHER,Util.COL_TIMETEACHER,Util.COL_ROOMTEACHER};

        Cursor cursor = resolver.query(Util.TEACHER_URI,projection,null,null,null);

        if(cursor!=null){

            int i=0;
            String n="",p="",e="",g="",a="",pu="",d="",t="",r="";

            while (cursor.moveToNext()){
                i = cursor.getInt(cursor.getColumnIndex(Util.COL_IDTEACHER));
                n = cursor.getString(cursor.getColumnIndex(Util.COL_NAMETEACHER));
                p = cursor.getString(cursor.getColumnIndex(Util.COL_PHONETEACHER));
                e = cursor.getString(cursor.getColumnIndex(Util.COL_EMAILTEACHER));
                g = cursor.getString(cursor.getColumnIndex(Util.COL_GENDERTEACHER));
                a = cursor.getString(cursor.getColumnIndex(Util.COL_ADDRESSTEACHER));
                pu = cursor.getString(cursor.getColumnIndex(Util.COL_PURPOSETEACHER));
                d = cursor.getString(cursor.getColumnIndex(Util.COL_DATETEACHER));
                t = cursor.getString(cursor.getColumnIndex(Util.COL_TIMETEACHER));
                r = cursor.getString(cursor.getColumnIndex(Util.COL_ROOMTEACHER));

                teacherList.add(new Teacher(i,n,p,e,g,a,pu,d,t,r));
            }

            adapter = new TeacherAdapter(this,R.layout.teacher1_list_item, teacherList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos = i;
        teacher = teacherList.get(i);
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
                        showTeacher();
                        break;

                    case 1:
                        Intent intent = new Intent(Teacher1Activity.this,Teacher1Activity.class);
                        intent.putExtra("keyTeacher",teacher);
                        startActivity(intent);
                        break;


                }

            }
        });
        builder.create().show();
    }

    void showTeacher(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details of "+ teacher.getName());
        builder.setMessage(teacher.toString());
        builder.setPositiveButton("Done",null);
        builder.create().show();
    }
}
