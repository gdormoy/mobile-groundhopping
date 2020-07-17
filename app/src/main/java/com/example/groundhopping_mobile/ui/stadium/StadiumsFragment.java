package com.example.groundhopping_mobile.ui.stadium;

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
import com.example.groundhopping_mobile.ui.contest.ContestFragment;
import com.example.groundhopping_mobile.utils.ApiClass;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StadiumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StadiumsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ApiClass apiClass = new ApiClass();

    public StadiumsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StadiumsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StadiumsFragment newInstance(String param1, String param2) {
        StadiumsFragment fragment = new StadiumsFragment();
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
        return inflater.inflate(R.layout.fragment_stadiums, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout stadiums = view.findViewById(R.id.stadiums_list);
        JsonNode res = null;

        apiClass.getStadiums();
        do {
            res = apiClass.getResp();
        } while (res == null);
        System.out.println("Stadiums Response " + res);
        apiClass.resetResp();

        int i = 0;
        for (final JsonNode element: res.get("body")){
            i++;
            Button button = new Button(getContext());
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setText(element.get("name").asText());
            button.setId(i);
            button.setOnClickListener(new View.OnClickListener() {
                String id = element.get("ID").asText();
                String name = element.get("name").asText();
                String picture = element.get("picture").asText();
                String capacity = element.get("capacity").asText();
                String address = element.get("address").asText();
                String city = element.get("city").asText();
                String longitude = element.get("longitude").asText();
                String latitude = element.get("latitude").asText();
                @Override
                public void onClick(View view) {
                    Bundle result = new Bundle();
                    result.putString("id", id);
                    result.putString("name", name);
                    result.putString("picture", picture);
                    result.putString("capacity", capacity);
                    result.putString("address", address);
                    result.putString("city", city);
                    result.putString("longitude", longitude);
                    result.putString("latitude", latitude);
                    StadiumFragment stadiumFragment = new StadiumFragment();
                    stadiumFragment.setArguments(result);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.stadium_layout, stadiumFragment)
                            .commit();

                }
            });

            stadiums.addView(button);
        }
    }
}