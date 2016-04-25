package com.wolfoxlabs.mychecklist;

/**
 * Created by uday on 08/04/16.
 */
public class Item {
    public  String name;
    public  int drawableId;
    public  int pre_DrawableId;
    Item(String name, int drawableId) {
        this.name = name;
        this.drawableId = drawableId;


    }

    public void setDrawableId(int drawableId)
    {
        this.drawableId = drawableId;
    }
    public void setPre_DrawableId(int drawableId)
    {
        this.pre_DrawableId = drawableId;
    }
}
