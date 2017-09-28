package com.example.appmanager.utils;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.appmanager.R;
import com.example.appmanager.adapter.MyRecyclerViewAdapter;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

/**
 * Created by 11070562 on 2017/9/27.
 */

public class DrawerUtil {

    public static Drawer setNavigationDrawer(Activity activity,final RecyclerView mRecyclerView, final MyRecyclerViewAdapter appAdapter, final MyRecyclerViewAdapter systemAppAdapter, String installedCount, String systemAppCount){

        //初始化侧标栏
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.header_day)
                .build();

        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(headerResult)
                .addDrawerItems(new PrimaryDrawerItem().withName("Installed").withIcon(R.mipmap.ic_launcher).withBadge(installedCount).withIdentifier(1))
                .addDrawerItems(new PrimaryDrawerItem().withName("System").withIcon(R.mipmap.ic_launcher).withBadge(systemAppCount).withIdentifier(2))
                .addDrawerItems(new PrimaryDrawerItem())

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int) drawerItem.getIdentifier()){
                            case 1:
                                mRecyclerView.setAdapter(appAdapter);

                                break;
                            case 2:
                                mRecyclerView.setAdapter(systemAppAdapter);
                                break;
                        }
                        return false;
                    }
                });

        return drawerBuilder.build();
    }
}
