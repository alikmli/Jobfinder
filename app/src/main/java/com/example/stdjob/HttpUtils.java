package com.example.stdjob;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    public static final String STUDENT_URI="https://sdkandroid.000webhostapp.com/login.php";
    public static final String EMP_URI="https://sdkandroid.000webhostapp.com/emp.php";
    public static final String JOB_ALL_URI="https://sdkandroid.000webhostapp.com/job.php?key=all";
    public static String streamToString(InputStream stream){;
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(stream));) {
            StringBuilder builder = new StringBuilder();

            String line = "";

            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            return builder.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";

    }


    public static String RequestData(RequestData requestData){
        String uri=requestData.getUri();
        if(requestData.getMethod().equalsIgnoreCase("get") && !requestData.getParam().isEmpty()){
            uri+="?" + requestData.encodeParams();
        }

        try {
            URL url=new URL(uri);
            HttpURLConnection http= (HttpURLConnection) url.openConnection();
            http.setRequestMethod(requestData.getMethod());
            if(requestData.getMethod().equalsIgnoreCase("post") && !requestData.getParam().isEmpty()){
                http.setDoOutput(true);
                try(OutputStreamWriter osw=new OutputStreamWriter(http.getOutputStream());) {
                    osw.write(requestData.encodeParams());
                    osw.flush();
                }
            }
            String response=streamToString(http.getInputStream());

            return response;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static class RequestData{
        private String uri="";
        private String method="GET";
        private Map<String,String> param;

        public RequestData(String uri,String method){
            this.uri=uri;
            this.method=method;
            param=new HashMap<>();
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Map<String, String> getParam() {
            return param;
        }

        public void setParam(Map<String, String> param) {
            if(param == null)
                param=new HashMap<>();
            this.param = param;
        }

        public void setParam(String key,String value){
            if(this.param == null) this.param=new HashMap<>();

            param.put(key,value);
        }


        public String encodeParams(){
            StringBuilder builder=new StringBuilder();

            for(String key:param.keySet()){
                String value=param.get(key);
                if(builder.length() > 0)
                    builder.append("&");
                builder.append(key);
                builder.append("=");
                try {
                    builder.append(URLEncoder.encode(value,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }


            return builder.toString();
        }

    }

}
