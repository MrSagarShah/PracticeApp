package com.practiceapp.ui.adapters

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.practiceapp.App
import com.practiceapp.R
import com.practiceapp.data.Feedback
import com.practiceapp.data.Feedback_
import com.practiceapp.data.Videos
import com.practiceapp.ui.videoplayer.VideoPlayer
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.query.QueryBuilder
import kotlinx.android.synthetic.main.row_main_list.view.*
import javax.inject.Inject

class ImagesAdapter(private val list: List<Videos>) : RecyclerView.Adapter<ImagesAdapter.ImageHolder>() {

    @Inject
    lateinit var boxStore: BoxStore
    private lateinit var box: Box<Feedback>
    private val requestOptions = RequestOptions()

    init {
        App.myComponent.inject(this)
        box = boxStore.boxFor(Feedback::class.java)
        requestOptions.placeholder(R.mipmap.ic_launcher)
        requestOptions.error(R.mipmap.ic_launcher)
        requestOptions.skipMemoryCache(true)
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_main_list, parent, false)
        return ImageHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val data = box.query().equal(Feedback_.id, list[position].id).build().find()
        Glide.with(holder.itemView.context)
                .setDefaultRequestOptions(requestOptions)
                .load(list[position].images.original?.url)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.itemView.ivThumb)
        holder.itemView.tvName.text = list[position].title

        holder.itemView.rlParent.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context,
                        if (data.isEmpty().not() && data[0].isRead) R.color.colorPrimaryLight else R.color.colorPrimary))

        holder.itemView.llDownVote.setOnClickListener {
            decrease(list[position], holder)
        }

        holder.itemView.llUpVote.setOnClickListener {
            increase(list[position], holder)
        }

        if (data.isEmpty().not()) {
            holder.itemView.tvUpVote.text = data[0].upVote.toString()
            holder.itemView.tvDownVote.text = data[0].downVote.toString()
        } else {
            holder.itemView.tvUpVote.text = "0"
            holder.itemView.tvDownVote.text = "0"
        }

        holder.itemView.ivThumb.setOnClickListener {
            val intent = Intent(holder.itemView.context, VideoPlayer::class.java)
            intent.putExtra("URL", list[position].images.original?.mp4)
            holder.itemView.context.startActivity(intent)
            processVisit(list[position], holder)
        }
    }

    private fun processVisit(videos: Videos, holder: ImageHolder) {
        var data = box.query().equal(Feedback_.id, videos.id).build().find()
        if (data.isEmpty().not()) {
            data[0].upVote = data[0].upVote + 1
        } else {
            data = listOf(Feedback(id = videos.id, upVote = 1, downVote = 0, isRead = true))
        }
        data[0].isRead = true
        box.put(data[0])
        notifyItemChanged(holder.adapterPosition)
    }

    private fun increase(videos: Videos, holder: ImageHolder) {
        var data = box.query().equal(Feedback_.id, videos.id).build().find()
        if (data.isEmpty().not()) {
            data[0].upVote = data[0].upVote + 1
        } else {
            data = listOf(Feedback(id = videos.id, upVote = 1, downVote = 0))
        }
        box.put(data[0])
        holder.itemView.tvUpVote.text = data[0].upVote.toString()
    }

    private fun decrease(videos: Videos, holder: ImageHolder) {
        var data = box.query().equal(Feedback_.id, videos.id).build().find()
        if (data.isEmpty().not()) {
            data[0].downVote = data[0].downVote + 1
        } else {
            data = listOf(Feedback(id = videos.id, upVote = 0, downVote = 1))
        }
        box.put(data[0])
        holder.itemView.tvDownVote.text = data[0].downVote.toString()
    }

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}