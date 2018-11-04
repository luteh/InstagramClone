package com.luteh.instagramclone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.luteh.instagramclone.common.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private EditText etProfileName, etProfileBio, etProfileProfession, etProfileHobbies, etProfileSport;
    private Button btnUpdate;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        etProfileBio = view.findViewById(R.id.etProfileName);
        etProfileBio = view.findViewById(R.id.etProfileBio);
        etProfileProfession = view.findViewById(R.id.etProfileProfession);
        etProfileHobbies = view.findViewById(R.id.etProfileHobbies);
        etProfileSport = view.findViewById(R.id.etProfileSport);

        btnUpdate = view.findViewById(R.id.btnProfileUpdate);
        btnUpdate.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProfileUpdate:
                break;
        }
    }
}
