package com.sunanda.newroutine.application.util;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunanda.newroutine.application.R;

public class View_Holder extends RecyclerView.ViewHolder {

    public CardView cv;
    public TextView title, tvShow;
    public TextView description;
    public ImageView imageView;

    public View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        title = (TextView) itemView.findViewById(R.id.title);
        tvShow = (TextView) itemView.findViewById(R.id.tvShow);
        description = (TextView) itemView.findViewById(R.id.description);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }

}