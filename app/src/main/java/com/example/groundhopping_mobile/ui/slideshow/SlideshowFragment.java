package com.example.groundhopping_mobile.ui.slideshow;

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

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private ApiClass apiClass = new ApiClass();
    private String username;
    private String token;
    private String userID;
    final Fragment frg = this;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout myStadiums = view.findViewById(R.id.my_stadiums);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 20, 20, 20);

        JsonNode stadiums = null;
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        this.username = sharedPref.getString("username", null);
        this.token = sharedPref.getString("token", null);
        this.userID = String.valueOf(sharedPref.getInt("userID", -1));
        apiClass.getUserStadium(username, userID, token);
        do {
        } while (apiClass.getResp() == null);
        stadiums = apiClass.getResp();
        apiClass.resetResp();

        for(int i = 0; i < stadiums.size(); i++) {
            TextView stadiumName = new TextView(getContext());
            ImageView pict = new ImageView(getContext());
            TextView capacity = new TextView(getContext());
            TextView address = new TextView(getContext());
            TextView city = new TextView(getContext());
            TextView rate = new TextView(getContext());
            TextView separator = new TextView(getContext());
            final Button delete = new Button(getContext());

            try {
                UrlImageViewHelper.setUrlDrawable(pict, stadiums.get(i).get("picture").asText());
                stadiumName.setText(stadiums.get(i).get("name").asText());
                capacity.setText(stadiums.get(i).get("capacity").asText());
                address.setText(stadiums.get(i).get("address").asText());
                city.setText(stadiums.get(i).get("city").asText());
                rate.setText(stadiums.get(i).get("note").asText() + "/10");
                separator.setText("----------");
                delete.setId(stadiums.get(i).get("ID").asInt());
                delete.setText("Remove");

                delete.setOnClickListener(new View.OnClickListener() {
                    String ID = String.valueOf(delete.getId());

                    @Override
                    public void onClick(View view) {
                        try {
                            apiClass.removeUserStadium(username, userID, ID, token);
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

                myStadiums.addView(stadiumName, layoutParams);
                myStadiums.addView(capacity, layoutParams);
                myStadiums.addView(address, layoutParams);
                myStadiums.addView(city, layoutParams);
                myStadiums.addView(rate, layoutParams);
                myStadiums.addView(pict, layoutParams);
                myStadiums.addView(delete, layoutParams);
                myStadiums.addView(separator, layoutParams);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}