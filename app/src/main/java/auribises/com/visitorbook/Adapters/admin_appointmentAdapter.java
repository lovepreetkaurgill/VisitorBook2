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

import auribises.com.visitorbook.Class.Adminappointment;
import auribises.com.visitorbook.R;

public class admin_appointmentAdapter extends ArrayAdapter<Adminappointment> {

    Context context;
    int resource;
    ArrayList<Adminappointment> adminappointmentsList,tempList;

    public admin_appointmentAdapter(Context context, int resource, ArrayList<Adminappointment> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        adminappointmentsList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(adminappointmentsList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtGender = (TextView)view.findViewById(R.id.textViewGender);

        Adminappointment Adminappointment = adminappointmentsList.get(position);
        txtName.setText(Adminappointment.getName());
        txtGender.setText(String.valueOf(Adminappointment.getId()));

        Log.i("Test", Adminappointment.toString());

        return view;
    }

    public void filter(String str){

        adminappointmentsList.clear();

        if(str.length()==0){
            adminappointmentsList.addAll(tempList);
        }else{
            for(Adminappointment a : tempList){
                if(a.getName().toLowerCase().contains(str.toLowerCase())){
                    adminappointmentsList.add(a);
                }
            }
        }

        notifyDataSetChanged();
    }
}
