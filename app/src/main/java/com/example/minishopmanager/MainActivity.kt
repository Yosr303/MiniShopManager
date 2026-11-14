package com.example.minishopmanager

import android.content.ContentValues
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
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val noms = ArrayList<String>()
    private val prix = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bouton Suivant (TP1)
        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Catalogue chargé depuis produits.json
        lireProduitsDepuisJSON()

        val icons = listOf(
            R.drawable.ic_phone,
            android.R.drawable.ic_lock_silent_mode_off,
            android.R.drawable.ic_menu_recent_history,
            android.R.drawable.ic_lock_idle_charging,
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_camera
        )

        val listView = findViewById<ListView>(R.id.listViewProduits)
        val adapter = object : ArrayAdapter<String>(
            this, R.layout.item_product, R.id.tvProduct, noms
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                view.findViewById<ImageView>(R.id.imgProduct).setImageResource(icons[position])
                return view
            }
        }
        listView.adapter = adapter

        // Clic sur un produit : Toast + ligne dans la table panier
        listView.setOnItemClickListener { _, _, position, _ ->
            val dbHelper = DBHelper(this)
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("nom", noms[position])
                put("prix", prix[position])
            }
            db.insert("panier", null, values)
            db.close()

            Toast.makeText(this, "${noms[position]} ajouté au panier", Toast.LENGTH_SHORT).show()
        }

        Log.d("LIFECYCLE", "onCreate appelé")
    }

    private fun lireProduitsDepuisJSON() {
        val jsonString = assets.open("produits.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONObject(jsonString).getJSONArray("produits")
        for (i in 0 until jsonArray.length()) {
            val p = jsonArray.getJSONObject(i)
            noms.add(p.getString("nom"))
            prix.add(p.getInt("prix"))
        }
    }
}