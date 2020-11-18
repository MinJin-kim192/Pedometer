package com.utinfra.minjin.notiservicetest.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utinfra.minjin.notiservicetest.Model.StepModel
import com.utinfra.minjin.notiservicetest.R
import kotlinx.android.synthetic.main.item_list.view.*

@Suppress("DEPRECATION")
class RvAdapter(
    private val context: Context,
    private val dateArray: List<StepModel>
) : RecyclerView.Adapter<RvAdapter.StepViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false)



        return StepViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {

        holder.bind(dateArray[position])
    }

    override fun getItemCount(): Int {

        return dateArray.size
    }

    inner class StepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var day = itemView.day_tv
        private var stepBar = itemView.progress_bar
        private var stepText = itemView.count_text

        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(stepData: StepModel) {

            day.text = "${stepData.date} (${stepData.dayKor})"
            stepText.text = "${stepData.stepCount} 걸음"
            stepBar.progressDrawable = context.resources.getDrawable(R.drawable.bg_rounded_progress)
            stepBar.progress = stepData.stepCount

        }


    }

}