package com.siddik.onlinefoods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class Confirm_Order extends AppCompatActivity {

    ImageView recipeImage;
    Uri uri;
    EditText txt_name,txt_description,txt_price,cname,phone,address,quantity;
    String imageUrl;
    String key,oldImageUrl;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String recipename,recipeDescription,recipePrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm__order);

        recipeImage = (ImageView)findViewById(R.id.iv_foodImage);
        txt_name = (EditText)findViewById(R.id.txt_recipe_name);
        cname=(EditText)findViewById(R.id.text_name);
        phone=(EditText)findViewById(R.id.text_phone);
        address=(EditText)findViewById(R.id.text_adderss);
        quantity=(EditText)findViewById(R.id.text_quentity);
        txt_price = (EditText)findViewById(R.id.text_price);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            Glide.with(Confirm_Order.this)
                    .load(bundle.getString("oldimageUrl"))
                    .into(recipeImage);
            txt_name.setText(bundle.getString("recipeNameKey"));
            txt_price.setText(bundle.getString("priceKey"));
            key = bundle.getString("key");
            oldImageUrl = bundle.getString("oldimageUrl");
        }


        databaseReference = FirebaseDatabase.getInstance().getReference("Recipe").child(key);



    }
    public void uploadImage(){

        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child("OrderImage").child(uri.getLastPathSegment());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Order prossing....");
        progressDialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                uploadRecipe();
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });



    }


    public void btnUploadRecipe(View view) {

        uploadImage();

    }

    public void uploadRecipe(){



        FoodData foodData = new FoodData(
                txt_name.getText().toString(),
                txt_description.getText().toString(),
                txt_price.getText().toString(),
                imageUrl
        );

        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Orders")
                .child(myCurrentDateTime).setValue(foodData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(Confirm_Order.this, "Thank Your !\n Your Order is Completed", Toast.LENGTH_SHORT).show();

                    finish();

                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Confirm_Order.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });




    }


}