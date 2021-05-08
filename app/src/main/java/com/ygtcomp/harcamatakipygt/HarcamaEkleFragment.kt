package com.ygtcomp.harcamatakipygt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ygtcomp.harcamatakipygt.data.HarcamaDataBase
import com.ygtcomp.harcamatakipygt.databinding.FragmentHarcamaEkleBinding
import com.ygtcomp.harcamatakipygt.model.HarcamaModel


class HarcamaEkleFragment : Fragment() {

    private var _binding: FragmentHarcamaEkleBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: HarcamaDataBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHarcamaEkleBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = HarcamaDataBase.getDatabase(requireContext())

        var harcamaDegeri: Int? = null
        var paraCinsi: Int? = null

        binding.radioGroupHarcamaTurleri.setOnCheckedChangeListener { group, checkedId ->
            harcamaDegeri = when(checkedId){
                R.id.radioButtonFatura -> 1
                R.id.radioButtonKira -> 2
                else -> 3//Diger
            }
        }

        binding.radioGroupParaCinsleri.setOnCheckedChangeListener { group, checkedId ->
            paraCinsi = when(checkedId){
                R.id.radioButtonTl -> 1
                R.id.radioButtonSterlin -> 2
                R.id.radioButtonDolar -> 3
                else -> 4//Euro
            }
        }

        binding.buttonEkleHarcama.setOnClickListener {
            if (binding.editTextHarcamaMiktari.text.isNotEmpty() && binding.radioGroupHarcamaTurleri.checkedRadioButtonId != -1
                && binding.editTextAciklama.text.isNotEmpty() && binding.radioGroupParaCinsleri.checkedRadioButtonId != -1){
                val aciklama = binding.editTextAciklama.text.toString()
                val harcamaMiktari = binding.editTextHarcamaMiktari.text.toString().toDouble()
                insertData(aciklama,harcamaMiktari.toInt(),harcamaDegeri!!,paraCinsi!!)
                findNavController().navigate(R.id.action_harcamaEkleFragment_to_homeFragment)
                Toast.makeText(requireContext(),"Başarıyla kaydedildi!",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(),"Bütün Alanları Doldurunuz",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun insertData(aciklama: String, harcamaMiktari: Int, faturaTuru: Int, paraCinsi: Int){
        val harcama = HarcamaModel(0,aciklama,harcamaMiktari,faturaTuru,paraCinsi)
        db.harcamaDao().harcamaEkle(harcama)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

