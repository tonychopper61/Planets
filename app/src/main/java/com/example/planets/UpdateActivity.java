package com.example.planets;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Base64;

public class UpdateActivity extends AppCompatActivity {

    ImageView imageView;
    EditText Name, Distance;
    Mask mask;
    Connection connection;
    View v;
    String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mask=getIntent().getParcelableExtra("Planet");

        imageView=findViewById(R.id.image_base);

        Name = findViewById(R.id.UpName);
        Name.setText(mask.getName());

        Distance=findViewById(R.id.UpDistance);
        Distance.setText(mask.getDistance());

        imageView.setImageBitmap(getImgBitmap(mask.getImage()));
        v =findViewById(com.google.android.material.R.id.ghost_view);
    }

    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(UpdateActivity.this.getResources(),
                R.drawable.planet);
    }

    public void onClickChooseImage(View view)
    {
        getImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                imageView.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            image=Base64.getEncoder().encodeToString(bytes);
            return image;
        }
        return "";
    }
    public void returnToMain()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClickUpdate(View v){

        AlertDialog.Builder builder=new AlertDialog.Builder(UpdateActivity.this);
        builder.setTitle("Изменение")
                .setMessage("Вы уверены что хотите изменить данные?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Name.getText().length()==0|| Distance.getText().length()==0 )
                        {
                            Toast.makeText(UpdateActivity.this, "Есть незаполненые обязательные поля", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            String query="";
                            ConSQL connectionHelper = new ConSQL();
                            connection = connectionHelper.conclass();
                            if (connection != null) {
                                if(image=="")
                                {
                                    query = "UPDATE Planets Set Name = '" + Name.getText() + "', Distance = '" + Distance.getText() + "' WHERE ID_planet = "+mask.getID()+"";

                                }
                                else
                                {
                                    query = "UPDATE Planets Set Name = '" + Name.getText() + "', Distance = '" + Distance.getText() + "', Image ='" + image + "' WHERE ID_planet = "+mask.getID()+"";
                                }
                                Statement statement = connection.createStatement();
                                statement.executeQuery(query);
                                Toast.makeText(UpdateActivity.this, "Данные изменены", Toast.LENGTH_SHORT).show();

                            }
                        }
                        catch (Exception ex)
                        {

                        }
                        returnToMain();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }

    public void onClickDelete(View v){

        AlertDialog.Builder builder=new AlertDialog.Builder(UpdateActivity.this);
        builder.setTitle("Удалить")
                .setMessage("Удалить?")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            ConSQL connectionHelper = new ConSQL();
                            connection = connectionHelper.conclass();
                            if (connection != null) {
                                String query = "DELETE FROM  Planets  WHERE ID_planet = "+mask.getID()+"";
                                Statement statement = connection.createStatement();
                                statement.executeQuery(query);
                            }
                        }

                        catch (Exception ex)
                        {

                        }
                        returnToMain();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void onClickBack(View v) {
        switch (v.getId()) {
            case R.id.Back:
                returnToMain();
                break;
        }
    }
}