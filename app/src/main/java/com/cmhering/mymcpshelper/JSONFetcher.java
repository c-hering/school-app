package com.cmhering.mymcpshelper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Caleb on 9/29/2017.
 */
public class JSONFetcher {

    protected String getStudentID(String url){
        try {
            URL urlConn = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) urlConn.openConnection();
            String response = new String(readResponse(conn));

            Scanner sc = new Scanner(response);
            while(sc.hasNextLine()){
                String nl = sc.nextLine();
                if(nl.contains("root.guardianId")){
                    response = nl;
                }
            }
//            System.out.println("StudentID " + response);
            response = response.split("'")[1];
//            System.out.println("StudentID " + response);
            return response;
        }catch(MalformedURLException m){
        }catch(IOException e){}
        return null;
    }

    private byte[] readResponse(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        int contentLength = connection.getContentLength();
        //int contentLength = 47343;
        byte[] buffer;
        buffer = new byte[contentLength];
        int bufferSize = 1024;
        int bytesRemaining = contentLength;
        int loadedBytes;
        for (int i = 0; i < contentLength; i = i + loadedBytes) {
            int readCount =   bytesRemaining > bufferSize ? bufferSize : bytesRemaining;
            loadedBytes = inputStream.read(buffer, i , readCount);
            bytesRemaining = bytesRemaining - loadedBytes;
        }
        return buffer;
    }

    protected String getGradeOverviewJSON(String studentid, String schoolid, String urlPassed){
        String data = "?schoolid=" + schoolid + "&studentId=" + studentid;
//        System.out.println(data);
        urlPassed = urlPassed + data;
        String d =  fetcher(urlPassed);
//        System.out.println(d);
        return fetcher(urlPassed);
    }

    protected String fetcher(String urlPassed){
        BufferedReader br;
        StringBuilder result = new StringBuilder();
        String line = null;
        try {
            URL url = new URL(urlPassed);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine()) != null){
                result.append(line + "\n");
            }
//            System.out.println(result.toString());
            return result.toString();
        }catch(MalformedURLException m){
        }catch(IOException e){}
        return null;
    }

}
