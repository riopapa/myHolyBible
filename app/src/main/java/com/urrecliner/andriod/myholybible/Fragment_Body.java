package com.urrecliner.andriod.myholybible;

import android.app.Fragment;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Body extends Fragment {
    public Fragment_Body() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ConstraintLayout contentView = new ConstraintLayout(getActivity());
        // add all your stuff
        ViewGroup.LayoutParams contentViewLayout = new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams( contentViewLayout);
//        return inflater.inflate(R.layout.fragment_body, container, false);
        Vars.constraintBody = contentView;
        return contentView;
    }
}
