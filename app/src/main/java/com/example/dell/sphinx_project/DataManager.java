package com.example.dell.sphinx_project;

/**
 * Created by Dell on 15-01-2018.
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class DataManager {
    static long enqueue = 0;
    static DownloadManager dm = null;
    static StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
            .permitAll().build();

    static String codelist[][] = {{" ", "_sp_"}, {"*", "_star_"},
            {"'", "_sq_"}, {"\"", "_dq_"}, {"<", "_lt_"},
            {"<=", "_lteq_"}, {">", "_gt_"}, {">=", "_gteq_"},
            {"=", "_eq_"}, {"<>", "_neq_"}, {"+", "_plus_"},
            {"-", "_minus_"}, {"/", "_div_"}, {"mod", "_mod_"},
            {"%", "_percent_"}, {"?", "_qm_"}, {",", "_cm_"}};

    public static boolean executeUpdate(final Context context, String queryUrl, final String qry) throws Exception {
        StrictMode.setThreadPolicy(policy);
        JSONObject object = sendHttpRequest(context, queryUrl, qry);
        if (object == null)
            return false;

        if (object.getString("code").equals("1"))
            return true;
        else
            return false;
    }

    public static JSONArray executeQuery(final Context context, String queryUrl, final String qry) throws Exception {
        StrictMode.setThreadPolicy(policy);
        JSONObject object = sendHttpRequest(context, queryUrl, qry);
        if (object != null) {
            JSONArray rows = object.getJSONArray("data");
            return rows;
        } else
            return null;
    }

    public static String decodeQuery(String qry) {
        for (int i = 0; i < 17; i++)
            qry = qry.replace(codelist[i][1], codelist[i][0]);
        return qry;
    }

    public static String encodeQuery(String qry) {
        for (int i = 0; i < 17; i++)
            qry = qry.replace(codelist[i][0], codelist[i][1]);
        return qry;
    }

    public static JSONObject sendHttpRequest(Context context, String queryUrl, String qry)
            throws Exception {

        if (!isInternetConnected(context)) {
            JSONObject result = new JSONObject();
            result.put("code", 0);
            result.put("message", "INTERNET NOT AVAILABLE");
            Toast.makeText(context,
                    "Internet is Not Available, please connect to Internet.",
                    Toast.LENGTH_LONG).show();
            return result;
        }
        InputStream is = null;
        String result = null;

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("qry", DataManager.encodeQuery(qry)));
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(queryUrl);
        httppost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
        result = parseResponse(is);
        result = result.replace("\\", "");
        result = result.replace("\"[", "[");
        result = result.replace("]\"", "]");
        return new JSONObject(result);
    }

    public static JSONObject sendHttpRequest(String url,
                                             ArrayList<NameValuePair> params) throws Exception {
        InputStream is = null;
        String result = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
        result = parseResponse(is);
        result = result.replace("\\", "");
        result = result.replace("\"[", "[");
        result = result.replace("]\"", "]");
        return new JSONObject(result);
    }

    public static String parseResponse(InputStream is) throws Exception {
        String result = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                "iso-8859-1"), 8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        result = sb.toString();
        return result;
    }

    public static String parseJSon(String result) throws Exception {
        String s = "";
        JSONObject jsonObj = new JSONObject(result);
        CharSequence w = (CharSequence) jsonObj.get("re");
        s = w.toString();
        return s;
    }

    public static InputStream sendRequest(String url, ArrayList<NameValuePair> params) throws Exception {
        InputStream is = null;
        String result = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
        return is;
    }


    public static InputStream sendRequest(String url, String qry) throws Exception {
        InputStream is = null;
        String result = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("qry", DataManager.encodeQuery(qry)));
        httppost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        is = entity.getContent();
        return is;
    }


    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;

        }
        return false;
    }


    public static void showImageFromServer(final Activity activity, final String url, final ImageView iv) {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    Query query = new Query();
                    query.setFilterById(enqueue);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                            //ImageView iv = (ImageView) findViewById(R.id.imageView1);
                            String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            iv.setImageURI(Uri.parse(uriString));
                        }
                    }
                }
            }
        };

        activity.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        dm = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        Request request = new Request(Uri.parse(url));
        enqueue = dm.enqueue(request);
    }


    public static void downloadFile(final Activity activity, final String sourceUrl, final String targetPath, final String targetFilename) {

        new AsyncTask<String, String, String>() {
            ProgressDialog mProgressDialog = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(activity);
                mProgressDialog.setMessage("File downloading..." + targetFilename);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }

            @Override
            protected String doInBackground(String... aurl) {
                int count;
                try {
                    URL url = new URL(aurl[0]);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    int lenghtOfFile = conexion.getContentLength();
                    Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
                    InputStream input = new BufferedInputStream(url.openStream());
                    if (!(new File(targetPath).exists()))
                        new File(targetPath).mkdirs();
                    OutputStream output = new FileOutputStream(targetPath + "/" + targetFilename);
                    byte data[] = new byte[1024];
                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        //publishProgress(""+(int)((total*100)/lenghtOfFile));
                        publishProgress("" + total, "" + lenghtOfFile);
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();

                    input.close();
                } catch (Exception e) {
                }
                return null;

            }

            protected void onProgressUpdate(String... progress) {
                int total = Integer.parseInt(progress[0]);
                int lengthOfFile = Integer.parseInt(progress[1]);
                mProgressDialog.setProgress((int) ((total * 100) / lengthOfFile));
            }

            @Override
            protected void onPostExecute(String unused) {
                mProgressDialog.dismiss();
                //dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            }
        }.execute(sourceUrl);
    }

    public static void downloadFileNoDialog(final String sourceUrl, final String targetPath, final String targetFilename) {

        new AsyncTask<String, String, String>() {
            //	       	   ProgressDialog mProgressDialog=null;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//	       		mProgressDialog=new ProgressDialog(activity);
//	       		mProgressDialog.setMessage("File downloading..."+targetFilename);
//	       		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//	   			mProgressDialog.setCancelable(false);
//	   			mProgressDialog.show();

            }

            @Override
            protected String doInBackground(String... aurl) {
                int count;

                try {

                    URL url = new URL(aurl[0]);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();

                    int lenghtOfFile = conexion.getContentLength();
                    Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                    InputStream input = new BufferedInputStream(url.openStream());
                    if (!(new File(targetPath).exists()))
                        new File(targetPath).mkdirs();
                    OutputStream output = new FileOutputStream(targetPath + "/" + targetFilename);

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        //publishProgress(""+(int)((total*100)/lenghtOfFile));
                        publishProgress("" + total, "" + lenghtOfFile);
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {
                }
                return null;

            }

            protected void onProgressUpdate(String... progress) {
                int total = Integer.parseInt(progress[0]);
                int lengthOfFile = Integer.parseInt(progress[1]);
//	       		 mProgressDialog.setProgress((int)((total*100)/lengthOfFile));
            }

            @Override
            protected void onPostExecute(String unused) {
//	       		mProgressDialog.dismiss();
                //dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            }
        }.execute(sourceUrl);
    }

    public static int uploadFile(String SERVER_URL, final String selectedFilePath, final String targetFile) {
        StrictMode.setThreadPolicy(policy);
        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {
            //dialog.dismiss();
            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file", selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + targetFile + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0) {
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.e("Upload File", "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
//                   runOnUiThread(new Runnable() {
//                       @Override
//                       public void run() {
//                           tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/"+ fileName);
//                       }
//                   });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
//           	Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

//               runOnUiThread(new Runnable() {
//                   @Override
//                   public void run() {
//                       Toast.makeText(MainActivity.this,"File Not Found",Toast.LENGTH_SHORT).show();
//                   }
//               });
            } catch (MalformedURLException e) {
//           	Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                Log.e("MainActivity:MalFormed", e.toString());

            } catch (IOException e) {
//           	Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                Log.e("MainActivity:IOE", e.toString());
            }
//           dialog.dismiss();
            return serverResponseCode;
        }

    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getFileAsString(String path) {
        try {
            File file = new File(path);
            byte[] arr = new byte[(int) file.length()];
            FileInputStream fin = new FileInputStream(path);
            fin.read(arr);
            return Base64.encodeToString(arr, Base64.DEFAULT);
        } catch (Exception ex) {

        }
        return null;
    }

    static class FilePath {
        /**
         * Method for return file path of Gallery image/ Document / Video / Audio
         *
         * @param context
         * @param uri
         * @return path of the selected image file from gallery
         */
