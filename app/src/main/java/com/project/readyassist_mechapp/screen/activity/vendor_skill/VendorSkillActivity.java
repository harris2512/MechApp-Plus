package com.project.readyassist_mechapp.screen.activity.vendor_skill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.readyassist_mechapp.databinding.ActivityVendorSkillBinding;
import com.project.readyassist_mechapp.screen.activity.add_vendor_skills.AddVendorSkills;
import com.project.readyassist_mechapp.screen.activity.vendor_skill.adapter.VendorSkillsAdapter;


public class VendorSkillActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    protected LinearLayoutManager linearLayoutManager;
    protected VendorSkillsAdapter skillsAdapter;


    protected ActivityVendorSkillBinding skillBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skillBinding = ActivityVendorSkillBinding.inflate(getLayoutInflater());
        setContentView(skillBinding.getRoot());

        init();


    }

    private void init() {

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        skillBinding.recyclerVendorSkill.setLayoutManager(linearLayoutManager);
        setVendorSkillsAdapter();


        /*Onclick*/
        skillBinding.fabAddSkill.setOnClickListener(v -> {
            startActivity(new Intent(this, AddVendorSkills.class));
            //  sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });


    }


    private void setVendorSkillsAdapter() {
        skillsAdapter = new VendorSkillsAdapter(this);
        skillBinding.recyclerVendorSkill.setAdapter(skillsAdapter);

        skillBinding.recyclerVendorSkill.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int currentFirstVisible = linearLayoutManager.findFirstVisibleItemPosition();
                if (currentFirstVisible > 0) {
                    //scroll up!
                    skillBinding.fabAddSkill.setVisibility(
                            linearLayoutManager.findLastVisibleItemPosition() !=
                                    linearLayoutManager.findLastCompletelyVisibleItemPosition()
                                    ? View.VISIBLE : View.GONE);

                } else {
                    //scroll down!
                    skillBinding.fabAddSkill.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }

            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }


}
