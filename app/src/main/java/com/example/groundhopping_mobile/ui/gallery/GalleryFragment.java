package com.example.groundhopping_mobile.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.groundhopping_mobile.R;
import com.example.groundhopping_mobile.utils.ApiClass;
import com.fasterxml.jackson.databind.JsonNode;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.json.JSONException;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private String username;
    private String token;
    private String userID;

    private ApiClass apiClass = new ApiClass();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout myContest = view.findViewById(R.id.my_contest);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 20, 20, 20);
        final Fragment frg = this;

        JsonNode contests = null;
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        this.username = sharedPref.getString("username", null);
        this.token = sharedPref.getString("token", null);
        this.userID = String.valueOf(sharedPref.getInt("userID", -1));


        apiClass.getUserContest(username, userID, token);
        do {
        } while (apiClass.getResp() == null);
        contests = apiClass.getResp();
        apiClass.resetResp();

        for(int i = 0; i < contests.size(); i++) {

            TextView startDate = new TextView(getContext());
            TextView description = new TextView(getContext());
            TextView team1Score = new TextView(getContext());
            TextView team2Score = new TextView(getContext());
            TextView t1Name = new TextView(getContext());
            TextView t2Name = new TextView(getContext());
            TextView sName = new TextView(getContext());


            TextView separator = new TextView(getContext());
            final Button delete = new Button(getContext());

            try {

                JsonNode resT1 = null;
                JsonNode resT2 = null;
                JsonNode resStade = null;

                apiClass.getTeam(contests.get(i).get("Team1").asText());
                do {
                    resT1 = apiClass.getResp();
                } while (resT1 == null);
                apiClass.resetResp();
                apiClass.getTeam(contests.get(i).get("Team2").asText());
                do {
                    resT2 = apiClass.getResp();
                } while (resT2 == null);
                apiClass.resetResp();
                apiClass.getStadium(contests.get(i).get("Stadium").asText());
                do {
                    resStade = apiClass.getResp();
                } while (resStade == null);
                apiClass.resetResp();

                String homeTeamName = resT1.get("Name").asText();
                String AwayTeamName = resT2.get("Name").asText();
                String stadiumname = resStade.get("Name").asText();

                startDate.setText(contests.get(i).get("StartDate").asText());
                description.setText(contests.get(i).get("Description").asText());
                team1Score.setText(contests.get(i).get("Team1Score").asText() + " Goal(s)");
                team2Score.setText(contests.get(i).get("Team2Score").asText() + " Goal(s)");
                t1Name.setText(homeTeamName);
                t2Name.setText(AwayTeamName);
                sName.setText(stadiumname);
                separator.setText("----------");
                delete.setId(contests.get(i).get("ID").asInt());
                delete.setText("Remove");

                delete.setOnClickListener(new View.OnClickListener() {
                    String ID = String.valueOf(delete.getId());

                    @Override
                    public void onClick(View view) {
                        try {
                            apiClass.removeUserContest(username, userID, ID, token);
                            do {
                            }while (apiClass.getResp() == null);
                            apiClass.resetResp();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            if (Build.VERSION.SDK_INT >= 26) {
                                ft.setReorderingAllowed(false);
                            }
                            ft.detach(frg).attach(frg).commit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                myContest.addView(description, layoutParams);
                myContest.addView(startDate, layoutParams);
                myContest.addView(t1Name, layoutParams);
                myContest.addView(team1Score, layoutParams);
                myContest.addView(t2Name, layoutParams);
                myContest.addView(team2Score, layoutParams);
                myContest.addView(sName, layoutParams);
                myContest.addView(delete, layoutParams);
                myContest.addView(separator, layoutParams);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}