package com.example.crudfirebase_kotlin

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.ListView
import android.widget.TextView

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.crudfirebase_kotlin.Alerts.Mensajes
import com.example.crudfirebase_kotlin.adapter.PersonAdapter
import com.example.crudfirebase_kotlin.models.Persona
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore


class ListPersonas : AppCompatActivity(),  DialogInterface.OnClickListener {
    private var list:ListView?=null;

    private  var db = FirebaseFirestore.getInstance();
    private var array: PersonAdapter?=null;
    private var persons: ArrayList<Persona>?=null;
    private var idpos:Int?=null;
    private var key:String?=null;
    private  var alertDialog: AlertDialog? = null;
    private  var alertconfirm:AlertDialog? = null;




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_personas)
        list = findViewById<ListView>(R.id.listpersons);
        alertDialog = Mensajes.crearAlertaDialog(this@ListPersonas);
        alertconfirm = Mensajes.crearDialogConfirmacion(this@ListPersonas);


        leerdatos();
        var registrarb: FloatingActionButton=findViewById(R.id.registrarb);
        registrarb.setOnClickListener {
            crearuser();

        }
      list?.setOnItemClickListener { adapterView, view, i, l ->
          idpos= i;
          Log.d("idpos", idpos.toString());
          alertDialog?.show();
      }

    }
    fun crearuser(){
        var  mover: Intent = Intent(this,MainActivity::class.java);
        startActivity(mover);
    }
    fun leerdatos(){
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
               persons= ArrayList();

                for (document in result) {
                    var  pers:Persona = Persona();
                    pers.id= document.id.toString();
                    pers.nombre = document.get("nombre").toString();
                    pers.apellido = document.get("apellido").toString();
                    pers.direccion = document.get("direccion").toString();
                    pers.telefono = document.get("telefono").toString();
                    pers.correo = document.get("correo").toString();


                    persons?.add(pers)


                }
                array = PersonAdapter(
                    applicationContext,
                    persons
                );

                list?.adapter = array;

            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

    }



    override fun onClick(p0: DialogInterface?, p1: Int) {
        key = persons?.get(idpos!!)?.id;



        when (p1) {
            0 -> {
                mostrardetalle();
            }
            1->{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id_person", key)
                startActivity(intent)
            }
            2 -> alertconfirm!!.show()
            DialogInterface.BUTTON_POSITIVE -> {
                persons?.remove(persons?.get(idpos!!));
                db.collection("users").document(key!!).delete();
                list?.invalidateViews();
                startActivity(intent)
                finish()
            }
            DialogInterface.BUTTON_NEGATIVE -> alertconfirm!!.dismiss()
        }
    }
     public  fun mostrardetalle(){
         val persona = persons?.get(idpos!!);
         val view:View = View.inflate(this@ListPersonas,R.layout.persondetail,null);
         var  nombrecompleto:TextView =  view.findViewById(R.id.nombrecompleto);
         nombrecompleto.setText(persona?.nombre + "    " + persona?.apellido)
         var  direcciondetail:TextView =  view.findViewById(R.id.direcciondetail);
         direcciondetail.setText(persona?.direccion);
         var  correodetail:TextView =  view.findViewById(R.id.correodetail);
         correodetail.setText(persona?.correo);
         var  telefonodetail:TextView =  view.findViewById(R.id.telefonodetail);
         telefonodetail.setText(persona?.telefono);

         val builder = AlertDialog.Builder(this@ListPersonas);
         builder.setView(view);
         val dialog = builder.create();
         dialog.show();
         dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);

     }


}