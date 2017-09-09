package com.example.android.android_me.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

import java.util.List;


public class MasterListFragment extends Fragment {

    private MasterListClickListener masterListClickListener;

    public MasterListFragment() {
    }

    public static MasterListFragment newInstance() {
        return new MasterListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        final RecyclerView grid = rootView.findViewById(R.id.gridview);
        grid.setLayoutManager(new GridLayoutManager(getContext(), 3));

        List<Integer> allImages = AndroidImageAssets.getAll();
        final RecyclerView.Adapter adapter = new MasterListAdapter(getContext(), allImages);
        grid.setAdapter(adapter);

        Button btnNext = rootView.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                masterListClickListener.onNextClicked();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof MasterListClickListener) {
            this.masterListClickListener = (MasterListClickListener) context;
        }
    }

    interface MasterListClickListener {
        void onImageSelected(int pos);
        void onNextClicked();
    }


}
