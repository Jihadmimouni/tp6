package com.example.tp6;

// CustomAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class List_View_Adapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] items;
    private final int[] images; // Tableaux d'icônes pour chaque item

    public List_View_Adapter(Context context, String[] items, int[] images) {
        super(context, R.layout.list_item, items);
        this.context = context;
        this.items = items;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);

        TextView textView = rowView.findViewById(R.id.item_text);
        ImageView imageView = rowView.findViewById(R.id.item_icon);

        textView.setText(items[position]);
        imageView.setImageResource(images[position]);

        return rowView;
    }
}

