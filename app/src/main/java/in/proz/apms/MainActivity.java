package in.proz.apms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.finger_print).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fingerIn = new Intent(getApplicationContext(),FingerPrintActivity.class);
                startActivity(fingerIn);
            }
        });
        findViewById(R.id.face_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent faceIDIn = new Intent(getApplicationContext(),FaceIDActivity.class);
                startActivity(faceIDIn);
            }
        });
    }
}