package com.example.appmanager.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmanager.R;

/**
 * Created by 11070562 on 2017/9/27.
 */

public class AppActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private CardView open;
    private CardView uninstall;
    private Intent intent;

    private ImageView icon;
    private TextView app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.app_detail);

        initView();


        intent = getIntent();
        icon.setImageBitmap((Bitmap) intent.getParcelableExtra("icon"));
        app.setText(intent.getStringExtra("app_name"));
    }

    private void initView() {
        open = (CardView) findViewById(R.id.id_open);
        uninstall = (CardView) findViewById(R.id.id_uninstall);
        icon = (ImageView) findViewById(R.id.id_icon);
        app = (TextView) findViewById(R.id.id_app_name);

        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        open.setOnClickListener(this);
        uninstall.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_open:

                try {
                    Intent intent1 = getPackageManager().getLaunchIntentForPackage(intent.getStringExtra("app_package"));
                    startActivity(intent1);
                } catch (Exception e) {
                    Toast.makeText(this, "sorry , can't open", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.id_uninstall:

                if (!intent.getBooleanExtra("isSystem",false)) {
                    Uri packageURI = Uri.parse("package:" + intent.getStringExtra("app_package"));
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(packageURI);
                    intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
                    startActivityForResult(intent, 1);
                }else
                    Toast.makeText(this, "sorry , can't uninstall system app", Toast.LENGTH_SHORT).show();

                break;
        }

//                Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
//                intent.setData(Uri.parse("package:" + intent.getStringExtra("app_package")));
//                intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
//                startActivityForResult(intent, 1);

    }
}

