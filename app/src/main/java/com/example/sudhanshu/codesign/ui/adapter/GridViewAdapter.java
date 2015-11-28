package com.example.sudhanshu.codesign.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sudhanshu.codesign.R;
import com.example.sudhanshu.codesign.listener.ItemClickListener;
import com.example.sudhanshu.codesign.model.RowItem;
import com.example.sudhanshu.codesign.utils.Constants;
import com.example.sudhanshu.codesign.utils.Utility;

import java.util.ArrayList;

/**
 * This Project is created by Sudhanshu Singh
 * about.me/sudhanshusingh
 * plus.google.com/+sudhanshusingh
 * <p/>
 * Created by sudhanshu on 11/28/2015.
 * <p/>
 * This adapter uses the LRUcache to load images if they are alraedy saved there.
 */
public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.BaseViewHolder> {

    private static final String TAG = GridViewAdapter.class.getSimpleName();

    private ArrayList<RowItem> mList;
    private Context mContext;
    private ItemClickListener mListener;

    public GridViewAdapter(Context ctx, ArrayList<RowItem> list, ItemClickListener listener) {
        mList = list;
        mContext = ctx;
        mListener = listener;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ItemViewHolder extends BaseViewHolder implements View.OnClickListener {

        public ImageView mImvItem;
        public TextView mTvTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImvItem = (ImageView) itemView.findViewById(R.id.row_grid_image);
            mTvTitle = (TextView) itemView.findViewById(R.id.row_grid_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClicked();

        }
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_grid_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        ItemViewHolder item = (ItemViewHolder) holder;
        String imageId = mList.get(position).getImage();

        //Loading saved image from the cache
        Bitmap savedimage = Utility.getImageFromCache(imageId);


        if (savedimage != null) {
            //if is saved laod bitmap from cache
            Log.i(TAG, "loading from cache :" + imageId);
            item.mImvItem.setImageBitmap(savedimage);

        } else {
            //Create the bitmap
            int resId = getImage(imageId);
            Bitmap image = BitmapFactory.decodeResource(mContext.getResources(), resId);

            Log.i(TAG, "saving to cache:" + imageId);
            item.mImvItem.setImageBitmap(image);

            //save this bitmap into LRUcache
            Utility.saveImageToCache(image, mList.get(position).getImage());
        }

        Log.i(TAG, "item no:" + position);
        item.mTvTitle.setText(mList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * Mapping the code from JSON to each respective Drawable ID
     *
     * @return
     */
    public int getImage(String imgName) {
        int resId = 0;
        switch (imgName) {
            case Constants.IMAGE_1:
                resId = R.drawable.img1;
                break;
            case Constants.IMAGE_2:
                resId = R.drawable.img2;
                break;
            case Constants.IMAGE_3:
                resId = R.drawable.img3;
                break;
            case Constants.IMAGE_4:
                resId = R.drawable.img4;
                break;
            case Constants.IMAGE_5:
                resId = R.drawable.img5;
                break;
            case Constants.IMAGE_6:
                resId = R.drawable.img6;
                break;
            case Constants.IMAGE_7:
                resId = R.drawable.img7;
                break;
            case Constants.IMAGE_8:
                resId = R.drawable.img8;
                break;
            case Constants.IMAGE_9:
                resId = R.drawable.img9;
                break;
            case Constants.IMAGE_10:
                resId = R.drawable.img10;
                break;
            case Constants.IMAGE_11:
                resId = R.drawable.img11;
                break;
            case Constants.IMAGE_12:
                resId = R.drawable.img12;
                break;
        }
        return resId;
    }

}
