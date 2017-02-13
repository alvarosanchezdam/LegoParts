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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button boton;
    Button boton2;
    EditText idText;
    ListView spinner;
    File fParts;
    private static final int REQUEST_WRITE_STORAGE = 112;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        boton= (Button)findViewById(R.id.button);
        idText=(EditText) findViewById(R.id.idText);
        spinner= (ListView) findViewById(R.id.spinner);
        //Para tratar con los ficheros de las descargas primero me aseguro que tiene el permiso de escribir y leer
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
            Log.d("prueba", "error no hay permiso");
        }

        //Envio la información del textfield al activity de la lista para descargar y mostrar
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("flx", "MOVE en");
                Intent intent = new Intent(MainActivity.this, LlistaPartsActivity.class);
                intent.putExtra ("id", idText.getText().toString());
                startActivity(intent);
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        //Cada vez que vuelve al MainActivity se actualizara la lista de los ficheros.
        // Esto lo uso para cuando vuelva a la Activity despues de haber hecho una busqueda
        // se actualice y ya se pueda ver
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (hasPermission) {
            updateSpinner();
        }

    }

    //Pido el permiso en el caso que no lo tenga
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

    //Actualizo la lista (antes era un spinner) donde están todos lo ficheros que se han creado
    public void updateSpinner(){
        String sDirectorio = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Reabrickable/";
        File f = new File(sDirectorio);
        f.mkdir();
        final File[] ficheros = f.listFiles();
        SetAdapter adapter=new SetAdapter(this, ficheros);
        spinner.setAdapter(adapter);

        //Cuando haces click te abre la caja donde aparece la lista de todas las piezas
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fParts=ficheros[position];
                Intent intent = new Intent(MainActivity.this, LlistaPartsActivity.class);
                intent.putExtra ("id", fParts.getName().replace(".txt", ""));
                startActivity(intent);
            }
        });

        //Si mantienes sobre un elemento de la lista de ficheros lo borra
        spinner.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                File file = ficheros[position];
                final String nom=(String) file.getName();
                String sDirectorio = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Reabrickable/"+nom;
                File f = new File(sDirectorio);
                f.delete();
                fParts=null;

                //Lo actualizo para que se vea el cambio inmediatamente
                updateSpinner();
                return true;
            }
        });
    }

    //Creo el adapter para llenar la lista
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

        //Para inflar el adapter uso el layout propio creado (llista_item2)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myView =convertView;
            if(myView ==null) {
                LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                myView = inflater.inflate(R.layout.llista_item2, parent, false);
                ViewHolder holder= new ViewHolder();
                holder.tvNom=(TextView) myView.findViewById(R.id.textView);
                myView.setTag(holder);
            }
            ViewHolder holder= (ViewHolder) myView.getTag();

            File file = files[position];
            // Reemplazo el .txt para quitar la extension
            final String nom=(String) file.getName().replace(".txt", "");
            holder.tvNom.setText(nom);

            return myView;
        }
    }
}
