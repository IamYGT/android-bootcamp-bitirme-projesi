package com.ygtcomp.harcamatakipygt

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ygtcomp.harcamatakipygt.data.HarcamaDataBase
import com.ygtcomp.harcamatakipygt.databinding.FragmentHarcamaDetayBinding
import java.text.DecimalFormat


class HarcamaDetayFragment : Fragment() {

    private var _binding: FragmentHarcamaDetayBinding? = null
    private val binding get() = _binding!!
    val args: HarcamaDetayFragmentArgs by navArgs()
    private lateinit var db: HarcamaDataBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHarcamaDetayBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = HarcamaDataBase.getDatabase(requireContext())

        val currentModel = db.harcamaDao().harcamaGetir(args.harcamaId)

        binding.imageView.setImageResource(
            when(currentModel.faturaTuru){
                1 -> R.drawable.bill
                2 -> R.drawable.home
                else -> R.drawable.shopping_bag
            }
        )

        val sharedPreferences = requireActivity().getSharedPreferences("Name", Context.MODE_PRIVATE)
        val lastClickedItem = sharedPreferences.getInt("lastClickedItem",1)

        val dataTl = sharedPreferences.getFloat("tl", 1F)
        val dataSterlin = sharedPreferences.getFloat("sterlin", 1F)
        val dataDolar = sharedPreferences.getFloat("dolar", 1F)
        val dataEuro = sharedPreferences.getFloat("euro", 1F)

        val list = ArrayList<Double>()
        list.clear()
        list.add(dataTl.toDouble())
        list.add(dataSterlin.toDouble())
        list.add(dataDolar.toDouble())
        list.add(dataEuro.toDouble())

        when(lastClickedItem){
            1 -> {//Tl
                when(currentModel.paraCinsi){
                    1 -> {//Tl
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(currentModel.harcamaMiktari)} Türk Lirası"
                    }
                    2 -> {//Sterlin
                        val x = 1/list[1]
                        val deger = x*list[0]
                        val toplam = currentModel.harcamaMiktari * deger
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(toplam)} Türk Lirası"
                    }
                    3 -> {//Dolar
                        val x = 1/list[2]
                        val deger = x*list[0]
                        val toplam = currentModel.harcamaMiktari * deger
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(toplam)} Türk Lirası"
                    }
                    4 -> { // Euro
                        val tlToEuro = 1*list[0]
                        val deger = currentModel.harcamaMiktari * tlToEuro
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(deger)} Türk Lirası"
                    }
                }
            }
            2 -> {//Sterlin
                when(currentModel.paraCinsi){
                    1 -> {//Tl
                        val x = 1/list[0]
                        val deger = x * list[1]
                        val toplam = currentModel.harcamaMiktari * deger
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(toplam)} Sterlin"
                    }
                    2 -> {//Sterlin
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(currentModel.harcamaMiktari)} Sterlin"
                    }
                    3 -> {//Dolar
                        val x = 1/list[2]
                        val deger = x * list[1]
                        val toplam = currentModel.harcamaMiktari * deger
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(toplam)} Sterlin"
                    }
                    4 -> { // Euro
                        val sterlinToEuro = 1*list[1]
                        val toplam = currentModel.harcamaMiktari * sterlinToEuro
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(toplam)} Sterlin"
                    }
                }
            }
            3 -> {
                when(currentModel.paraCinsi){
                    1 -> {//Tl
                        val x = 1/list[0]
                        val deger = x * list[2]
                        val toplam = currentModel.harcamaMiktari * deger
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(toplam)} Dolar"
                    }
                    2 -> {//Sterlin
                        val x = 1/list[1]
                        val deger = x * list[2]
                        val toplam = currentModel.harcamaMiktari * deger
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(toplam)} Dolar"
                    }
                    3 -> {//Dolar
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(currentModel.harcamaMiktari)} Dolar"
                    }
                    4 -> {//Euro
                        val dolarToEuro = 1*list[2]
                        val toplam = currentModel.harcamaMiktari * dolarToEuro
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(toplam)} Dolar"
                    }
                }
            }
            4 -> {
                when(currentModel.paraCinsi){
                    1 -> {//Tl
                        val x = 1/list[0]
                        val deger = x * list[3]
                        val toplam = currentModel.harcamaMiktari * deger
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(toplam)} Euro"
                    }
                    2 -> {//Sterlin
                        val x = 1/list[1]
                        val deger = x * list[3]
                        val toplam = currentModel.harcamaMiktari * deger
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(toplam)} Euro"
                    }
                    3 -> {//Dolar
                        val x = 1/list[2]
                        val deger = x * list[3]
                        val toplam = currentModel.harcamaMiktari * deger
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(toplam)} Euro"
                    }
                    4 -> {//Euro
                        binding.textViewFiyat.text = "${DecimalFormat(".#").format(currentModel.harcamaMiktari)} Euro"
                    }
                }
            }
        }

        binding.textViewOdeme.text = currentModel.aciklama

        binding.buttonSil.setOnClickListener {
            deleteExpense()
            findNavController().navigate(R.id.action_harcamaDetayFragment_to_homeFragment)
            Toast.makeText(requireContext(),"Başarıyla silindi!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteExpense(){
        val x = db.harcamaDao().harcamaGetir(args.harcamaId)
        db.harcamaDao().harcamaSil(x)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}