//	    public static String getPath(final Context context, final Uri uri) {
//
//	        // check here to KITKAT or new version
//	        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//
//	        // DocumentProvider
//	        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
//
//	            // ExternalStorageProvider
//	            if (isExternalStorageDocument(uri)) {
//	                final String docId = DocumentsContract.getDocumentId(uri);
//	                final String[] split = docId.split(":");
//	                final String type = split[0];
//
//	                if ("primary".equalsIgnoreCase(type)) {
//	                    return Environment.getExternalStorageDirectory() + "/"
//	                            + split[1];
//	                }
//	            }
//	            // DownloadsProvider
//	            else if (isDownloadsDocument(uri)) {
//
//	                final String id = DocumentsContract.getDocumentId(uri);
//	                final Uri contentUri = ContentUris.withAppendedId(
//	                        Uri.parse("content://downloads/public_downloads"),
//	                        Long.valueOf(id));
//
//	                return getDataColumn(context, contentUri, null, null);
//	            }
//	            // MediaProvider
//	            else if (isMediaDocument(uri)) {
//	                final String docId = DocumentsContract.getDocumentId(uri);
//	                final String[] split = docId.split(":");
//	                final String type = split[0];
//
//	                Uri contentUri = null;
//	                if ("image".equals(type)) {
//	                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//	                } else if ("video".equals(type)) {
//	                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//	                } else if ("audio".equals(type)) {
//	                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//	                }
//
//	                final String selection = "_id=?";
//	                final String[] selectionArgs = new String[] { split[1] };
//
//	                return getDataColumn(context, contentUri, selection,
//	                        selectionArgs);
//	            }
//	        }
//	        // MediaStore (and general)
//	        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//
//	            // Return the remote address
//	            if (isGooglePhotosUri(uri))
//	                return uri.getLastPathSegment();
//
//	            return getDataColumn(context, uri, null, null);
//	        }
//	        // File
//	        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//	            return uri.getPath();
//	        }
//
//	        return null;
//	    }
//

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         */
        public static String getDataColumn(Context context, Uri uri,
                                           String selection, String[] selectionArgs) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};

            try {
                cursor = context.getContentResolver().query(uri, projection,
                        selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        public static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri
                    .getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        public static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri
                    .getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        public static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri
                    .getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        public static boolean isGooglePhotosUri(Uri uri) {
            return "com.google.android.apps.photos.content".equals(uri
                    .getAuthority());
        }
    }

    public static Bitmap compressImage(Activity activity, String imagefilepath) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            Bitmap original = BitmapFactory.decodeFile(imagefilepath, options);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            original.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
            return decoded;
        } catch (Exception e) {

        }
        return null;
    }

    public static String saveImageToFile(Bitmap bitmap, String filepath, String filename) {
        FileOutputStream out = null;

        try {
            if (!new File(filepath).exists())
                new File(filepath).mkdirs();

            out = new FileOutputStream(filepath + "/" + filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filepath + "/" + filename;
    }
}
