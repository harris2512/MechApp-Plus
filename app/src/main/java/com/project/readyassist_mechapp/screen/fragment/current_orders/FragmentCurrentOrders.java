package com.project.readyassist_mechapp.screen.fragment.current_orders;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.helper.SessionManager;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;
import com.project.readyassist_mechapp.screen.activity.homepage.adapter.CurrentOrdersListAdapter;
import com.project.readyassist_mechapp.utils.RAUtils;

import org.greenrobot.eventbus.Subscribe;

public class FragmentCurrentOrders extends Fragment {

    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

    protected RecyclerView recycler_current_orders;
    protected LinearLayoutManager linearLayoutManager;
    protected CurrentOrdersListAdapter ordersListAdapter;


    public static FragmentCurrentOrders newInstance() {
        return new FragmentCurrentOrders();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_current_orders, container, false);

        // sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);

        init();

        return view;
    }

    private void init() {

        initializeVariable();

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);

        showPb();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissPb();
            }
        }, 2000);

        setCurrentOrderListAdapter();

    }

    private void initializeVariable() {

        recycler_current_orders = view.findViewById(R.id.recycler_current_orders);

    }

    private void setCurrentOrderListAdapter() {
        sendMessageToActivity("1");
        recycler_current_orders.setLayoutManager(linearLayoutManager);
        ordersListAdapter = new CurrentOrdersListAdapter(getContext());
        recycler_current_orders.setAdapter(ordersListAdapter);

        recycler_current_orders.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int currentFirstVisible = linearLayoutManager.findFirstVisibleItemPosition();
                if (currentFirstVisible > 0) {
                    //scroll up!
                } else {
                    //scroll down!
                }

            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something

                    sendMessageToActivity("scroll_order");

                } else {
                    // Do something
                }
            }
        });

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

    private void dismissPb() {
        RAUtils.dismissProgressDialog();
    }

    private void showPb() {
        RAUtils.showProgressDialog(getContext(), true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        GlobalBus.getBus().unregister(this);
    }

}
