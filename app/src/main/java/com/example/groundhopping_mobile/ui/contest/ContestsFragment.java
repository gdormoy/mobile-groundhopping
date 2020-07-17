package com.example.groundhopping_mobile.ui.contest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groundhopping_mobile.R;
import com.example.groundhopping_mobile.utils.ApiClass;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class ContestsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ApiClass apiClass = new ApiClass();

    public ContestsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContestsFragment.
     */
    public static ContestsFragment newInstance(String param1, String param2) {
        ContestsFragment fragment = new ContestsFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_contests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout contests = view.findViewById(R.id.contests_list);
        JsonNode res = null;

        apiClass.getContests();
        do {
            res = apiClass.getResp();
        } while (res == null);
        System.out.println("Contests Response " + res);
        apiClass.resetResp();
        int i = 0;
        for (final JsonNode element: res.get("body")){
            i++;
            Button button = new Button(getContext());
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setText(element.get("Description").asText());

            button.setOnClickListener(new View.OnClickListener() {
                String id = element.get("ID").asText();
                String desc = element.get("Description").asText();
                String startDate = element.get("StartDate").asText();
                String team1Score = element.get("Team1Score").asText();
                String team2Score = element.get("Team2Score").asText();
                String team1 = element.get("Team1").asText();
                String team2 = element.get("Team2").asText();
                String stadium = element.get("Stadium").asText();
                @Override
                public void onClick(View view) {
                    Bundle result = new Bundle();
                    result.putString("id", id);
                    result.putString("description", desc);
                    result.putString("startDate", startDate);
                    result.putString("team1Score", team1Score);
                    result.putString("team2Score", team2Score);
                    result.putString("team1", team1);
                    result.putString("team2", team2);
                    result.putString("stadium", stadium);
                    ContestFragment contestFragment = new ContestFragment();
                    contestFragment.setArguments(result);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contest_layout, contestFragment)
                            .commit();
                }
            });
            button.setId(i);

            System.out.println(element);
            System.out.println(button.getText());
//            mArrData.add(description);
            contests.addView(button);
        }

    }
}