package com.example.myforecast.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myforecast.R;

public class TestFragment extends Fragment {
    public static final String TAG = "TestFragment";
    private double mLatitude, mLongitude;

    public TestFragment(double mLatitude, double mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: TEST " + mLatitude + " " + mLongitude);


        return inflater.inflate(R.layout.fragment_test, container, false);
    }


}
