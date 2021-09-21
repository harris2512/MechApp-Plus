package com.project.readyassist_mechapp.screen.fragment.my_earnings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.readyassist_mechapp.R;
import com.project.readyassist_mechapp.helper.SessionManager;
import com.project.readyassist_mechapp.helper.events.Events;
import com.project.readyassist_mechapp.helper.events.GlobalBus;
import com.project.readyassist_mechapp.utils.RAUtils;

import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentEarnings extends Fragment {


    protected View view;
    protected Fragment selectedFragment;
    protected SessionManager sessionManager;

    protected TextView tv_earning_selected_date;
    protected ImageView img_earning_previous_date, img_earning_next_date;

    protected Calendar calendar;
    protected boolean isClickPrev = false, isClickNext = false;


    public static FragmentEarnings newInstance() {
        return new FragmentEarnings();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_earnings, container, false);

        // sessionManager = new SessionManager(getActivity());

        // register the event to listen.
        GlobalBus.getBus().register(this);

        init();

        return view;
    }

    private void init() {

        sendMessageToActivity("3");

        tv_earning_selected_date = view.findViewById(R.id.tv_earning_selected_date);
        img_earning_previous_date = view.findViewById(R.id.img_earning_previous_date);
        img_earning_next_date = view.findViewById(R.id.img_earning_next_date);


        String[] current_week = getCurrentWeek();
        String currentWeek = current_week[0] + " - " + current_week[current_week.length - 1];
        tv_earning_selected_date.setText(currentWeek);


        img_earning_previous_date.setOnClickListener(v -> {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

            if (!isClickPrev) {

                if (isClickNext) {
                    String[] previous = new String[14];
                    for (int i = 0; i < 14; i++) {
                        calendar.add(Calendar.DATE, -1);
                        previous[i] = df.format(this.calendar.getTime());
                    }
                    String value = previous[previous.length - 1] + " - " + previous[7];
                    tv_earning_selected_date.setText(value);
                } else {
                    this.calendar = Calendar.getInstance();
                    this.calendar.setFirstDayOfWeek(Calendar.SUNDAY);
                    this.calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                    String[] previous = new String[7];
                    for (int i = 0; i < 7; i++) {
                        calendar.add(Calendar.DATE, -1);
                        previous[i] = df.format(this.calendar.getTime());
                    }
                    String value = previous[previous.length - 1] + " - " + previous[0];
                    tv_earning_selected_date.setText(value);

                }
                isClickPrev = true;
                isClickNext = false;

                return;
            }

            String[] previous = new String[7];
            for (int i = 0; i < 7; i++) {
                calendar.add(Calendar.DATE, -1);
                previous[i] = df.format(this.calendar.getTime());
            }
            String value = previous[previous.length - 1] + " - " + previous[0];
            tv_earning_selected_date.setText(value);
            isClickNext = false;

        });


        img_earning_next_date.setOnClickListener(v -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            if (!isClickNext) {

                if (isClickPrev) {
                    String[] next = new String[14];
                    for (int i = 0; i < 14; i++) {
                        next[i] = sdf.format(this.calendar.getTime());
                        this.calendar.add(Calendar.DATE, 1);
                    }
                    String value = next[7] + " - " + next[next.length - 1];
                    tv_earning_selected_date.setText(value);
                } else {
                    String[] next = new String[7];
                    for (int i = 0; i < 7; i++) {
                        next[i] = sdf.format(this.calendar.getTime());
                        this.calendar.add(Calendar.DATE, 1);
                    }
                    String value = next[0] + " - " + next[next.length - 1];
                    tv_earning_selected_date.setText(value);
                }

                isClickNext = true;
                isClickPrev = false;

                return;
            }

            String[] next = new String[7];
            for (int i = 0; i < 7; i++) {
                next[i] = sdf.format(this.calendar.getTime());
                this.calendar.add(Calendar.DATE, 1);
            }
            String value = next[0] + " - " + next[next.length - 1];
            tv_earning_selected_date.setText(value);
            isClickPrev = false;

        });

    }

    public String[] getCurrentWeek() {
        this.calendar = Calendar.getInstance();
        this.calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        this.calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return getNextWeek();
    }

    public String[] getNextWeek() {
        DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String[] days = new String[7];
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(this.calendar.getTime());
            this.calendar.add(Calendar.DATE, 1);
        }
        return days;
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
