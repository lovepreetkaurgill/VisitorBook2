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

import auribises.com.visitorbook.Class.Vehicle;
import auribises.com.visitorbook.R;


public class VehicleAdapter extends ArrayAdapter<Vehicle> {

    Context context;
    int resource;
    ArrayList<Vehicle> vehicleList,tempList;

    public VehicleAdapter(Context context, int resource, ArrayList<Vehicle> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        vehicleList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(vehicleList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtGender = (TextView)view.findViewById(R.id.textViewGender);

        Vehicle vehicle = vehicleList.get(position);
        txtName.setText(vehicle.getName());
        txtGender.setText(String.valueOf(vehicle.getId()));

        Log.i("Test", vehicle.toString());

        return view;
    }

    public void filter(String str){

        vehicleList.clear();

        if(str.length()==0){
            vehicleList.addAll(tempList);
        }else{
            for(Vehicle s : tempList){
                if(s.getName().toLowerCase().contains(str.toLowerCase())){
                    vehicleList.add(s);
                }
            }
        }

        notifyDataSetChanged();
    }
}

