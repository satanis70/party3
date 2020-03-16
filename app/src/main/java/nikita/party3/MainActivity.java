package nikita.party3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TimingLogger;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity {
    FirebaseDatabase firebaseDatabase;
    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this, regclass.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
