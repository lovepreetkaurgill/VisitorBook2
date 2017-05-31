package auribises.com.visitorbook.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import auribises.com.visitorbook.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends AppCompatActivity {

    @InjectView(R.id.buttonAdmin)
    Button eTxtButtonAdmin;

    @InjectView(R.id.buttonTeacher)
    Button eTxtButtonTeacher;

    @InjectView(R.id.buttonGuard)
    Button eTxtButtonGuard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
    }

    public void onClickHandler(View view) {
        if (view.getId() == R.id.buttonTeacher) {
            Intent i = new Intent(HomeActivity.this, TeacherloginActivity.class);
            startActivity(i);
        } else {
            if (view.getId() == R.id.buttonGuard) {
                Intent i = new Intent(HomeActivity.this, GuardloginActivity.class);
                startActivity(i);
            }

            if (view.getId() == R.id.buttonAdmin) {
                Intent i = new Intent(HomeActivity.this, AdminloginActivity.class);
                startActivity(i);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == 101){
            Log.i("test","----0");
            Intent i = new Intent(HomeActivity.this,TeacherhomeActivity.class);
            startActivityForResult(i,301);
        }else{
            if(id == 102){
                Log.i("test","----4");
                Intent j = new Intent(HomeActivity.this,GuardhomeActivity.class);
                startActivityForResult(j,303);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("test","----2");
        if(requestCode==301&&resultCode==302){
            Log.i("test","----1");
            finish();
        }else {
            if (requestCode == 303&&resultCode == 304){
                Log.i("test", "----5");
                finish();
            }
        }
    }
}