package com.example.cricscorer.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cricscorer.MainActivity3
import com.example.cricscorer.MainActivity4
import com.example.cricscorer.databinding.FragmentNewMatchBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentNewMatchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentNewMatchBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button1.setOnClickListener{

                view ->



            if(binding.OversEditText.text.toString() =="" || binding.teamA.text.toString()=="" || binding.teamB.text.toString()==""){
                Toast.makeText(activity,"Please fill all details", Toast.LENGTH_LONG).show()
            }
            else {
                val i = Intent(activity, MainActivity3::class.java)
                i.putExtra("overs", binding.OversEditText.text.toString())
                i.putExtra("teamAname", binding.teamA.text.toString())
                i.putExtra("teamBname", binding.teamB.text.toString())
                if (binding.teamAToss.isChecked) {
                    if (binding.bat.isChecked) {
                        i.putExtra("battingFirst", binding.teamA.text.toString())
                        i.putExtra("bfdg", binding.teamB.text.toString())
                    } else {
                        i.putExtra("battingFirst", binding.teamB.text.toString())
                        i.putExtra("bfdg", binding.teamA.text.toString())
                    }
                } else {
                    if (binding.bat.isChecked) {
                        i.putExtra("battingFirst", binding.teamB.text.toString())
                        i.putExtra("bfdg", binding.teamA.text.toString())
                    } else {
                        i.putExtra("battingFirst", binding.teamA.text.toString())
                        i.putExtra("bfdg", binding.teamB.text.toString())
                    }
                }
                startActivity(i)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}