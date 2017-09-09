/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.android_me.ui;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class AndroidMeActivity extends AppCompatActivity implements View.OnTouchListener, BodyPartFragment.DragAndDropSwitcher {

    public final static String INDEX_KEY_HEAD = "IMAGE_INDEX_HEAD";
    public final static String INDEX_KEY_BODY = "IMAGE_INDEX_BODY";
    public final static String INDEX_KEY_FOOT = "IMAGE_INDEX_FOOT";

    private final static String FRAGMENT_TAG_HEAD = "FRAGMENT_TAG_HEAD";
    private final static String FRAGMENT_TAG_BODY = "FRAGMENT_TAG_BODY";
    private final static String FRAGMENT_TAG_FOOT = "FRAGMENT_TAG_FOOT";

    private int headIndex;
    private int bodyIndex;
    private int footIndex;

    // drag and drop
    String dragTag;
    int dragContainer;
    String dropTag;
    int dropContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            headIndex = bundle.getInt(INDEX_KEY_HEAD);
            bodyIndex = bundle.getInt(INDEX_KEY_BODY);
            footIndex = bundle.getInt(INDEX_KEY_FOOT);
        }

        if(savedInstanceState == null) {

            BodyPartFragment headFragment = BodyPartFragment.newInstance(AndroidImageAssets.getHeads(), headIndex);
            BodyPartFragment bodyFragment = BodyPartFragment.newInstance(AndroidImageAssets.getBodies(), bodyIndex);
            BodyPartFragment footFragment = BodyPartFragment.newInstance(AndroidImageAssets.getLegs(), footIndex);

            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .add(R.id.head_container, headFragment, FRAGMENT_TAG_HEAD)
                    .add(R.id.body_container, bodyFragment, FRAGMENT_TAG_BODY)
                    .add(R.id.foot_container, footFragment, FRAGMENT_TAG_FOOT)
                    .commit();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder builder = new View.DragShadowBuilder(view);
            view.startDrag(data, builder, view, 0);
            view.setVisibility(View.INVISIBLE);

            return true;
        }

        return false;
    }

    @Override
    public void registerDrag(int container, String tag) {
        this.dragContainer = container;
        this.dragTag = tag;
    }

    @Override
    public void registerDrop(int container, String tag) {
        this.dropContainer = container;
        this.dropTag = tag;

        switchDragAndDropImages();
    }

    private void switchDragAndDropImages() {
        if(TextUtils.isEmpty(dragTag) || TextUtils.isEmpty(dropTag) || dragTag.equals(dropTag)) {
            return; // do nothing
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment dragFragment = fm.findFragmentByTag(dragTag);
        Fragment dropFragment = fm.findFragmentByTag(dropTag);

        fm.beginTransaction()
                .remove(dropFragment)
                .remove(dragFragment)
                .commit();
        fm.executePendingTransactions();

        fm.beginTransaction()
                .add(dropContainer, dragFragment, dragTag)
                .add(dragContainer, dropFragment, dropTag)
                .commit();
    }

    public static Intent getIntent(Context context, int headIndex, int bodyIndex, int footIndex) {
        Bundle bundle = new Bundle();
        bundle.putInt(AndroidMeActivity.INDEX_KEY_HEAD, headIndex);
        bundle.putInt(AndroidMeActivity.INDEX_KEY_BODY, bodyIndex);
        bundle.putInt(AndroidMeActivity.INDEX_KEY_FOOT, footIndex);

        Intent intent = new Intent(context, AndroidMeActivity.class);
        intent.putExtras(bundle);

        return intent;
    }
}
