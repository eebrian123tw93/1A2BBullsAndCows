package brianlu.a2a1b;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner;
    Button playButton, rankButton, aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIComponent();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.digits, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition("4"));

        playButton.setOnClickListener(this);
        rankButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);


    }


    private void initializeUIComponent() {
        //Find view by Id
        playButton = findViewById(R.id.Play_Button);
        rankButton = findViewById(R.id.Rank_Button);
        aboutButton = findViewById(R.id.About_Button);
        spinner = findViewById(R.id.spinner1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Play_Button:

                Intent playIntent = new Intent(MainActivity.this, PlayActivity.class);
                playIntent.putExtra("digits", spinner.getSelectedItem().toString().trim());
                startActivity(playIntent);
                break;

            case R.id.Rank_Button:

                Intent rankIntent = new Intent(MainActivity.this, RankActivity.class);
                rankIntent.putExtra("digits", spinner.getSelectedItem().toString().trim());
                startActivity(rankIntent);
                break;

            case R.id.About_Button:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
        }
    }
}
