package com.ygtcomp.harcamatakipygt

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.ygtcomp.harcamatakipygt.databinding.FragmentIsimDegistirBinding

class IsimDegistirFragment : Fragment() {

    private var _binding: FragmentIsimDegistirBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIsimDegistirBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var cinsiyet: Int? = null

        binding.radioGroup.setOnCheckedChangeListener { group, kontrolId ->
            cinsiyet = when (kontrolId) {
                R.id.radioButtonErkek -> {
                    1
                }
                R.id.radioButtonKadin -> {
                    2
                }
                else -> {
                    3
                }
            }
        }

        binding.buttonKaydet.setOnClickListener {
            if (binding.editTextKullaniciAdi.text.isNotEmpty() && binding.radioGroup.checkedRadioButtonId != -1) {
                val userName = binding.editTextKullaniciAdi.text.toString()

                val sharedPreferences = requireActivity().getSharedPreferences(
                    "Name",
                    Context.MODE_PRIVATE
                )
                val editor = sharedPreferences.edit()
                editor.putString("name", userName)
                editor.putInt("cinsiyet", cinsiyet!!)
                editor.apply()

                val action = IsimDegistirFragmentDirections.actionIsimDegistirFragmentToHomeFragment()
                Navigation.findNavController(it).navigate(action)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Lütfen Bütün Alanları Doldurunuz!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
