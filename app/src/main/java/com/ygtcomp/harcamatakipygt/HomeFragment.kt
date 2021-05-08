package com.ygtcomp.harcamatakipygt

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ygtcomp.harcamatakipygt.adapter.HarcamaRecylerAdapter
import com.ygtcomp.harcamatakipygt.data.HarcamaDataBase
import com.ygtcomp.harcamatakipygt.databinding.FragmentHomeBinding
import com.ygtcomp.harcamatakipygt.model.HarcamaModel
import com.ygtcomp.harcamatakipygt.viewmodel.HomeViewModel
import java.text.DecimalFormat


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var paraBirimDataList: ArrayList<Double>
    private lateinit var harcamaList: List<HarcamaModel>
    private lateinit var db: HarcamaDataBase
    private lateinit var adapter: HarcamaRecylerAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = HarcamaDataBase.getDatabase(requireContext())
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        harcamaList = db.harcamaDao().tumVeriyiOku()
        paraBirimDataList = ArrayList()
        sharedPreferences = requireActivity().getSharedPreferences("Name", Context.MODE_PRIVATE)

        workPlanner(sharedPreferences, harcamaList, paraBirimDataList)

        binding.buttonTl.setOnClickListener {
            renkDegistir(it as Button)
            sonTiklamaylaDegistir(sharedPreferences, 1)
            paraBirimDegeriDegis(1)
        }
        binding.buttonSterlin.setOnClickListener {
            renkDegistir(it as Button)
            sonTiklamaylaDegistir(sharedPreferences, 2)
            paraBirimDegeriDegis(2)
        }
        binding.buttonDolar.setOnClickListener {
            renkDegistir(it as Button)
            sonTiklamaylaDegistir(sharedPreferences, 3)
            paraBirimDegeriDegis(3)
        }
        binding.buttonEuro.setOnClickListener {
            renkDegistir(it as Button)
            sonTiklamaylaDegistir(sharedPreferences, 4)
            paraBirimDegeriDegis(4)
        }
        binding.textViewKullaniciAdi.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToIsimDegistirFragment()
            Navigation.findNavController(it).navigate(action)
        }
        binding.HarcamaEklemeButon.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToHarcamaEkleFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun paraBirimDegeriDegis(paraCinsi: Int) {
        when (paraCinsi) {
            1 -> {
                val adapter = HarcamaRecylerAdapter(1, paraBirimDataList)
                adapter.setData(db.harcamaDao().tumVeriyiOku())
                binding.recyclerView.adapter = adapter
                val toplamHarcama = hesaplama(1, paraBirimDataList, harcamaList)
                binding.textViewHarcamaniz.text = "${DecimalFormat("#.#").format(toplamHarcama)} ₺"
            }
            2 -> {
                val adapter = HarcamaRecylerAdapter(2, paraBirimDataList)
                adapter.setData(db.harcamaDao().tumVeriyiOku())
                binding.recyclerView.adapter = adapter
                val toplamHarcama = hesaplama(2, paraBirimDataList, harcamaList)
                binding.textViewHarcamaniz.text = "${DecimalFormat("#.#").format(toplamHarcama)} £"
            }
            3 -> {
                val adapter = HarcamaRecylerAdapter(3, paraBirimDataList)
                adapter.setData(db.harcamaDao().tumVeriyiOku())
                binding.recyclerView.adapter = adapter
                val toplamHarcama = hesaplama(3, paraBirimDataList, harcamaList)
                binding.textViewHarcamaniz.text = "${DecimalFormat("#.#").format(toplamHarcama)} $"
            }
            4 -> {
                val adapter = HarcamaRecylerAdapter(4, paraBirimDataList)
                adapter.setData(db.harcamaDao().tumVeriyiOku())
                binding.recyclerView.adapter = adapter
                val toplamHarcama = hesaplama(4, paraBirimDataList, harcamaList)
                binding.textViewHarcamaniz.text = "${DecimalFormat("#.#").format(toplamHarcama)} €"
            }
        }
    }
    private fun sonTiklamaylaDegistir(sharedPreferences: SharedPreferences, lastClickedItem: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("lastClickedItem", lastClickedItem)
        editor.apply()
    }
    private fun hesaplama(
        paraCinsi: Int,
        list: ArrayList<Double>,
        harcamaList: List<HarcamaModel>
    ): Double {
        var harcamaMiktari = 0.0
        for (i in harcamaList) {
            when (paraCinsi) {
                1 -> {//Tl
                    when (i.paraCinsi) {
                        1 -> {//Tl
                            harcamaMiktari += i.harcamaMiktari
                        }
                        2 -> {//Sterlin
                            val x = 1 / list[1]
                            val deger = x * list[0]
                            val toplam = i.harcamaMiktari * deger
                            harcamaMiktari += toplam
                        }
                        3 -> {//Dolar
                            val x = 1 / list[2]
                            val deger = x * list[0]
                            val toplam = i.harcamaMiktari * deger
                            harcamaMiktari += toplam
                        }
                        4 -> {//Euro
                            val tlToEuro = 1 * list[0]
                            val y = i.harcamaMiktari * tlToEuro
                            harcamaMiktari += y
                        }
                    }
                }
                2 -> {//Sterlin
                    when (i.paraCinsi) {
                        1 -> {//Tl
                            val x = 1 / list[0]
                            val deger = x * list[1]
                            val toplam = i.harcamaMiktari * deger
                            harcamaMiktari += toplam
                        }
                        2 -> {//Sterlin
                            harcamaMiktari += i.harcamaMiktari
                        }
                        3 -> {//Dolar
                            val x = 1 / list[2]
                            val deger = x * list[1]
                            val toplam = i.harcamaMiktari * deger
                            harcamaMiktari += toplam
                        }
                        4 -> {//Euro
                            val tlToEuro = 1 * list[1]
                            val y = i.harcamaMiktari * tlToEuro
                            harcamaMiktari += y
                        }
                    }
                }
                3 -> {//Dolar
                    when (i.paraCinsi) {
                        1 -> { // TL
                            val x = 1 / list[0]
                            val deger = x * list[2]
                            val toplam = i.harcamaMiktari * deger
                            harcamaMiktari += toplam
                        }
                        2 -> {//Sterlin
                            val x = 1 / list[1]
                            val deger = x * list[2]
                            val toplam = i.harcamaMiktari * deger
                            harcamaMiktari += toplam
                        }
                        3 -> {//Dolar
                            harcamaMiktari += i.harcamaMiktari
                        }
                        4 -> {//Euro
                            val tlToEuro = 1 * list[2]
                            val y = i.harcamaMiktari * tlToEuro
                            harcamaMiktari += y
                        }
                    }
                }
                4 -> {//Euro
                    when (i.paraCinsi) {
                        1 -> {//Tl
                            val x = 1 / list[0]
                            val deger = x * list[3]
                            val toplam = i.harcamaMiktari * deger
                            harcamaMiktari += toplam
                        }
                        2 -> {//Sterlin
                            val x = 1 / list[1]
                            val deger = x * list[3]
                            val toplam = i.harcamaMiktari * deger
                            harcamaMiktari += toplam
                        }
                        3 -> {//Dolar
                            val x = 1 / list[2]
                            val deger = x * list[3]
                            val toplam = i.harcamaMiktari * deger
                            harcamaMiktari += toplam
                        }
                        4 -> {//Euro
                            harcamaMiktari += i.harcamaMiktari
                        }
                    }
                }
            }
        }
        return harcamaMiktari
    }
    private fun renkDegistir(button: Button) {
        binding.buttonTl.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.buttonDolar.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.buttonSterlin.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.buttonEuro.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
    }
    private fun observeLiveData(sharedPreferences: SharedPreferences) {
        viewModel.data.observe(viewLifecycleOwner, { data ->
            data.let {
                val editor = sharedPreferences.edit()
                editor.putFloat("tl", it.rates.tRY.toFloat())
                editor.putFloat("sterlin", it.rates.gBP.toFloat())
                editor.putFloat("dolar", it.rates.uSD.toFloat())
                editor.putFloat("euro", it.rates.eUR.toFloat())
                editor.apply()
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel.yukleniyorMesaj.observe(viewLifecycleOwner, { loading ->
            loading?.let {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
        viewModel.hataMesaj.observe(viewLifecycleOwner, { error ->
            error?.let {
                if (it) {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    Toast.makeText(context, "Güncel veri alınamadı!", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    @SuppressLint("SetTextI18n")
    private fun workPlanner(
        sharedPreferences: SharedPreferences,
        harcamaList: List<HarcamaModel>,
        currencyDataList: ArrayList<Double>
    ) {
        val x = sharedPreferences.getInt("number", 0)
        if (x == 0) {
            val net = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = net.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if (isConnected) {
                viewModel.getData()
                observeLiveData(sharedPreferences)
            } else {
                Toast.makeText(context, "Internet Bağlantısı Sağlanamadı!", Toast.LENGTH_LONG)
                    .show()
            }
            val name = sharedPreferences.getString("name", "İsim Giriniz")
            val lastClickedItem = sharedPreferences.getInt("lastClickedItem", 1)
            val dataTl = sharedPreferences.getFloat("tl", 1F)
            val dataSterlin = sharedPreferences.getFloat("sterlin", 1F)
            val dataDolar = sharedPreferences.getFloat("dolar", 1F)
            val dataEuro = sharedPreferences.getFloat("euro", 1F)

            currencyDataList.clear()
            currencyDataList.add(dataTl.toDouble())
            currencyDataList.add(dataSterlin.toDouble())
            currencyDataList.add(dataDolar.toDouble())
            currencyDataList.add(dataEuro.toDouble())

            when (sharedPreferences.getInt("cinsiyet", 3)) {
                1 -> binding.textViewKullaniciAdi.text = "$name Bey"
                2 -> binding.textViewKullaniciAdi.text = "$name Hanım"
                3 -> binding.textViewKullaniciAdi.text = name
            }
            when (lastClickedItem) {
                1 -> binding.buttonTl.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                2 -> binding.buttonSterlin.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    )
                )
                3 -> binding.buttonDolar.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    )
                )
                else -> binding.buttonEuro.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    )
                )
            }
            adapter = HarcamaRecylerAdapter(lastClickedItem, currencyDataList)
            adapter.setData(harcamaList)
            val layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = adapter
            binding.recyclerView.setHasFixedSize(true)
            paraBirimDegeriDegis(lastClickedItem)
            val editor = sharedPreferences.edit()
            editor.putInt("number", 1)
            editor.apply()

        } else {
            val name = sharedPreferences.getString("name", "İsim Giriniz")
            val lastClickedItem = sharedPreferences.getInt("lastClickedItem", 1)
            val dataTl = sharedPreferences.getFloat("tl", 1F)
            val dataSterlin = sharedPreferences.getFloat("sterlin", 1F)
            val dataDolar = sharedPreferences.getFloat("dolar", 1F)
            val dataEuro = sharedPreferences.getFloat("euro", 1F)
            currencyDataList.clear()
            currencyDataList.add(dataTl.toDouble())
            currencyDataList.add(dataSterlin.toDouble())
            currencyDataList.add(dataDolar.toDouble())
            currencyDataList.add(dataEuro.toDouble())
            adapter = HarcamaRecylerAdapter(1, currencyDataList)
            adapter.setData(harcamaList)

            val layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = adapter
            binding.recyclerView.setHasFixedSize(true)

            when (sharedPreferences.getInt("cinsiyet", 3)) {
                1 -> binding.textViewKullaniciAdi.text = "$name Bey"
                2 -> binding.textViewKullaniciAdi.text = "$name Hanım"
                3 -> binding.textViewKullaniciAdi.text = name
            }
            paraBirimDegeriDegis(lastClickedItem)
            when (lastClickedItem) {
                1 -> binding.buttonTl.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.white
                    )
                )
                2 -> binding.buttonSterlin.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    )
                )
                3 -> binding.buttonDolar.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    )
                )
                else -> binding.buttonEuro.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.white
                    )
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
