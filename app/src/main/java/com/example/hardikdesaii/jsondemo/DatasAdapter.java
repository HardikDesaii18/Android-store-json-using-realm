package com.example.hardikdesaii.jsondemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HardikDesaii on 26/01/17.
 */

public class DatasAdapter extends RecyclerView.Adapter<DatasAdapter.UserViewHolder>
{
    Context context;
    ArrayList<JSONObject> datalist;
    DatasAdapter(Context context,ArrayList<JSONObject> datalist)
    {
        this.context=context;
        this.datalist=datalist;
    }
    @Override
    public DatasAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_jsondata, null);
        DatasAdapter.UserViewHolder userViewHolder = new DatasAdapter.UserViewHolder(view);
        return userViewHolder;

    }

    @Override
    public void onBindViewHolder(DatasAdapter.UserViewHolder holder, int position)
    {
        JSONObject object=datalist.get(position);

        try {
            holder.one.setText(object.getString(Constants.KEY1));
            holder.two.setText(object.getString(Constants.KEY2));
            holder.three.setText(object.getString(Constants.KEY3));
            holder.four.setText(object.getString(Constants.KEY4));
            holder.five.setText(String.valueOf(object.getInt(Constants.KEY5)));
            holder.six.setText(object.getString(Constants.KEY6));
            holder.seven.setText(object.getString(Constants.KEY7));
        }
        catch (JSONException e)
        {
            Toast.makeText(context,"Error while loading data in adapter",Toast.LENGTH_LONG).show();
            Log.e("DatasAdapter" ," "+e);

        }


    }

    @Override
    public int getItemCount()
    {
        return datalist.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder
    {
        TextView one,two,three,four,five,six,seven;
        public UserViewHolder(View itemView)
        {
            super(itemView);
            one=(TextView)itemView.findViewById(R.id.jsonarg1);
            two=(TextView)itemView.findViewById(R.id.jsonarg2);
            three=(TextView)itemView.findViewById(R.id.jsonarg3);
            four=(TextView)itemView.findViewById(R.id.jsonarg4);
            five=(TextView)itemView.findViewById(R.id.jsonarg5);
            six=(TextView)itemView.findViewById(R.id.jsonarg6);
            seven=(TextView)itemView.findViewById(R.id.jsonarg7);

        }
    }
}
