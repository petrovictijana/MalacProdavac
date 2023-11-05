package com.example.batmobile.fragments.neulogovan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.batmobile.R

class InformationNeulogovanFragment : Fragment() {
    private lateinit var dugme1 : Button
    private lateinit var dugme2 : Button
    private lateinit var dugme3 : Button
    private lateinit var dugme4 : Button

    private lateinit var prvaInformacija : LinearLayout
    private lateinit var drugaInformacija : LinearLayout
    private lateinit var trecaInformacija : LinearLayout
    private lateinit var cetvrtaInformacija : LinearLayout

    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view =  inflater.inflate(R.layout.fragment_information_neulogovan, container, false)

        dugme1 = view.findViewById<Button>(R.id.dugme1)
        dugme2 = view.findViewById<Button>(R.id.dugme2)
        dugme3 = view.findViewById<Button>(R.id.dugme3)
        dugme4 = view.findViewById<Button>(R.id.dugme4)

        prvaInformacija = view.findViewById<LinearLayout>(R.id.prvaInformacija)
        drugaInformacija = view.findViewById<LinearLayout>(R.id.drugaInformacija)
        trecaInformacija = view.findViewById<LinearLayout>(R.id.trecaInformacija)
        cetvrtaInformacija = view.findViewById<LinearLayout>(R.id.cetvrtaInformacija)

        dugme1.setOnClickListener {
            prikaziPrvuInformaciju()
            promeniBojuDugmeta(dugme1)}
        dugme2.setOnClickListener {
            prikaziDruguInformaciju()
            promeniBojuDugmeta(dugme2)}
        dugme3.setOnClickListener {
            prikaziTrecuInformaciju()
            promeniBojuDugmeta(dugme3)}
        dugme4.setOnClickListener {
            prikaziCetvrtuInformaciju()
            promeniBojuDugmeta(dugme4)}

        return view
    }

    private fun prikaziPrvuInformaciju() {
        prvaInformacija.visibility = View.VISIBLE
        drugaInformacija.visibility = View.GONE
        trecaInformacija.visibility = View.GONE
        cetvrtaInformacija.visibility = View.GONE
    }

    private fun prikaziDruguInformaciju() {
        prvaInformacija.visibility = View.GONE
        drugaInformacija.visibility = View.VISIBLE
        trecaInformacija.visibility = View.GONE
        cetvrtaInformacija.visibility = View.GONE
    }

    private fun prikaziTrecuInformaciju() {
        prvaInformacija.visibility = View.GONE
        drugaInformacija.visibility = View.GONE
        trecaInformacija.visibility = View.VISIBLE
        cetvrtaInformacija.visibility = View.GONE
    }

    private fun prikaziCetvrtuInformaciju() {
        prvaInformacija.visibility = View.GONE
        drugaInformacija.visibility = View.GONE
        trecaInformacija.visibility = View.GONE
        cetvrtaInformacija.visibility = View.VISIBLE
    }

    private fun promeniBojuDugmeta(dugme: Button) {
        dugme1.setBackgroundResource(R.drawable.empty_button)
        dugme1.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        dugme2.setBackgroundResource(R.drawable.empty_button)
        dugme2.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        dugme3.setBackgroundResource(R.drawable.empty_button)
        dugme3.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        dugme4.setBackgroundResource(R.drawable.empty_button)
        dugme4.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        dugme.setBackgroundResource(R.drawable.full_fill_button)
        dugme.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

    }
}