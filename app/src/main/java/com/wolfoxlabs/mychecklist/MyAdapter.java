package com.wolfoxlabs.mychecklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uday on 02/03/16.
 */
public final class MyAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public MyAdapter(Context context) {
        mInflater = LayoutInflater.from(context);


        for (int i = 0; i < 30 ; i++) {

            mItems.add(new Item("Red",       R.color.Blue1));
            mItems.add(new Item("Magenta",   R.color.Blue2));
            mItems.add(new Item("Red",       R.color.Blue3));
            mItems.add(new Item("Magenta",   R.color.Blue4));
        }




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



        viewHolder.name.setText(item.name);



        viewHolder.picture.setImageResource(item.drawableId);


        return view;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }

    private static class ViewHolder{
        ImageView picture;
        TextView name;
    }
}
