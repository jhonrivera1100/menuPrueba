package ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jhon.myapplication.databinding.ActivitySegundaBinding

class SegundaActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySegundaBinding
    private lateinit var sharedPrefer: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySegundaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user = sharedPrefer.getUser()
        val boolean = sharedPrefer.getUserIsLoged()
        initUi()
    }

    private fun initUi() {
        setupText()
    }

    private fun setupText() {
        val intent = intent
        val username = intent.getStringArrayExtra("username")
        val password = intent.getStringExtra("Password")

        // Ahora puedes usar las variables username y password según sea necesario
    }
}
