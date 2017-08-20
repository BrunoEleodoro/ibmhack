package com.example.victorgabriel.peoplefinder.activities;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.victorgabriel.peoplefinder.Internet;
import com.example.victorgabriel.peoplefinder.Message;
import com.example.victorgabriel.peoplefinder.R;
import com.example.victorgabriel.peoplefinder.tasks.cadastroDesaparecido;
import com.example.victorgabriel.peoplefinder.tasks.novoCadastro;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class cadastro_des extends AppCompatActivity {
    ImageView txt_img;
    File file;
    EditText txt_nome,txt_idade,txt_descricao,txt_contato;
    TextView txt_data,txt_hora;
    String latitude;
    String longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_des);
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");
        txt_img = (ImageView) findViewById(R.id.txt_img);
        txt_nome = (EditText) findViewById(R.id.txt_nome);
        txt_idade = (EditText) findViewById(R.id.txt_idade);
        txt_descricao = (EditText) findViewById(R.id.txt_descricao);
        txt_data = (TextView) findViewById(R.id.txt_data);
        txt_hora = (TextView) findViewById(R.id.txt_hora);
        txt_contato = (EditText) findViewById(R.id.txt_contato);

        txt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(cadastro_des.this);
                dialog.setTitle("Aviso");
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setSingleChoiceItems(new String[]{"Tirar foto","Galeria"}, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which == 0) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                takePic();
                            } else {
                                camera();
                            }

                        } else {
                            pickImage();
                        }
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.M)
    public void takePic() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    camera();
                } else {
                    if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        Toast.makeText(this, "É necessario autorizar o aplicativo identificar a foto!", Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 667);
                }
            } else {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "É necessario autorizar o aplicativo salvar a foto!", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 666);
            }
            //camera();
        } else {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                Toast.makeText(this, "É necessario autorizar o aplicativo usar a camera!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 668);
        }

    }
    public void camera() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), timeStamp + ".png");
        Uri tempUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startActivityForResult(intent, 1337);
    }
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }
    public void salvar(View view)
    {
        Internet internet = new Internet();
        String nome = txt_nome.getText().toString().trim();
        String idade = txt_idade.getText().toString().trim();
        String descricao = txt_descricao.getText().toString().trim();
        String data = internet.data_certa(txt_data.getText().toString().trim());
        String hora = txt_hora.getText().toString().trim();
        String contato = txt_contato.getText().toString().trim();

        if(nome.equals("") || idade.equals(""))
        {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            new cadastroDesaparecido(this,nome,idade,descricao,latitude,longitude,data,hora,contato,file).execute("");
        }

    }
    public void escolherData(View view)
    {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txt_data.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void escolherHora(View view)
    {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txt_hora.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1337) {
            if (resultCode == RESULT_OK) {
                try {
                    final ImageView imageView = (ImageView) findViewById(R.id.txt_img);
                    final ImageView imagem = new ImageView(cadastro_des.this);
                    Picasso.with(this).load(file).fit().into(imageView);
                    Picasso.with(this).load(file).into(imagem, new Callback() {
                        @Override
                        public void onSuccess() {
                            try {
                                //String filename = System.currentTimeMillis()+".jpg";
                                //File sd = Environment.getExternalStorageDirectory();
                                //file = new File(folder, filename);
                                Bitmap bitmap = ((BitmapDrawable) imagem.getDrawable()).getBitmap();
                                //Bitmap bitmap = getBitmap(imageView.getDrawable());
                                FileOutputStream out = new FileOutputStream(getRealPathFromURI(Uri.fromFile(file)));
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
                                out.flush();
                                out.close();
                            } catch (Exception e) {
                                Message messagem = new Message();
                                messagem.showMessage(cadastro_des.this, "Erro criando imagem, e=" + e);
                                Log.i("Script", "erro img=" + e);
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    //camera();
                    //showMessage("Desculpe.\nOcorreu um erro, tente novamente\n\nErro="+e);
                    Message message = new Message();
                    message.showMessage(cadastro_des.this, "Ocorreu um erro, tente novamente");
                }
                //Toast.makeText(this, file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 1) {
            try {
                Uri Selected_Image_Uri = data.getData();
                file = new File(getRealPathFromURI(Selected_Image_Uri));
                //Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show();
                ImageView imageView = (ImageView) findViewById(R.id.txt_img);
                imageView.setImageURI(Uri.fromFile(file));
                //imageView.setImageURI(Selected_Image_Uri);
                //Toast.makeText(this, getRealPathFromURI(Selected_Image_Uri), Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                //showMessage(""+e);
            }

        }
    }
        /*
        ImageView imageView = (ImageView) findViewById(R.id.imageView4);
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), capturedImageUri);
            imageView.setImageBitmap(bitmap);
         */

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = this.getContentResolver().query(contentURI,
                null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
