package com.example.dam.legoparts;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PartsDownloader extends AsyncTask<Void, String, String> {
    private Context context;
    private String id;
    private String descarga;
    private LlistaPartsActivity llistaPartsActivity;

    public LlistaPartsActivity getLlistaPartsActivity() {
        return llistaPartsActivity;
    }

    public void setLlistaPartsActivity(LlistaPartsActivity llistaPartsActivity) {
        this.llistaPartsActivity = llistaPartsActivity;
    }

    public String getDescarga() {
        return descarga;
    }

    public void setDescarga(String descarga) {
        this.descarga = descarga;
    }

    public PartsDownloader(Context context, String id) {
        this.context = context;
        this.id=id;
    }

    private OnPartsLoadedListener listener = null;
    public void setOnPartsLoadedListener(OnPartsLoadedListener listener) {
        this.listener = listener;
    }
    private ProgressDialog pDialog;
    @Override protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
        pDialog.setTitle("Please wait...");
        String msg = "Downloading...";
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        int count;

        try {
            URL url = new URL("http://www.rebrickable.com/api/get_set_parts?key=ezTu0j5OCx&format=csv&set="+this.id);
            URLConnection connection = url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();
            pDialog.setMax(100);
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += 10;
                publishProgress("" + (int) total);

                output.write(data, 0, count);
            }

            String tsv = new String(output.toByteArray());
            input.close();
            output.flush();
            descarga=tsv;
            LlistaPartsActivity.descarga=tsv;
            montarFile(descarga, id);
            return tsv;


        } catch (MalformedURLException e) {
            System.out.println("Error 1");
            return null;
        } catch (IOException e) {
            System.out.println("Error 2");
            return null;
        }


    }
    protected void onProgressUpdate(String... progress) {
        pDialog.setProgress(Integer.parseInt(progress[0]));
    }
    @Override public void onPostExecute(String result) {
        pDialog.dismiss();
        if(result!=null){
            llistaPartsActivity.notifyDescarga(result);
        }
    }

    private void montarFile(String descarga, String id) {
        if (!descarga.equals("NOSET")) {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Reabrickable");
            boolean result = dir.mkdir();
            if (dir == null) return;
            File f = new File(dir, id + ".txt");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //f.delete();

            PrintWriter wr = null;
            try {
                wr = new PrintWriter(f);
                wr.println(descarga);
                wr.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }
    /*String str = "SomeMoreTextIsHere";
    File newTextFile = new File("C:/thetextfile.txt");

    FileWriter fw = new FileWriter(newTextFile);
    fw.write(str);
    fw.close();*/

}
