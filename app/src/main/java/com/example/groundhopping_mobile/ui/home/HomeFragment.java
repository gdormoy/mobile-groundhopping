package com.example.groundhopping_mobile.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.groundhopping_mobile.R;
import com.example.groundhopping_mobile.utils.ApiClass;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HomeFragment extends Fragment {

    private String username;
    private String userID;
    private String token;

    private ApiClass apiClass = new ApiClass();

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavigationView navigationView = view.getRootView().findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem logout = menu.findItem(R.id.nav_logout);

        TextView username_field = view.findViewById(R.id.name_fiel);
        TextView bdate_field = view.findViewById(R.id.date_field);
        TextView money_field = view.findViewById(R.id.money_field);
        TextView email_field = view.findViewById(R.id.email_field);
        TextView credit = view.findViewById(R.id.textView7);
        credit.setVisibility(View.VISIBLE);
        LinearLayout vehicule_list = view.findViewById(R.id.vehicule_list);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 20, 20, 20);

        if (logout.isVisible()){
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            this.username = sharedPref.getString("username", null);
            this.token = sharedPref.getString("token", null);
            this.userID = String.valueOf(sharedPref.getInt("userID", -1));

            JsonNode user = null;
            JsonNode vehicules = null;
            apiClass.getUserByName(username, token);
            do {
            } while (apiClass.getResp() == null);
            user = apiClass.getResp();
            apiClass.resetResp();
            username_field.setText(user.get("Username").asText());
            bdate_field.setText(user.get("BirthDate").asText());
            money_field.setText(user.get("Money").asText());
            email_field.setText(user.get("Email").asText());

            apiClass.getUservehicules(username, token, userID);
            do {
            } while (apiClass.getResp() == null);
            vehicules = apiClass.getResp();
            apiClass.resetResp();
            Integer paddingTop = 0;
            Integer paddingBot = 0;
            Integer paddingLeft = 0;
            Integer paddingRight = 0;

            for(int i = 0; i < vehicules.size(); i++) {
                paddingTop += 20;
                paddingLeft += 20;
                System.out.println("Vehicule: " + vehicules.get(i).getClass());
                TextView vehiculModel = new TextView(getContext());
                TextView vehiculConsumption = new TextView(getContext());
                TextView vehiculFuel = new TextView(getContext());
                try {
                    vehiculModel.setText(vehicules.get(i).get("Model").asText());
                    vehiculConsumption.setText(vehicules.get(i).get("Consumption").asText());
                    vehiculFuel.setText(vehicules.get(i).get("Fuel").asText());

                    vehicule_list.addView(vehiculModel, layoutParams);
                    vehicule_list.addView(vehiculConsumption, layoutParams);
                    vehicule_list.addView(vehiculFuel, layoutParams);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
}