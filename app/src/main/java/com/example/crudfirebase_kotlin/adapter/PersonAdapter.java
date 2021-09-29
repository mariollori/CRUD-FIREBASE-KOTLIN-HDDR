package com.example.crudfirebase_kotlin.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.crudfirebase_kotlin.R;
import com.example.crudfirebase_kotlin.models.Persona;

import java.util.List;

public class PersonAdapter extends BaseAdapter {
    private Context context;
    private List<Persona> lista;

    public PersonAdapter(Context context, List<Persona> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Persona persona = lista.get(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.persona, null);
        }
        TextView txtNom = (TextView) view.findViewById(R.id.persona_list_nombre);
        txtNom.setText(persona.getNombre());
        TextView txtdata = (TextView) view.findViewById(R.id.persona_list_data);
        txtdata.setText(" "+persona.getTelefono());
        TextView txtdata2 = (TextView) view.findViewById(R.id.persona_list_data2);
        txtdata2.setText(" "+persona.getCorreo());

        return view;
    }


}
