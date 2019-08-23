package select.multiple.item.in.recycler.view.with.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;

public class SelectMultipleItemWithToolBarActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private ArrayList<Model> itemArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;

    private Button deleteSelected,getSelected,reverseSelected,sendSelectedToNextActivity;

    private ActionMode actionMode;
    Menu context_menu;
    boolean isMultiSelect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_multiple_item_with_tool_bar);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        initView();
        recyclerViewSetup();
        initData();
        initEvents();
    }

    private void initView()
    {
        recyclerView = findViewById(R.id.recycler_view);

        deleteSelected=findViewById(R.id.delete_selected_item);
        getSelected=findViewById(R.id.get_selected_item);
        reverseSelected=findViewById(R.id.reverse_selected_item);
        sendSelectedToNextActivity=findViewById(R.id.send_selected_item_to_next_activity);
    }

    private void recyclerViewSetup()
    {
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider_10dp)));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerViewItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
               /* Model currentRecyclerViewItem = itemArrayList.get(position);
                Toast.makeText(getApplicationContext(), "onItemClick "+currentRecyclerViewItem.getItemTitle(), Toast.LENGTH_SHORT).show();
                recyclerViewAdapter.checkCheckBox(position, !(recyclerViewAdapter.isSelected(position)));*/
                if (actionMode != null)
                {
                    isMultiSelect = recyclerViewAdapter.getSelectedItemCount() > 0;//Check if any items are already selected or not

                    if (isMultiSelect)
                    {
                        // there are some selected items, start the actionMode
                        if (actionMode == null) {
                            actionMode = ((AppCompatActivity) SelectMultipleItemWithToolBarActivity.this).startSupportActionMode(new ToolbarActionModeCallback(SelectMultipleItemWithToolBarActivity.this, recyclerViewAdapter,itemArrayList));
                        }
                        recyclerViewAdapter.checkCheckBox(position, !(recyclerViewAdapter.isSelected(position)));
                    }
                    else if (!isMultiSelect&&actionMode!=null)
                    {
                        // there no selected items, finish the actionMode
                        actionMode.finish();
                    }
                    if (actionMode!=null)
                    {
                        //set action mode title on item selection
                        actionMode.setTitle(String.valueOf(recyclerViewAdapter.getSelectedItemCount()) + " selected");
                    }
                }
            }

            @Override
            public void onItemLongClick(View view, int position)
            {
                /*Model currentRecyclerViewItem = itemArrayList.get(position);
                Toast.makeText(getApplicationContext(), "onItemLongClick "+currentRecyclerViewItem.getItemTitle(), Toast.LENGTH_SHORT).show();*/

                isMultiSelect = recyclerViewAdapter.getSelectedItemCount() > 0;//Check if any items are already selected or not

                if (!isMultiSelect)
                {
                    if (actionMode == null) {
                        actionMode = ((AppCompatActivity) SelectMultipleItemWithToolBarActivity.this).startSupportActionMode(new ToolbarActionModeCallback(SelectMultipleItemWithToolBarActivity.this, recyclerViewAdapter,itemArrayList));
                    }
                }
                else if (!isMultiSelect&&actionMode!=null)
                {
                    // there no selected items, finish the actionMode
                    actionMode.finish();
                }
                if (actionMode!=null)
                {
                    //set action mode title on item selection
                    actionMode.setTitle(String.valueOf(recyclerViewAdapter.getSelectedItemCount()) + " selected");
                }

                recyclerViewAdapter.checkCheckBox(position, !(recyclerViewAdapter.isSelected(position)));
            }
        }));
    }

    private void initData()
    {
        itemArrayList = new ArrayList<>();
        itemArrayList.add(new Model(1, "Salmon Teriyaki", "Roasted salon dumped in soa sauce and mint", 140, R.drawable.one));
        itemArrayList.add(new Model(2, "Grilled Mushroom and Vegetables", "Spcie grills mushrooms, cucumber, apples and lot more", 150, R.drawable.two));
        itemArrayList.add(new Model(3, "Chicken Overload Meal", "Grilled chicken & tandoori chicken in masala curry", 185, R.drawable.three));
        itemArrayList.add(new Model(4, "Chinese Egg Fry", "Exotic eggs Fried served steaming hot", 200, R.drawable.four));
        itemArrayList.add(new Model(5, "Chicken Wraps", "Grilled chicken tikka rool wrapped", 125, R.drawable.five));
        itemArrayList.add(new Model(6, "Veggie Delight", "Loads of veggies with olives", 250, R.drawable.six));
        itemArrayList.add(new Model(7, "Seafood Combo", "combo of prawns, scallop, sliced fish, calanmari, potato fries", 170, R.drawable.seven));
        itemArrayList.add(new Model(8, "Full Tandoori", "Chicken roated with lip smacking mayo dressing", 300, R.drawable.eight));

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initEvents()
    {
        deleteSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SparseBooleanArray selectedRows = recyclerViewAdapter.getSelectedIds();//Get the selected ids from adapter
                //Check if item is selected or not via size
                if (selectedRows.size() > 0)
                {
                    //Loop to all the selected rows array
                    for (int i = (selectedRows.size() - 1); i >= 0; i--) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {

                            //remove the checked item
                            recyclerViewAdapter.removeItem(selectedRows.keyAt(i));
                        }
                    }
                    //notify the adapter and remove all checked selection
                    recyclerViewAdapter.deselectAll();
                }
            }
        });

        getSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SparseBooleanArray selectedRows = recyclerViewAdapter.getSelectedIds();//Get the selected ids from adapter

                //Check if item is selected or not via size
                if (selectedRows.size() > 0)
                {
                    StringBuilder stringBuilder = new StringBuilder();

                    for(int i=0; i<selectedRows.size(); i++)
                    {
                        int key = selectedRows.keyAt(i);
                        String selectedRowLabel ="Selected Position "+key;
                        stringBuilder.append(selectedRowLabel + "\n");
                        Log.d("Element at "+key, " is "+selectedRows.get(key));
                    }
                    Toast.makeText(getApplicationContext(), "Selected Rows\n" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        reverseSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sendSelectedToNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SparseBooleanArray selectedRows = recyclerViewAdapter.getSelectedIds();//Get the selected ids from adapter
                //Check if item is selected or not via size
                if (selectedRows.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    //Loop to all the selected rows array
                    for (int i = 0; i < selectedRows.size(); i++) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {

                            //Get the checked item text from array list by getting keyAt method of selectedRowsarray
                            String selectedRowLabel = itemArrayList.get(selectedRows.keyAt(i)).getItemTitle();

                            //append the row label text
                            stringBuilder.append(selectedRowLabel + "\n");
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Selected Rows\n" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Set action mode null after use
    public void setNullToActionMode() {
        if (actionMode != null)
            actionMode = null;
    }
}
