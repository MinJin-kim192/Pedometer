package com.utinfra.minjin.notiservicetest.Model

import androidx.room.*
import java.util.*

@Dao
interface StepResultDAO {

    @Query("SELECT * FROM StepModel ORDER BY id DESC")
    fun getAll(): List<StepModel>

    @Insert
    fun insertAll(vararg stepResult: StepModel)

    @Delete
    fun delete(stepModel: StepModel)

    @Query("UPDATE stepModel set id = :id, date = :date, stepCount = :stepCount, dayKor = :dayKor WHERE id = :id")
     fun update(id : Int, date : String, stepCount: Int, dayKor : String)

}