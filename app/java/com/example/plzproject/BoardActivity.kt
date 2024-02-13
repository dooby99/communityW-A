package com.example.plzproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.plzproject.boardList.BoardAdapter
import com.example.plzproject.boardList.BoardData
import com.example.plzproject.databinding.ActivityBoardBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class BoardActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy { ActivityBoardBinding.inflate(layoutInflater) }

    private val writeBtn = View.OnClickListener {
        val intent = Intent(this, WriteActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        setContentView(binding.root)

        val dividerItemDecoration =
            DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.refreshLayout01)


        swipeRefreshLayout.setOnRefreshListener {
            loadRecyclerViewData()
            binding.refreshLayout01.isRefreshing = false
        }

        loadRecyclerViewData()

        binding.writeButton.setOnClickListener(writeBtn)




        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 백그라운드 스레드에서 데이터를 가져옴
        GlobalScope.launch(Dispatchers.IO) {
            val dataList: List<BoardData> = parseHtmlData() // HTML 데이터 파싱 함수 호출

            // UI 스레드에서 RecyclerView 어댑터 설정
            runOnUiThread {
                val adapter = BoardAdapter(dataList)
                recyclerView.adapter = adapter
            }
        }
    }

    private fun loadRecyclerViewData() {

        // Divider Item Decoration
        val dividerItemDecoration =
            DividerItemDecoration(applicationContext, LinearLayoutManager(this).orientation)

        // Add item decoration to RecyclerView
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        // Set layout manager for RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // 백그라운드 스레드에서 데이터를 가져옴
        GlobalScope.launch(Dispatchers.IO) {
            val dataList: List<BoardData> = parseHtmlData() // HTML 데이터 파싱 함수 호출

            // UI 스레드에서 RecyclerView 어댑터 설정
            runOnUiThread {
                val adapter = BoardAdapter(dataList)
                binding.recyclerView.adapter = adapter
            }
        }
    }

    private fun parseHtmlData(): List<BoardData> {
        val dataList = mutableListOf<BoardData>()

        try {
            val url = "http://13.124.165.16:8080/"
            val document: Document = Jsoup.connect(url).get()
            val table: Element? = document.select("table").first()
            val rows: Elements? = table?.select("tr")

            for (row in rows?.subList(1, rows.size) ?: emptyList()) {
                val cells: Elements = row.select("td")
                val category = cells[0].text()
                val titleElement: Element? = cells[1].select("a").first()
                val linkHref = titleElement?.attr("href") ?: ""
                val title = titleElement?.text() ?: ""
                val author = cells[2].text()
                val date = cells[3].text()

                val boardData = BoardData(category, title, author, date, linkHref)
                dataList.add(boardData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dataList
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}
