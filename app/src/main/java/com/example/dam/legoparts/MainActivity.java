package com.example.dam.legoparts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button boton;
    EditText idText;
    Spinner spinner;
    File fParts;
    private static final int REQUEST_WRITE_STORAGE = 112;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton= (Button)findViewById(R.id.button);
        idText=(EditText) findViewById(R.id.idText);
        spinner= (Spinner) findViewById(R.id.spinner);
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
            Log.d("prueba", "error no hay permiso");
        }
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sDirectorio = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Reabrickable/";
                File f = new File(sDirectorio);
                f.mkdir();
                File[] ficheros = f.listFiles();
                fParts=ficheros[position];
            }
        });
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("flx", "MOVE en");
                Intent intent = new Intent(MainActivity.this, LlistaPartsActivity.class);
                intent.putExtra("id", idText.getText().toString());
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (hasPermission) {
            updateSpinner();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    updateSpinner();
                } else
                {
                    Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
    public void updateSpinner(){
        String sDirectorio = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Reabrickable/";
        File f = new File(sDirectorio);
        f.mkdir();
        File[] ficheros = f.listFiles();
        SetAdapter adapter=new SetAdapter(this, ficheros);
        spinner.setAdapter(adapter);
    }
    public class SetAdapter extends BaseAdapter {
        private Context context;
        private File[] files;
        public SetAdapter(Context context, File[] parts){
            this.context=context;
            this.files=parts;
        }
        @Override
        public int getCount() {
            return files.length;
        }

        @Override
        public Object getItem(int position) {
            return files[position];
        }

        @Override
        public long getItemId(int position) {
            int id= 1;
            return id;
        }

        public class ViewHolder{
            public TextView tvNom;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView =convertView;
            if(myView ==null) {
                LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.spinner_item, parent, false);
                ViewHolder holder= new ViewHolder();
                holder.tvNom=(TextView) myView.findViewById(R.id.name);
                myView.setTag(holder);
            }
            ViewHolder holder= (ViewHolder) myView.getTag();

            File file = files[position];
            String nom=(String) file.getName();
            holder.tvNom.setText(nom);
            return myView;
        }
    }
}
