package select.multiple.item.in.recycler.view.with.toolbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();

    private ArrayList<Model> arrayList;
    private Context context;
    private SparseBooleanArray selectedItemPosition;

    public RecyclerViewAdapter(Context context, ArrayList<Model> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        selectedItemPosition = new SparseBooleanArray();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_row, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position)
    {
        Model currentRecyclerViewItem = arrayList.get(position);
        holder.itemTitleTextView.setText(currentRecyclerViewItem.getItemTitle());
        holder.itemDescriptionTextView.setText(currentRecyclerViewItem.getItemDescription());
        holder.itemPriceTextView.setText("\u20B9 "+currentRecyclerViewItem.getItemPrice());
        holder.itemImageView.setImageResource(currentRecyclerViewItem.getItemImage());

        if(isSelected(position))
        {
            holder.itemIsCheckLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.itemIsCheckLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {
        return (null != arrayList ? arrayList.size() : 0);
    }

    /**
     * Select all checkbox
     **/
    public void selectAll() {
        Log.d(TAG, "selectAll() : " + arrayList);
    }

    /**
     * Remove all checkbox
     **/
    public void deselectAll()
    {
        Log.d(TAG, "deselectAll() : " + arrayList);
        List<Integer> selection = getSelectedItems();
        selectedItemPosition.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    /**
     * Check the Checkbox if not checked, if already check then unchecked
     **/
    public void checkCheckBox(int position, boolean value)
    {
        if (value)
        {
            selectedItemPosition.put(position, true);
        }
        else
        {
            selectedItemPosition.delete(position);
        }
        Log.d(TAG, "selectAll() : " +selectedItemPosition);
        //notifyDataSetChanged();
        notifyItemChanged(position);
    }

    /**
     * Return the selected Checkbox position
     **/
    public SparseBooleanArray getSelectedIds() {
        return selectedItemPosition;
    }

    /**
     * Count the selected items
     * @return Selected items count
     */
    public int getSelectedItemCount() {
        return selectedItemPosition.size();
    }

    /**
     * Indicates the list of selected items
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItems()
    {
        List<Integer> items = new ArrayList<>(selectedItemPosition.size());

        for (int i = 0; i < selectedItemPosition.size(); ++i)
        {
            items.add(selectedItemPosition.keyAt(i));
        }
        return items;
    }

    /**
     * Indicates if the item at position position is selected
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }


    public void removeItem(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
    }

    private void removeRange(int positionStart, int itemCount) {
        for (int i = 0; i < itemCount; ++i) {
            arrayList.remove(positionStart);
        }
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView itemImageView;
        private TextView itemTitleTextView,itemDescriptionTextView,itemPriceTextView;
        private RelativeLayout itemIsCheckLayout;

        RecyclerViewHolder(View itemView)
        {
            super(itemView);
            itemImageView =itemView.findViewById(R.id.item_image);
            itemTitleTextView = itemView.findViewById(R.id.item_title);
            itemDescriptionTextView = itemView.findViewById(R.id.item_description);
            itemPriceTextView = itemView.findViewById(R.id.item_price);
            itemIsCheckLayout=itemView.findViewById(R.id.item_is_check_layout);
        }
    }
}