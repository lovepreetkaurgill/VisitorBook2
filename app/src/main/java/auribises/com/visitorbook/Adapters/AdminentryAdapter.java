package auribises.com.visitorbook.Adapters;

import android.content.Context;
import auribises.com.visitorbook.Class.Adminentry;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import auribises.com.visitorbook.R;


public class AdminentryAdapter extends ArrayAdapter<Adminentry> {

    Context context;
    int resource;
    ArrayList<Adminentry> adminentryList,tempList;

    public AdminentryAdapter(Context context, int resource, ArrayList<Adminentry> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        adminentryList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(adminentryList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtGender = (TextView)view.findViewById(R.id.textViewGender);

        Adminentry adminentry = adminentryList.get(position);
        txtName.setText(adminentry.getName());
        txtGender.setText(String.valueOf(adminentry.getId()));

        Log.i("Test", adminentry.toString());

        return view;
    }

    public void filter(String str){

        adminentryList.clear();

        if(str.length()==0){
            adminentryList.addAll(tempList);
        }else{
            for(Adminentry a : tempList){
                if(a.getName().toLowerCase().contains(str.toLowerCase())){
                    adminentryList.add(a);
                }
            }
        }

        notifyDataSetChanged();
    }
}


