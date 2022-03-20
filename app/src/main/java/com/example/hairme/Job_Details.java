package com.example.hairme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.hairme.Models.UserModle;
import com.example.hairme.Services.FilePath;
import com.example.hairme.Services.Mysingleton;
import com.example.hairme.Services.SharedPrefmanager;
import com.example.hairme.Services.SingleUploadBroadcastReceiver;
import com.example.hairme.Services.URLs;
import com.example.hairme.Services.VolleyMultipartRequest;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("ALL")
public class Job_Details extends AppCompatActivity  implements SingleUploadBroadcastReceiver.Delegate{
    ImageView wallpapper;
    ShapeableImageView logo;
    TextView roal, com_name, UploadeAt, dead_line, years_of_experience, contry_city, salary_range, vacancies, Employment_status, employment_type, education_level, career_level, Gender, industry, des;

    private Button buttonChoose;
    private Button buttonUpload;
//    private EditText editText;
    //Pdf request code
    private int PICK_PDF_REQUEST = 1;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Uri to store the image uri
    private Uri filePath;
    private static final String TAG = "AndroidUploadService";

    private final SingleUploadBroadcastReceiver uploadReceiver =
            new SingleUploadBroadcastReceiver();

    @Override
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        uploadReceiver.unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        //hooks
        wallpapper = findViewById(R.id.wallpaper);
        logo = findViewById(R.id.imageView5);
        roal = findViewById(R.id.job_role);
        com_name = findViewById(R.id.instituesName);
        UploadeAt = findViewById(R.id.UploadeAt);
        dead_line = findViewById(R.id.dead_line);
        years_of_experience = findViewById(R.id.years_of_experience);
        contry_city = findViewById(R.id.contry_city);
        salary_range = findViewById(R.id.salary_range);
        vacancies = findViewById(R.id.vacancies);
        Employment_status = findViewById(R.id.Employment_status);
        employment_type = findViewById(R.id.employment_type);
        education_level = findViewById(R.id.education_level);
        career_level = findViewById(R.id.career_level);
        Gender = findViewById(R.id.Gender);
        industry = findViewById(R.id.industry);
        des = findViewById(R.id.desdes);

        buttonChoose = (Button) findViewById(R.id.pickFile);
        buttonUpload = (Button) findViewById(R.id.Apply_job);

        // getting the bundle back from the android
        Bundle bundle = getIntent().getExtras();

        try {

            Picasso.get().load(bundle.getString("wallpaper", "@tools:sample/avatars")).fit().centerInside().into(wallpapper);
            Picasso.get().load(bundle.getString("imageCompny", "@tools:sample/avatars")).fit().centerInside().into(logo);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "bad network", Toast.LENGTH_SHORT).show();
        }

        roal.setText(bundle.getString("job_role", "غير محدد "));
        com_name.setText(bundle.getString("instituesName", "غير محدد "));
        UploadeAt.setText(bundle.getString("UploadeAt", "غير محدد   :"));
        dead_line.setText(bundle.getString("dead_line", "غير محدد  :"));
        years_of_experience.setText(new StringBuilder().append(" سنين الخبره  :   ").append(bundle.getString("years_of_experience", "غير محدد")).toString());
        contry_city.setText(new StringBuilder().append(" الموقع  : ").append(bundle.getString("contry_city", "غير محدد")).toString());
        salary_range.setText(new StringBuilder().append("متوسط المرتب   :  ").append(bundle.getString("salary_range", "غير محدد")).toString());
        vacancies.setText(new StringBuilder().append("الخانات  : ").append(bundle.getString("vacancies", "غير محدد")).toString());
        Employment_status.setText(new StringBuilder().append("طبيعة العمل  :   ").append(bundle.getString("Employment_status", "غير محدد")).toString());
        employment_type.setText(new StringBuilder().append("نوع العمل   :  ").append(bundle.getString("employment_type", "غير محدد")).toString());
        education_level.setText(new StringBuilder().append(" المؤهل العلمي  : ").append(bundle.getString("education_level", "غير محدد")).toString());
        career_level.setText(new StringBuilder().append(" المستوي  :").append(bundle.getString("career_level", "غير محدد")).toString());
        Gender.setText(new StringBuilder().append(" الجنس  :  ").append(bundle.getString("Gender", "غير محدد")).toString());
        industry.setText(new StringBuilder().append(" مجال العمل  :").append(bundle.getString("industry", "غير محدد")).toString());
        des.setText(bundle.getString("joo_description", "غير محدد  :"));

        // uploadMultipart(bundle.getString("id",null));
        ///set the back boutton
        findViewById(R.id.BackBotton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               startActivity( new Intent(getApplicationContext(),SandBox.class));
                Job_Details.super.onBackPressed();
            }
        });
