package com.cmhering.mymcpshelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends AppCompatActivity {

    Button sign_in_button;
    EditText password_input, username_input;
    TextView textView;

    static List<HttpCookie> cookies;
    private String password;
    private static String username;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sign_in_button = (Button)findViewById(R.id.email_sign_in_button);
        password_input = (EditText)findViewById(R.id.password);
        username_input = (EditText)findViewById(R.id.email);
        textView = (TextView)findViewById(R.id.textView);


        sign_in_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                username = username_input.getText().toString();
                user = username_input.getText().toString();
                password = password_input.getText().toString();
                attemptLogin();
            }
        });
    }

    protected void attemptLogin(){
        if(user.matches("")|| password.matches("")){
            textView.setText("**Missing Fields**");
            textView.setTextColor(Color.RED);
        }else {
            textView.setText("WORKING");
            textView.setTextColor(Color.BLACK);
            new loginAsync2().execute();
        }
    }

    public static List<HttpCookie> getCookies(){
        return cookies;
    }

    public static String getUsername(){
        return username;
    }

    private void launchGradeOverview() {
        Intent intent = new Intent(this, GradeOverview.class);
        startActivity(intent);
    }

    class loginAsync2 extends AsyncTask<Void, Void, Void>{

        protected Void doInBackground(Void... x){
            String data = "ldappassword=" + password + "&account=" + user + "&pw=" + password;
            try {
                CookieManager cm = new CookieManager();
                CookieHandler.setDefault(cm);
                cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
                URL url = new URL("https://portal.mcpsmd.org/guardian/home.html");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.addRequestProperty("Cookie", "currentSchool=427");
                conn.getOutputStream().write(data.getBytes());
                List<String> req_cookies = conn.getHeaderFields().get("Set-Cookie");
                System.out.println(cm.getCookieStore().getCookies());
                cookies = cm.getCookieStore().getCookies();
            }catch(MalformedURLException m) {
            }catch(IOException e){}
            return null;
        }
        protected void onPostExecute(Void arg) {
            boolean succeded = false;
            for (HttpCookie cookie : cookies) {
                System.out.println(cookie.getName() + cookie.getValue());
                if(cookie.getName().equals("psaid")){
                    succeded = true;
                    break;
                }
            }
            if(succeded){
                textView.setText("Connected!");
                textView.setTextColor(Color.GREEN);
                launchGradeOverview();
            }else{
                textView.setText("**Wrong Password or Username**");
                textView.setTextColor(Color.RED);
            }
        }
    }


    class loginAsync extends AsyncTask<Void, Void, Void>{

        protected Void doInBackground(Void... x){
            String data = "ldappassword=" + password + "&account=" + user + "&pw=" + password;
            CookieManager cookieManager = new CookieManager();
            CookieHandler.setDefault(cookieManager);

            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            System.out.println(cookieManager.getCookieStore().getCookies().size());
            try {
                URL url = new URL("https://portal.mcpsmd.org");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                System.out.println(cookieManager.getCookieStore().getCookies().size());
                if (cookieManager.getCookieStore().getCookies().size() > 0) {

                    List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();

                    if (cookies != null) {
                        for (HttpCookie cookie : cookies) {
                            conn.setRequestProperty("Cookie", cookie.getName() + "=" + cookie.getValue());
                        }
                    }
                }
                conn.getOutputStream().write(data.getBytes());
                cookies = cookieManager.getCookieStore().getCookies();
            }catch(MalformedURLException m) {
            }catch(IOException e){}
            return null;
        }
        protected void onPostExecute(Void arg) {
            boolean succeded = false;
            for (HttpCookie cookie : cookies) {
                System.out.println(cookie.getName() + cookie.getValue());
                if(cookie.getName().equals("psaid")){
                    succeded = true;
                    break;
                }
            }
            if(succeded){
                textView.setText("Connected!");
                textView.setTextColor(Color.GREEN);
                launchGradeOverview();
            }else{
                textView.setText("**Wrong Password or Username**");
                textView.setTextColor(Color.RED);
            }
        }
    }
}




