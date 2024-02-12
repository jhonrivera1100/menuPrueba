package com.jhon.menuprueba.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProvider
import android.widget.Button
import com.jhon.menuprueba.R
import com.jhon.menuprueba.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val num1: TextView = root.findViewById((R.id.txtNum1))
        val num2: TextView = root.findViewById((R.id.txtNum2))
        val res: TextView = root.findViewById((R.id.txtResultado))
        val botonResta: Button = root.findViewById(R.id.btnResta)

        botonResta.setOnClickListener(){
            var n1 = num1.text.toString().toInt()
            var n2 = num2.text.toString().toInt()
            var resta = n1 - n2
            res.text = "el resultado es $resta"
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}