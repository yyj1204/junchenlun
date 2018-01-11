package com.wktx.www.subjects.Decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int count;

    public SpaceItemDecoration(int space, int count) {
        this.space = space;
        this.count = count;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if (count==2)
        {
            outRect.left = space;
            outRect.top = space;
            outRect.right = space;
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.right = 0;
            }
        }else if(count==3)
        {
            outRect.top = space;
            outRect.right = space;
            if (parent.getChildLayoutPosition(view) % 3 == 0) {
                outRect.left = space;
            }
        }

    }

}