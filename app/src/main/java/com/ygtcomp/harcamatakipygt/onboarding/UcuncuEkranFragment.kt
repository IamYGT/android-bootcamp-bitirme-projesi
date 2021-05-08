package com.ygtcomp.harcamatakipygt.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ygtcomp.harcamatakipygt.R
import com.ygtcomp.harcamatakipygt.databinding.FragmentUcuncuEkranBinding


class UcuncuEkranFragment : Fragment() {
    private var _binding: FragmentUcuncuEkranBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUcuncuEkranBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.lottie.setAnimation("thirdscreenanimation.json")
        binding.lottie.playAnimation()

        binding.buttonBitir.setOnClickListener {
            findNavController().navigate(R.id.action_gorunumSayfasiFragment_to_homeFragment)
            onBoardingFinished()
        }

        return view
    }

    private fun onBoardingFinished(){
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}