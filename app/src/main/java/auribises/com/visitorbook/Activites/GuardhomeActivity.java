package auribises.com.visitorbook.Activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import auribises.com.visitorbook.R;
import butterknife.InjectView;

public class GuardhomeActivity extends AppCompatActivity {

    @InjectView(R.id.buttonAdminEntryGuard)
    Button eTxtAdmin;

    @InjectView(R.id.buttonTeacherEntryGuard)
    Button eTxtTeacher;

    @InjectView(R.id.buttonVehicle)
    Button eTxtVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardhome);
    }

    public void onClickHandler(View view) {
        if (view.getId() == R.id.buttonAdminEntryGuard) {
            Intent i = new Intent(GuardhomeActivity.this, AdminEntryActivity.class);
            startActivity(i);
        } else {
            if (view.getId() == R.id.buttonTeacherEntryGuard) {
                Intent i = new Intent(GuardhomeActivity.this, VisitorEntryActivity.class);
                startActivity(i);
            }

            if (view.getId() == R.id.buttonVehicle) {
                Intent i = new Intent(GuardhomeActivity.this, VehicleActivity.class);
                startActivity(i);
            }
        }
    }

    void Guardhome() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout ");
        builder.setMessage("Do you wish to Logout?");
        builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.putExtra("exitSignal",1);
                setResult(304,intent);
                finish();
                Log.i("test","----5");
            }

        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }


    @Override
    public void onBackPressed() {
        Guardhome();
    }
}

