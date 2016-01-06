package com.qiang.art;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qiang on 2016/1/6.
 */
public class MyListAdapter extends BaseAdapter {


    Map<String, Class> map;

    Context mContext;
    LayoutInflater inflater;

    public MyListAdapter(Map<String, Class> map, Context context) {
        super();
        this.map = map;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_list, parent, false);
             holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else holder = (ViewHolder) convertView.getTag();

        Set<String > keySet = map.keySet();

        List<String > list = new ArrayList(keySet);

        String str =  list.get(position);

        holder.textView.setText(str);



        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_list.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.textView)
        TextView textView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
