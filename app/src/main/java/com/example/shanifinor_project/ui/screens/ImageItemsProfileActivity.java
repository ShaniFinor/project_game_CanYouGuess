package com.example.shanifinor_project.ui.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shanifinor_project.R;
import com.example.shanifinor_project.model.classes.User;
import com.example.shanifinor_project.ui.adapter.ImageItemAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class ImageItemsProfileActivity extends AppCompatActivity {

    private ArrayList<String> imagelist = new ArrayList<>();

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageItemAdapter adapter;
    private FirebaseStorage firebaseStorage;
    private StorageReference listRef;
    private static final int GALLERY = 1;
    private Uri filePath;
    private static final int CAMERA_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_items_profile);

        setImageItemList();
    }

    private void setImageItemList() {
        recyclerView = findViewById(R.id.imageItem_recyclerview);
        adapter = new ImageItemAdapter(imagelist, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        firebaseStorage = FirebaseStorage.getInstance();
        listRef = firebaseStorage.getReference().child("imageProfileOptions");
        listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference file : listResult.getItems()) {
                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imagelist.add(uri.toString());
                            Log.e("ItemValue", uri.toString());
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    public void closeAndSendUrlToUserProfile(String url) {
        // if you press on image item from the storage image options.
        // the method is declare in the class "ImageItemAdapter"
        Intent intent = new Intent();
        intent.putExtra("url", url);
        setResult(-1, intent);
        finish();//finishing activity
    }

    public void chooseImageFromLocalFile(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);
        if (requestCode == GALLERY && resultCode == RESULT_OK && dataIntent != null && dataIntent.getData() != null) {
            filePath = dataIntent.getData();
        }

        if (requestCode == CAMERA_CODE) {
            if (resultCode == RESULT_OK) {
                if (dataIntent != null) {
                    //start the camera and set the picture in the app
                    Bitmap bitmap = (Bitmap) dataIntent.getExtras().get("data");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    filePath = Uri.parse(MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver()
                            , bitmap, "Current", null));
                }
            }
        }

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = firebaseStorage.getReference().child("imageProfile/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                    User.getInstance().setIcon(uri.toString());
                                    Intent intent = new Intent(ImageItemsProfileActivity.this, UserProfileActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    public void camera(View view) {
        //open the camera
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamera, CAMERA_CODE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_design, menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem item_stare_points = menu.findItem(R.id.star_points);
        final MenuItem item_level = menu.findItem(R.id.level);

        item_stare_points.setTitle("כוכבים: " + User.getInstance().getStars());
        item_level.setTitle("ניצחונות: " + User.getInstance().getNumOfWin());

        return true;
    }
}