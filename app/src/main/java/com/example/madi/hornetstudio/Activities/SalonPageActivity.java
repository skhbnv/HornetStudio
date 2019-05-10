package com.example.madi.hornetstudio.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.madi.hornetstudio.Models.SalonsCardInfo;
import com.example.madi.hornetstudio.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.w3c.dom.Text;

public class SalonPageActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView salon_name;
    private TextView instagram;
    private TextView address;
    private TextView phone;

    private TextView mHair;
    private RecyclerView mHairRecycler;

    private TextView mNail;
    private RecyclerView mNailRecycler;

    private TextView mMassage;
    private RecyclerView mMassageRecycler;

    private TextView mDepil;
    private RecyclerView mDepilRecycler;

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
        salon_name = findViewById(R.id.salon_page_name);
        address = findViewById(R.id.salon_page_address);
        phone = findViewById(R.id.salon_page_phone);
        instagram = findViewById(R.id.salon_page_instagram);

        mHair = findViewById(R.id.hair_button_salpage);
        mHairRecycler = findViewById(R.id.hair_recycler);

        mNail = findViewById(R.id.nail_button_salpage);
        mNailRecycler = findViewById(R.id.nail_recycler);

        mMassage = findViewById(R.id.massage_button_salpage);
        mMassageRecycler = findViewById(R.id.massage_recycler);

        mDepil = findViewById(R.id.depil_button_salpage);
        mDepilRecycler = findViewById(R.id.depil_recycler);


        carouselView = findViewById(R.id.salon_page_carousel);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });


        salon_name.setText(salonsCardInfo.getSalon_name());
        address.setText(salonsCardInfo.getAddress());
        instagram.setText(salonsCardInfo.getInstagram());
        phone.setText(salonsCardInfo.getPhone());

        mHair.setOnClickListener(this);
        mNail.setOnClickListener(this);
        mDepil.setOnClickListener(this);
        mMassage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hair_button_salpage:
                if (mHairRecycler.getLayoutParams().height != 1000){
                    mHairRecycler.getLayoutParams().height = 1000;
                    mHairRecycler.requestLayout();
                }else{
                    mHairRecycler.getLayoutParams().height = 0;
                    mHairRecycler.requestLayout();
                }
                break;
            case R.id.nail_button_salpage:
                if (mNailRecycler.getLayoutParams().height != 1000){
                    mNailRecycler.getLayoutParams().height = 1000;
                    mNailRecycler.requestLayout();
                }else{
                    mNailRecycler.getLayoutParams().height = 0;
                    mNailRecycler.requestLayout();
                }
                break;
            case R.id.massage_button_salpage:
                if (mMassageRecycler.getLayoutParams().height != 1000){
                    mMassageRecycler.getLayoutParams().height = 1000;
                    mMassageRecycler.requestLayout();
                }else{
                    mMassageRecycler.getLayoutParams().height = 0;
                    mMassageRecycler.requestLayout();
                }
                break;
            case R.id.depil_button_salpage:
                if (mDepilRecycler.getLayoutParams().height != 1000){
                    mDepilRecycler.getLayoutParams().height = 1000;
                    mDepilRecycler.requestLayout();
                }else{
                    mDepilRecycler.getLayoutParams().height = 0;
                    mDepilRecycler.requestLayout();
                }
                break;
        }
    }
}
