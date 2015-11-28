package com.example.sudhanshu.codesign.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.sudhanshu.codesign.R;
import com.example.sudhanshu.codesign.listener.ItemClickListener;
import com.example.sudhanshu.codesign.ui.adapter.GridViewAdapter;
import com.example.sudhanshu.codesign.model.ListData;
import com.example.sudhanshu.codesign.model.RowItem;
import com.example.sudhanshu.codesign.utils.GsonUtil;
import com.example.sudhanshu.codesign.utils.Utility;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * This Project is created by Sudhanshu Singh
 * about.me/sudhanshusingh
 * plus.google.com/+sudhanshusingh
 *
 * @author sudhanshu
 *         <p/>
 *         <p/>
 *         <p/>
 *         This is the one and only Activity in this project.
 *         I am using recycler View to load about 30 items initially from the asset/data.json.
 *         once List reaches to the end, re-add items into the main list and notifyAdapter.
 *         <p/>
 *         Inside adapter LRUcache is being implemented to load images already saved in the cache.
 */
public class LandingActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerView mGridView;
    private GridViewAdapter mGridViewAdapter;
    private GridLayoutManager mLayoutManager;
    private static final int GRID_SIZE = 2;
    private static final int GRID_ITEM_BASE_SPACING = 16;
    private ListData dataItem;
    private ArrayList<RowItem> list = new ArrayList<>();
    private ArrayList<RowItem> mainList = new ArrayList<>();
    private Gson gson = GsonUtil.getGson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initList();
        setRecyclerView();

    }

    /**
     * Initialize recycler view and set adapter.
     */
    private void setRecyclerView() {

        mGridView = (RecyclerView) findViewById(R.id.grid_view);
//        mGridView.setHasFixedSize(true);
        mGridView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (isLastItemDisplaying(recyclerView)) {
                    Toast.makeText(LandingActivity.this, "added new items..", Toast.LENGTH_LONG).show();
                    refreshList();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
        FeedItemDecor decoration = new FeedItemDecor(GRID_ITEM_BASE_SPACING);
        mGridViewAdapter = new GridViewAdapter(this, mainList, this);
        mLayoutManager = new GridLayoutManager(this, GRID_SIZE);
        mLayoutManager.setSpanSizeLookup(onSpanSizeLookup);
        mGridView.setLayoutManager(mLayoutManager);
        mGridView.addItemDecoration(decoration);
        mGridView.setAdapter(mGridViewAdapter);
    }

    /**
     * Check whether the last item in RecyclerView is being displayed or not. But we will
     * refresh the list based on this decision.
     *
     * @param recyclerView which you would like to check
     * @return true if last position was Visible and false Otherwise
     */
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter()
                    .getItemCount() - 1)
                return true;
        }
        return false;
    }

    @Override
    public void onItemClicked() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.parentLayout), this.getResources()
                        .getString(R.string.me_test), Snackbar.LENGTH_SHORT);

        snackbar.show();
        snackbar.setAction(this.getResources()
                .getString(R.string.snack_action), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(LandingActivity.this.getResources().getString(R.string.url_)));
                try {
                startActivity(intent);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * Item Decoration to set item spacing.
     */
    public class FeedItemDecor extends RecyclerView.ItemDecoration {
        private int mSpace;

        public FeedItemDecor(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace * 2;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) == 0)
                outRect.top = mSpace;

        }
    }

    /**
     * This the SpanSize decision making step. Here based on any specific value, we decide
     * what will be the Span(width) of each item. Since this Grid has a Maximum Span count of 2,
     * as it is a 2 column Grid, returning a span of 2 means full width and 1 means obviously the half.
     */
    GridLayoutManager.SpanSizeLookup onSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            return 1;
        }
    };


    /**
     * Loading list form the JSON data in assets.
     */
    private void initList() {
        try {
            String dataString = Utility.prepString(this, "data.json");
            dataItem = gson.fromJson(dataString, ListData.class);
            list = dataItem.getData();
            mainList.addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * We call this method to add more items to the list when we want to, usually when the user reaches
     * the end of the current count in the list. ADD more and notifyadapter.
     */
    private void refreshList() {
        mainList.addAll(list);
        mGridViewAdapter.notifyDataSetChanged();
    }
}
