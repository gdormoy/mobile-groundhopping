package com.example.groundhopping_mobile.ui.team;

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
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String name;
    private String address;
    private String logo;
    private String id;
    private String president;

    public TeamFragment() {
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
    public static TeamFragment newInstance(String param1, String param2) {
        TeamFragment fragment = new TeamFragment();
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
        return inflater.inflate(R.layout.fragment_team, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            this.id = bundle.getString("id");
            this.name = bundle.getString("name");
            this.president = bundle.getString("president");
            this.address = bundle.getString("address");
            this.logo = bundle.getString("logo");
        }

        TextView teamName = view.findViewById(R.id.text_name);
        ImageView pict = view.findViewById(R.id.team_logo);
        TextView president = view.findViewById(R.id.text_president);
        TextView address = view.findViewById(R.id.text_address);

        teamName.setText(this.name);
        president.setText(this.president);
        address.setText(this.address);
        System.out.println(logo);

        UrlImageViewHelper.setUrlDrawable(pict, this.logo);
    }
}