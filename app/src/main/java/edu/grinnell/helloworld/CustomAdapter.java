package edu.grinnell.helloworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class CustomAdapter extends ArrayAdapter<String> {

    public CustomAdapter(Context context, String[] names) {
        super(context, R.layout.custom_row, names);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater someInflater = LayoutInflater.from(getContext());
        View customView = someInflater.inflate(R.layout.custom_row, parent, false);

        String singleNameItem = getItem(position);
        TextView someText = (TextView) customView.findViewById(R.id.nameText);
        ImageView someImage = (ImageView) customView.findViewById(R.id.imageView);

        someText.setText(singleNameItem);
        someImage.setImageResource(R.drawable.larry_pic);

        return customView;
    }
}
