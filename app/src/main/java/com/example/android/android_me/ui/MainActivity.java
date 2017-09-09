package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.android_me.R;

import static com.example.android.android_me.ui.MasterListFragment.newInstance;

public class MainActivity extends AppCompatActivity implements MasterListFragment.MasterListClickListener {

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
    }

    @Override
    public void onNextClicked() {
        startActivity(AndroidMeActivity.getIntent(this, headIndex, bodyIndex, legIndex));
    }
}
