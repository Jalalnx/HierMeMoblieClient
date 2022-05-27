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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hairme.Fragments.HomeFragment;
import com.example.hairme.Models.Job;
import com.example.hairme.Models.UserModle;
import com.example.hairme.Services.FilePath;
import com.example.hairme.Services.Mysingleton;
import com.example.hairme.Services.SharedPrefmanager;
import com.example.hairme.Services.SingleUploadBroadcastReceiver;
import com.example.hairme.Services.TimeAgo2;
import com.example.hairme.Services.URLs;
import com.example.hairme.Services.VolleyMultipartRequest;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
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
public class Job_Details extends AppCompatActivity{
    ImageView wallpapper;
    ShapeableImageView logo;
    TextView roal, com_name, UploadeAt, dead_line, years_of_experience, contry_city, salary_range, vacancies, Employment_status, employment_type, education_level, career_level, Gender, industry, des;

    private Button apply;
    String meatuser,meatinst,meatjob;
//    private EditText editText;
    //Pdf request code
    private int PICK_PDF_REQUEST = 1;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Uri to store the image uri
    private Uri filePath;



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

//        buttonChoose = (Button) findViewById(R.id.pickFile);
        apply = (Button) findViewById(R.id.Apply_job);

        // getting the bundle back from the android
        Bundle bundle = getIntent().getExtras();
        UserModle user = SharedPrefmanager.getInstance(Job_Details.this).getUser();

        meatuser= String.valueOf(user.getId());
        meatinst=bundle.getString("instituesid", "غير محدد ");
        meatjob=bundle.getString("id", "غير محدد ");

        try {

            Picasso.get().load(bundle.getString("wallpaper", "@tools:sample/avatars")).fit().centerInside().into(wallpapper);
            Picasso.get().load(bundle.getString("imageCompny", "@tools:sample/avatars")).fit().centerInside().into(logo);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "bad network", Toast.LENGTH_SHORT).show();
        }

        roal.setText(bundle.getString("job_role", "غير محدد "));
        com_name.setText(bundle.getString("instituesName", "غير محدد "));


        //get time since job inserted
        String time2 = bundle.getString("createdAt", "غير محدد   :");
        TimeAgo2 timeAgo = new TimeAgo2();
        String createdAt = timeAgo.covertTimeToText(time2);
        UploadeAt.setText(  createdAt );



        //get data of closing
        String time = bundle.getString("dead_line", "غير محدد  :");
        TimeAgo2 timeAgo2 = new TimeAgo2();
        String MyFinalValue = timeAgo2.covertTimeToAfter(time);
        dead_line.setText( MyFinalValue);



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

        ///set the back boutton
        findViewById(R.id.BackBotton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               startActivity( new Intent(getApplicationContext(),SandBox.class));
                Job_Details.super.onBackPressed();
            }
        });
//Setting clicklistener

        //chang to apply
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                applayforJobs(meatuser,meatinst,meatjob);
            }
        });

//        requestStoragePermission();


    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("هل انت متأكد في رغبتك في الخروج ؟")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Job_Details.super.onBackPressed();
                    }
                }).create().show();
    }



    private void applayforJobs(String user, String compny, String job_id) {
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
                params.put("userId", user);
                params.put("jobId", job_id);
                params.put("instituteId", compny);

        JsonObjectRequest jsonOblect = new JsonObjectRequest(Request.Method.POST, URLs.URL_Applay , new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                prog.dismiss();

                try {
                    if (response.getBoolean("error")) {
                        ///replace tosat with dilog
                        Toast.makeText(getApplicationContext(), response.getString("masseg"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Job_Details.this,
                                response.getString("masseg"),
                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Job_Details.this, SandBox.class);
                        startActivity(intent);

                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();
                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(Job_Details.this,
                            "ServerError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Job_Details.this,
                            "AuthFailureError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Job_Details.this,
                            "ParseError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Job_Details.this,
                            "NoConnectionError!",
                            Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(Job_Details.this,
                            "Oops. Timeout error!",
                            Toast.LENGTH_LONG).show();
                }


                prog.dismiss();

                final AlertDialog dailog = new AlertDialog.Builder(Job_Details.this)
                        .setTitle("Oops Check you Internet connection")
                        .setMessage(error.toString())
                        .setPositiveButton("Reload", null)
                        .setNegativeButton("Cancel", null)
                        .show();
                Button Retry = dailog.getButton(AlertDialog.BUTTON_POSITIVE);
                Retry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        applayforJobs(user,compny,job_id);
                        dailog.dismiss();
                    }
                });
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

        Mysingleton.getInstance(this).addToReguestQueu(jsonOblect);



    }


}
