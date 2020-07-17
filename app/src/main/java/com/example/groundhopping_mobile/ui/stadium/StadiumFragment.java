package com.example.groundhopping_mobile.ui.stadium;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.groundhopping_mobile.R;
import com.example.groundhopping_mobile.utils.ApiClass;
import com.fasterxml.jackson.databind.JsonNode;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StadiumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StadiumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String name;
    private String address;
    private String city;
    private String id;
    private String picture;
    private String latitude;
    private String longitude;
    private String capacity;

    private ApiClass apiClass = new ApiClass();

    public StadiumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StadiumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StadiumFragment newInstance(String param1, String param2) {
        StadiumFragment fragment = new StadiumFragment();
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
        return inflater.inflate(R.layout.fragment_stadium, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            this.id = bundle.getString("id");
            this.name = bundle.getString("name");
            this.capacity = bundle.getString("capacity");
            this.latitude = bundle.getString("latitude");
            this.longitude = bundle.getString("longitude");
            this.address = bundle.getString("address");
            this.city = bundle.getString("city");
            this.picture = bundle.getString("picture");
        }

        TextView stadiumName = view.findViewById(R.id.stadium_name);
        ImageView pict = view.findViewById(R.id.stadium_image);
        TextView capacity = view.findViewById(R.id.stadium_capacity);
        TextView address = view.findViewById(R.id.stadium_address);
        TextView city = view.findViewById(R.id.stadium_city);

        stadiumName.setText(this.name);
        capacity.setText(this.capacity);
        address.setText(this.address);
        city.setText(this.city);

        UrlImageViewHelper.setUrlDrawable(pict, this.picture);
    }
}