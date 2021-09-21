package com.project.readyassist_mechapp.screen.fragment.service_providers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.readyassist_mechapp.R;

public class FragmentActiveVendors extends Fragment {


    protected View view;
    protected Fragment selectedFragment;


    public static FragmentActiveVendors newInstance() {
        return new FragmentActiveVendors();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_active_providers, container, false);

//        sessionManager = new SessionManager(getActivity());


        // init();


        return view;
    }

}
