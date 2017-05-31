package auribises.com.visitorbook.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import auribises.com.visitorbook.Class.RegisterGuard;
import auribises.com.visitorbook.R;


public class GuardRegisterAdapter extends ArrayAdapter<RegisterGuard> {

    Context context;
    int resource;
    ArrayList<RegisterGuard> registerguardList,tempList;

    public GuardRegisterAdapter(Context context, int resource, ArrayList<RegisterGuard> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        registerguardList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(registerguardList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtGender = (TextView)view.findViewById(R.id.textViewGender);

        RegisterGuard registerguard = registerguardList.get(position);
        txtName.setText(registerguard.getName());
        txtGender.setText(String.valueOf(registerguard.getId()));

//        Log.i("Test", RegisterGuard.toString());

        return view;
    }

    public void filter(String str){

        registerguardList.clear();

        if(str.length()==0){
            registerguardList.addAll(tempList);
        }else{
            for(RegisterGuard r : tempList){
                if(r.getName().toLowerCase().contains(str.toLowerCase())){
                    registerguardList.add(r);
                }
            }
        }

        notifyDataSetChanged();
    }
}

