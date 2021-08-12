package com.jbgYimtr.asistencia.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.jbgYimtr.asistencia.CentroAsistencia
import com.jbgYimtr.asistencia.R
import com.jbgYimtr.asistencia.databinding.ActivityMainBinding
import com.jbgYimtr.asistencia.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: ActivityMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = ActivityMainBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val buscar: View =binding.Btbuscar
        buscar.setOnClickListener {
            buscar()
        }
        val NameCenterAtras: View =binding.NameCenter
        NameCenterAtras.setOnClickListener {
            atras()
        }
        val SaveBt: View =binding.btSave
        SaveBt.setOnClickListener {
            saveprueba()
        }

        val textView: TextView = binding.textView
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun atras(){
        binding.apply {
            binding.NameCenter.visibility=View.GONE
            binding.Turno.visibility=View.GONE
            binding.LLetiquetas.visibility=View.GONE
            binding.Scroll.visibility=View.GONE
            binding.Btbuscar.visibility= View.VISIBLE
            binding.CodEst.visibility= View.VISIBLE
            binding.CodEst.requestFocus()
            binding.CodEst.text.clear()
            binding.btSave.visibility=View.GONE
            //binding.Dependencia.visibility=View.GONE
            binding.AsisF1.text.clear()
            binding.AsisF2.text.clear()
            binding.AsisF3.text.clear()
            binding.AsisF4.text.clear()
            binding.AsisM1.text.clear()
            binding.AsisM2.text.clear()
            binding.AsisM3.text.clear()
            binding.AsisM4.text.clear()
            binding.Modalidad1.text=""
            binding.Modalidad2.text=""
            binding.Modalidad3.text=""
            binding.Modalidad4.text=""

        }
    }
    private fun buscar(){
        var opcion = 0
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val c= Calendar.getInstance()
        val hora = c.get(Calendar.HOUR_OF_DAY)
        var turno: String
        val codigo=binding.CodEst.text.toString()
        //val minuto = c.get(Calendar.MINUTE)
        if (hora>=12){
            turno="vespertino"
        }
        else{
            turno="matutino"
        }
        db.collection("centro")
            .document(turno)
            .collection("nombrecentros")
            .document(codigo).get().addOnSuccessListener {documentSnapsot->
                val data= documentSnapsot.toObject(CentroAsistencia::class.java)
                if(data!= null) {
                    binding.apply {
                        binding.Btbuscar.visibility = View.GONE
                        binding.CodEst.visibility = View.GONE
                        binding.NameCenter.visibility = View.VISIBLE
                        binding.NameCenter.text=data.Nombre
                        binding.Turno.visibility = View.VISIBLE
                        if(turno=="matutino"){
                            binding.Mat.isChecked=true
                            binding.Vesp.visibility=View.GONE
                        }
                        else{
                            binding.Vesp.isChecked=true
                            binding.Mat.visibility=View.GONE
                        }
                        binding.LLetiquetas.visibility = View.VISIBLE
                        binding.AsisF1.requestFocus()
                        binding.btSave.visibility = View.VISIBLE
                        binding.Scroll.visibility = View.VISIBLE
                    }
                    if (data.MA_F_1 != null) {
                        opcion = 1
                    }
                    if (data.MA_F_2 != null) {
                        opcion = 2
                    }
                    if (data.MA_F_3 != null) {
                        opcion = 3
                    }
                    if (data.MA_F_4 != null) {
                        opcion = 4
                    }
                    when (opcion) {
                        1 -> {
                            binding.md1.visibility=View.VISIBLE
                            binding.md2.visibility=View.GONE
                            binding.md3.visibility=View.GONE
                            binding.md4.visibility=View.GONE
                            binding.Modalidad1.text=data.Modalidad1
                            binding.Dependencia.text=data.Dependencia1
                        }
                        2->{
                            binding.md1.visibility=View.VISIBLE
                            binding.md2.visibility=View.VISIBLE
                            binding.md3.visibility=View.GONE
                            binding.md4.visibility=View.GONE
                            binding.Modalidad1.text=data.Modalidad1
                            binding.Modalidad2.text=data.Modalidad2
                            binding.Dependencia.text=data.Dependencia1
                            binding.Dependencia2.text=data.Dependencia2
                        }
                        3->{
                            binding.md1.visibility=View.VISIBLE
                            binding.md2.visibility=View.VISIBLE
                            binding.md3.visibility=View.VISIBLE
                            binding.md4.visibility=View.GONE
                            binding.Modalidad1.text=data.Modalidad1
                            binding.Modalidad2.text=data.Modalidad2
                            binding.Modalidad3.text=data.Modalidad3
                            binding.Dependencia.text=data.Dependencia1
                            binding.Dependencia2.text=data.Dependencia2
                            binding.Dependencia3.text=data.Dependencia3
                        }
                        4->{
                            binding.md1.visibility=View.VISIBLE
                            binding.md2.visibility=View.VISIBLE
                            binding.md3.visibility=View.VISIBLE
                            binding.md4.visibility=View.VISIBLE
                            binding.Modalidad1.text=data.Modalidad1
                            binding.Modalidad2.text=data.Modalidad2
                            binding.Modalidad3.text=data.Modalidad3
                            binding.Modalidad4.text=data.Modalidad4
                            binding.Dependencia.text=data.Dependencia1
                            binding.Dependencia2.text=data.Dependencia2
                            binding.Dependencia3.text=data.Dependencia3
                            binding.Dependencia4.text=data.Dependencia4
                        }
                    }
                }else{
                    val text = "Codigo no Encontrado"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(context, "cogo no Encontrado", Toast.LENGTH_SHORT)
                    toast.show()
                }

            }
        //val imm= getSystemService(this.INPUT_METHOD_SERVICE) as InputMethodManager
        //imm.hideSoftInputFromWindow(binding.CodEst.windowToken,0)
    }
    private fun Save(){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var datos = hashMapOf(
            "Codigo" to binding.CodEst.text.toString(),
            "Dependencia1" to binding.Dependencia.text.toString(),
            "Nombre" to binding.NameCenter.text.toString(),
            "Modalidad1" to binding.Modalidad1.text.toString(),
            "MA_F_1" to binding.AsisF1.text.toString().toLong(),
            "MA_M_1" to binding.AsisM1.text.toString().toLong()
        )
        val sdf = SimpleDateFormat("ddMMyyyy")
        val currenDate=sdf.format(Date())
        val fecha=currenDate.toString()
        val c= Calendar.getInstance()
        val hora = c.get(Calendar.HOUR_OF_DAY)
        var turno: String
        val codigo=binding.CodEst.text.toString()
        //val minuto = c.get(Calendar.MINUTE)
        if (hora>=12){
            turno="vespertino"
        }
        else{
            turno="matutino"
        }

        db.collection("asistencia")
            .document(fecha)
            .collection(turno)
            .document(codigo).set(datos).addOnCompleteListener {
                val text = "Datos Guardados"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(context, text, duration)
                toast.show()
                atras()
            }

    }
    private fun hora(){
        val c= Calendar.getInstance()
        val hora = c.get(Calendar.HOUR_OF_DAY)
        //val minuto = c.get(Calendar.MINUTE)
        if (hora>=12){
            binding.apply {
                binding.Vesp.isChecked=true
            }
        }
        else{
            binding.apply {
                binding.Mat.isChecked=true
            }
        }

    }
    private fun saveprueba(){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var opcion: Int=0
        val c= Calendar.getInstance()
        val hora = c.get(Calendar.HOUR_OF_DAY)
        var turno: String
        val codigo=binding.CodEst.text.toString()
        //val minuto = c.get(Calendar.MINUTE)
        if (hora>=12){
            turno="vespertino"
        }
        else{
            turno="matutino"
        }
        db.collection("centro")
            .document(turno)
            .collection("nombrecentros")
            .document(codigo)
            .get()
            .addOnSuccessListener {documentSnapsot->
                //binding.textView.setText(datos.getString("Modalidad1"))
                val data= documentSnapsot.toObject(CentroAsistencia::class.java)

                if (data != null) {
                    if(data.MA_F_1 !=null){
                        opcion=1
                    }
                    if(data.MA_F_2 !=null){
                        opcion=2
                    }
                    if(data.MA_F_3 !=null){
                        opcion=3
                    }
                    if(data.MA_F_4 !=null){
                        opcion=4
                    }

                    when(opcion){
                        1 ->{
                            if(data.MA_F_1 != null) {
                                if(binding.AsisF1.text.isNotEmpty() && binding.AsisM1.text.isNotEmpty()) {
                                    if (data.MA_F_1!! >= binding.AsisF1.text.toString()
                                            .toLong() && data.MA_M_1!! >= binding.AsisM1.text.toString()
                                            .toLong()
                                    ) {
                                        Save()
                                    } else {
                                        val text = "Asistencia invalida,"+data.Modalidad1 +" no puede ser mayor a su MA"
                                        val duration = Toast.LENGTH_LONG
                                        val toast =
                                            Toast.makeText(context, text, duration)
                                        toast.show()
                                    }
                                }else{
                                    val text = "Debe Rellenar Todos Los Campos"
                                    val duration = Toast.LENGTH_SHORT
                                    val toast = Toast.makeText(context, text, duration)
                                    toast.show()
                                }
                            }
                        }
                        2 ->{
                            if (data.MA_F_2 != null){
                                if(binding.AsisF1.text.isNotEmpty() && binding.AsisM1.text.isNotEmpty()
                                    && binding.AsisF2.text.isNotEmpty() && binding.AsisM2.text.isNotEmpty()){
                                    if (data.MA_F_1!! >= binding.AsisF1.text.toString().toLong() && data.MA_M_1!! >= binding.AsisM1.text.toString().toLong()
                                        && data.MA_F_2!! >= binding.AsisF2.text.toString().toLong() && data.MA_M_2!! >= binding.AsisM2.text.toString().toLong()) {
                                        Save2()
                                    } else {
                                        val text = "Asistencia invalida,"+data.Modalidad1+ " ó "+data.Modalidad2+" no puede ser mayor a su MA"
                                        val duration = Toast.LENGTH_LONG
                                        val toast = Toast.makeText(context, text, duration)
                                        toast.show()
                                    }
                                }else{
                                    val text = "Debe Rellenar Todos Los Campos"
                                    val duration = Toast.LENGTH_SHORT
                                    val toast = Toast.makeText(context, text, duration)
                                    toast.show()
                                }
                            }
                        }
                        3 ->{
                            if (data.MA_F_3 != null){
                                if(binding.AsisF1.text.isNotEmpty() && binding.AsisM1.text.isNotEmpty()
                                    && binding.AsisF2.text.isNotEmpty() && binding.AsisM2.text.isNotEmpty()
                                    && binding.AsisF3.text.isNotEmpty() && binding.AsisM3.text.isNotEmpty()) {
                                    if (data.MA_F_1!! >= binding.AsisF1.text.toString().toLong()
                                        && data.MA_M_1!! >= binding.AsisM1.text.toString().toLong()
                                        && data.MA_F_2!! >= binding.AsisF2.text.toString().toLong()
                                        && data.MA_M_2!! >= binding.AsisM2.text.toString().toLong()
                                        && data.MA_F_3!! >= binding.AsisF3.text.toString().toLong()
                                        && data.MA_M_3!! >= binding.AsisM3.text.toString().toLong()
                                    ) {
                                        Save3()
                                    } else {
                                        val text = "Asistencia invalida,"+data.Modalidad1+ " ó "+data.Modalidad2+"ó"+data.Modalidad3+" no puede ser mayor a su MA"
                                        val duration = Toast.LENGTH_SHORT

                                        val toast =
                                            Toast.makeText(context, text, duration)
                                        toast.show()
                                    }
                                }else{
                                    val text = "Debe Rellenar Todos Los Campos"
                                    val duration = Toast.LENGTH_SHORT
                                    val toast = Toast.makeText(context, text, duration)
                                    toast.show()
                                }
                            }
                        }
                        4 ->{
                            if (data.MA_F_4 != null){
                                if(binding.AsisF1.text.isNotEmpty() && binding.AsisM1.text.isNotEmpty()
                                    && binding.AsisF2.text.isNotEmpty() && binding.AsisM2.text.isNotEmpty()
                                    && binding.AsisF3.text.isNotEmpty() && binding.AsisM3.text.isNotEmpty()
                                    && binding.AsisF4.text.isNotEmpty() && binding.AsisM4.text.isNotEmpty()) {
                                    if (data.MA_F_1!! >= binding.AsisF1.text.toString().toLong()
                                        && data.MA_M_1!! >= binding.AsisM1.text.toString().toLong()
                                        && data.MA_F_2!! >= binding.AsisF2.text.toString().toLong()
                                        && data.MA_M_2!! >= binding.AsisM2.text.toString().toLong()
                                        && data.MA_F_3!! >= binding.AsisF3.text.toString().toLong()
                                        && data.MA_M_3!! >= binding.AsisM3.text.toString().toLong()
                                        && data.MA_F_4!! >= binding.AsisF4.text.toString().toLong()
                                        && data.MA_M_4!! >= binding.AsisM4.text.toString().toLong()
                                    ) {
                                        Save4()
                                    } else {
                                        val text = "Asistencia invalida,"+data.Modalidad1+ " ó "+data.Modalidad2+"ó"+data.Modalidad3+"ó"+data.Modalidad4+" no puede ser mayor a su MA"
                                        val duration = Toast.LENGTH_LONG

                                        val toast =
                                            Toast.makeText(context, text, duration)
                                        toast.show()
                                    }
                                }else{

                                }
                            }
                        }
                        else ->{
                            val text = "Ha ocurrido un error"
                            val duration = Toast.LENGTH_SHORT
                            val toast = Toast.makeText(context, text, duration)
                            toast.show()
                        }

                    }
                }

            }

    }
    private fun Save2(){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var datos = hashMapOf(
            "Codigo" to binding.CodEst.text.toString(),
            "Dependencia1" to binding.Dependencia.text.toString(),
            "Dependencia2" to binding.Dependencia2.text.toString(),
            "Nombre" to binding.NameCenter.text.toString(),
            "Modalidad1" to binding.Modalidad1.text.toString(),
            "MA_F_1" to binding.AsisF1.text.toString().toLong(),
            "MA_M_1" to binding.AsisM1.text.toString().toLong(),
            "MA_F_2" to binding.AsisF2.text.toString().toLong(),
            "Modalidad2" to binding.Modalidad2.text.toString(),
            "MA_M_2" to binding.AsisM2.text.toString().toLong()
        )
        val sdf = SimpleDateFormat("ddMMyyyy")
        val currenDate=sdf.format(Date())
        val fecha=currenDate.toString()
        val c= Calendar.getInstance()
        val hora = c.get(Calendar.HOUR_OF_DAY)
        var turno: String
        val codigo=binding.CodEst.text.toString()
        //val minuto = c.get(Calendar.MINUTE)
        if (hora>=12){
            turno="vespertino"
        }
        else{
            turno="matutino"
        }

        db.collection("asistencia")
            .document(fecha)
            .collection(turno)
            .document(codigo).set(datos).addOnCompleteListener {
                val text = "Datos Guardados"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(context, text, duration)
                toast.show()
                atras()
            }

    }
    private fun Save3(){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var datos = hashMapOf(
            "Dependencia1" to binding.Dependencia.text.toString(),
            "Dependencia2" to binding.Dependencia2.text.toString(),
            "Dependencia3" to binding.Dependencia3.text.toString(),
            "Nombre" to binding.NameCenter.text.toString(),
            "Modalidad1" to binding.Modalidad1.text.toString(),
            "MA_F_1" to binding.AsisF1.text.toString().toLong(),
            "MA_M_1" to binding.AsisM1.text.toString().toLong(),
            "Modalidad2" to binding.Modalidad2.text.toString(),
            "MA_F_2" to binding.AsisF2.text.toString().toLong(),
            "MA_M_2" to binding.AsisM2.text.toString().toLong(),
            "Modalidad3" to binding.Modalidad1.text.toString(),
            "MA_F_3" to binding.AsisF3.text.toString().toLong(),
            "MA_M_3" to binding.AsisM3.text.toString().toLong()
        )
        val sdf = SimpleDateFormat("ddMMyyyy")
        val currenDate=sdf.format(Date())
        val fecha=currenDate.toString()
        val c= Calendar.getInstance()
        val hora = c.get(Calendar.HOUR_OF_DAY)
        var turno: String
        val codigo=binding.CodEst.text.toString()
        //val minuto = c.get(Calendar.MINUTE)
        if (hora>=12){
            turno="vespertino"
        }
        else{
            turno="matutino"
        }

        db.collection("asistencia")
            .document(fecha)
            .collection(turno)
            .document(codigo).set(datos).addOnCompleteListener {
                val text = "Datos Guardados"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(context, text, duration)
                toast.show()
                atras()
            }

    }
    private fun Save4(){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var datos = hashMapOf(
            "Dependencia1" to binding.Dependencia.text.toString(),
            "Dependencia2" to binding.Dependencia2.text.toString(),
            "Dependencia3" to binding.Dependencia3.text.toString(),
            "Dependencia4" to binding.Dependencia4.text.toString(),
            "Nombre" to binding.NameCenter.text.toString(),
            "Modalidad1" to binding.Modalidad1.text.toString(),
            "MA_F_1" to binding.AsisF1.text.toString().toLong(),
            "MA_M_1" to binding.AsisM1.text.toString().toLong(),
            "Modalidad2" to binding.Modalidad2.text.toString(),
            "MA_F_2" to binding.AsisF2.text.toString().toLong(),
            "MA_M_2" to binding.AsisM2.text.toString().toLong(),
            "Modalidad3" to binding.Modalidad1.text.toString(),
            "MA_F_3" to binding.AsisF3.text.toString().toLong(),
            "MA_M_3" to binding.AsisM3.text.toString().toLong(),
            "Modalidad4" to binding.Modalidad1.text.toString(),
            "MA_F_4" to binding.AsisF4.text.toString().toLong(),
            "MA_M_4" to binding.AsisM4.text.toString().toLong()
        )
        val sdf = SimpleDateFormat("ddMMyyyy")
        val currenDate=sdf.format(Date())
        val fecha=currenDate.toString()
        val c= Calendar.getInstance()
        val hora = c.get(Calendar.HOUR_OF_DAY)
        var turno: String
        val codigo=binding.CodEst.text.toString()
        //val minuto = c.get(Calendar.MINUTE)
        if (hora>=12){
            turno="vespertino"
        }
        else{
            turno="matutino"
        }

        db.collection("asistencia")
            .document(fecha)
            .collection(turno)
            .document(codigo).set(datos).addOnCompleteListener {
                val text = "Datos Guardados"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(context, text, duration)
                toast.show()
                atras()
            }

    }
    private fun test(){
        val sdf = SimpleDateFormat("ddMMyyyy")
        val currenDate=sdf.format(Date())
        binding.textView.text=currenDate.toString()
    }


}