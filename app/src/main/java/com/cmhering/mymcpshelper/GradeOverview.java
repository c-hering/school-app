package com.cmhering.mymcpshelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class GradeOverview extends AppCompatActivity {
    String username;
    String json;

    TextView class_status;
    TextView pd1_tv;
    TextView pd2_tv;
    TextView pd3_tv;
    TextView pd4_tv;
    TextView pd5_tv;
    TextView pd6_tv;
    TextView pd7_tv;
    TextView pd8_tv;

    Button pd1_btn;
    Button pd2_btn;
    Button pd3_btn;
    Button pd4_btn;
    Button pd5_btn;
    Button pd6_btn;
    Button pd7_btn;
    Button pd8_btn;

    Button[] pds_btn = new Button[8];
    TextView[] pds_tv = new TextView[8];
    getJSON mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_overview);

        pd1_tv = (TextView)findViewById(R.id.textView_pd1);
        pds_tv[0] = pd1_tv;
        pd2_tv = (TextView)findViewById(R.id.textView_pd2);
        pds_tv[1] = pd2_tv;
        pd3_tv = (TextView)findViewById(R.id.textView_pd3);
        pds_tv[2] = pd3_tv;
        pd4_tv = (TextView)findViewById(R.id.textView_pd4);
        pds_tv[3] = pd4_tv;
        pd5_tv = (TextView)findViewById(R.id.textView_pd5);
        pds_tv[4] = pd5_tv;
        pd6_tv = (TextView)findViewById(R.id.textView_pd6);
        pds_tv[5] = pd6_tv;
        pd7_tv = (TextView)findViewById(R.id.textView_pd7);
        pds_tv[6] = pd7_tv;
        pd8_tv = (TextView)findViewById(R.id.textView_pd8);
        pds_tv[7] = pd8_tv;



        pd1_btn = (Button)findViewById(R.id.pd1);
        pds_btn[0] = pd1_btn;
        pd2_btn = (Button)findViewById(R.id.pd2);
        pds_btn[1] = pd2_btn;
        pd3_btn = (Button)findViewById(R.id.pd3);
        pds_btn[2] = pd3_btn;
        pd4_btn = (Button)findViewById(R.id.pd4);
        pds_btn[3] = pd4_btn;
        pd5_btn = (Button)findViewById(R.id.pd5);
        pds_btn[4] = pd5_btn;
        pd6_btn = (Button)findViewById(R.id.pd6);
        pds_btn[5] = pd6_btn;
        pd7_btn = (Button)findViewById(R.id.pd7);
        pds_btn[6] = pd7_btn;
        pd8_btn = (Button)findViewById(R.id.pd8);
        pds_btn[7] = pd8_btn;


        username = LoginActivity.getUsername();
        mTask = new getJSON();
        mTask.execute();


        pd1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkGrade(pd1_btn)) {
                    JSONObject jsonObj;
                    JSONArray classArray;
                    try {
                        classArray = new JSONArray(json);
                        jsonObj = classArray.getJSONObject(0);
                        launchActivity(jsonObj.getString("sectionid"), jsonObj.getString("schoolid"), jsonObj.getString("termid"));
                    } catch (JSONException e) {
                    }
                }
            }
        });
        pd2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkGrade(pd2_btn)) {
                    JSONObject jsonObj;
                    JSONArray classArray;
                    try {
                        classArray = new JSONArray(json);
                        jsonObj = classArray.getJSONObject(1);
                        launchActivity(jsonObj.getString("sectionid"), jsonObj.getString("schoolid"), jsonObj.getString("termid"));
                    } catch (JSONException e) {
                    }
                }
            }
        });
        pd3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkGrade(pd3_btn)) {
                    JSONObject jsonObj;
                    JSONArray classArray;
                    try {
                        classArray = new JSONArray(json);
                        jsonObj = classArray.getJSONObject(2);
                        launchActivity(jsonObj.getString("sectionid"), jsonObj.getString("schoolid"), jsonObj.getString("termid"));
                    } catch (JSONException e) {
                    }
                }
            }
        });
        pd4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkGrade(pd4_btn)) {
                    JSONObject jsonObj;
                    JSONArray classArray;
                    try {
                        classArray = new JSONArray(json);
                        jsonObj = classArray.getJSONObject(3);
                        launchActivity(jsonObj.getString("sectionid"), jsonObj.getString("schoolid"), jsonObj.getString("termid"));
                    } catch (JSONException e) {
                    }
                }
            }
        });
        pd5_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkGrade(pd5_btn)) {
                    JSONObject jsonObj;
                    JSONArray classArray;
                    try {
                        classArray = new JSONArray(json);
                        jsonObj = classArray.getJSONObject(4);
                        launchActivity(jsonObj.getString("sectionid"), jsonObj.getString("schoolid"), jsonObj.getString("termid"));
                    } catch (JSONException e) {
                    }
                }
            }
        });
        pd6_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkGrade(pd6_btn)) {
                    JSONObject jsonObj;
                    JSONArray classArray;
                    try {
                        classArray = new JSONArray(json);
                        jsonObj = classArray.getJSONObject(5);
                        launchActivity(jsonObj.getString("sectionid"), jsonObj.getString("schoolid"), jsonObj.getString("termid"));
                    } catch (JSONException e) {
                    }
                }
            }
        });
        pd7_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkGrade(pd7_btn)) {
                    JSONObject jsonObj;
                    JSONArray classArray;
                    try {
                        classArray = new JSONArray(json);
                        jsonObj = classArray.getJSONObject(6);
                        launchActivity(jsonObj.getString("sectionid"), jsonObj.getString("schoolid"), jsonObj.getString("termid"));
                    } catch (JSONException e) {
                    }
                }
            }
        });
        pd8_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkGrade(pd8_btn)) {
                    JSONObject jsonObj;
                    JSONArray classArray;
                    try {
                        classArray = new JSONArray(json);
                        jsonObj = classArray.getJSONObject(7);
                        launchActivity(jsonObj.getString("sectionid"), jsonObj.getString("schoolid"), jsonObj.getString("termid"));
                    } catch (JSONException e) {
                    }
                }
            }
        });

    }

    public void onBackPressed(){
        mTask.cancel(true);
        Intent intent = new Intent(GradeOverview.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    protected boolean checkGrade(Button btn){
        if(btn.getText().equals("X")){
            return false;
        }
        return true;
    }

    protected void launchActivity(String courseid, String schoolid, String termid){
        Intent intent = new Intent(this, CourseLook.class);
        Bundle b = new Bundle();
        String[] args = {courseid, schoolid, termid};
        b.putStringArray("args" , args);
        intent.putExtras(b);
        startActivity(intent);
    }

    protected void setTextColor(Button btn){
     if(btn.getText().equals("A")){
         btn.setBackgroundColor(Color.GREEN);
     }else if(btn.getText().equals("B")){
         btn.setBackgroundColor(Color.BLUE);
     }else if(btn.getText().equals("C")){
         btn.setBackgroundColor(Color.YELLOW);
     }else{
         btn.setBackgroundColor(Color.RED);
     }
    }

    class getJSON extends AsyncTask<Void, Void, Void>{
        protected Void doInBackground(Void... args){
            List<HttpCookie> cookies = LoginActivity.getCookies();
            String schoolid = "427";
            for(HttpCookie cookie : cookies) {
                if (cookie.getName().equals("schoolid"))
                    schoolid = cookie.getValue();
            }
            JSONFetcher jsonFetcher = new JSONFetcher();
            String studentid = jsonFetcher.getStudentID("https://portal.mcpsmd.org/guardian/home.html#/termGrades");
            String url = "https://portal.mcpsmd.org/guardian/prefs/gradeByCourseSecondary.json";
            json = jsonFetcher.getGradeOverviewJSON(studentid, schoolid, url);
            return null;
        }

       protected void onPostExecute(Void v){
          if(json != null){
              Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
              try {
                  JSONArray classArray = new JSONArray(json);
                  int counter = 0;
                  JSONObject jsonObj;
                  for(Button btn : pds_btn){
                      jsonObj = classArray.getJSONObject(counter);
                      String grade = jsonObj.getString("overallgrade");
                      if(!(grade.equals(""))){
                          btn.setText(grade);
                      }
                      setTextColor(btn);
                      counter++;
                  }
                  counter = 0;
                  for(TextView tv : pds_tv){
                      jsonObj = classArray.getJSONObject(counter);
                      String courseName = jsonObj.getString("courseName");
                      if(!(courseName.equals(""))){
                          tv.setText(courseName);
                      }
                      counter++;
                  }
              }catch(JSONException e){}

          }else{
              Toast.makeText(getApplicationContext(), "Error Fetching Grades", Toast.LENGTH_SHORT);

          }
        }

    }

}
