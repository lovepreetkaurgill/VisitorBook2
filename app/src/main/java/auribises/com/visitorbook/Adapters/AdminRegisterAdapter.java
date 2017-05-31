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

import auribises.com.visitorbook.Class.RegisterAdmin;
import auribises.com.visitorbook.R;


public class AdminRegisterAdapter extends ArrayAdapter<RegisterAdmin> {

    Context context;
    int resource;
    ArrayList<RegisterAdmin> registeradminList,tempList;

    public AdminRegisterAdapter(Context context, int resource, ArrayList<RegisterAdmin> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        registeradminList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(registeradminList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtGender = (TextView)view.findViewById(R.id.textViewGender);

        RegisterAdmin _RegisterAdmin = registeradminList.get(position);
        txtName.setText(_RegisterAdmin.getName());
        txtGender.setText(String.valueOf(_RegisterAdmin.getId()));

        Log.i("Test", _RegisterAdmin.toString());

        return view;
    }

    public void filter(String str){

        registeradminList.clear();

        if(str.length()==0){
            registeradminList.addAll(tempList);
        }else{
            for(RegisterAdmin r : tempList){
                if(r.getName().toLowerCase().contains(str.toLowerCase())){
                    registeradminList.add(r);
                }
            }
        }

        notifyDataSetChanged();
    }
}
