package com.example.groundhopping_mobile.ui.carpooling;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.groundhopping_mobile.R;
import com.example.groundhopping_mobile.utils.ApiClass;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link carpoolingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class carpoolingFragment extends Fragment {

    private ApiClass apiClass = new ApiClass();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String username;
    private String token;
    private String userID;

    public carpoolingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment carpoolingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static carpoolingFragment newInstance(String param1, String param2) {
        carpoolingFragment fragment = new carpoolingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_carpooling, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavigationView navigationView = view.getRootView().findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem logout = menu.findItem(R.id.nav_logout);

        LinearLayout carpoolingoffer = view.findViewById(R.id.offer);
        LinearLayout contest = view.findViewById(R.id.contestCarpooling);
        final TextView address = view.findViewById(R.id.address);
        final TextView godate = view.findViewById(R.id.godate);
        final TextView backdate = view.findViewById(R.id.backdate);
        final TextView price = view.findViewById(R.id.pricecarpooling);
        Button submit = view.findViewById(R.id.addcarpooling);
        final String[] selectedcontest = new String[1];

        if(logout.isVisible()) {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            this.username = sharedPref.getString("username", null);
            this.token = sharedPref.getString("token", null);
            this.userID = String.valueOf(sharedPref.getInt("userID", -1));

            address.setVisibility(View.VISIBLE);
            godate.setVisibility(View.VISIBLE);
            backdate.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);
            contest.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            JsonNode rescontest = null;
            apiClass.resetResp();
            apiClass.getContests();
            do {
            } while (apiClass.getResp() == null);
            rescontest = apiClass.getResp();
            apiClass.resetResp();

            for (final JsonNode element: rescontest.get("body")) {
                System.out.println(element);
                Button button = new Button(getContext());
                button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                button.setText(element.get("Description").asText());

                button.setOnClickListener(new View.OnClickListener() {
                    String id = element.get("ID").asText();
                    @Override
                    public void onClick(View view) {
                        selectedcontest[0] = id;
                    }
                });
//                button.setId(Integer.valueOf(selectedcontest[0]));
                contest.addView(button);
            }

            submit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    try {
                        apiClass.resetResp();
                        apiClass.addCarpoolingoffer(username,token,userID,selectedcontest[0], address.getText().toString(),
                                godate.getText().toString(), backdate.getText().toString(), price.getText().toString());
                        do {
                        } while (apiClass.getResp() == null);
                        apiClass.resetResp();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        JsonNode rescarpoolingoffer = null;
        apiClass.resetResp();
        apiClass.getCarpoolingoffer();
        do{
        }while (apiClass.getResp() == null);
        rescarpoolingoffer = apiClass.getResp();
        apiClass.resetResp();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 20, 20, 20);

        for (final JsonNode element: rescarpoolingoffer.get("body")){
            TextView pricepooling = new TextView(getContext());
            TextView AddressFrompooling = new TextView(getContext());
            TextView GoDatepooling = new TextView(getContext());
            TextView BackDatepooling = new TextView(getContext());
            TextView contestpooling = new TextView(getContext());
            TextView Hometeam = new TextView(getContext());
            TextView Awayteam = new TextView(getContext());
            TextView stadium = new TextView(getContext());
            TextView separator = new TextView(getContext());
            try {
                pricepooling.setText(element.get("Price").asText() + "€");
                AddressFrompooling.setText("meeting point: " + element.get("AddressFrom").asText());
                GoDatepooling.setText("Go Date: " + element.get("GoDate").asText());
                BackDatepooling.setText("Back date: " + element.get("BackDate").asText());
                contestpooling.setText(element.get("Contest").asText());
                Hometeam.setText("Home team: " + element.get("Team1").get("Name").asText());
                Awayteam.setText("Away team: " + element.get("Team2").get("Name").asText());
                stadium.setText(element.get("Stadium").get("Name").asText());
                separator.setText("----------");

                carpoolingoffer.addView(pricepooling, layoutParams);
                carpoolingoffer.addView(AddressFrompooling, layoutParams);
                carpoolingoffer.addView(GoDatepooling, layoutParams);
                carpoolingoffer.addView(BackDatepooling, layoutParams);
                carpoolingoffer.addView(contestpooling, layoutParams);
                carpoolingoffer.addView(Hometeam, layoutParams);
                carpoolingoffer.addView(Awayteam, layoutParams);
                carpoolingoffer.addView(stadium, layoutParams);
                carpoolingoffer.addView(separator, layoutParams);

            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}