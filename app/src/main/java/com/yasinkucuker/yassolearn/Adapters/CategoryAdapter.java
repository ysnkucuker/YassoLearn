package com.yasinkucuker.yassolearn.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yasinkucuker.yassolearn.Models.Category;
import com.yasinkucuker.yassolearn.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Context context;

    public CategoryAdapter(Context context, List<Category> categories) {
        super(context, 0, categories);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_category, parent, false);
        }

        Category currentCategory = getItem(position);

        ImageView icon = listItem.findViewById(R.id.categoryIcon);
        TextView title = listItem.findViewById(R.id.categoryTitle);
        TextView description = listItem.findViewById(R.id.categoryDescription);

        if (currentCategory != null) {
            icon.setImageResource(currentCategory.getIconResource());
            title.setText(currentCategory.getTitle());
            description.setText(currentCategory.getDescription());
        }

        return listItem;
    }
}
