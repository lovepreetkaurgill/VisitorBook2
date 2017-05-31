package auribises.com.visitorbook.Adapters;

import android.content.Context;
import auribises.com.visitorbook.Class.Visitorentry;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import auribises.com.visitorbook.R;


public class VisitorentryAdapter extends ArrayAdapter<Visitorentry> {

    Context context;
    int resource;
    ArrayList<Visitorentry> visitorentryList,tempList;

    public VisitorentryAdapter(Context context, int resource, ArrayList<Visitorentry> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        visitorentryList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(visitorentryList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtGender = (TextView)view.findViewById(R.id.textViewGender);

        Visitorentry visitorentry = visitorentryList.get(position);
        txtName.setText(visitorentry.getName());
        txtGender.setText(String.valueOf(visitorentry.getId()));

        Log.i("Test", visitorentry.toString());

        return view;
    }

    public void filter(String str){

        visitorentryList.clear();

        if(str.length()==0){
            visitorentryList.addAll(tempList);
        }else{
            for(Visitorentry v : tempList){
                if(v.getName().toLowerCase().contains(str.toLowerCase())){
                    visitorentryList.add(v);
                }
            }
        }

        notifyDataSetChanged();
    }
}


