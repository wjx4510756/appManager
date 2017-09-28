package com.example.appmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmanager.AppBean;
import com.example.appmanager.R;
import com.example.appmanager.activities.AppActivity;

import java.util.List;

/**
 * Created by 11070562 on 2017/9/27.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {


    private Context mContext;
    private List<AppBean> mList;
    private LayoutInflater mInflater;

    public MyRecyclerViewAdapter(Context context, List<AppBean> list) {

        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.cardview_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    private void setListener(MyViewHolder holder, final AppBean bean) {

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AppActivity.class);
                intent.putExtra("app_name",bean.getAppName());
                intent.putExtra("app_package",bean.getAppPackage());
                intent.putExtra("icon",((BitmapDrawable)bean.getIcon()).getBitmap());
                intent.putExtra("isSystem",bean.isSystem());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(mList.get(position).getAppName());
        holder.content.setText(mList.get(position).getAppPackage());
        holder.icon.setImageDrawable(mList.get(position).getIcon());

        setListener(holder, mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        ImageView icon;
        View mItemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.id_app_name);
            content = itemView.findViewById(R.id.id_package_name);
            icon = itemView.findViewById(R.id.id_icon);
            mItemView = itemView;
        }
    }
}
