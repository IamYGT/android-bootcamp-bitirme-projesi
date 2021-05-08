package com.ygtcomp.harcamatakipygt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ygtcomp.harcamatakipygt.HomeFragmentDirections
import com.ygtcomp.harcamatakipygt.R
import com.ygtcomp.harcamatakipygt.databinding.HarcamaRecylerRowBinding
import com.ygtcomp.harcamatakipygt.model.HarcamaModel
import java.text.DecimalFormat

class HarcamaRecylerAdapter(private val paraCinsi: Int, private val list: ArrayList<Double>) :
    RecyclerView.Adapter<HarcamaRecylerAdapter.HarcamaViewHolder>() {

    private var harcamaList = emptyList<HarcamaModel>()

    class HarcamaViewHolder(val itemBinding: HarcamaRecylerRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HarcamaViewHolder {
        val binding = HarcamaRecylerRowBinding.inflate(LayoutInflater.from(parent.context))
        return HarcamaViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HarcamaViewHolder, position: Int) {
        val currentItem = harcamaList[position]
        val currentPrice = currentItem.harcamaMiktari
        holder.itemBinding.textViewBaslik.text = currentItem.aciklama
        when (currentItem.faturaTuru) {
            1 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.bill)
            2 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.home)
            3 -> holder.itemBinding.imageViewImage.setImageResource(R.drawable.shopping_bag)
        }
        when (paraCinsi) {
            1 -> {//TL
                when (currentItem.paraCinsi) {
                    1 -> {//TL
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(currentPrice)} TL"
                    }
                    2 -> {//Sterlin
                        val x = 1 / list[1]
                        val value = x * list[0]
                        val total = currentPrice * value
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} TL"
                    }
                    3 -> {//Dolar
                        val x = 1 / list[2]
                        val value = x * list[0]
                        val total = currentPrice * value
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} TL"
                    }
                    4 -> {//Euro
                        val tl_1_kac_euro = 1 * list[0]
                        val total = currentPrice * tl_1_kac_euro
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} TL"
                    }
                }
            }
            2 -> {//Sterlin
                when (currentItem.paraCinsi) {
                    1 -> {//TL
                        val x = 1 / list[0]
                        val value = x * list[1]
                        val total = currentPrice * value
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} Sterlin"
                    }
                    2 -> {//Sterlin
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(currentPrice)} Sterlin"
                    }
                    3 -> { //Dolar
                        val x = 1 / list[2]
                        val value = x * list[1]
                        val total = currentPrice * value
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} Sterlin"
                    }
                    4 -> {//Euro
                        val sterlin1_kac_euro = 1 * list[1]
                        val total = currentPrice * sterlin1_kac_euro
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} Sterlin"
                    }
                }
            }
            3 -> {//Dolar
                when (currentItem.paraCinsi) {
                    1 -> { //TL
                        val x = 1 / list[0]
                        val value = x * list[2]
                        val total = currentPrice * value
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} Dolar"
                    }
                    2 -> { //Sterlin
                        val x = 1 / list[1]
                        val value = x * list[2]
                        val total = currentPrice * value
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} Dolar"
                    }
                    3 -> { //Dolar
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(currentPrice)} Dolar"
                    }
                    4 -> { //Euro
                        val dolar1_kac_euro = 1 * list[2]
                        val total = currentPrice * dolar1_kac_euro
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} Dolar"
                    }
                }
            }
            4 -> {//Euro
                when (currentItem.paraCinsi) {
                    1 -> {//TL
                        val x = 1 / list[0]
                        val value = x * list[3]
                        val total = currentPrice * value
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} Euro"
                    }
                    2 -> {//Sterlin
                        val x = 1 / list[1]
                        val value = x * list[3]
                        val total = currentPrice * value
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} Euro"
                    }
                    3 -> {//Dolar
                        val x = 1 / list[2]
                        val value = x * list[3]
                        val total = currentPrice * value
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(total)} Euro"
                    }
                    4 -> {//Euro
                        holder.itemBinding.textViewFiyat.text =
                            "${DecimalFormat("##.#").format(currentPrice)} Euro"
                    }
                }
            }
        }

        holder.itemBinding.cardView.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToHarcamaDetayFragment(currentItem.harcamaId)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return harcamaList.size
    }

    fun setData(harcama: List<HarcamaModel>) {
        this.harcamaList = harcama
        notifyDataSetChanged()
    }

}