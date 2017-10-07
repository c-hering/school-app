package com.cmhering.mymcpshelper;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseLook extends AppCompatActivity {

    String[] urlArgs;
    ArrayList<String> categories = new ArrayList<>();
//    EditText[] assignments;
    ArrayList<EditText> assignments = new ArrayList<>();

    String courseName;
    String before_change_state_grade;

    TextView coursename_tv;

    LinearLayout lm;
    LinearLayout assignmentsLm;

    JSONArray categoryDetailJSON;
    JSONArray assignmentDetailJSON;
    JSONObject courseDetailJSON;

    getCourseInfo mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_look);

        coursename_tv = (TextView)findViewById(R.id.textView_coursename);
        lm = (LinearLayout)findViewById(R.id.vertical_layout);
        assignmentsLm = (LinearLayout)findViewById(R.id.vertical_layout);

        urlArgs = getIntent().getExtras().getStringArray("args");
        mTask = null;
        mTask = new getCourseInfo();
        mTask.execute();
    }

    public void onBackPressed(){
        mTask.cancel(true);
        Intent intent = new Intent(CourseLook.this, GradeOverview.class);
        startActivity(intent);
        finish();
    }


    protected void parseAssignments(JSONArray jsonArray){
        try {
            Space spacer = new Space(this);
            spacer.setLayoutParams(new LinearLayout.LayoutParams(100,100));
            lm.addView(spacer);
            TextView assignmentsTitle = new TextView(this);
            assignmentsTitle.setText("Assignments");
            assignmentsTitle.setGravity(Gravity.CENTER_HORIZONTAL);
            assignmentsTitle.setTextSize(Utils.pxFromDp(6, CourseLook.this));
            lm.addView(assignmentsTitle);
            Space spacer_bot = new Space(this);
            spacer_bot.setLayoutParams(new LinearLayout.LayoutParams(100,25));
            lm.addView(spacer_bot);

            for (int i = 0; i < jsonArray.length() - 1; i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                final EditText pts_tv = new EditText(this);
                TextView grade_tv = new TextView(this);

                String assignment_type = (jsonObj.getString("AssignmentType").split("\\(")[0]).trim();
//                String percent_grade = Double.toString(Utils.round(Double.parseDouble(jsonObj.getString("Points")) / Double.parseDouble(jsonObj.getString("Possible"))));
                String assignment_name = jsonObj.getString("Description");


                LinearLayout ll2 = new LinearLayout(this);
                ll2.setOrientation(LinearLayout.HORIZONTAL);

                TextView assignmentTitle = new TextView(this);
                assignmentTitle.setText(jsonObj.getString("Description"));
                assignmentTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                assignmentTitle.setTextSize(Utils.pxFromDp(5, CourseLook.this));
                assignmentTitle.setMaxLines(1);
                assignmentsTitle.setMinWidth(350);
                assignmentsTitle.setWidth(350);
                assignmentTitle.setMaxWidth(350);
                ll2.addView(assignmentTitle);

                Spinner category = new Spinner(this);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categories);
                category.setLayoutParams(new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT));
                category.setAdapter(spinnerArrayAdapter);
                category.setSelection(Utils.getIndex(categories, (jsonObj.getString("AssignmentType").split("\\(")[0]).trim()));
                category.setTag(grade_tv);
                ll2.addView(category);

                pts_tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                pts_tv.setText(jsonObj.getString("Points") + "/" + jsonObj.getString("Possible"));
                pts_tv.setSingleLine(true);
                pts_tv.setMaxLines(1);
                pts_tv.setLines(1);
                pts_tv.setTag(category);
                pts_tv.setImeOptions(EditorInfo.IME_ACTION_DONE);
                pts_tv.setGravity(Gravity.CENTER);
                System.out.println(pts_tv);
                pts_tv.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        updateGrades();
                    }
                });
                assignments.add(pts_tv);
