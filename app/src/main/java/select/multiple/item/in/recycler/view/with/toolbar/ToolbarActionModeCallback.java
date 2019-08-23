package select.multiple.item.in.recycler.view.with.toolbar;

import android.app.Activity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class ToolbarActionModeCallback implements ActionMode.Callback {

    private Activity context;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Model> itemArrayList;

    public ToolbarActionModeCallback(Activity context, RecyclerViewAdapter recyclerViewAdapter, ArrayList<Model> itemArrayList) {
        this.context = context;
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.itemArrayList =itemArrayList;
    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        actionMode.getMenuInflater().inflate(R.menu.main_menu, menu);//Inflate the menu over action mode
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu)
    {
        Log.d("TOOLBAR", "onPrepareActionMode() : " +actionMode);
        //Sometimes the meu will not be visible so for that we need to set their visibility manually in this method
        //So here show action menu according to SDK Levels

        menu.findItem(R.id.action_select_all).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.findItem(R.id.action_deselect_ll).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.action_select_all:
                Toast.makeText(context, "clicked select all!", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < itemArrayList.size(); i++)
                    recyclerViewAdapter.checkCheckBox(i, true);
                break;
            case R.id.action_deselect_ll:
                Toast.makeText(context, "clicked deselect all", Toast.LENGTH_SHORT).show();
                recyclerViewAdapter.deselectAll();
                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {
        ((SelectMultipleItemWithToolBarActivity) context).setNullToActionMode();//Set action mode null
        recyclerViewAdapter.deselectAll();
    }
}
