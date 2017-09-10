package com.example.android.android_me;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.android.android_me.ui.BodyPartFragment;

public class DragAndDropSwitcherImpl implements BodyPartFragment.DragAndDropSwitcher {

    private int dragContainer;
    private int dropContainer;

    private String dragTag;
    private String dropTag;

    private AppCompatActivity activity;

    public DragAndDropSwitcherImpl(AppCompatActivity activity) {
        this.activity = activity;
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

        FragmentManager fm = activity.getSupportFragmentManager();
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
}
