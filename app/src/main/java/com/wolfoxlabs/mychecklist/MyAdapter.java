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
    private int previousSelectedPosition = -1;
    Context context;
    public MyAdapter(Context context, List<Item> mItems) {
        this.context = context;
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
        final ViewHolder viewHolder;
        final  int position = i;
        final ViewGroup vwGroup = viewGroup;
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
       final Item item = getItem(i);



        if(i%5==0 && i<60)
           viewHolder.name.setText("AM>>");
        else if(i%5==0 && i>=60)
            viewHolder.name.setText("PM>>");
        else
           viewHolder.name.setText(item.name);

        viewHolder.picture.setImageResource(item.drawableId);

        viewHolder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               if(position%5!=0 && previousSelectedPosition!=position) {
                   getItem(position).setPre_DrawableId(getItem(position).drawableId);
                   getItem(position).setDrawableId(R.color.Pink);
                   notifyDataSetChanged();
                   if(previousSelectedPosition != -1)
                   {

                       getItem(previousSelectedPosition).setDrawableId(getItem(previousSelectedPosition).pre_DrawableId);

                   }
                   previousSelectedPosition = position;
               }
                else if(position%5==0)
               {

               }
                else if(getItem(position).drawableId != getItem(previousSelectedPosition).pre_DrawableId)
               {
                   ((HomeActivity)context).createReminder();
               }

            }
        });


        return view;
    }




    private static class ViewHolder{
        ImageView picture;

        TextView name;
    }
}
