package com.example.hairme.Services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.hairme.LoginActivity;
import com.example.hairme.Models.UserModle;
import com.google.gson.annotations.SerializedName;


public class SharedPrefmanager {
    private static SharedPrefmanager mInstance;
    private static Context mCtx;
    private static SharedPreferences pref;
    private static  SharedPreferences.Editor editor;
    private static  Context _context;


    private  static final String SHARED_PREF_NAME = "user-Data-Storg";

    private static final String  KEY_ID = "keyid";
    private static final String KEY_l_name = "ke_l_yname";
    private static final String KEY_f_name = "ke_f_yname";
    private static final String KEY_phone = "keyphone";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_adress = "key_adress";
    private static final String KEY_gender = "keygender";
    private static final String Key_photo = "keyphoto";
    private static final String KEY_profession = "KEY_profession";
    private static final String KEY_education_level = "KEY_education_level";
    private static final String KEY_password = "KEY_password";

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "androidhive-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public SharedPrefmanager(Context context) {
        this._context = context;
        mCtx = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public static synchronized SharedPrefmanager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefmanager(context);
        }
//        if (pref == null) {
//            pref =  new SharedPrefmanager(context);
//        }
        return  mInstance;
    }


    //this method will store the user data in shared preferences
    public void userLogin(UserModle user) {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_l_name, user.getF_name());
        editor.putString(KEY_f_name, user.getL_name());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_gender, user.getGender());
        editor.putString(KEY_phone, user.getPhone_Number());
        editor.putString(KEY_adress, user.getAdress());
        editor.putString(Key_photo, user.getPhoto());
        editor.putString(KEY_profession, user.getProfession());
        editor.putString(KEY_education_level, user.getEducation_level());
        editor.apply();



    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    //this method will give the logged in user
    public UserModle getUser() {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return new UserModle(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_f_name, null),
                sharedPreferences.getString(KEY_l_name, null),
                sharedPreferences.getString(KEY_phone, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_adress, null),
                sharedPreferences.getString(KEY_gender, null),
                sharedPreferences.getString(Key_photo, null),
                sharedPreferences.getString(KEY_profession, null),
                sharedPreferences.getString(KEY_education_level, null)


        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = _context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
       /* Intent i = new Intent((), login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);*/
        _context.startActivity(new Intent(_context, LoginActivity.class));
    }
}
