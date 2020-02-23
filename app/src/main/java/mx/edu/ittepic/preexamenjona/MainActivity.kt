package mx.edu.ittepic.preexamenjona

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(ContextCompat.checkSelfPermission(this,     //context es estática  --- check
                Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            //SI ENTRA ENTONCES AUN NO SE HAN OTORGADO PERMISOS
            //EL SIGUIENTE CODIGO LOS SOLICITA
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE),0)

        }else{
            Toast.makeText(this,"Permisos ya otorgados",Toast.LENGTH_LONG).show()
        }

        btnLlamar.setOnClickListener {
            var activity2 = Intent(this,Main2Activity::class.java)
            startActivity(activity2)
        }
        btnMatriz.setOnClickListener {


                var pares=""
                var impares=""

                val arrayEstatico = intArrayOf(1,22,3,11,6,8,7,9,5,12)
            //Pares: 22,6,8,12
            //Impares: 1,3,11,7,9,5


                (0..9).forEach {
                    if((arrayEstatico[it] % 2) == 0){
                        pares += arrayEstatico[it].toString()

                    }else{
                        impares+=arrayEstatico[it]

                    }
                }
                var rutaSD = Environment.getExternalStorageDirectory()

                var archivoPares = File(rutaSD.absolutePath,"valorespares.txt")
                var archivoImpares = File(rutaSD.absolutePath,"valoresimpares.txt")

                var flujoSalidaPar = OutputStreamWriter(FileOutputStream(archivoPares))
                var flujoSalidaImpar = OutputStreamWriter(FileOutputStream(archivoImpares))

                flujoSalidaPar.write(pares)
                flujoSalidaPar.flush()
                flujoSalidaPar.close()

                flujoSalidaImpar.write(impares)
                flujoSalidaImpar.flush()
                flujoSalidaImpar.close()

                Toast.makeText(this,"Archivos Guardados",Toast.LENGTH_LONG).show()


        }

        lista.setOnItemClickListener { parent, view, position, id ->

            when (position) {

                //1) Manda a llamar un Main2Activivty
                0 -> {

                    var activity2 = Intent(this, Main2Activity::class.java)

                    startActivity(activity2)
                }
                // 2) Genera un timer que pone en el titulo de ventana el mensaje "Hola mundo" 20 veces
                1 -> {
                    var contador=0
                    var timer = object : CountDownTimer(20000, 1000) {
                        override fun onFinish() {
                            //Esto se ejecuta cuando acaba por completo
                        }

                        override fun onTick(millisUntilFinished: Long) {
                          setTitle("Hola mundo: "+contador++)
                        }
                    }
                    timer.start()
                }
                /*3) Genera una matriz de 20 renglones x 10 columnas de números enteros y los rellena con números random
                   del 0 al 100  mostrandolos en un alertDialog */
                2 -> {
                    var matriz: Array<Array<Int>> = Array(20,{ Array(10,{0})})

                    var datosArreglo = ""
                    var r =0
                    var c =0
                    (0..19).forEach {it->r
                        datosArreglo += "\n"
                        (0..9).forEach {it->c

                            matriz[r][c] =  (0..100).shuffled().first()
                            datosArreglo += "[ ${matriz[r][c].toString()} ]"
                        }
                    }

                    AlertDialog.Builder(this)
                        .setTitle("Matriz")
                        .setMessage(datosArreglo)
                        .setPositiveButton("OK") {d,i->
                            d.dismiss() }.show()



                }
                //4) Sale del activity
                3 -> {
                    finish()
                }

            }
        }

        button3.setOnClickListener{
            var vector = ArrayList<String>()
            vector.add("La que no es fruta no disfruta")
            vector.add("Porque uno es lo que uno es")
            vector.add("Wobba lobba dub dub")

            var ultimoIndice = vector.size-1
            var arregloEstatico : Array<String> = Array(vector.size,{""})
            (0..ultimoIndice).forEach {
                arregloEstatico[it] = vector[it]
            }
            //SE CONSTRUYE EL ADAPTER
            var adaptadorListView = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arregloEstatico)

            listaFrases.adapter = adaptadorListView

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.acercade ->{
              Toast.makeText(this,"Jonathan Lopez - Google",Toast.LENGTH_LONG).show()
            }
            R.id.salir->{
                finish()
            }
            R.id.ayuda->{
                AlertDialog.Builder(this)
                    .setTitle("Atención")
                    .setMessage("Estamos en construcción")
                    .setPositiveButton("Ok"){d,i->
                       d.dismiss()
                    }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
