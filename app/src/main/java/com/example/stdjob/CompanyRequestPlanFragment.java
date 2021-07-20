package com.example.stdjob;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

public class CompanyRequestPlanFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_request_plan_fragment,container,false);

        LinearLayout layout=view.findViewById(R.id.layout_plan);
        AppCompatTextView tv=view.findViewById(R.id.message_plan_disable);


        if(App.prefs.isTrueBooleanValue(AppPreferences.PreferencesKeys.SETTINGPLANFORCOMAPNIES)){
            tv.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
        }else{
            tv.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
        }


        return view;
    }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        if(App.prefs.isTrueBooleanValue(AppPreferences.PreferencesKeys.SETTINGPLANFORCOMAPNIES)) {
            menu.add(getActivity().getResources().getString(R.string.refresh)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(getActivity().getResources().getString(R.string.send)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }
}
