package liophan.socketserverdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnServer)
    Button btnServer;
    @BindView(R.id.btnClient)
    Button btnClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btnServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent server = new Intent(MainActivity.this, ServerActivity.class);
                startActivity(server);
            }
        });

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent client = new Intent(MainActivity.this, ClientActivity.class);
                startActivity(client);
            }
        });
    }
}
