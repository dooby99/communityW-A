package com.example.plzproject

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.plzproject.boardDetail.DeleteRetrofitWork
import com.example.plzproject.boardDetail.EditRetrofitWork
import com.example.plzproject.databinding.ActivityDetailViewBinding
import org.jsoup.Jsoup

class DetailViewActivity : AppCompatActivity(), View.OnClickListener {
    private val binding by lazy { ActivityDetailViewBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)
        setContentView(binding.root)

        val linkId = intent.getStringExtra("linkId") ?: ""

        binding.editBtn.setOnClickListener {
            val edit = EditRetrofitWork(this, this)
            edit.work(linkId)

        }
        binding.deleteBtn.setOnClickListener {
            val delete = DeleteRetrofitWork(this, this)
            delete.work(linkId)
        }
        // Get the html data from the Intent
        val htmlData = intent.getStringExtra("htmlData") ?: ""

        // Parse the HTML string with Jsoup
        val doc = Jsoup.parse(htmlData)

        // Extract the desired values
        val titleText = doc.select("h3 > span").text()
        val writerText = doc.select(".memberInfo > .member").text()
        val dateText = doc.select(".memberInfo > .localDateTime").text()
        val contentText = doc.select(".content > span").text()


        // Extract image URL and content text

        var imgUrl: String? = null
        doc.select(".content > div").forEach { div ->
            imgUrl = div.selectFirst("img.getImg")?.attr("src")
            if (imgUrl != null) {
                return@forEach  // replaces 'break'
            }
        }

        findViewById<TextView>(R.id.View_title).text = titleText
        findViewById<TextView>(R.id.View_Writer).text = writerText
        findViewById<TextView>(R.id.text_date).text = dateText
        findViewById<TextView>(R.id.View_Content).text = contentText


        Glide.with(this)
            .load(imgUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false;
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false;
                }
            })
            .into(findViewById(R.id.imageView2))

        findViewById<TextView>(R.id.View_Content).text = contentText

        // Check for delete and edit links and set button visibility accordingly
        val deleteLinkExists = doc.selectFirst("span > a[href*=delete]") != null
        val editLinkExists = doc.selectFirst("span > a[href*=edit]") != null

        findViewById<Button>(R.id.delete_btn).visibility =
            if (deleteLinkExists) View.VISIBLE else View.GONE

        findViewById<Button>(R.id.edit_btn).visibility =
            if (editLinkExists) View.VISIBLE else View.GONE

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}