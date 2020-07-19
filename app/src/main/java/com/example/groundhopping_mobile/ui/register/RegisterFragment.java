package com.example.groundhopping_mobile.ui.register;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groundhopping_mobile.R;
import com.example.groundhopping_mobile.utils.ApiClass;

import org.json.JSONException;

import java.security.NoSuchAlgorithmException;

import static com.example.groundhopping_mobile.utils.ApiClass.getSHA;
import static com.example.groundhopping_mobile.utils.ApiClass.toHexString;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ApiClass apiClass = new ApiClass();

        final EditText usernameEditText = view.findViewById(R.id.edit_username);
        final EditText passwordEditText = view.findViewById(R.id.edit_password);
        final EditText birthdateEditText = view.findViewById(R.id.edit_date);
        final EditText emailEditText = view.findViewById(R.id.edit_email);
        final Button registerButton = view.findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                String text = "Every field are required";
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String birthdate = birthdateEditText.getText().toString();
                String email = emailEditText.getText().toString();

                if (!(username.isEmpty() || password.isEmpty() || birthdate.isEmpty() || email.isEmpty())){
                    String hashed_password = "";
                    Toast toast;
                    try {
                        hashed_password = toHexString(getSHA(password));
                        System.out.println(hashed_password);
                        apiClass.register(username, hashed_password,email,birthdate);
                        toast = Toast.makeText(context, "Your are now register", Toast.LENGTH_LONG);
                    } catch (NoSuchAlgorithmException e) {
                        toast = Toast.makeText(context, "Unexpected Error", Toast.LENGTH_LONG);
                        e.printStackTrace();
                    } catch (JSONException e) {
                        toast = Toast.makeText(context, "Unexpected Error", Toast.LENGTH_LONG);
                        e.printStackTrace();
                    }
                    toast.show();
                    apiClass.resetResp();
                    getActivity().onBackPressed();
                } else {
                    Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                    toast.show();
                    apiClass.resetResp();
                }
                apiClass.resetResp();
            }
        });
    }
}