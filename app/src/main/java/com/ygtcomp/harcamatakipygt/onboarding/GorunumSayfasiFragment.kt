package com.ygtcomp.harcamatakipygt.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ygtcomp.harcamatakipygt.databinding.FragmentGorunumSayfasiBinding


class GorunumSayfasiFragment : Fragment() {
    private var _binding: FragmentGorunumSayfasiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGorunumSayfasiBinding.inflate(inflater,container,false)
        val view = binding.root

        val fragmentList = arrayListOf<Fragment>(
            IlkEkranFragment(),
            IkinciEkranFragment(),
            UcuncuEkranFragment()
        )

        val adapter = GorunumSayfasiAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)

        binding.gorunumSayfasi.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}