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

import auribises.com.visitorbook.Class.Teacher;
import auribises.com.visitorbook.R;


public class TeacherAdapter extends ArrayAdapter<Teacher> {

    Context context;
    int resource;
    ArrayList<Teacher> teacherList,tempList;

    public TeacherAdapter(Context context, int resource, ArrayList<Teacher> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        teacherList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(teacherList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtGender = (TextView)view.findViewById(R.id.textViewGender);

        Teacher teacher = teacherList.get(position);
        txtName.setText(teacher.getName());
        txtGender.setText(String.valueOf(teacher.getId()));

        Log.i("Test",teacher.toString());

        return view;
    }

    public void filter(String str){

        teacherList.clear();

        if(str.length()==0){
            teacherList.addAll(tempList);
        }else{
            for(Teacher t : tempList){
                if(t.getName().toLowerCase().contains(str.toLowerCase())){
                    teacherList.add(t);
                }
            }
        }

        notifyDataSetChanged();
    }
}

