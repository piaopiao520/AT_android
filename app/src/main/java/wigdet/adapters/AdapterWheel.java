package wigdet.adapters;

import android.content.Context;

import wigdet.WheelAdapter;

/**
 * Adapter class for old wheel com.example.administrator.atandroid.adapter (deprecated WheelAdapter class).
 * 
 * @deprecated Will be removed soon
 */
public class AdapterWheel extends AbstractWheelTextAdapter {

    // Source com.example.administrator.atandroid.adapter
    private WheelAdapter adapter;
    
    /**
     * Constructor
     * @param context the current context
     * @param adapter the source com.example.administrator.atandroid.adapter
     */
    public AdapterWheel(Context context, WheelAdapter adapter) {
        super(context);
        
        this.adapter = adapter;
    }

    /**
     * Gets original com.example.administrator.atandroid.adapter
     * @return the original com.example.administrator.atandroid.adapter
     */
    public WheelAdapter getAdapter() {
        return adapter;
    }
    
    @Override
    public int getItemsCount() {
        return adapter.getItemsCount();
    }

    @Override
    protected CharSequence getItemText(int index) {
        return adapter.getItem(index);
    }

}
