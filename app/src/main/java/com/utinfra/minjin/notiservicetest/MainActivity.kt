package com.utinfra.minjin.notiservicetest


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.utinfra.minjin.notiservicetest.Test.COUNT
import com.utinfra.minjin.notiservicetest.databinding.ActivityMainBinding
import com.utinfra.minjin.notiservicetest.service.TestService

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {

            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 200)

        }


        val intent = Intent(this, TestService::class.java)

        stopService(intent)

        binding.text1.text = "걸음 수 :${COUNT}"

        binding.btn1.setOnClickListener {

            startForegroundService(intent)
        }


        binding.btn2.setOnClickListener {

            stopService(intent)

        }

        binding.btn3.setOnClickListener {

            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)

        }

    }

}