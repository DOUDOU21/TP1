package com.ndoudou.tp1.presentation.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ndoudou.tp1.R
import com.ndoudou.tp1.databinding.FragmentFormulaireBinding
import com.ndoudou.tp1.domain.model.User
class FormFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels()

    private var _binding: FragmentFormulaireBinding? = null;
    private val binding get() = _binding!!;

    lateinit var mActionbarBack: ImageView
    lateinit var mTitleActionBar: TextView

    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFormulaireBinding.inflate(inflater,container,false);
        val view = binding.root;
        init(view)
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun init(view: View) {

        mActionbarBack = view.findViewById(R.id.actionbar_back)
        mTitleActionBar = view.findViewById(R.id.actionbar_title)

        mActionbarBack.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.buttonValider.setOnClickListener {
            viewModel.insertUser(
                User(
                    2,
                    binding.editTextEmail.text.toString(),
                    binding.editTextNom.text.toString(),
                    binding.editTextPrenom.text.toString(),
                    binding.editTextVille.text.toString(),
                    binding.editTextPays.text.toString(),
                    binding.editTextFonction.text.toString(),
                    binding.editTextDescription.text.toString(),
                    binding.editTextTelephone.text.toString(),
                    binding.editTextPortable.text.toString(),
                    ""
                )
            )
            Toast.makeText(requireContext(), "Ajouté avec succès", Toast.LENGTH_SHORT).show()

        }

        binding.imageUser.setOnClickListener {
            // Check if permission is not granted
            if (checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                // Ask permission
                requestPermissions(
                    permissions, 1
                )
            } else {
                // Permission is already granted, open image gallery
                openGallery()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted, open image gallery
                openGallery()
            } else {
                // Permission has been denied, display an error message
                Toast.makeText(requireContext(), "Permission refusée", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }
}