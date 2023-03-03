package brianlu.a2a1b;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);

                    startActivity(intent);


                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
