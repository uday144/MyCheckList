package com.wolfoxlabs.mychecklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by uday on 02/03/16.
 */
public final class MyAdapter extends BaseAdapter {
    private final List<Item> mItems;
    private final LayoutInflater mInflater;

    public MyAdapter(Context context, List<Item> mItems) {
        mInflater = LayoutInflater.from(context);
        this.mItems = mItems;


    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;


        if (view == null) {
            view = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            viewHolder = new ViewHolder();

            viewHolder.picture = (ImageView) view.findViewById(R.id.picture);
            viewHolder.name = (TextView) view.findViewById(R.id.text);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Item item = getItem(i);



       if(i%5==0)
           viewHolder.name.setText("AM>>");
        else
           viewHolder.name.setText(item.name);

        viewHolder.picture.setImageResource(item.drawableId);


        return view;
    }




    private static class ViewHolder{
        ImageView picture;
        TextView name;
    }
}
