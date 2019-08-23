package select.multiple.item.in.recycler.view.with.toolbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

    private RecyclerViewItemClickListener recyclerViewItemClickListener;
    private GestureDetector gestureDetector;

    public RecyclerTouchListener(Context context, final RecyclerView recycleView, final RecyclerViewItemClickListener recyclerViewItemClickListener){

        this.recyclerViewItemClickListener=recyclerViewItemClickListener;
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && recyclerViewItemClickListener!=null){
                    recyclerViewItemClickListener.onItemLongClick(child,recycleView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child=rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && recyclerViewItemClickListener!=null && gestureDetector.onTouchEvent(e))
        {
            recyclerViewItemClickListener.onItemClick(child,rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
