package com.example.groundhopping_mobile.ui.roadmap;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.ColorInt;
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

import com.example.groundhopping_mobile.MainActivity;
import com.example.groundhopping_mobile.R;
import com.example.groundhopping_mobile.data.model.Stadium;
import com.example.groundhopping_mobile.data.model.Vehicule;
import com.example.groundhopping_mobile.utils.ApiClass;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoadMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoadMapFragment extends Fragment {

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

    private ApiClass apiClass = new ApiClass();


    public RoadMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoadMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoadMapFragment newInstance(String param1, String param2) {
        RoadMapFragment fragment = new RoadMapFragment();
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
        return inflater.inflate(R.layout.fragment_road_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JsonNode stadiums = null;

        final ArrayList<Stadium> list = new ArrayList<Stadium>();
        final Vehicule vehicule = new Vehicule();

        final TextView vehiculecomsumption = view.findViewById(R.id.vehiculeconsumption);
        final TextView vehiculetype = view.findViewById(R.id.vehiculetype);

        final TextView comsumption = view.findViewById(R.id.text_comsumption);
        final TextView price = view.findViewById(R.id.text_price);
        final TextView distance = view.findViewById(R.id.text_distance);
        Button submit = view.findViewById(R.id.button_submit);

        LinearLayout stadiumLayout = view.findViewById(R.id.stadiums_list_layout);
        LinearLayout vehiculeLayout = view.findViewById(R.id.vehicule_list_layout);

        apiClass.getStadiums();
        do {
        } while (apiClass.getResp() == null);
        stadiums = apiClass.getResp();
        apiClass.resetResp();
        for (final JsonNode element: stadiums.get("body")){
            final Button button = new Button(getContext());
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setText(element.get("name").asText());
            final Stadium stadium = new Stadium(
                    Integer.valueOf(element.get("ID").asText()),
                    element.get("name").asText(),
                    element.get("latitude").asText(),
                    element.get("longitude").asText()
            );
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {


                    if (button.getBackground().getAlpha() == 255) {
                        button.getBackground().setAlpha(20);
                        list.add(stadium);
                    } else {
                        list.remove(stadium);
                        button.getBackground().setAlpha(255);
                    }
                }
            });
            stadiumLayout.addView(button);
        }


        NavigationView navigationView = view.getRootView().findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem logout = menu.findItem(R.id.nav_logout);

        if (logout.isVisible()) {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            this.username = sharedPref.getString("username", null);
            this.token = sharedPref.getString("token", null);
            this.userID = String.valueOf(sharedPref.getInt("userID", -1));

            apiClass.getUservehicules(username, token, userID);
            do {
            } while (apiClass.getResp() == null);
            final JsonNode vehicules = apiClass.getResp();
            apiClass.resetResp();
            if (vehicules.size() > 0){
                vehiculeLayout.setVisibility(View.VISIBLE);
                for(int i = 0; i < vehicules.size(); i++) {
                    final Button vehiculebutton = new Button(getContext());
                    vehiculebutton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    vehiculebutton.setText(vehicules.get(i).get("Model").asText());
                    final int finalI = i;
                    vehiculebutton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            vehicule.setId(vehicules.get(finalI).get("ID").asInt());
                            vehicule.setModel(vehicules.get(finalI).get("Model").asText());
                            vehicule.setType(vehicules.get(finalI).get("Fuel").asText());
                            vehicule.setConsumption(vehicules.get(finalI).get("Consumption").asDouble());
                            vehiculetype.setText(vehicule.getType());
                            vehiculecomsumption.setText(vehicule.getConsumption().toString());
                        }
                    });
                    vehiculeLayout.addView(vehiculebutton);
                }
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                JSONObject origine = new JSONObject();
                JsonNode requiredFuel = null;
                try {

//                    Location location = ((MainActivity)getActivity()).getLocation();
//                    System.out.println("latitude: " + location.getLatitude() + ", longitude: " + location.getLongitude());
                    origine.put("latitude", "48.8491");
                    origine.put("longitude", "2.3896");
                    apiClass.getRequiredFuel(vehiculecomsumption.getText().toString(), vehiculetype.getText().toString(), list, origine);
                    do {
                    }while (apiClass.getResp() == null);
                    requiredFuel = apiClass.getResp();
                    apiClass.resetResp();
                    System.out.println(requiredFuel);
                    comsumption.setText(requiredFuel.get("body").get("Fuel").asText() + "L");
                    price.setText(requiredFuel.get("body").get("Price").asText() + "€");
                    distance.setText(requiredFuel.get("body").get("Distance").asText() + "Km");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}