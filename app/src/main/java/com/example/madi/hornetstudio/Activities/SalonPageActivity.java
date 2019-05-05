package com.example.madi.hornetstudio.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madi.hornetstudio.Models.SalonsCardInfo;
import com.example.madi.hornetstudio.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class SalonPageActivity extends AppCompatActivity {
    private TextView salon_name;
    private TextView working_hours;
    private TextView address;
    private TextView personal_rating;
    private CarouselView carouselView;
    private int[] mImages = new int[]{
            R.drawable.kitty, R.drawable.kitty3, R.drawable.kitty2
    };

    private SalonsCardInfo salonsCardInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salon_page_activity);
        setExtra();
        initUI();
    }

    private void setExtra() {
        Bundle bundle = getIntent().getExtras();

        if(bundle.getParcelable("salon_object")!= null)
        {
            this.salonsCardInfo = bundle.getParcelable("salon_object");
        }
    }

    private void initUI() {
        salon_name = findViewById(R.id.salon_name);
        address = findViewById(R.id.salon_address);

        carouselView = findViewById(R.id.salon_page_carousel);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });

//        personal_rating = findViewById(R.id.personal_rating);

        salon_name.setText(salonsCardInfo.getSalon_name());
//        working_hours.setText(salonsCardInfo.getWorking_hours());
        address.setText(salonsCardInfo.getAddress());
//        personal_rating.setText(String.valueOf(salonsCardInfo.getRating()));
    }
}
