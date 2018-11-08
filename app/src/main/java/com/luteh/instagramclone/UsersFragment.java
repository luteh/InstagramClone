package com.luteh.instagramclone;


import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.luteh.instagramclone.common.Common;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
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
        lvUsers.setOnItemClickListener(this);
        lvUsers.setOnItemLongClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), UsersPostActivity.class);
        intent.putExtra(Common.KEY_USERNAME, usersList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo(Common.KEY_USERNAME, usersList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (object != null && e == null) {
//                    Common.showSuccessMessage(getContext(), object.get(Common.KEY_PROFILE_BIO) + "");
                    showUsersInfoDialog(object);
                }
            }
        });
        return true;
    }

    public void showUsersInfoDialog(ParseUser object) {
        final PrettyDialog prettyDialog = new PrettyDialog(getContext());
        prettyDialog
                .setTitle(object.get(Common.KEY_USERNAME)+"'s Info")
                .setMessage(object.get(Common.KEY_PROFILE_BIO) + "\n"
                        + object.get(Common.KEY_PROFILE_PROFESSION) + "\n"
                        + object.get(Common.KEY_PROFILE_HOBBIES) + "\n"
                        + object.get(Common.KEY_PROFILE_SPORT))
                .addButton(
                        "OK",
                        R.color.pdlg_color_white,
                        R.color.pdlg_color_green,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                prettyDialog.dismiss();
                            }
                        }
                )
                .show();
    }
}
