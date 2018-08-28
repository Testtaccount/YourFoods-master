package com.trannguyentanthuan2903.yourfoods.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.trannguyentanthuan2903.yourfoods.R;

/**
 * Created by Administrator on 10/26/2017.
 */

public class AboutUs_Fragment extends Fragment {
    Button btnClose;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us,container,false);
        return view;
    }
}
