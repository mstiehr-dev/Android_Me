package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.android_me.R;

import static com.example.android.android_me.ui.MasterListFragment.newInstance;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();

            MasterListFragment masterListFragment = newInstance();
            fm.beginTransaction()
                    .add(R.id.container, masterListFragment)
                    .commit();

        }
    }

    @Override
    public void onImageSelected(int pos) {
        Toast.makeText(this, "position clicked: " + pos, Toast.LENGTH_SHORT).show();

        int bodyPart = pos / 12;
        int index = pos % 12;

        switch (bodyPart) {
            case 0:
                headIndex = index;
                break;
            case 1:
                bodyIndex = index;
                break;
            case 2:
            default:
                legIndex = index;
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putInt(AndroidMeActivity.INDEX_KEY_HEAD, headIndex);
        bundle.putInt(AndroidMeActivity.INDEX_KEY_BODY, bodyIndex);
        bundle.putInt(AndroidMeActivity.INDEX_KEY_FOOT, legIndex);

        final Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(bundle);

        Button btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }
}
