package com.example.crudfirebase_kotlin.Alerts;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class Mensajes {



    public static AlertDialog crearAlertaDialog(Activity activity){
        final CharSequence[] items = {
                "Ver detalle",
                "Editar",
                "Eliminar"
        };
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Opciones");
        alert.setItems(items, (DialogInterface.OnClickListener) activity);
        return alert.create();
    }
    public static AlertDialog crearDialogConfirmacion(Activity activity){
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setMessage("Desea Eliminar..?");
        alert.setPositiveButton("Si", (DialogInterface.OnClickListener) activity);
        alert.setNeutralButton("No", (DialogInterface.OnClickListener) activity);
        return alert.create();
    }
}
