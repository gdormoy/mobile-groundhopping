package com.example.groundhopping_mobile.ui.contest;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.groundhopping_mobile.R;
import com.example.groundhopping_mobile.utils.ApiClass;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContestFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String username;
    private String userID;
    private String token;
    private String id;
    private String description;
    private String startDate;
    private String team1Score;
    private String team2Score;
    private String team1;
    private String team2;
    private String stadium;

    private ApiClass apiClass = new ApiClass();

    public ContestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContestFragment newInstance(String param1, String param2) {
        ContestFragment fragment = new ContestFragment();
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
        return inflater.inflate(R.layout.fragment_contest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            this.id = bundle.getString("id");
            this.description = bundle.getString("description");
            this.startDate = bundle.getString("startDate");
            this.team1Score = bundle.getString("team1Score");
            this.team2Score = bundle.getString("team2Score");
            this.team1 = bundle.getString("team1");
            this.team2 = bundle.getString("team2");
            this.stadium = bundle.getString("stadium");
        }

        JsonNode resT1 = null;
        JsonNode resT2 = null;
        JsonNode resStade = null;

        apiClass.getTeam(this.team1);
        do {
            resT1 = apiClass.getResp();
        } while (resT1 == null);
        apiClass.resetResp();
        apiClass.getTeam(this.team2);
        do {
            resT2 = apiClass.getResp();
        } while (resT2 == null);
        apiClass.resetResp();
        apiClass.getStadium(this.stadium);
        do {
            resStade = apiClass.getResp();
        } while (resStade == null);
        apiClass.resetResp();

        String homeTeamName = resT1.get("Name").asText();
        String AwayTeamName = resT2.get("Name").asText();
        String stadiumname = resStade.get("Name").asText();
        System.out.println("resT1: " + homeTeamName);
        System.out.println("resT2: " + AwayTeamName);
        System.out.println("resStade: " + stadiumname);

        TextView descr = view.findViewById(R.id.contest_description);
        TextView sdate = view.findViewById(R.id.contest_date);
        TextView homeT = view.findViewById(R.id.contest_home_team);
        TextView homeS = view.findViewById(R.id.contest_home_score);
        TextView awayT = view.findViewById(R.id.contest_away_team);
        TextView aways = view.findViewById(R.id.contest_away_score);
        TextView stade = view.findViewById(R.id.contest_stadium);
        Button add = view.findViewById(R.id.add_contest);
        Button bet = view.findViewById(R.id.bet_button);
        descr.setText(this.description);
        sdate.setText(this.startDate);
        homeT.setText(homeTeamName);
        homeS.setText(this.team1Score);
        awayT.setText(AwayTeamName);
        aways.setText(this.team2Score);
        stade.setText(stadiumname);

        NavigationView navigationView = view.getRootView().findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem logout = menu.findItem(R.id.nav_logout);
        if (logout.isVisible()){
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            this.username = sharedPref.getString("username", null);
            this.token = sharedPref.getString("token", null);
            this.userID = String.valueOf(sharedPref.getInt("userID", -1));
            add.setVisibility(View.VISIBLE);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Click add contest");
                    try {
                        apiClass.addUserContest(id, userID, username, token);
                        do {
                        }while (apiClass.getResp() == null);
                        System.out.println("res: " + apiClass.getResp());
                        Toast toast = Toast.makeText(getContext(), apiClass.getResp().get("res").asText(), Toast.LENGTH_LONG);
                        toast.show();
                        apiClass.resetResp();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                Date today = new Date();
                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(startDate));

                if(date1.compareTo(today) > 0) {
                    bet.setVisibility(View.VISIBLE);
                    bet.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            System.out.println("add BET");
                        }
                    });
                    System.out.println(date1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}