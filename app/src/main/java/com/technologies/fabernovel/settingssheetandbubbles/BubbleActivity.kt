package com.technologies.fabernovel.settingssheetandbubbles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_bubble.*
import kotlinx.android.synthetic.main.item_text.view.*

class BubbleActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble)

        recycler.setup()
    }

    private fun RecyclerView.setup() {
        val textAdapter = SimpleTextAdapter()
        adapter = textAdapter

        textAdapter.setItems(
            listOf(
                "TITLE 0" to "SUBTITLE 0",
                "TITLE 1" to "SUBTITLE 1",
                "TITLE 2" to "SUBTITLE 2",
                "TITLE 3" to "SUBTITLE 3",
                "TITLE 4" to "SUBTITLE 4",
                "TITLE 5" to "SUBTITLE 5",
                "TITLE 6" to "SUBTITLE 6",
                "TITLE 7" to "SUBTITLE 7",
                "TITLE 8" to "SUBTITLE 8",
                "TITLE 9" to "SUBTITLE 9",
                "TITLE 10" to "SUBTITLE 10",
                "TITLE 11" to "SUBTITLE 11",
                "TITLE 12" to "SUBTITLE 12",
                "TITLE 13" to "SUBTITLE 13",
                "TITLE 14" to "SUBTITLE 14",
                "TITLE 15" to "SUBTITLE 15",
                "TITLE 16" to "SUBTITLE 16",
                "TITLE 17" to "SUBTITLE 17",
                "TITLE 18" to "SUBTITLE 18",
                "TITLE 19" to "SUBTITLE 19",
                "TITLE 20" to "SUBTITLE 20",
                "TITLE 21" to "SUBTITLE 21",
                "TITLE 22" to "SUBTITLE 22",
                "TITLE 23" to "SUBTITLE 23"
            )
        )
    }

}

class SimpleTextAdapter : RecyclerView.Adapter<SimpleTextViewHolder>() {
    private val items: MutableList<Pair<String, String>> = mutableListOf()

    fun setItems(items: List<Pair<String, String>>) {
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimpleTextViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_text, parent, false)
        return SimpleTextViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SimpleTextViewHolder, position: Int) {
        val (title, subtitle) = items[position]
        holder.bind(title, subtitle)
    }

}

class SimpleTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(title: String, subtitle: String) {
        itemView.title.text = title
        itemView.subtitle.text = subtitle
    }
}