//                assignments[i] = pts_tv;
                ll2.addView(pts_tv);

                grade_tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                grade_tv.setText(jsonObj.getString("Grade"));
                grade_tv.setTextSize(Utils.pxFromDp(5, CourseLook.this));
                grade_tv.setTypeface(null, Typeface.BOLD);
                grade_tv.setGravity(Gravity.RIGHT);
                Utils.setColor(grade_tv);
                ll2.addView(grade_tv);

                assignmentsLm.addView(ll2);
            }
        }catch(JSONException e){}
    }

    protected void parseCourseDetail(JSONObject jsonArray){
        try {
            courseName = jsonArray.getString("courseName");
            coursename_tv.setText(courseName);
        }catch(JSONException e){}
    }

    protected void parseCategoryDetail(JSONArray jsonArray){
        try{
            for(int i = 0; i < jsonArray.length()-1; i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                //Get Nessecary Data
                float pts_possible = Float.parseFloat(jsonObj.getString("PointsPossible"));
                float pts_earned = Float.parseFloat(jsonObj.getString("PointsEarned"));
                double percent = Utils.round(pts_earned/pts_possible) * 100;

                //Create Linear Layout for category
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                //Create TextView Description
                categories.add(jsonObj.getString("Description"));
                TextView category_title = new TextView(this);
                category_title.setText(jsonObj.getString("Description"));
                category_title.setPadding(10, 0, 10, 0);
                category_title.setWidth((int)Utils.pxFromDp(200, CourseLook.this));
                ll.addView(category_title);

                //Create TextView Percent
                TextView percent_tv = new TextView(this);
                percent_tv.setText(Double.toString(percent));
                percent_tv.setPadding(10, 0, 10, 0);
                percent_tv.setTypeface(null, Typeface.BOLD);
                percent_tv.setWidth((int)Utils.pxFromDp(80, CourseLook.this));
                ll.addView(percent_tv);

                //Create TextView Grade
                TextView grade_tv = new TextView(this);
                if(pts_possible != 0){
                    grade_tv.setText(Utils.getGrade(percent));
                }else{
                    grade_tv.setText("X");
                }
                grade_tv.setPadding(10,0,10,0);
                grade_tv.setTypeface(null, Typeface.BOLD);

                ll.addView(grade_tv);

                lm.addView(ll);
            }

        }catch(JSONException e){}
    }

    protected void updateGrades(){

        for(int i = 0; i < assignments.size(); i++) {
            if ((assignments.get(i).getText().toString()).contains("/")) {
                System.out.println(assignments.get(i).getText().toString());
                String pts = (assignments.get(i).getText()).toString().split("/")[0];
                if(pts.equals("")){
                    pts = (assignments.get(i).getText()).toString().split("/")[1];
                }
                String overall = (assignments.get(i).getText()).toString().split("/")[1];
                Spinner spinner = (Spinner) assignments.get(i).getTag();
                TextView tv = (TextView) spinner.getTag();
                System.out.println(tv.getText());

                String category = spinner.getSelectedItem().toString();
                double weight = 0;

                for (int x = 0; x < categoryDetailJSON.length() - 1; x++) {
                    try {
                        if (categoryDetailJSON.getJSONObject(x).getString("Description").equals(category)) {
                            weight = Double.parseDouble(categoryDetailJSON.getJSONObject(x).getString("Weight"));
                        }
                    } catch (JSONException e) {
                    }
                }
                System.out.println(pts + " " + overall);
                double local_percent = Utils.round((Double.parseDouble(pts) / Double.parseDouble(overall)) * 100);
                double percent = Utils.round(local_percent * (weight / 100));
                tv.setText(Utils.getGrade(local_percent));
                Utils.setColor(tv);

            }
        }
    }

    class getCourseInfo extends AsyncTask<Void, Void, Void>{

        protected Void doInBackground(Void... args){
            JSONFetcher jsonFetcher = new JSONFetcher();
            String assignmentDetailURL = "https://portal.mcpsmd.org/guardian/prefs/assignmentGrade_AssignmentDetail.json?secid="
                    + urlArgs[0] + "&student_number="
                    + LoginActivity.getUsername() + "&schoolid="
                    + urlArgs[1] + "&termid="
                    + urlArgs[2];
            String courseDetailURL = "https://portal.mcpsmd.org/guardian/prefs/assignmentGrade_CourseDetail.json?secid="
                    + urlArgs[0] + "&student_number="
                    + LoginActivity.getUsername() + "&schoolid="
                    + urlArgs[1] + "&termid="
                    + urlArgs[2];
            String categoryDetailURL = "https://portal.mcpsmd.org/guardian/prefs/assignmentGrade_CategoryDetail.json?secid="
                    + urlArgs[0] + "&student_number="
                    + LoginActivity.getUsername() + "&schoolid="
                    + urlArgs[1] + "&termid="
                    + urlArgs[2];
            try{
                assignmentDetailJSON = new JSONArray(jsonFetcher.fetcher(assignmentDetailURL));
                courseDetailJSON = new JSONObject(jsonFetcher.fetcher(courseDetailURL));
                categoryDetailJSON = new JSONArray(jsonFetcher.fetcher(categoryDetailURL));
            }catch(JSONException e){}

            return null;
        }

        protected void onPostExecute(Void v){
            parseCourseDetail(courseDetailJSON);
            parseCategoryDetail(categoryDetailJSON);
            parseAssignments(assignmentDetailJSON);
        }

    }



}
