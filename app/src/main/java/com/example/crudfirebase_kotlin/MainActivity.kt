package com.example.crudfirebase_kotlin

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.crudfirebase_kotlin.models.Persona
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dmax.dialog.SpotsDialog

class MainActivity : AppCompatActivity() {
    private  var db = FirebaseFirestore.getInstance();
     var dialog:AlertDialog?=null ;
    var apellido:TextInputEditText? = null;
    var title:TextView? = null;
    var direccion:TextInputEditText?=null;
    var telefono:TextInputEditText?=null;
    var email:TextInputEditText?=null;
    var nombre:TextInputEditText?  = null;
    var btn : Button?=null;

    var key :String?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = Firebase.firestore;
        apellido = findViewById(R.id.apellido);
        direccion = findViewById(R.id.direccion);
        nombre= findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        title = findViewById(R.id.title);
        key = intent.getStringExtra("id_person");
        btn = findViewById(R.id.registrarbtn);
        dialog=SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Cargando .....")
            .setCancelable(false).build();

        if(key!=null){

            db.collection("users").document(key!!).get().addOnSuccessListener {


                title?.setText("Actualizar Persona");
                btn?.setText("Actualizar ");
                nombre?.setText(it.get("nombre").toString());
                email?.setText(it.get("correo").toString());
                apellido?.setText(it.get("apellido").toString());
                direccion?.setText(it.get("direccion").toString());
                telefono?.setText(it.get("telefono").toString());

            }


        }else{
            title?.setText("Registrar Usuario");
            btn?.setText("Registrar ");
        }



        var bt2: FloatingActionButton = findViewById(R.id.listarbt);
        bt2.setOnClickListener {   listar();}
        btn?.setOnClickListener { registraruser();}




    }
    fun listar(){
        var  mover: Intent = Intent(this,ListPersonas::class.java);
        key = null;
        limpiarcodigo();
        startActivity(mover);

    }
    fun registraruser(){
        dialog?.show();
        val name = nombre?.text.toString();
        val lastname = apellido?.text.toString();
        val addres = direccion?.text.toString();
        val phone = telefono?.text.toString();
        val eeemail = email?.text.toString();


         var persona:Persona = Persona();

        if(key!=null){
            persona.nombre=name;
            persona.apellido = lastname;
            persona.direccion=addres;
            persona.telefono=phone;
            persona.correo=eeemail;
            db.collection("users").document(key!!).set(persona!!).addOnSuccessListener {
                dialog?.dismiss()
                listar();
            }

        }else{
            persona.nombre=name;
            persona.apellido = lastname;
            persona.direccion=addres;
            persona.telefono=phone;
            persona.correo=eeemail;
            db.collection("users")
                .add(persona)
                .addOnSuccessListener { documentReference ->
                    dialog?.dismiss()
                    Toast.makeText(this,"Se registro correctamente",Toast.LENGTH_LONG).show();
                    listar();

                }
                .addOnFailureListener { e ->
                    Toast.makeText(this,"No se registro correctamente:" +  e.message,Toast.LENGTH_LONG).show();
                }
        }




    }


    fun limpiarcodigo(){
        nombre?.setText("");
        email?.setText("");
        apellido?.setText("");
        direccion?.setText("");
        telefono?.setText("");
    }





// Add a new document with a generated ID

}