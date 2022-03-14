package com.example.hairme;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hairme.Services.URLs;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("ALL")
public class singupActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    AppCompatImageButton Btn45, rgestrion;
    ImageView userImgae;
    android.widget.RadioGroup gender;
    TextInputEditText f_name, l_name, phone, email, profession, Adress, passowrd;
    int bitmap_size = 60;
    public Uri mMediaUri,path;
    private Bitmap bitmap, decoded;
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int WRITE_EXTERNAL_STORAGE = 123;
    String imageString ;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                path =imageUri;
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                userImgae.setImageBitmap(selectedImage);
//                encode(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(singupActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(singupActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
    }


    public String encode(Uri path) throws FileNotFoundException {
        //encode image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final InputStream imageStream = getContentResolver().openInputStream(path);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getPermission();
        f_name = findViewById(R.id.f_name);
        l_name = findViewById(R.id.l_name);
        phone = findViewById(R.id.phon_namber);
        email = findViewById(R.id.email);
        Adress = findViewById(R.id.adress);
        gender = findViewById(R.id.group);
        rgestrion = findViewById(R.id.reg);
        passowrd = findViewById(R.id.password);
        userImgae = findViewById(R.id.takePhoto);

        String[] type = new String[]{" مهندس", "طبيب ", "عازف ", "عامل حر", "مصمم ", "طيار", "مبرمج", "مدير تنفيذي", "عامل"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, type);
        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.IDTYPE);
        editTextFilledExposedDropdown.setAdapter(adapter);


        rgestrion.setOnClickListener(view -> {
            String textusername = f_name.getText().toString().trim();
            String textL_ame = l_name.getText().toString().trim();
            String textPhonenumber =phone.getText().toString().trim();
            String profession = editTextFilledExposedDropdown.getText().toString().trim();
            String textEmailemail = email.getText().toString().trim();
            String textAdress = Adress.getText().toString().trim();
            String textPassword = passowrd.getText().toString().trim();
            if (textusername.length() == 0 || !textusername.matches("[a-zA-Z ]+")) {
                f_name.requestFocus();
                f_name.setError("FIELD CANNOT BE EMPTY");
            } else if (textL_ame.length() == 0 || !textL_ame.matches("[a-zA-Z ]+")) {
                l_name.requestFocus();
                l_name.setError("ENTER ONLY ALPHABETICAL CHARACTER");
            } else if (textPhonenumber.length() == 0) {
                phone.requestFocus();
                phone.setError("FIELD CANNOT BE EMPTY");
            } else if (profession.length() == 0) {
                editTextFilledExposedDropdown.requestFocus();
                editTextFilledExposedDropdown.setError("FIELD CANNOT BE EMPTY");
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(textEmailemail).matches()) {
                email.setError("Enter a valid email");
                email.requestFocus();

            } else if (textAdress.length() == 0) {
                Adress.requestFocus();
                Adress.setError("FIELD CANNOT BE EMPTY");
            } else if (textPassword.length() == 0) {
                passowrd.requestFocus();
                passowrd.setError("FIELD CANNOT BE EMPTY");
            } else {
                int gender_ID = gender.getCheckedRadioButtonId();
                android.widget.RadioButton SelectedGender = findViewById(gender_ID);
                if (SelectedGender == null) {
                    android.widget.Toast.makeText(singupActivity.this, "Select you Gender", android.widget.Toast.LENGTH_LONG).show();
                } else {
                    String textSelectedGorup = SelectedGender.getText().toString();

                    try {
                        newAccount(textusername, textL_ame, textPhonenumber, profession, textEmailemail, textAdress, textSelectedGorup, textPassword);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }

            }

        });
        findViewById(R.id.takePhoto).setOnClickListener(view -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, REQUEST_TAKE_PHOTO);
//            }
        });


    }


    private Uri getOutputMediaFileUri() {
        // check for external storage
        if (true) {
            // get the URI

            // 1. Get the external storage directory
            File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            // 2. Create a unique file name
            String fileName;
            String fileType;
            @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            fileName = "IMG_" + timeStamp;
            fileType = ".jpg";


            // 3. Create the file
            File mediaFile;
            try {
                mediaFile = File.createTempFile(fileName, fileType, mediaStorageDir);
                Log.i(TAG, "File: " + Uri.fromFile(mediaFile));

                // 4. Return the file's URI
                return Uri.fromFile(mediaFile);
            } catch (IOException e) {
                Log.e(TAG, "Error creating file: " +
                        mediaStorageDir.getAbsolutePath() + fileName + fileType);
            }
        } else {

            final androidx.appcompat.app.AlertDialog.Builder dailog = new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Storage")
                    .setMessage("You don't have enough space in you device :(")
                    .setCancelable(true);

            dailog.show();
            //   Toast.makeText(this,"You don't have enough space in you device :(",Toast.LENGTH_LONG).show();
        }

        // something went wrong
        return null;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        Toast.makeText(this, "2", Toast.LENGTH_LONG).show();
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void setToImageView(Bitmap bmp) {
        Toast.makeText(this, "3", Toast.LENGTH_LONG).show();

        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        userImgae.setImageBitmap(decoded);
    }


    private void newAccount(String first_name, String Last_name, String Phone, String profession, String email, String Adress, String gender, String Passcode) throws FileNotFoundException {

        com.kaopiz.kprogresshud.KProgressHUD prog = com.kaopiz.kprogresshud.KProgressHUD.create(this)
                .setStyle(com.kaopiz.kprogresshud.KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("الرجاء الانتظار")
                .setDetailsLabel("يتم معالجة طلبك ")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setSize(200, 200)
                .show();
                String URL = "http://192.168.137.1:8000/mobile/user/New_Acount";
                HashMap<String, String> params = new HashMap<String, String>();
            params.put("f_name", first_name);
            params.put("l_name", Last_name);
            params.put("phone", Phone);
            params.put("Email", email);
            params.put("gender", gender);
            params.put("adress", Adress);
            params.put("profession", profession);
            params.put("password", Passcode);
            params.put("photo",encode(path));

        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URLs.URL_REGISTER, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    prog.dismiss();
                    if (response.getBoolean("error"))
                        ///replace tosat with dilog
                        Toast.makeText(getApplicationContext(), "Response:  " + response.getString("message"), Toast.LENGTH_SHORT).show();
                    else {


                        JSONObject jsonArry = response.getJSONObject("user");
                        Toast.makeText(getApplicationContext(), "Response:  " + jsonArry, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Response:  " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    onBackPressed();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json" );//put your token here
                    headers.put("Content-Type", "application/json" );//put your token here
                    headers.put("Connection", "keep-alive" );//put your token here
                return headers;
            }
        };
//        Mysingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonOblect);
    }

}