//Setting clicklistener
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModle user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
                saveProfileAccount(user.getF_name(), String.valueOf(user.getId()),bundle.getString("id", "غير محدد "), getApplicationContext());
            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

//        requestStoragePermission();


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Job_Details.super.onBackPressed();
                    }
                }).create().show();
    }

//    https://www.simplifiedcoding.net/upload-pdf-file-server-android/

//    public void uploadMultipart(String jobid) {
//        //getting name for the image
////        String name = editText.getText().toString().trim();
//        UserModle user = SharedPrefmanager.getInstance(getApplicationContext()).getUser();
//        //getting the actual path of the image
//        String path = FilePath.getPath(this, filePath);
//
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("jobId", "1");
//        params.put("userId", "1");
//
//        JSONObject meta = new JSONObject(params);
//
//
//        if (path == null) {
//
//            Toast.makeText(this, "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
//        } else {
//            //Uploading code
//            try {
//                String uploadId = UUID.randomUUID().toString();
//
//                //Creating a multi part request
//                new MultipartUploadRequest(this, uploadId, URLs.URL_Applay)
//                        .addFileToUpload(path, "file") //Adding file
//                        .addParameter("jobId",jobid) //Adding text parameter to the request
//                        .setNotificationConfig(new UploadNotificationConfig())
//                        .setMaxRetries(2)
//                        .startUpload(); //Starting the upload
//
//            } catch (Exception exc) {
//                Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    private void saveProfileAccount(String name, String userid, String job_id, Context context) {
        com.kaopiz.kprogresshud.KProgressHUD prog = com.kaopiz.kprogresshud.KProgressHUD.create(this)
                .setStyle(com.kaopiz.kprogresshud.KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("الرجاء الانتظار")
                .setDetailsLabel("يتم معالجة طلبك ")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setSize(200, 200)
                .show();

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,  URLs.URL_Applay, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                prog.dismiss();
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");
                    String message = result.getString("message");

                    if (status.equals(200)) {
                        Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();

                        // tell everybody you have succed upload image and post strings
                        Log.i("Messsage", message);
                    } else {
                        Log.i("Unexpected", message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("api_token", "gh659gjhvdyudo973823tt9gvjf7i6ric75r76");
                params.put("jobId", job_id);
                params.put("userId", userid);


                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
               final String path =FilePath.getPath(context, filePath);
                Map<String, DataPart> params = new HashMap<>();
                Path pdfFilePath = Paths.get(path); //File path
                try {
                    byte[] pdfByteArray = Files.readAllBytes(pdfFilePath );
                    params.put("file",new DataPart(name,pdfByteArray,"application/pdf"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView

                return params;
            }
        };
        Mysingleton.getInstance(getBaseContext()).addToReguestQueu(multipartRequest);

//        Mysingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onProgress(int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, String.valueOf(1))
                .setSmallIcon(R.drawable.applictios)
                .setContentTitle("تقديم الي وظيفه")
                .setContentText("يتم رفع بياناتك ومرفقاتك")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {
        //your implementation
    }

    @Override
    public void onError(Exception exception) {
        //your implementation
    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {
        //your implementation
    }

    @Override
    public void onCancelled() {
        //your implementation
    }
}
