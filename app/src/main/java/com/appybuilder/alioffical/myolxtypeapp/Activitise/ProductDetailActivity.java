package com.appybuilder.alioffical.myolxtypeapp.Activitise;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appybuilder.alioffical.myolxtypeapp.Adapters.UploadListAdapter;
import com.appybuilder.alioffical.myolxtypeapp.Apis.APIService;
import com.appybuilder.alioffical.myolxtypeapp.Apis.ApiClient;
import com.appybuilder.alioffical.myolxtypeapp.Models.AdsCategoryModel;
import com.appybuilder.alioffical.myolxtypeapp.Models.ImageFilePath;
import com.appybuilder.alioffical.myolxtypeapp.Models.MyPrefrence;
import com.appybuilder.alioffical.myolxtypeapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView addImagesBtn;
    EditText etTitle, etDescription;
    Button btnaddvidadd;
    ImageView btnaddloc;
    TextView showlocation;
    RecyclerView reclerImages;
    String strTitleItem, strDescriptionItem, strLocationItem;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    String path;
    public static final int RESULT_IMAGE =1;
    private Button btnUpload;
    private RecyclerView mRecyclerView;
    String add_cate_id;
    Intent intent;
    Uri tempUri;
    Button btnSaveRecord;
    File finalFile;
    ImageView backbtn;
    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    ArrayList<Uri> mArrayUri;
    String selectedImage;
    InputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        intent = getIntent();
        inti_views();
        intit_views_pictures();

    }

    public void inti_views() {
        add_cate_id = intent.getStringExtra("add_id");
        addImagesBtn = findViewById(R.id.imageview_one);
        addImagesBtn.setOnClickListener(this);
        etTitle = findViewById(R.id.name_item);
        etDescription = findViewById(R.id.description_id);
        btnaddloc = findViewById(R.id.addlocation);
        btnaddloc.setOnClickListener(this);
        btnaddvidadd = findViewById(R.id.btnvideo_add);
        btnaddvidadd.setOnClickListener(this);
        showlocation = findViewById(R.id.locationtxt);
        btnSaveRecord = findViewById(R.id.btnsaverecord);
        btnSaveRecord.setOnClickListener(this);
        backbtn = findViewById(R.id.back_icon);
        backbtn.setOnClickListener(this);
    }

    public void intit_views_pictures() {
    }

    public void selectImagesFromGalary() {
        requestPermission();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RESULT_IMAGE){
            Uri uri = data.getData();


            selectedImage = ImageFilePath.getPath(ProductDetailActivity.this, data.getData());
//                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());

            Log.i(TAG, "onActivityResult: file path : " + selectedImage);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Log.d(TAG, String.valueOf(bitmap));

            //   ImageView imageView = (ImageView) findViewById(R.id.img_profile);
            addImagesBtn.setImageBitmap(bitmap);
            addImagesBtn.setVisibility(View.VISIBLE);

        } else {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }

        }
    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.imageview_one){
            chooseImagefromGallery();//select images from
        }
        if (view.getId()==R.id.btnsaverecord){
           savedDataFun();
        }
        if (view.getId()==R.id.back_icon){
            finish();
        }
    }

    private void savedDataFun() {
        if (etTitle.getText().toString().trim().isEmpty()){
            etTitle.setError("Enter Title Please");
            etTitle.requestFocus();
        } if (etDescription.getText().toString().trim().isEmpty()){
            etDescription.setError("Enter Title Please");
            etDescription.requestFocus();
        }
        else {
            putrecord_fun();
        }

    }
    private MultipartBody.Part prePareImages(String strPath,String strPartName){
        File file=new File(path);
        RequestBody requestBody=RequestBody.create(MediaType.parse(getContentResolver().getType(Uri.fromFile(file))),file);
        return MultipartBody.Part.createFormData(strPartName,file.getName(),requestBody);

    }
    public void chooseImagefromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    private void putrecord_fun() {
        strTitleItem=etTitle.getText().toString().trim();
        strDescriptionItem=etDescription.getText().toString().trim();
        strLocationItem=showlocation.getText().toString();
//        RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"),
//                finalFile);
        File file = new File(selectedImage);
       // MultipartBody.Part surveyImagesParts = new MultipartBody.Part[];
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("ads_images[]", file.getAbsolutePath(), requestFile);

        RequestBody strTitleItem = RequestBody.create(MediaType.parse("multipart/form-data"),
                etTitle.getText()
                        .toString());
        RequestBody strDescriptionItem = RequestBody.create(MediaType.parse("multipart/form-data"),
                etDescription.getText().toString());
        RequestBody strLocationItem = RequestBody.create(MediaType.parse("multipart/form-data"),
                showlocation.getText().toString());
        RequestBody strAddCate = RequestBody.create(MediaType.parse("multipart/form-data"),
                add_cate_id);

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("ads_images", String.valueOf(finalFile));
        String strToken = MyPrefrence.getStr("token");
        Call<AdsCategoryModel.AdsCatModel> call;
        APIService service = ApiClient.getClient().create(APIService.class);
       // call = service.create_Ads_2("Bearer " + strToken,hashMap);
        call = service.create_Ads_2("Bearer " + strToken,strTitleItem,strAddCate,strDescriptionItem,strLocationItem,body);
        call.enqueue(new Callback<AdsCategoryModel.AdsCatModel>() {
            @Override
            public void onResponse(Call<AdsCategoryModel.AdsCatModel> call, Response<AdsCategoryModel.AdsCatModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), response.body().getMessage().toString(),Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<AdsCategoryModel.AdsCatModel> call, Throwable t) {
                Log.d("Tag",t.getMessage());
            }
        });


    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GO TO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
    private void requestPermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        //Toast.makeText(RegisterActivity2.this, "Permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        PermissionListener dialogPermissionListener =
                                DialogOnDeniedPermissionListener.Builder
                                        .withContext(ProductDetailActivity.this)
                                        .withTitle("Read External Storage permission")
                                        .withMessage("Read External Storage  permission is needed")
                                        .withButtonText(android.R.string.ok)
                                        .withIcon(R.mipmap.ic_launcher)
                                        .build();


                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
}