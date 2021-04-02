package com.dbf.studyandtest.myrecyclerview;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dbf.common.myutils.MyLog;
import com.dbf.studyandtest.R;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private final String TAG = "RecyclerViewActivity";
    private static Object i = new Object();

   static class Test {
         Object obj = new Object();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int j = 0; j < 10; j++) {
            Log.i(TAG, "onCreate: i.hashCode()=" + i.hashCode() );
        }
        hideBottomMenu();
        setContentView(R.layout.activity_recycler_view);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyLog.INSTANCE.i(TAG, "长按recyclerView");
                return true;
            }
        });
//        recyclerView.setLayoutManager(new CopyOppoWatcheLayoutManager(this));
//        recyclerView.setLayoutManager(new CopyOppoWatcheLayoutManager2(this));
//        recyclerView.setLayoutManager(new CopyOppoWatcheLayoutManager3(this));
        recyclerView.setLayoutManager(new CopyOppoWatcheLayoutManager4(this));
//        recyclerView.setLayoutManager(new CopyOppoWatcheLayoutManager5(this, 3));

        recyclerView.setAdapter(new RecyclerAdapter());
//        CardConfig.initConfig(this);
//        ItemTouchHelper.Callback callback = new SwipeCardCallback();
//        ItemTouchHelper helper = new ItemTouchHelper(callback);
//        helper.attachToRecyclerView(recyclerView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        hideBottomMenu();
    }

    protected void hideBottomMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewHolder(getLayoutInflater().inflate(R.layout.item_recyclerview, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
            holder.cardTextView.setText("" + position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyLog.INSTANCE.i(TAG, "点击了" + position);
                }
            });
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    MyLog.INSTANCE.i(TAG, "长按itemView");
//                    return true;
//                }
//            });
//            holder.itemView.setBackgroundColor(R.color.color_headbg);
            switch (position % 2) {
                case 0:
                    holder.imageView.setImageResource(R.mipmap.ic_launcher);
                    break;
                case 1:
                    holder.imageView.setImageResource(R.mipmap.ic_launcher);
                    break;
            }
//            if (position < 3 ||position > 95) {
//                holder.imageView.setVisibility(View.INVISIBLE);
//            } else {
//                holder.imageView.setVisibility(View.VISIBLE);
//            }
        }

        @Override
        public int getItemCount() {
            return 58;
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView cardTextView;
        ImageView imageView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            cardTextView = itemView.findViewById(R.id.cardTextView);
            imageView = itemView.findViewById(R.id.imgv);
        }
    }
}
