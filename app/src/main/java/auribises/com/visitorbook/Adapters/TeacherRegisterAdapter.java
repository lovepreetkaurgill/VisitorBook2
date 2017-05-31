package auribises.com.visitorbook.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import auribises.com.visitorbook.Class.RegisterTeacher;
import auribises.com.visitorbook.R;


public class TeacherRegisterAdapter extends ArrayAdapter<RegisterTeacher> {

    Context context;
    int resource;
    ArrayList<RegisterTeacher> registerteacherList,tempList;

    public TeacherRegisterAdapter(Context context, int resource, ArrayList<RegisterTeacher> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        registerteacherList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(registerteacherList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtGender = (TextView)view.findViewById(R.id.textViewGender);

        RegisterTeacher registerteacher = registerteacherList.get(position);
        txtName.setText(registerteacher.getName());
        txtGender.setText(String.valueOf(registerteacher.getId()));

        Log.i("Test", registerteacher.toString());

        return view;
    }

    public void filter(String str){

        registerteacherList.clear();

        if(str.length()==0){
            registerteacherList.addAll(tempList);
        }else{
            for(RegisterTeacher r : tempList){
                if(r.getName().toLowerCase().contains(str.toLowerCase())){
                    registerteacherList.add(r);
                }
            }
        }

        notifyDataSetChanged();
    }
}

