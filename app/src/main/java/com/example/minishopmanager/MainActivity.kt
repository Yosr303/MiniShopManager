package com.example.minishopmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bouton Suivant (TP1)
        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            Toast.makeText(this, "Bonjour Yosr !", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Catalogue de produits (TP2)
        val listView = findViewById<ListView>(R.id.listViewProduits)
        val products = resources.getStringArray(R.array.products)

        // Un logo par produit, dans le même ordre que la liste
        val icons = listOf(
            R.drawable.ic_phone,
            android.R.drawable.ic_lock_silent_mode_off,
            android.R.drawable.ic_menu_recent_history,
            android.R.drawable.ic_lock_idle_charging,
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_camera
        )

        // Adaptateur personnalisé : texte + logo pour chaque ligne
        val adapter = object : ArrayAdapter<String>(
            this, R.layout.item_product, R.id.tvProduct, products
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                view.findViewById<ImageView>(R.id.imgProduct).setImageResource(icons[position])
                return view
            }
        }
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Produit sélectionné : $item", Toast.LENGTH_SHORT).show()
        }

        Log.d("LIFECYCLE", "onCreate appelé")
    }
}