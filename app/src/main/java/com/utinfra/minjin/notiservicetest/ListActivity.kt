package com.utinfra.minjin.notiservicetest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.utinfra.minjin.notiservicetest.Adapter.RvAdapter
import com.utinfra.minjin.notiservicetest.Model.AppDatabase
import com.utinfra.minjin.notiservicetest.Model.StepModel
import com.utinfra.minjin.notiservicetest.Model.StepResultDAO
import com.utinfra.minjin.notiservicetest.Test.COUNT
import kotlinx.android.synthetic.main.activity_list.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


// 오늘 날짜
// 요일
// 걸음 수
// 오늘 걸음 수 == 데이터가 일치할 시 텍스트를 현재 걸음 수로 변경


class ListActivity : AppCompatActivity() {

    private var db : AppDatabase? = null
    private var saveDB : List<StepModel>? = null
    private var stepResult = mutableListOf<StepModel>()
    private var check = true


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        Log.d("로그","onCreate - call")

        var dayKor = "월"
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val date: DateFormat = SimpleDateFormat("MM월 dd일")
        val dateStr = date.format(calendar.time)

        dayKor = when (dayOfWeek) {
            1 -> "일"
            2 -> "월"
            3 -> "화"
            4 -> "수"
            5 -> "목"
            6 -> "금"
            7 -> "토"
            else -> ""
        }


        db = AppDatabase.getInstance(this)
        saveDB = db!!.stepDAO().getAll()
        if(saveDB!!.isNotEmpty()) {
            stepResult.addAll(saveDB!!)
        }

        val resultSave = StepModel(id = 0, dateStr, COUNT, dayKor)

        for(i in stepResult.indices) {
            if(dateStr == stepResult[i].date) {


                db!!.stepDAO().update(resultSave.id, resultSave.date, resultSave.stepCount, resultSave.dayKor)

                stepResult[i] = resultSave
                Log.d("로그", "update ${db?.stepDAO()?.getAll()}")

                check = false

            }
        }

        if(check) {
            db?.stepDAO()?.insertAll(resultSave)
            stepResult.add(resultSave)
        }



        val getDB : List<StepModel> = db!!.stepDAO().getAll()
        Log.d("로그","db ${db!!.stepDAO().getAll()}")



        val adapter = RvAdapter(this,getDB)
        adapter.notifyDataSetChanged()

        step_rv.adapter = adapter
        step_rv.layoutManager = LinearLayoutManager(this)




        back_btn.setOnClickListener {
            onBackPressed()
        }


    }



}