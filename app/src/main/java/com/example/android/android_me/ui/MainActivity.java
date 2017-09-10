package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.android.android_me.DragAndDropSwitcherImpl;
import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import static com.example.android.android_me.ui.MasterListFragment.newInstance;

public class MainActivity extends AppCompatActivity implements MasterListFragment.MasterListClickListener, BodyPartFragment.DragAndDropSwitcher {

    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    private final static String FRAGMENT_TAG_HEAD = "FRAGMENT_TAG_HEAD";
    private final static String FRAGMENT_TAG_BODY = "FRAGMENT_TAG_BODY";
    private final static String FRAGMENT_TAG_FOOT = "FRAGMENT_TAG_FOOT";

    private boolean mTwoPane;
    private BodyPartFragment.DragAndDropSwitcher mDragAndDropSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTwoPane = findViewById(R.id.android_me_linear_layout) != null;

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();

            if(mTwoPane) {
                mDragAndDropSwitcher = new DragAndDropSwitcherImpl(this);

                BodyPartFragment headFragment = BodyPartFragment.newInstance(AndroidImageAssets.getHeads(), 0);
                fm.beginTransaction().add(R.id.head_container, headFragment, FRAGMENT_TAG_HEAD).commit();

                BodyPartFragment bodyFragment = BodyPartFragment.newInstance(AndroidImageAssets.getBodies(), 0);
                fm.beginTransaction().add(R.id.body_container, bodyFragment, FRAGMENT_TAG_BODY).commit();

                BodyPartFragment footFragment = BodyPartFragment.newInstance(AndroidImageAssets.getLegs(), 0);
                fm.beginTransaction().add(R.id.foot_container, footFragment, FRAGMENT_TAG_FOOT).commit();

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
            String tag;
            FragmentManager fm = getSupportFragmentManager();
            switch (bodyPart) {
                case 0: // head
                    containerId = fm.findFragmentByTag(FRAGMENT_TAG_HEAD).getId();
                    bodyPartFragment = BodyPartFragment.newInstance(AndroidImageAssets.getHeads(), index);
                    tag = FRAGMENT_TAG_HEAD;
                    break;
                case 1: // body
                    containerId = fm.findFragmentByTag(FRAGMENT_TAG_BODY).getId();
                    bodyPartFragment = BodyPartFragment.newInstance(AndroidImageAssets.getBodies(), index);
                    tag = FRAGMENT_TAG_BODY;
                    break;
                case 2: // foot
                default:
                    containerId = fm.findFragmentByTag(FRAGMENT_TAG_FOOT).getId();
                    bodyPartFragment = BodyPartFragment.newInstance(AndroidImageAssets.getLegs(), index);
                    tag = FRAGMENT_TAG_FOOT;
                    break;
            }
            fm.beginTransaction().replace(containerId, bodyPartFragment, tag).commit();
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

    @Override
    public void registerDrag(int container, String tag) {
        mDragAndDropSwitcher.registerDrag(container, tag);
    }

    @Override
    public void registerDrop(int container, String tag) {
        mDragAndDropSwitcher.registerDrop(container, tag);
    }
}
