package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import static com.example.android.android_me.ui.MasterListFragment.newInstance;

public class MainActivity extends AppCompatActivity implements MasterListFragment.MasterListClickListener {

    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTwoPane = findViewById(R.id.android_me_linear_layout) != null;

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();

            if(mTwoPane) {
                BodyPartFragment headFragment = BodyPartFragment.newInstance(AndroidImageAssets.getHeads(), 0);
                fm.beginTransaction().add(R.id.head_container, headFragment).commit();

                BodyPartFragment bodyFragment = BodyPartFragment.newInstance(AndroidImageAssets.getBodies(), 0);
                fm.beginTransaction().add(R.id.body_container, bodyFragment).commit();

                BodyPartFragment footFragment = BodyPartFragment.newInstance(AndroidImageAssets.getLegs(), 0);
                fm.beginTransaction().add(R.id.foot_container, footFragment).commit();

                // get rid of next-button
                Button nextButton = (Button) findViewById(R.id.btn_next);
                nextButton.setVisibility(View.GONE);

                RecyclerView gridView = (RecyclerView) findViewById(R.id.gridview);
                gridView.setLayoutManager(new GridLayoutManager(this, 2));
            } else {
                MasterListFragment masterListFragment = newInstance();
                fm.beginTransaction()
                        .add(R.id.container, masterListFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onImageSelected(int pos) {
        int bodyPart = pos / 12;
        int index = pos % 12;

        if(mTwoPane) {
            BodyPartFragment bodyPartFragment;
            int containerId;
            FragmentManager fm = getSupportFragmentManager();
            switch (bodyPart) {
                case 0: // head
                    containerId = R.id.head_container;
                    bodyPartFragment = BodyPartFragment.newInstance(AndroidImageAssets.getHeads(), index);
                    break;
                case 1: // body
                    containerId = R.id.body_container;
                    bodyPartFragment = BodyPartFragment.newInstance(AndroidImageAssets.getBodies(), index);
                    break;
                case 2: // foot
                default:
                    containerId = R.id.foot_container;
                    bodyPartFragment = BodyPartFragment.newInstance(AndroidImageAssets.getLegs(), index);
                    break;
            }
            fm.beginTransaction().replace(containerId, bodyPartFragment).commit();
        } else {
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
    }

    @Override
    public void onNextClicked() {
        startActivity(AndroidMeActivity.getIntent(this, headIndex, bodyIndex, legIndex));
    }
}
