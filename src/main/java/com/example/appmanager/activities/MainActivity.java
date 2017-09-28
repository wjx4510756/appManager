package com.example.appmanager.activities;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.example.appmanager.AppBean;
import com.example.appmanager.R;
import com.example.appmanager.adapter.MyRecyclerViewAdapter;
import com.example.appmanager.utils.DrawerUtil;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.List;

import static com.example.appmanager.utils.DrawerUtil.setNavigationDrawer;

public class MainActivity extends AppCompatActivity {


    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private Drawer drawer;
    private ProgressBar mProgressBar;

    private List<AppBean> mAppList;
    private List<AppBean> mSystemAppList;

    private MyRecyclerViewAdapter appAdapter;
    private MyRecyclerViewAdapter systemAppAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();


    }

    private void initData() {

        mAppList = new ArrayList<>();
        mSystemAppList = new ArrayList<>();
        appAdapter = new MyRecyclerViewAdapter(MainActivity.this, mAppList);
        systemAppAdapter = new MyRecyclerViewAdapter(MainActivity.this, mSystemAppList);

        new getInstalledApps().execute();

    }


    private void initView() {

        mProgressBar = (ProgressBar) findViewById(R.id.id_progressBar);

        //初始化RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //初始化ToolBar
        mToolbar = (Toolbar) findViewById(R.id.id_toolBar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.menu1);

        drawer = DrawerUtil.setNavigationDrawer(this, mRecyclerView, appAdapter, systemAppAdapter, "..", "..");

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    class getInstalledApps extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            publishProgress();

            PackageManager packageManager = getPackageManager();
            List<PackageInfo> list = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);


            // Installed & System Apps
            for (PackageInfo packageInfo : list) {
                if (!(packageManager.getApplicationLabel(packageInfo.applicationInfo).equals("") || packageInfo.packageName.equals(""))) {

                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        try {
                            // Non System Apps
                            AppBean appBean = new AppBean();
                            appBean.setAppPackage(packageInfo.packageName);
                            appBean.setAppVersion("" + packageInfo.versionCode);
                            appBean.setAppName(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString());
                            appBean.setIcon(packageManager.getApplicationIcon(packageInfo.applicationInfo));
                            appBean.setSystem(false);
                            mAppList.add(appBean);

                        } catch (OutOfMemoryError e) {
                            //OutOfMemoryError
                            AppBean appBean = new AppBean();
                            appBean.setAppPackage(packageInfo.packageName);
                            appBean.setAppVersion("" + packageInfo.versionCode);
                            appBean.setAppName(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString());
                            appBean.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                            appBean.setSystem(false);
                            mAppList.add(appBean);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            // System Apps

                            AppBean appBean = new AppBean();
                            appBean.setAppPackage(packageInfo.packageName);
                            appBean.setAppVersion("" + packageInfo.versionCode);
                            appBean.setAppName(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString());
                            appBean.setIcon(packageManager.getApplicationIcon(packageInfo.applicationInfo));
                            appBean.setSystem(true);
                            mSystemAppList.add(appBean);

                        } catch (OutOfMemoryError e) {
                            //OutOfMemoryError
                            AppBean appBean = new AppBean();
                            appBean.setAppPackage(packageInfo.packageName);
                            appBean.setAppVersion("" + packageInfo.versionCode);
                            appBean.setAppName(packageManager.getApplicationLabel(packageInfo.applicationInfo).toString());
                            appBean.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                            appBean.setSystem(false);
                            mSystemAppList.add(appBean);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mRecyclerView.setAdapter(appAdapter);
            mProgressBar.setVisibility(View.GONE);

            if (drawer.isDrawerOpen())
                drawer.closeDrawer();
            drawer = setNavigationDrawer(MainActivity.this, mRecyclerView, appAdapter, systemAppAdapter, mAppList.size() + "", mSystemAppList.size() + "");


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

}
