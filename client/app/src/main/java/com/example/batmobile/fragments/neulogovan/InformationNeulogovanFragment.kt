package com.example.batmobile.fragments.neulogovan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
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

        dugme1.setOnClickListener { prikaziPrvuInformaciju() }
        dugme2.setOnClickListener { prikaziDruguInformaciju() }
        dugme3.setOnClickListener { prikaziTrecuInformaciju() }
        dugme4.setOnClickListener { prikaziCetvrtuInformaciju() }

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

}