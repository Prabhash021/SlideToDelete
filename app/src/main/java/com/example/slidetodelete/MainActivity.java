package com.example.slidetodelete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.OnDeleteListener {

    ArrayList<DataModel> arrData = new ArrayList<>();
    RecyclerView rcyclViw;
    LinearLayout liner;
    Adapter adapter;

    private int swipedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcyclViw = findViewById(R.id.recycler);
        rcyclViw.setLayoutManager(new LinearLayoutManager(this));
        liner = findViewById(R.id.linearLay);

        DataModel dataModel1 = new DataModel("Name1");
        DataModel dataModel2 = new DataModel("NAme2");
        DataModel dataModel3 = new DataModel("Name3");
        DataModel dataModel4 = new DataModel("Name4");

        arrData.add(dataModel1);
        arrData.add(dataModel2);
        arrData.add(dataModel3);
        arrData.add(dataModel4);

        adapter = new Adapter(MainActivity.this, arrData, this);

        rcyclViw.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                swipedPosition = viewHolder.getAdapterPosition();
                deleteData(swipedPosition);
            }
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // Customize the appearance of the swiped item here
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && viewHolder.getAdapterPosition() == swipedPosition) {
                    // Set the background color
                    ColorDrawable background = new ColorDrawable(Color.RED);
                    background.setBounds(viewHolder.itemView.getRight() + (int) dX, viewHolder.itemView.getTop(), viewHolder.itemView.getRight(), viewHolder.itemView.getBottom());
                    background.draw(c);

                    // You can also draw a delete icon or text if needed
                    // Example: drawDeleteIcon(c, viewHolder);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };



        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rcyclViw);
    }

    private void deleteData(int position) {
        Snackbar snackbar = Snackbar.make(liner, "Item Deleted", Snackbar.LENGTH_LONG);
        snackbar.show();

        arrData.remove(position);
        adapter.notifyDataSetChanged();

        adapter.setSwipedPosition(RecyclerView.NO_POSITION);
    }

    @Override
    public void onDelete(int position) {
        adapter.setSwipedPosition(position);
        deleteData(position);
    }
}