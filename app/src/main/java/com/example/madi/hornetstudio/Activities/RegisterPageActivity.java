package com.example.madi.hornetstudio.Activities;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madi.hornetstudio.Models.SalonsCardInfo;
import com.example.madi.hornetstudio.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;

public class RegisterPageActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mSalonName;
    private TextView mSalonType;
    private TextView mAddress;
    private TextView mInstagram;
    private TextView mPhone;
    private ImageView mSalonBackPoster;
    private TextView mClickToDownload;

    private StorageReference mStorageRef;
    private DatabaseReference mDataBase;
    private StorageTask mUploads;

    private LinearLayout downloadImage;

    private TextView mHair;
    private TextView mNail;
    private TextView mMassage;
    private TextView mDepilation;
    private Uri mImageUri;

    private AlertDialog.Builder mBuilder;

    private CarouselView carouselView;
    private Button mButtonReg;

    String[] Haircuts;
    String[] nails;
    String[] massage;
    String[] depilation;

    boolean[] checkedItems;
    boolean[] nailsCheckedItems;
    boolean[] massageCheckedItems;
    boolean[] depilationCheckedItems;

    ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayList<Integer> mUseritemsNails = new ArrayList<>();
    ArrayList<Integer> mUserItemsMassage = new ArrayList<>();
    ArrayList<Integer> mUserItemsDepilation = new ArrayList<>();

    ArrayList<String> allServices = new ArrayList<>();
    final static int REQUEST_CODE = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salon_registration_page);

        initUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null
                && data.getData() != null){
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mSalonBackPoster);
            mClickToDownload.setVisibility(View.GONE);
        }
    }

    private void initUI() {
        mSalonBackPoster = findViewById(R.id.salon_backgr);
        mSalonName = findViewById(R.id.register_salon_name);
        mSalonType = findViewById(R.id.register_salon_type);
        mAddress = findViewById(R.id.registration_salon_address);
        mInstagram = findViewById(R.id.register_instagram_account);
        mPhone = findViewById(R.id.register_phone);
        mButtonReg = findViewById(R.id.regpage_register_button);
        downloadImage = findViewById(R.id.image_downloader);
        mClickToDownload = findViewById(R.id.click_to_download);

        mHair = findViewById(R.id.hair_list);
        mNail = findViewById(R.id.nail_list);
        mMassage = findViewById(R.id.massage_list);
        mDepilation = findViewById(R.id.depil_list);

        carouselView = findViewById(R.id.register_salon_carousel);

        mButtonReg.setOnClickListener(this);
        mHair.setOnClickListener(this);
        mNail.setOnClickListener(this);
        mMassage.setOnClickListener(this);
        mDepilation.setOnClickListener(this);
        downloadImage.setOnClickListener(this);

        Haircuts = new String[]{"Женская", "Мужская", "Детская", "Укладка"};
        nails = new String[]{"Гелевая", "Комплексная", "Лак-покрытие", "Стразы"};
        massage = new String[]{"Массаж головы", "тайский массаж", "массаж лица", "Точечный массаж"};
        depilation = new String[]{"Эпиляция ног", "Эпиляция рук", "Депиляция лица", "Лазерная"};

        checkedItems = new boolean[Haircuts.length];
        nailsCheckedItems = new boolean[nails.length];
        massageCheckedItems = new boolean[massage.length];
        depilationCheckedItems = new boolean[depilation.length];

        mBuilder = new AlertDialog.Builder(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_downloader:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
                break;


            case R.id.regpage_register_button:
                if (mUploads != null && mUploads.isInProgress()) {

                } else {
                    addSalonToTheBase();
                }
                break;
            case R.id.hair_list:
                mBuilder.setTitle("Выберите из списка предлагаемые вами услуги");
                mBuilder.setMultiChoiceItems(Haircuts, checkedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (!mUserItems.contains(which)) {
                                    mUserItems.add(which);
                                } else {
                                    mUserItems.remove(which);
                                }
                            }
                        });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            allServices.add(Haircuts[mUserItems.get(i)]);
                        }
                        Log.d("___", "onClick: " + allServices.get(0));
                    }
                });
                mBuilder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Очистить", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                        }
                    }
                });

                break;
            case R.id.nail_list:
                mBuilder.setTitle("Выберите из списка предлагаемые вами услуги");
                mBuilder.setMultiChoiceItems(nails, nailsCheckedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (!mUseritemsNails.contains(which)) {
                                    mUseritemsNails.add(which);
                                } else {
                                    mUseritemsNails.remove(which);
                                }
                            }
                        });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < mUseritemsNails.size(); i++) {
                            allServices.add(nails[mUseritemsNails.get(i)]);
                        }
                    }
                });
                mBuilder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Очистить", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < nailsCheckedItems.length; i++) {
                            nailsCheckedItems[i] = false;
                            mUserItems.clear();
                        }
                    }
                });
                break;

            case R.id.massage_list:
                mBuilder.setTitle("Выберите из списка предлагаемые вами услуги");
                mBuilder.setMultiChoiceItems(massage, massageCheckedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (!mUserItemsMassage.contains(which)) {
                                    mUserItemsMassage.add(which);
                                } else {
                                    mUserItemsMassage.remove(which);
                                }
                            }
                        });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItemsMassage.size(); i++) {
                            allServices.add(massage[mUserItemsMassage.get(i)]);
                        }
                    }
                });
                mBuilder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Очистить", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < massageCheckedItems.length; i++) {
                            massageCheckedItems[i] = false;
                            mUserItemsMassage.clear();
                        }
                    }
                });
                break;

            case R.id.depil_list:
                mBuilder.setTitle("Выберите из списка предлагаемые вами услуги");
                mBuilder.setMultiChoiceItems(depilation, depilationCheckedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (!mUserItemsDepilation.contains(which)) {
                                    mUserItemsDepilation.add(which);
                                } else {
                                    mUserItemsDepilation.remove(which);
                                }
                            }
                        });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItemsDepilation.size(); i++) {
                            allServices.add(depilation[mUserItemsDepilation.get(i)]);
                        }
                    }
                });
                mBuilder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Очистить", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < depilationCheckedItems.length; i++) {
                            depilationCheckedItems[i] = false;
                            mUserItemsDepilation.clear();
                        }
                    }
                });
                break;
        }
        if (v.getId() == R.id.hair_list && v.getId() == R.id.nail_list
                && v.getId() == R.id.massage_list && v.getId() == R.id.depil_list) {
            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
    public void addSalonToTheBase(){
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        if(mImageUri != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+
                    getFileExtension(mImageUri));
            mUploads = fileReference.putFile(mImageUri).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            SalonsCardInfo obj = new SalonsCardInfo(mSalonName.getText().toString(),
                                    0, 0,
                                    mAddress.getText().toString(),
                                    mInstagram.getText().toString(),
                                    allServices,
                                    taskSnapshot.getStorage().getDownloadUrl().toString());

                            DatabaseReference myRef = FirebaseDatabase.getInstance().
                                    getReference("salons");
                            String key = myRef.push().getKey();
                            myRef.child(key).setValue(obj)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegisterPageActivity.this,
                                                    "Succsessfully added"
                                                    , Toast.LENGTH_SHORT).show();
                                            finishAct();
                                        }
                                    })
                            ;
                        }
                    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterPageActivity.this,
                            e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                }
            })
            ;
        }else{
            Toast.makeText(this, "no file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void finishAct() {
        this.finish();
    }
}
