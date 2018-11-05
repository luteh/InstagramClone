package com.luteh.instagramclone;


import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.luteh.instagramclone.common.Common;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {
    private ListView lvUsers;
    private ArrayAdapter<String> usersAdapter;
    private ArrayList<String> usersList;
    private final String TAG = "UsersFragment";


    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        lvUsers = view.findViewById(R.id.lvUsers);
        usersList = new ArrayList<String>();
        usersAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, usersList);

        getAllUsers();

        return view;
    }

    public void getAllUsers() {
        try {
            Common.showProgressBar(getContext());
            ParseQuery<ParseUser> userParseQuery = ParseUser.getQuery();
            userParseQuery.whereNotEqualTo(Common.KEY_USERNAME, ParseUser.getCurrentUser().getUsername());
            userParseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            for (ParseUser users : objects) {
                                usersList.add(users.getUsername());
                            }
                            lvUsers.setAdapter(usersAdapter);

                            //implement animation to make the textview gone
                            //textview.animate().alpha(0).setDuration(2000);
                        }
                        Common.dismissProgressBar();
                    } else {
                        Common.dismissProgressBar();
                        Common.showErrorMessage(getContext(), "Something wrong!");
                        Log.e(TAG, e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Common.dismissProgressBar();
            Common.showErrorMessage(getContext(), "Something wrong!");
            Log.e(TAG, e.getMessage());
        }
    }

}
