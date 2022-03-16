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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hairme.Models.UserModle;
import com.example.hairme.Services.Mysingleton;
import com.example.hairme.Services.SharedPrefmanager;
import com.example.hairme.Services.URLs;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

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
//        //encode image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final InputStream imageStream = getContentResolver().openInputStream(path);
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);

//        try {
//            imageString =  ImageBase64.with(getApplicationContext())
//                    .requestSize(128, 128) // default size
//                    .encodeFile(path);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

//        //decode base64 string to image
//        imageBytes = Base64.decode(imageString, Base64.DEFAULT);
//        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        image.setImageBitmap(decodedImage);
//        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
//        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
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

        String[] education_level = new String[]{" لايوجد", "اعدادي ", "ثانوي عام ", "بكلارويس", "ماستر ", "دكتوراء", "باحث"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, education_level);
        AutoCompleteTextView education = findViewById(R.id.education_level);
        education.setAdapter(adapter2);


        rgestrion.setOnClickListener(view -> {
            String textusername = Objects.requireNonNull(f_name.getText()).toString().trim();
            String textL_ame = Objects.requireNonNull(l_name.getText()).toString().trim();
            String textPhonenumber = Objects.requireNonNull(phone.getText()).toString().trim();
            String profession = editTextFilledExposedDropdown.getText().toString().trim();
            String texteducation = education.getText().toString().trim();
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
                        newAccount(textusername, textL_ame, textPhonenumber, profession,texteducation, textEmailemail, textAdress, textSelectedGorup, textPassword);
                    } catch (Exception e) {
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


    private void newAccount(String first_name, String Last_name, String Phone, String profession,String education, String email, String Adress, String gender, String Passcode) {

        com.kaopiz.kprogresshud.KProgressHUD prog = com.kaopiz.kprogresshud.KProgressHUD.create(this)
                .setStyle(com.kaopiz.kprogresshud.KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("الرجاء الانتظار")
                .setDetailsLabel("يتم معالجة طلبك ")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setSize(200, 200)
                .show();

                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("f_name", first_name);
                    params.put("l_name", Last_name);
                    params.put("phone", Phone);
                    params.put("Email", email);
                    params.put("gender", gender);
                    params.put("adress", Adress);
                    params.put("profession", profession);
                    params.put("education_level", education);
                    params.put("password", Passcode);
        try {
            params.put("photo", "data:image/webp;base64,"+encode(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URLs.URL_REGISTER, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                prog.dismiss();
                try {
                    if (response.getBoolean("error")) {
                        ///replace tosat with dilog
                        Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                    } else {

//                        Toast.makeText(getApplicationContext(), "Response:  " + response.getJSONObject("Newuser"), Toast.LENGTH_SHORT).show();
//
//                        JSONObject jsonArry = response.getJSONObject("Newuser");
//                        Gson gson = new Gson();
//                        UserModle userModle = gson.fromJson(jsonArry.toString(), UserModle.class);
//                        //storing the user in shared preferences
//                        SharedPrefmanager.getInstance(getApplicationContext()).userLogin(userModle);
                        Intent intent = new Intent(singupActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }
                } catch (JSONException e) {
                    prog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(),
                            "ServerError!",
                            Toast.LENGTH_LONG).show();

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(),
                            "AuthFailureError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(),
                            "ParseError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),
                            "NoConnectionError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(),
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }

//                Toast.makeText(getApplicationContext(), "Response:  " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    onBackPressed();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                final Map<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json" );//put your token here
//                    headers.put("Content-Type", "application/json" );//put your token here
//                    headers.put("Connection", "keep-alive" );//put your token here
                return headers;
            }
        };

        jsonOblect.setRetryPolicy(new com.android.volley.DefaultRetryPolicy
                (30000, com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Mysingleton.getInstance(getApplicationContext()).addToReguestQueu(jsonOblect);
    }

}