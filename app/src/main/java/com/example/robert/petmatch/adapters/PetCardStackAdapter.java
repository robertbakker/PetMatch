package com.example.robert.petmatch.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robert.petmatch.R;
import com.example.robert.petmatch.models.Pet;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Robert on 12-09-15.
 */
public class PetCardStackAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;

    private List<Pet> mObjects;

    private int mResource;

    private Context mContext;

    public PetCardStackAdapter(Context context, int resource, List<Pet> objects) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        mObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(mInflater, position, convertView, parent, mResource);
    }

    private View createViewFromResource(LayoutInflater inflater, int position, View view,
                                        ViewGroup parent, int resource) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(resource, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        // Map from Pet model values into views
        Pet item = getItem(position);
        int imageResource = getContext().getResources().getIdentifier(item.getImage(), "drawable", getContext().getPackageName());
        int familyId = getContext().getResources().getIdentifier("pet.family." + item.getFamily(), "string", getContext().getPackageName());
        String family = getContext().getResources().getString(familyId);

        holder.image.setImageResource(imageResource);
        holder.title.setText(item.getName() + " de " + family.toLowerCase());

        // Make a visually nice stack of cards
        view.setTranslationY(position * 10);
        float scale = 1.0f - (0.01f * position);
        view.setScaleX(scale);

        return view;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Pet getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        @Bind(R.id.title)
        TextView title;

        @Bind(R.id.image)
        ImageView image;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
