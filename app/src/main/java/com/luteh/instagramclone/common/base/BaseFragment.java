package com.luteh.instagramclone.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Luthfan Maftuh on 02/11/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class BaseFragment extends Fragment {

    protected Context context;

    BaseActivity baseActivity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity = (BaseActivity) context;
    }
}
