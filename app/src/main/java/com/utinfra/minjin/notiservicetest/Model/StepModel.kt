package com.utinfra.minjin.notiservicetest.Model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stepModel")
data class StepModel(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    @ColumnInfo(name = "date")
    val date : String,
    @ColumnInfo(name = "stepCount")
    val stepCount : Int,
    @ColumnInfo(name = "dayKor")
    val dayKor : String
)