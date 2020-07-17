package com.example.groundhopping_mobile.ui.team;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.groundhopping_mobile.R;
import com.example.groundhopping_mobile.ui.stadium.StadiumFragment;
import com.example.groundhopping_mobile.utils.ApiClass;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ApiClass apiClass = new ApiClass();

    public TeamsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamsFragment newInstance(String param1, String param2) {
        TeamsFragment fragment = new TeamsFragment();
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
        return inflater.inflate(R.layout.fragment_teams, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout teams = view.findViewById(R.id.teams_list);
        JsonNode res = null;

        apiClass.getTeams();
        do {
            res = apiClass.getResp();
        } while (res == null);
        System.out.println("teams Response " + res);
        apiClass.resetResp();

        int i = 0;
        for (final JsonNode element: res.get("body")){
            i++;
            Button button = new Button(getContext());
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setText(element.get("Name").asText());
            button.setId(i);
            button.setOnClickListener(new View.OnClickListener() {
                String id = element.get("ID").asText();
                String name = element.get("Name").asText();
                String logo = element.get("Logo").asText();
                String president = element.get("President").asText();
                String address = element.get("Adress").asText();
                @Override
                public void onClick(View view) {
                    Bundle result = new Bundle();
                    result.putString("id", id);
                    result.putString("name", name);
                    result.putString("logo", logo);
                    result.putString("address", address);
                    result.putString("president", president);
                    TeamFragment teamFragment = new TeamFragment();
                    teamFragment.setArguments(result);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.team_layout, teamFragment)
                            .commit();
                }
            });

            teams.addView(button);
        }

    }
}