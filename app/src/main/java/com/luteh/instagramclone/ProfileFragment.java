package com.luteh.instagramclone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.luteh.instagramclone.common.Common;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private EditText etProfileName, etProfileBio, etProfileProfession, etProfileHobbies, etProfileSport;
    private Button btnUpdateProfile;
    private String profileName, profileBio, profileProfession, profileHobbies, profileSport;
    private String keyProfileName, keyProfileBio, keyProfileProfession, keyProfileHobbies, keyProfileSport;
    private final String TAG = "Profile Fragment";

    private ParseUser parseUser;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        parseUser = ParseUser.getCurrentUser();

        etProfileName = view.findViewById(R.id.etProfileName);
        etProfileBio = view.findViewById(R.id.etProfileBio);
        etProfileProfession = view.findViewById(R.id.etProfileProfession);
        etProfileHobbies = view.findViewById(R.id.etProfileHobbies);
        etProfileSport = view.findViewById(R.id.etProfileSport);

        keyProfileName = getResources().getString(R.string.key_profile_name);
        keyProfileBio = getResources().getString(R.string.key_profile_bio);
        keyProfileProfession = getResources().getString(R.string.key_profile_profession);
        keyProfileHobbies = getResources().getString(R.string.key_profile_hobbies);
        keyProfileSport = getResources().getString(R.string.key_profile_sport);

        btnUpdateProfile = view.findViewById(R.id.btnProfileUpdate);
        btnUpdateProfile.setOnClickListener(this);

        getUserProfile();

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProfileUpdate:
                updateProfile();
                break;
        }
    }

    public void updateProfile() {
        try {
            if (isFieldsEmpty()) {
                Common.showInfoMessage(getContext(), "Fill out all fields!");
            } else {
                Common.showProgressBar(getContext());

                parseUser.put(keyProfileName, profileName);
                parseUser.put(keyProfileBio, profileBio);
                parseUser.put(keyProfileProfession, profileProfession);
                parseUser.put(keyProfileHobbies, profileHobbies);
                parseUser.put(keyProfileSport, profileSport);

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Common.dismissProgressBar();
                            Common.showSuccessMessage(getContext(), "Update profile success");
                        } else {
                            Common.dismissProgressBar();
                            Common.showErrorMessage(getContext(), "Update profile failed");
                            Log.e(TAG, e.getMessage());
                        }
                    }
                });
            }
        } catch (Exception e) {
            Common.dismissProgressBar();
            Common.showErrorMessage(getContext(), "Something wrong");
            Log.e(TAG, e.getMessage());
        }
    }

    public boolean isFieldsEmpty() {
        profileName = etProfileName.getText().toString();
        profileBio = etProfileBio.getText().toString();
        profileProfession = etProfileProfession.getText().toString();
        profileHobbies = etProfileHobbies.getText().toString();
        profileSport = etProfileSport.getText().toString();

        return (profileName.equals("") ||
                profileBio.equals("") ||
                profileProfession.equals("") ||
                profileHobbies.equals("") ||
                profileSport.equals(""));
    }

    public void getUserProfile() {
        try {
            Common.showProgressBar(getContext());

            if (parseUser.get(keyProfileName) != null) {
                etProfileName.setText(parseUser.get(keyProfileName).toString());
                etProfileBio.setText(parseUser.get(keyProfileBio).toString());
                etProfileProfession.setText(parseUser.get(keyProfileProfession).toString());
                etProfileHobbies.setText(parseUser.get(keyProfileHobbies).toString());
                etProfileSport.setText(parseUser.get(keyProfileSport).toString());

                Common.dismissProgressBar();
            } else Common.dismissProgressBar();
        } catch (Exception e) {
            Common.dismissProgressBar();
            Common.showErrorMessage(getContext(), "Something wrong!");
            Log.e(TAG, e.getMessage());
        }
    }
}
