package com.example.plzproject.boardList


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plzproject.R
import com.example.plzproject.boardDetail.DetailRetrofitWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BoardAdapter(private val dataList: List<BoardData>) :
    RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)
        Log.i("onCreateViewHolder", "createViewholder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.headerView.text = item.category
        holder.titleView.text = item.title
        holder.writerView.text = item.author
        holder.dateView.text = item.date
        Log.i("holder_header", holder.headerView.toString())

        // title view 클릭 이벤트 설정
        holder.titleView.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                DetailRetrofitWork(holder.itemView.context).work(item.linkHref)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerView: TextView = itemView.findViewById(R.id.textViewHeader)
        val titleView: TextView = itemView.findViewById(R.id.textViewTitle)
        val writerView: TextView = itemView.findViewById(R.id.textViewWriter)
        val dateView: TextView = itemView.findViewById(R.id.textViewDate)

    }
}
