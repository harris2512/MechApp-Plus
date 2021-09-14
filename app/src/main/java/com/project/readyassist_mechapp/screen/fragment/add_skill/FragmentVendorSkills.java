package com.project.readyassist_mechapp.screen.fragment.add_skill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.helper.AppConstants;
import com.project.readyassist_mechapp.helper.SessionManager;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;
import com.project.readyassist_mechapp.screen.activity.vendor_skill.adapter.PopularServicesAdapter;

import org.greenrobot.eventbus.Subscribe;


public class FragmentVendorSkills extends Fragment {


    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;


    protected AutoCompleteTextView edt_search_add_skill;
    protected RecyclerView recycler_popular_services;
    protected PopularServicesAdapter servicesAdapter;
    ArrayAdapter<String> adapter;


    public static FragmentVendorSkills newInstance() {
        return new FragmentVendorSkills();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vendor_skills, container, false);

//         sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);

        init();

        return view;
    }

    private void init() {

        initializeVariable();

        sendMessageToActivity("Add Skill");


    }

    private void initializeVariable() {

        edt_search_add_skill = view.findViewById(R.id.edt_search_add_skill);
        recycler_popular_services = view.findViewById(R.id.recycler_popular_services);

        recycler_popular_services.setLayoutManager(new GridLayoutManager(getContext(), 2));
        setPopularServiceAdapter();

    }


    private void setPopularServiceAdapter() {

        /*Auto Complete*/
        adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, AppConstants.arrayPopularServices);
        edt_search_add_skill.setThreshold(2);
        edt_search_add_skill.setAdapter(adapter);
        edt_search_add_skill.setOnItemClickListener((parent, view, position, id) -> {

            String selectedService = parent.getItemAtPosition(position).toString();
            showToast(selectedService);
            edt_search_add_skill.setText(selectedService, false);

        });


        servicesAdapter = new PopularServicesAdapter(getContext(), AppConstants.arrayPopularServices);
        recycler_popular_services.setAdapter(servicesAdapter);

        servicesAdapter.setOnRecyclerViewItemClickListener(new PopularServicesAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                servicesAdapter.setThisPosition(position + 1);


                sendMessageToActivity(AppConstants.arrayPopularServices[position]);

                Fragment fragment = FragmentVendorSkillCharges.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_add_skill, fragment);
                ft.addToBackStack(null);
                ft.commit();


                servicesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(int position) {

            }
        });


    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    private void sendMessageToActivity(String message) {
        Events.FragmentActivityMessage msgFragmentToActivityEvent = new Events.
                FragmentActivityMessage(message);
        GlobalBus.getBus().post(msgFragmentToActivityEvent);
    }

    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        GlobalBus.getBus().unregister(this);
    }

}
