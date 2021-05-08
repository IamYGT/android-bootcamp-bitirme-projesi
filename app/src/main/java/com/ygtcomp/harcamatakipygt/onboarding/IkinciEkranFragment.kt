package com.ygtcomp.harcamatakipygt.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ygtcomp.harcamatakipygt.R
import com.ygtcomp.harcamatakipygt.databinding.FragmentIkinciEkranBinding

class IkinciEkranFragment : Fragment() {
    private var _binding: FragmentIkinciEkranBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIkinciEkranBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewPager = activity?.findViewById<ViewPager2>(R.id.gorunumSayfasi)

        binding.lottie.setAnimation("secondscreenanimation.json")
        binding.lottie.playAnimation()

        binding.buttonGec.setOnClickListener {
            viewPager?.currentItem = 2
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

