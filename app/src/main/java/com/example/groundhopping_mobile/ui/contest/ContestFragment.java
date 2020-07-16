package com.example.groundhopping_mobile.ui.contest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.groundhopping_mobile.R;

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

    private String description;
    private String startDate;
    private String team1Score;
    private String team2Score;
    private String team1;
    private String team2;
    private String stadium;

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
//        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
//            @Override
//            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
//                // We use a String here, but any type that can be put in a Bundle is supported
//                String description = bundle.getString("Description");
//                String startDate = bundle.getString("StartDate");
//                String team1Score = bundle.getString("Team1Score");
//                String team2Score = bundle.getString("Team2Score");
//                String team1 = bundle.getString("Team1");
//                String team2 = bundle.getString("Team2");
//                String stadium = bundle.getString("Stadium");
//            }
//        });
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
        Bundle bundle = this.getArguments();
        if(bundle != null){
            this.description = bundle.getString("description");
            this.startDate = bundle.getString("startDate");
            this.team1Score = bundle.getString("team1Score");
            this.team2Score = bundle.getString("team2Score");
            this.team1 = bundle.getString("team1");
            this.team2 = bundle.getString("team2");
            this.stadium = bundle.getString("stadium");
        }
        System.out.println("Description: " + this.description);
        TextView textView = view.findViewById(R.id.contest_text);
        textView.setText(this.description);
        super.onViewCreated(view, savedInstanceState);

    }
}