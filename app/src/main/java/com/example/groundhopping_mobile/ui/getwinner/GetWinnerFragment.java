package com.example.groundhopping_mobile.ui.getwinner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.groundhopping_mobile.R;
import com.example.groundhopping_mobile.utils.ApiClass;
import com.fasterxml.jackson.databind.JsonNode;

import org.json.JSONException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GetWinnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetWinnerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ApiClass apiClass = new ApiClass();
    TextView home;
    TextView away;
    TextView winner;
    Button button;

    public GetWinnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetWinnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GetWinnerFragment newInstance(String param1, String param2) {
        GetWinnerFragment fragment = new GetWinnerFragment();
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
        return inflater.inflate(R.layout.fragment_get_winner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        home = view.findViewById(R.id.home_team);
        away = view.findViewById(R.id.away_team);
        winner = view.findViewById(R.id.winner_text);
        button = view.findViewById(R.id.winner_button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                JsonNode res = null;
                try {
                    apiClass.getwinner(home.getText().toString(), away.getText().toString());
                    apiClass.resetResp();
                    do {
                    } while (apiClass.getResp() == null);
                    res = apiClass.getResp();
                    apiClass.resetResp();
                    System.out.println(res);
                    winner.setText(res.get("body").asText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}