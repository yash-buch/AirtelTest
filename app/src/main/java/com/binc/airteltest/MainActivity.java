package com.binc.airteltest;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static androidx.recyclerview.widget.LinearLayoutManager.VERTICAL;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getName();

    int screenWidth;
    int screenHeight;
    int baseWidth = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        Log.i(TAG, "screen width: "+screenWidth);
        final RecyclerView rv = findViewById(R.id.rv);
        LinearLayoutManager lm = new LinearLayoutManager(this, VERTICAL, false);
        rv.setLayoutManager(lm);
        final CustomAdapter adapter = new CustomAdapter(getRVItemPairs(3));
        rv.setAdapter(adapter);
        final EditText et = findViewById(R.id.editText);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et.getText().toString();
                try {
                    rv.setAdapter(new CustomAdapter(getRVItemPairs(Integer.parseInt(str))));
                } catch (NumberFormatException e) {
                    rv.setAdapter(new CustomAdapter(getRVItemPairs(3)));
                }
            }
        });
    }

    private List<Row> getRVItemPairs(int total) {
        int frameNum = 1;
        List<Row> rows = new ArrayList<>();
        while(frameNum <= total) {
            int sw = screenWidth;
            Row row = new Row();
            row.viewList = new ArrayList<>();
            while(sw >= 0) {
                int frameWidth = baseWidth * frameNum;
                if (frameWidth > sw || frameNum > total) {
                    rows.add(row);
                    break;
                }
                View v = new View(MainActivity.this);
                v.setLayoutParams(new LinearLayout.LayoutParams(frameWidth, screenHeight/10));
                Log.i(TAG, "frameWidth assigned:"+frameWidth);
                row.viewList.add(v);
                sw -= frameWidth;
                frameNum++;
            }
        }
        return rows;
    }

    class Row {
        List<View> viewList;
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

        List<Row> mRows;
        public CustomAdapter(List<Row> rows) {
            this.mRows = rows;
        }

        public void updateData(List<Row> rows) {
            this.
            mRows = rows;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout ll = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new CustomViewHolder(ll);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            List<View> views = mRows.get(position).viewList;
            holder.ll.removeAllViews();
            for(int i = 0; i < views.size(); i++) {
                View v = views.get(i);
                if(i%2 == 0) {
                    v.setBackgroundColor(Color.BLACK);
                } else {
                    v.setBackgroundColor(Color.LTGRAY);
                }
                holder.ll.addView(v);
            }
        }

        @Override
        public int getItemCount() {
            return mRows.size();
        }
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll;
        public CustomViewHolder(@NonNull LinearLayout itemView) {
            super(itemView);
            ll = itemView;
        }
    }
}
