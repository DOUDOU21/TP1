package com.ndoudou.tp1.presentation.ui.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.ndoudou.tp1.databinding.FragmentFormulaireBinding
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.presentation.ui.activity.MainActivity
import com.ndoudou.tp1.presentation.ui.UserViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FormFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels()

    private var _binding: FragmentFormulaireBinding? = null;
    private val binding get() = _binding!!;


    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFormulaireBinding.inflate(inflater, container, false);
        val view = binding.root;
        init(view)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun init(view: View) {
        binding.actionBar.actionbarTitle.text = "Ajouter un utilisateur"
        if (viewModel.user != null) {
            lifecycleScope.launch {
                viewModel.user.collectLatest {
                    if (it != null) {
                        binding.editTextEmail.setText(it.email)
                        binding.editTextNom.setText(it.nom)
                        binding.editTextPrenom.setText(it.prenom)
                        binding.editTextVille.setText(it.ville)
                        binding.editTextPays.setText(it.pays)
                        binding.editTextFonction.setText(it.fonction)
                        binding.editTextDescription.setText(it.description)
                        binding.editTextTelephone.setText(it.tel)
                        binding.editTextPortable.setText(it.portable)
                        binding.actionBar.actionbarTitle.text = "Modifier un utilisateur"
                    }

                }
            }

        }


        binding.actionBar.actionbarBack.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }


        binding.buttonValider.setOnClickListener {
            if (viewModel.user != null) {
                var idUser=0
                viewModel.user.value?.let { idUser = it.id}

                viewModel.updateUser(
                    User(
                        idUser,
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
                Toast.makeText(requireContext(), "Modifié avec succès", Toast.LENGTH_SHORT).show()
                clearAllETextInputEditText(view as ViewGroup)
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)

            } else {
                viewModel.insertUser(
                    User(
                        0,
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
                clearAllETextInputEditText(view as ViewGroup)
            }
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

    fun clearAllETextInputEditText(layout: ViewGroup) {
        for (i in 0 until layout.childCount) {
            val view = layout.getChildAt(i)
            if (view is ViewGroup) {
                clearAllETextInputEditText(view)
            } else if (view is TextInputEditText) {
                view.setText("")
            }
        }
    }

}