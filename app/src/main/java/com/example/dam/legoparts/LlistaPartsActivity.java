package com.example.dam.legoparts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class LlistaPartsActivity extends AppCompatActivity {
    public static String descarga;
    private ListView llista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_parts);
        llista=(ListView) findViewById(R.id.llista_parts);
        Intent intent=this.getIntent();

            String id = intent.getStringExtra("id");
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Reabrickable/"+id+".txt");
        if(dir.exists()){
            String cadenaTotal="";
            String cadena;
            FileReader f = null;
            try {
                f = new FileReader(dir);
                BufferedReader b = new BufferedReader(f);
                int cont=0;
                while((cadena = b.readLine())!=null) {
                    if(cont==0){
                        cadenaTotal+=cadena;
                        cont++;
                    }else {
                        cadenaTotal += "\n" + cadena;
                    }
                }
                b.close();
                notifyDescarga(cadenaTotal);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else {
            downloadLegoParts(id);
        }

    }
    public void notifyDescarga(String descarga){
        String csvFile = descarga;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<Part> parts = new ArrayList<>();
        try {

            br = new BufferedReader(new StringReader(csvFile));
            int count=0;
            while ((line = br.readLine()) != null) {
                if(count>0) {
                    String[] part = line.split(cvsSplitBy);

                    Part p = new Part();
                    p.setCantidad(Integer.parseInt(part[1]));
                    p.setNombre(part[4]);
                    if(part.length==12){
                        p.setImgUrl(part[7]);
                    }else {
                        p.setImgUrl(part[6]);
                    }
                    parts.add(p);
                }else{
                    count++;
                }
            }

            PartsAdapter adapter=new PartsAdapter(this, parts);
            llista.setAdapter(adapter);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void downloadLegoParts(String id) {
        PartsDownloader dd = new PartsDownloader(this, id);
        dd.setLlistaPartsActivity(this);
                dd.execute();

    }
    public class PartsAdapter extends BaseAdapter {
        private Context context;
        private List<Part> parts;
        public PartsAdapter(Context context, List<Part> parts){
            this.context=context;
            this.parts=parts;
        }
        @Override
        public int getCount() {
            return parts.size();
        }

        @Override
        public Object getItem(int position) {
            return parts.get(position);
        }

        @Override
        public long getItemId(int position) {
            int id= (int)parts.get(position).getCantidad();
            return id;
        }

        public class ViewHolder{
            public TextView tvNom;
            public ImageView ivImage;
            public TextView tvId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView =convertView;
            if(myView ==null) {
                LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.llista_item, parent, false);
                ViewHolder holder= new ViewHolder();
                holder.tvNom=(TextView) myView.findViewById(R.id.name);
                holder.ivImage=(ImageView) myView.findViewById(R.id.image);
                holder.tvId=(TextView) myView.findViewById(R.id.id);
                myView.setTag(holder);
            }
            ViewHolder holder= (ViewHolder) myView.getTag();

            Part part = parts.get(position);
            String image=part.getImgUrl();
            String urldisplay = image;
            Bitmap mIcon11 = null;
            try {
               /* InputStream in = new java.net.URL(image).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                holder.ivImage.setImageBitmap(mIcon11);*/
            }catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            String nom=(String) part.getNombre();
            holder.tvNom.setText(nom);
            long id= (long) part.getCantidad();
            holder.tvId.setText(id+"");
            return myView;
        }
    }


}
