package com.project.readyassist_mechapp.screen.activity.service_providers;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.databinding.ActivityServiceProvidersBinding;
import com.project.readyassist_mechapp.screen.activity.create_service_provider.CreateServiceProviderActivity;
import com.project.readyassist_mechapp.screen.fragment.service_providers.FragmentActiveVendors;
import com.project.readyassist_mechapp.screen.fragment.service_providers.FragmentInActiveVendors;

public class ServiceProvidersActivity extends AppCompatActivity {


    protected Fragment fragment;
    protected Bundle b;


    protected ActivityServiceProvidersBinding vendorsBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            setTheme(R.style.status_bar_black);
        }

        vendorsBinding = ActivityServiceProvidersBinding.inflate(getLayoutInflater());
        setContentView(vendorsBinding.getRoot());


        init();

    }

    private void init() {

        vendorsBinding.tabVendorsLayout.addTab(vendorsBinding.tabVendorsLayout.newTab()
                .setText(getString(R.string.label_active).concat("(").concat(String.valueOf(0)).concat(")")));
        vendorsBinding.tabVendorsLayout.addTab(vendorsBinding.tabVendorsLayout.newTab()
                .setText(getString(R.string.label_in_active).concat("(").concat(String.valueOf(0)).concat(")")));

        tabFragmentContainer();

        vendorsBinding.fabAddServiceProvider.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateServiceProviderActivity.class));
        });

        vendorsBinding.imgVendorsBack.setOnClickListener(v -> {
            finish();
        });

    }


    private void tabFragmentContainer() {

        vendorsBinding.tabVendorsLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {

                    case 0:
                        b = new Bundle();
//                        b.putInt("sellerActiveCount", sellerActiveCount);
//                        b.putInt("sellerInActiveCount", sellerInActiveCount);
//                        b.putInt("sellerNewCount", sellerNewCount);
                        fragment = FragmentActiveVendors.newInstance();
                        fragment.setArguments(b);
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.tabVendorsFrame, fragment);
                        ft.commit();
                        break;

                    case 1:
                        b = new Bundle();
//                        b.putInt("sellerActiveCount", sellerActiveCount);
//                        b.putInt("sellerInActiveCount", sellerInActiveCount);
//                        b.putInt("sellerNewCount", sellerNewCount);
                        fragment = FragmentInActiveVendors.newInstance();
                        fragment.setArguments(b);
                        FragmentTransaction ftt = getSupportFragmentManager().beginTransaction();
                        ftt.replace(R.id.tabVendorsFrame, fragment);
                        ftt.commit();
                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


//        if (sellerOnBoardBinding.tabLayout.getSelectedTabPosition() == 1) {
//            return;
//        }


        /*Default Fragment*/
        b = new Bundle();
//        b.putInt("sellerActiveCount", sellerActiveCount);
//        b.putInt("sellerInActiveCount", sellerInActiveCount);
        fragment = FragmentActiveVendors.newInstance();
        fragment.setArguments(b);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.tabVendorsFrame, fragment);
        fragmentTransaction.commit();

    }


}
