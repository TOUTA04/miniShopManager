package com.example.minishopmanager

import android.content.ContentValues
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class CatalogueActivity : AppCompatActivity() {

    private val noms = ArrayList<String>()
    private val prix = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        lireProduitsDepuisJSON()

        val listView = findViewById<ListView>(R.id.listViewProduits)
        val affichage = noms.mapIndexed { i, n -> "$n - ${prix[i]} DT" }
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, affichage)

        listView.setOnItemClickListener { _, _, position, _ ->
            val db = DBHelper(this).writableDatabase
            val values = ContentValues().apply {
                put("nom", noms[position])
                put("prix", prix[position])
            }
            db.insert("panier", null, values)
            db.close()

            Toast.makeText(this, "${noms[position]} ajouté au panier", Toast.LENGTH_SHORT).show()
        }
    }

    private fun lireProduitsDepuisJSON() {
        val json = assets.open("produits.json").bufferedReader().use { it.readText() }
        val array = JSONObject(json).getJSONArray("produits")
        for (i in 0 until array.length()) {
            val p = array.getJSONObject(i)
            noms.add(p.getString("nom"))
            prix.add(p.getInt("prix"))
        }
    }
}