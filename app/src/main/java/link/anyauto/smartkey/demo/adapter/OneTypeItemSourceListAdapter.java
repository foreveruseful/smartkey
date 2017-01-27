package link.anyauto.smartkey.demo.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import link.anyauto.smartkey.demo.BR;
import link.anyauto.smartkey.demo.R;

/**
 * Created by discotek on 17-1-24.
 */

public class OneTypeItemSourceListAdapter extends BaseAdapter {

    OneTypeItemSource source;
    LayoutInflater inflater;
    ObservableList.OnListChangedCallback callback = new ObservableList.OnListChangedCallback() {
        @Override
        public void onChanged(ObservableList observableList) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList observableList, int i, int i1) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(ObservableList observableList, int i, int i1) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeMoved(ObservableList observableList, int i, int i1, int i2) {
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(ObservableList observableList, int i, int i1) {
            notifyDataSetChanged();
        }
    };

    public OneTypeItemSourceListAdapter(OneTypeItemSource source, LayoutInflater inflater) {
        this.source = source;
        this.inflater = inflater;
        if(this.source.items != null) {
            this.source.items.removeOnListChangedCallback(callback);
            this.source.items.addOnListChangedCallback(callback);
        }
    }

    @Override
    public int getCount() {
        return source == null || source.items == null ? 0 : source.items.size();
    }

    @Override
    public Object getItem(int i) {
        return source == null || source.items == null ? null : source.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewDataBinding binding;
        PositionListeners pl;
        if(view != null) {
            binding = (ViewDataBinding) view.getTag(R.id.binding);
            pl = (PositionListeners) view.getTag(R.id.pl);
        } else {
            view = inflater.inflate(source.itemViewId, null);
            binding = DataBindingUtil.bind(view);
            view.setTag(R.id.binding, binding);
            pl = new PositionListeners();
            pl.listeners = source.listeners;
        }
        binding.setVariable(BR.item, getItem(i));
        try {
            pl.position = i;
            binding.setVariable(BR.pl, pl);
        } catch (Exception e){}
        return view;
    }
}
