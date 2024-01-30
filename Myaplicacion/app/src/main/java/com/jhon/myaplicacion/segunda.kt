package com.jhon.myaplicacion

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhon.myaplicacion.databinding.ActivitySegundaBinding

class SegundaActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySegundaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySegundaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}