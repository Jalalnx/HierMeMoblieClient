package com.example.hairme;

import static com.example.hairme.Services.URLs.URL_REGISTER;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.hairme.Services.Mysingleton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("ALL")
public class singupActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    android.widget.Button Btn45, rgestrion;
    CircleImageView userImgae;
    android.widget.RadioGroup gender;
    TextInputEditText f_name ,l_name, phone, email, profession,Adress, passowrd;
    int bitmap_size = 60;
    public Uri mMediaUri;
    private Bitmap bitmap, decoded;
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int WRITE_EXTERNAL_STORAGE = 123;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) { return; }
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO && data != null && data.getData() != null) {
                try {
                    Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mMediaUri));
                    setToImageView(getResizedBitmap(bitmap, 512));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
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
        rgestrion =findViewById(R.id.reg);
        userImgae = findViewById(R.id.takePhoto);

        String[] type = new String[]{" مهندس", "طبيب ","عازف ","عامل حر","مصمم ","طيار","مبرمج","مدير تنفيذي","عامل"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, type);
        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.IDTYPE);
        editTextFilledExposedDropdown.setAdapter(adapter);


        rgestrion.setOnClickListener(view -> {
            String textusername = Objects.requireNonNull(f_name.getText()).toString();
            String textL_ame = Objects.requireNonNull(l_name.getText()).toString();
            String textPhonenumber = Objects.requireNonNull(phone.getText()).toString();
            String profession = editTextFilledExposedDropdown.getText().toString();
            String textEmailemail = Objects.requireNonNull(email.getText()).toString();
            String textAdress = Objects.requireNonNull(Adress.getText()).toString();
            String textPassword = Objects.requireNonNull(passowrd.getText()).toString();
            if (textusername.length() == 0 ||!textusername.matches("[a-zA-Z ]+")) {
                f_name.requestFocus();
                f_name.setError("FIELD CANNOT BE EMPTY");
            } else if (textL_ame.length() == 0 ||!textL_ame.matches("[a-zA-Z ]+")) {
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

                    newAccount(textusername, textL_ame, textPhonenumber, profession, textEmailemail, textAdress, textSelectedGorup, textPassword);

                }

            }

        });
        findViewById(R.id.takePhoto).setOnClickListener(view -> {

            if (mMediaUri == null) {
                Toast.makeText(this,
                        "There was a problem accessing your device's external storage.",
                        Toast.LENGTH_LONG).show();
            } else {
                mMediaUri = getOutputMediaFileUri();
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
            }
        });



    }


    private void newAccount(String first_name, String Last_name, String Phone, String profession, String email, String Adress, String gender, String Passcode) {

        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, URL_REGISTER, response -> {
                    if (response.equals("done")) {
                        Objects.requireNonNull(this.l_name.getText()).clear();
                        Objects.requireNonNull(this.f_name.getText()).clear();
                        Objects.requireNonNull(this.phone.getText()).clear();
                        Objects.requireNonNull(this.passowrd.getText()).clear();
                        Objects.requireNonNull(this.Adress.getText()).clear();
//                        this.userImgae.setImageResource(0);

                        android.widget.Toast.makeText(getApplicationContext(), response, android.widget.Toast.LENGTH_LONG).show();
                        startActivity(new android.content.Intent(this, LoginActivity.class));

                    } else {

                        android.widget.Toast.makeText(singupActivity.this, response, android.widget.Toast.LENGTH_LONG).show();
                    }
                }, error -> {
//                    hud.dismiss();
                    android.widget.Toast.makeText(singupActivity.this, error.toString(), android.widget.Toast.LENGTH_LONG).show();
                }) {
                    @Override
                    protected java.util.Map<String, String> getParams() {
                        java.util.HashMap<String, String> prime = new java.util.HashMap<>();
                        prime.put("f_name", first_name);
                        prime.put("l_name", Last_name);
                        prime.put("phone", Phone);
                        prime.put("Email", email);
                        prime.put("gender", gender);
                        prime.put("adress", Adress);
                        prime.put("profession", profession);
                        prime.put("password", Passcode);
                        prime.put("photo", getStringImage(decoded));
                        return prime;
                    }
                };
        request.setRetryPolicy(new com.android.volley.DefaultRetryPolicy
                (30000, com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Mysingleton.getInstance(singupActivity.this).addToReguestQueu(request);

    }
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
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

    public  Bitmap getResizedBitmap(Bitmap image, int maxSize) {
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

}

/*
* public void signup(){

            String URL = "http://192.168.137.1:8000/mobile/user/New_Acount";
//            JSONObject jsonBody = new JSONObject();
//
//            jsonBody.put("f_name", "abc@abc.com");
//            jsonBody.put("l_name", "jdvjsdvs");
//            jsonBody.put("phone", "872342");
//            jsonBody.put("Email", "sfdjd");
//            jsonBody.put("password", "dnfgkjgf");
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("f_name", "jghbh");
            params.put("l_name", "kbilkkljvblhj");
            params.put("phone","09976");
            params.put("Email", "holjhbklj@gmail.com");
            params.put("password", "gbiugb8iu8");

            JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URL,  new JSONObject(params), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "Response:  " + error, Toast.LENGTH_SHORT).show();
//
//                    onBackPressed();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    final Map<String, String> headers = new HashMap<>();
//                    headers.put("Accept", "application/json" );//put your token here
//                    headers.put("Content-Type", "application/json" );//put your token here
//                    headers.put("Connection", "keep-alive" );//put your token here
                    return headers;
                }
            };
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonOblect);


//        Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();

    }
* */