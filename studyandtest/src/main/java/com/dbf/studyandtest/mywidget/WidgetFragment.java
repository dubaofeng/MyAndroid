package com.dbf.studyandtest.mywidget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dbf.studyandtest.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class WidgetFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private WidgetMainActivity activity;

    private WearableRecyclerView bigWrv;
    private WearableRecyclerView smallWrv;
    private ArrayList<View> bigViews = new ArrayList<View>();
    private ArrayList<Bitmap> smallViews = new ArrayList<Bitmap>();
    private BigAdapter bigAdapter;
    private SmallAdapter smallAdapter;
    private LinearLayoutManager bigLayoutManager;
    private LinearLayoutManager smallLayoutManager;
    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            bigAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            smallAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    });
    private View wfView;
    private ImageView aniImageView;

    public WidgetFragment() {
    }

    public static WidgetFragment newInstance(String param1, String param2) {
        WidgetFragment fragment = new WidgetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (WidgetMainActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (wfView == null) {
            initData();
            wfView = inflater.inflate(R.layout.fragment_widget, container, false);
            aniImageView = wfView.findViewById(R.id.aniImageView);
            bigWrv = wfView.findViewById(R.id.bigRv);
            smallWrv = wfView.findViewById(R.id.smallRv);
            bigLayoutManager = new LinearLayoutManager(activity);
            bigWrv.setLayoutManager(bigLayoutManager);
            PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
            pagerSnapHelper.attachToRecyclerView(bigWrv);
            bigAdapter = new BigAdapter();
            bigWrv.setAdapter(bigAdapter);

            smallWrv.setCenterEdgeItems(true);
            smallLayoutManager = new LinearLayoutManager(activity);
            smallWrv.setLayoutManager(smallLayoutManager);
            LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
            linearSnapHelper.attachToRecyclerView(smallWrv);
            itemTouchHelper.attachToRecyclerView(smallWrv);
            smallAdapter = new SmallAdapter();
            smallWrv.setAdapter(smallAdapter);
        }
        return wfView;
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(700, 700);
            layoutParams.setMargins(0, 0, 0, 0);
            TextView button = new Button(activity);
            button.setLayoutParams(layoutParams);
            button.setBackgroundColor(0xffFFFF00);
            button.setText("widget" + i);
            button.setTextSize(36);
            bigViews.add(button);

        }
        for (int i = 0; i < 5; i++) {
            smallViews.add(null);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class SmallViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public SmallViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.smallImage);
        }
    }

    class BigAdapter extends RecyclerView.Adapter<ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(bigViews.get(viewType));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, ((Button) v).getText(), Toast.LENGTH_SHORT).show();
                    viewShot(position, v);
                    Bitmap bitmap = smallViews.get(position);
                    if (bitmap != null) {
                        aniImageView.setImageBitmap(bitmap);
                    } else {
                        aniImageView.setImageResource(R.mipmap.ic_launcher);
                    }
                    startAni(aniImageView, 0);

                }
            });
        }

        @Override
        public int getItemCount() {
            return bigViews.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }
    }

    class SmallAdapter extends RecyclerView.Adapter<SmallViewHolder> {
        @NonNull
        @Override
        public SmallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SmallViewHolder(getLayoutInflater().inflate(R.layout.item_smallrv, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SmallViewHolder holder, int position) {
            Bitmap bitmap = smallViews.get(position);
            if (bitmap != null) {
                holder.imageView.setImageBitmap(bitmap);
            } else {
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
            }
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bigLayoutManager.scrollToPosition(position);
                    Bitmap bitmap = smallViews.get(position);
                    if (bitmap != null) {
                        aniImageView.setImageBitmap(bitmap);
                    } else {
                        aniImageView.setImageResource(R.mipmap.ic_launcher);
                    }
                    startAni(aniImageView, 1);
                }
            });


        }

        @Override
        public int getItemCount() {
            return smallViews.size();
        }

    }

    private ScaleAnimation scaleAnimation;

    private void startAni(View view, int aniFlag) {
        float fx = 0;
        float tx = 0;
        float fy = 0;
        float ty = 0;
        if (aniFlag == 0) {
            fx = 1f;
            tx = 0.5f;
            fy = 1f;
            ty = 0.5f;
        } else {
            fx = 0.5f;
            tx = 1f;
            fy = 0.5f;
            ty = 1f;
        }
        ScaleAnimation scaleAnimation = new ScaleAnimation(fx, tx, fy, ty, view.getWidth() / 2, view.getHeight() / 2);
        scaleAnimation.setDuration(500);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                view.clearAnimation();
                if (aniFlag != 0) {
                    bigWrv.setVisibility(View.VISIBLE);
                    smallWrv.setVisibility(View.INVISIBLE);
                }
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setVisibility(View.VISIBLE);
        if (aniFlag == 0) {
            smallWrv.setVisibility(View.VISIBLE);
            bigWrv.setVisibility(View.INVISIBLE);
        }
        view.startAnimation(scaleAnimation);
    }

    /**
     * view截图
     *
     * @return
     */
    public void viewShot(int position, @NonNull final View v) {
        if (null == v) {
            return;
        }
        // 核心代码start
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bitmap);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        Bitmap mBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap = null;
        smallViews.set(position, mBitmap);
        smallAdapter.notifyDataSetChanged();
        smallLayoutManager.scrollToPosition(position);
//        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                } else {
//                    v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                }
//                // 核心代码start
//                Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas c = new Canvas(bitmap);
//                v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
//                v.draw(c);
//                smallViews.add(bitmap);
//                smallAdapter.notifyDataSetChanged();
//            }
//        });
    }

}
