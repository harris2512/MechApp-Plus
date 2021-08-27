package com.project.readyassist_mechapp.screen.activity.vendor_skill;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.project.readyassist_mechapp.databinding.ActivityVendorSkillBinding;
import com.project.readyassist_mechapp.screen.activity.vendor_skill.adapter.PopularServicesAdapter;
import com.project.readyassist_mechapp.screen.activity.vendor_skill.adapter.VendorSkillsAdapter;

public class VendorSkillActivity extends AppCompatActivity {


    protected LinearLayoutManager linearLayoutManager;
    protected VendorSkillsAdapter skillsAdapter;
    protected PopularServicesAdapter servicesAdapter;

    protected BottomSheetBehavior<ConstraintLayout> sheetBehavior;

    protected ActivityVendorSkillBinding skillBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        skillBinding = ActivityVendorSkillBinding.inflate(getLayoutInflater());
        setContentView(skillBinding.getRoot());

        init();


    }

    private void init() {

        sheetBehavior = BottomSheetBehavior.from(skillBinding.layoutBottomSheet.dialogBottomSheet);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        skillBinding.recyclerVendorSkill.setLayoutManager(linearLayoutManager);

        skillBinding.layoutBottomSheet.recyclerPoppularServices.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false));

        setVendorSkillsAdapter();

        setPopularServiceAdapter();

        setBottomSheetBehaviour();


        /*Onclick*/
        skillBinding.fabAddSkill.setOnClickListener(v -> {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        skillBinding.layoutBottomSheet.tvCancelAddSkill.setOnClickListener(v -> {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

    }

    private void setPopularServiceAdapter() {
        servicesAdapter = new PopularServicesAdapter(this);
        skillBinding.layoutBottomSheet.recyclerPoppularServices.setAdapter(servicesAdapter);

    }

    private void setBottomSheetBehaviour() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {

                    case BottomSheetBehavior.STATE_DRAGGING:

                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        skillBinding.viewBgVendorSkill.setVisibility(View.VISIBLE);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:

                    case BottomSheetBehavior.STATE_HIDDEN:
                        skillBinding.viewBgVendorSkill.setVisibility(View.GONE);
                        break;

                    case BottomSheetBehavior.STATE_HALF_EXPANDED:

                        break;
                    case BottomSheetBehavior.STATE_SETTLING:

                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View view, float slideOffset) {

            }
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


}
