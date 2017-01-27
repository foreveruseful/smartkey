package link.anyauto.smartkey.demo.adapter;

import android.databinding.ObservableArrayList;

public class OneTypeItemSource<T> {

    public int itemViewId = 0;

    public ObservableArrayList<T> items = new ObservableArrayList<>();

    public SomethingClicked[] listeners;
}
