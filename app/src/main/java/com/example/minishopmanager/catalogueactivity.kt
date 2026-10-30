package com.example.minishopmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

data class Product(val name: String, val imageRes: Int)

class CatalogueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        val products = listOf(
            Product("Téléphone", R.drawable.telephone),
            Product("Casque Bluetooth", R.drawable.casque),
            Product("Montre connectée", R.drawable.montre),
            Product("Chargeur USB", R.drawable.chargeur),
            Product("Accessoires", R.drawable.accessoires),
            Product("Cosmétiques", R.drawable.cosmetiques)
        )

        val listView = findViewById<ListView>(R.id.listViewProduits)
        listView.adapter = ProductAdapter(products)

        listView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(
                this,
                "Produit sélectionné : ${products[position].name}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    inner class ProductAdapter(private val items: List<Product>) : BaseAdapter() {
        override fun getCount() = items.size
        override fun getItem(position: Int) = items[position]
        override fun getItemId(position: Int) = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView
                ?: LayoutInflater.from(this@CatalogueActivity)
                    .inflate(R.layout.item_product, parent, false)

            val product = items[position]
            view.findViewById<ImageView>(R.id.imgProduct).setImageResource(product.imageRes)
            view.findViewById<TextView>(R.id.tvProductName).text = product.name
            return view
        }
    }
}