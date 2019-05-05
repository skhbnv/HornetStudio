package com.example.madi.hornetstudio.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.madi.hornetstudio.Fragments.DashboardFragment;
import com.example.madi.hornetstudio.ItemClickListener;
import com.example.madi.hornetstudio.Models.SalonsCardInfo;
import com.example.madi.hornetstudio.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MovieViewHolder>  {
    private List<SalonsCardInfo> mData;
    private Context context;
    private ItemClickListener itemClickListener;

    public Adapter(List<SalonsCardInfo> dataset, ItemClickListener itemClickListener,
                   Context context){
        mData = dataset;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private TextView salonName;
        private TextView salonAddress;
        private ImageView backPoster;

//        private  backPoster;
        ItemClickListener itemClickListener;

        public MovieViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            salonName = itemView.findViewById(R.id.name_of_the_salon);
            salonAddress = itemView.findViewById(R.id.salon_address);
            backPoster = itemView.findViewById(R.id.background_fone);

            itemView.setOnClickListener(this);
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(mData.get(getAdapterPosition()));
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.salons_card, viewGroup, false);
        MovieViewHolder vh = new MovieViewHolder(v, itemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, int i) {
        onBindMovieHolder(movieViewHolder, i);
    }

    void onBindMovieHolder(@NonNull final MovieViewHolder movieViewHolder, int i){
        movieViewHolder.salonName.setText(mData.get(i).getSalon_name());
        movieViewHolder.salonAddress.setText("Адресс: "+ mData.get(i).getAddress());
        Picasso.
                with(context).
                load(mData.get(i).getBackPoster()).
                fit().
                centerCrop().
                into(movieViewHolder.backPoster);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}