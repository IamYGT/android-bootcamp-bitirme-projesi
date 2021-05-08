package com.ygtcomp.harcamatakipygt.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.ygtcomp.harcamatakipygt.R
import com.ygtcomp.harcamatakipygt.databinding.FragmentIlkEkranBinding

class IlkEkranFragment : Fragment() {

    private var _binding: FragmentIlkEkranBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIlkEkranBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.lottie.setAnimation("firstscreenanimation.json")
        binding.lottie.playAnimation()

        val viewPager = activity?.findViewById<ViewPager2>(R.id.gorunumSayfasi)

        binding.buttonGec.setOnClickListener {
            viewPager?.currentItem = 1
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

