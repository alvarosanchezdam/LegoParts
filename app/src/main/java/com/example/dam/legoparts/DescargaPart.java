package com.example.dam.legoparts;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.EditText;
        import android.widget.ListView;

        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;

        import java.io.BufferedInputStream;
        import java.io.ByteArrayOutputStream;
        import java.io.File;
        import java.io.InputStream;
        import java.io.PrintWriter;
        import java.net.URL;
        import java.net.URLConnection;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

public class DescargaPart extends AsyncTask<Void, String, String> {

    private Context context;
    private String tema;
    private GsonBuilder builder ;
    private Gson gson;
    private PartActivity partActivity;

    public PartActivity getPartActivity() {
        return partActivity;
    }

    public void setPartActivity(PartActivity partActivity) {
        this.partActivity = partActivity;
    }

    public DescargaPart(Context context, String tema) {
        this.tema=tema;
        this.context = context;
        this.builder = new GsonBuilder();
        this.gson = builder.create();
    }

    public String getTema() {
        return tema;
    }
    public void setTema(String tema) {
        this.tema = tema;
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

    @Override protected String doInBackground(Void... params) {
        int count;
        try {
            URL url = new URL("http://rebrickable.com/api/v3/lego/parts/"+this.tema+"/?key=ezTu0j5OCx");
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }
            input.close();
            output.flush();
            String json = new String(output.toByteArray());
            return json;

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            return e.toString();
        }
    }
    protected void onProgressUpdate(String... progress) {
        pDialog.setProgress(Integer.parseInt(progress[0]));
    }
    @Override public void onPostExecute(String json) {
        Pieza pieza = gson.fromJson(json,  Pieza.class);
        pDialog.dismiss();
        partActivity.notifyPart(pieza);
    }

}
