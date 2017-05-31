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

public class TeacherhomeActivity extends AppCompatActivity {

    @InjectView(R.id.buttonTeacher1)
    Button eTxtTeacher1;

    @InjectView(R.id.buttonTeacher_visitors)
    Button eTxtTeachervisitors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherhome);
    }
    public void onClickHandler(View view) {
        if(view.getId()==R.id.buttonTeacher1) {
            Intent i = new Intent(TeacherhomeActivity.this,TeacherActivity.class);
            startActivity(i);
        }else {
            if (view.getId() == R.id.buttonTeacher_visitors) {
                Intent i = new Intent(TeacherhomeActivity.this, Visitor1Activity.class);
                startActivity(i);
            }
        }
    }

    void Teacherhome() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Do you wish to Logout?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.putExtra("exitSignal",1);
                setResult(302,intent);
                finish();
                Log.i("test","----1");
            }

        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }


    @Override
    public void onBackPressed() {
        Teacherhome();
    }
}