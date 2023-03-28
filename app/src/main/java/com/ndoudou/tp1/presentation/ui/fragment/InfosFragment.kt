package com.ndoudou.tp1.presentation.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ndoudou.tp1.R
import com.ndoudou.tp1.databinding.FragmentInfosBinding
import com.ndoudou.tp1.presentation.ui.UserViewModel
import com.ndoudou.tp1.presentation.ui.activity.MainActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class InfosFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels()
    private var _binding: FragmentInfosBinding? = null;
    private val binding get() = _binding!!;

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentInfosBinding.inflate(inflater,container,false);
        val view = binding.root;
        init(view)
        return view
    }

    private fun init(view : View){

        binding.actionBarInfo.actionbarBack.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.actionBarInfo.actionbarTitle.text = it.lastName+ " " + it.firstName
            binding.textViewNomAndPrenom.text = it.lastName + " " + it.firstName
            binding.textViewDescription.text = it.description
            binding.textViewLocation.text = it.country + " " + it.city
            binding.textViewTelephone.text = it.phone
            binding.textViewPortable.text = it.portable
            binding.textViewEmail.text = it.email
            Glide.with(view.context).load(it?.avatar).into(binding.cameraUser)
        })


        binding.delete.setOnClickListener {
            viewModel.user.value?.let { it1 -> viewModel.deleteUser(it1)}
            Toast.makeText(requireContext(), "Supprimé avec succès", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.update.setOnClickListener {
            findNavController().navigate(R.id.action_infosFragment_to_formFragment)
        }


        binding.textViewTelephone.setOnClickListener {
            // Check if permission is not granted
            if (checkSelfPermission(requireContext(),
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                // Ask permission
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 1)
            } else {
                // Permission is already granted, make a phone call
                makePhoneCall()
            }
        }

        binding.textViewEmail.setOnClickListener {
            val email = binding.textViewEmail.text.toString()
            val subject = "Sujet de l'e-mail"
            val body = "Corps de l'e-mail"
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:$email")
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, body)
            val chooser = Intent.createChooser(intent, "Choisir une application d'envoi de mail")
            startActivity(chooser)
        }

        binding.cameraUser.setOnClickListener {
            // Check if permission is not granted
            if (checkSelfPermission(requireContext(),
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                // Ask permission
                requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CODE)
            } else {
                // Permission is already granted, open image gallery
                openCamera()
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted, make a phone call
                makePhoneCall()
            } else {
                // Permission has been denied, display an error message
                Toast.makeText(requireContext(), "Permission refusée", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted, take a picture
                openCamera()
            } else {
                // Permission has been denied, display an error message
                Toast.makeText(requireContext(), "Permission refusée", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun makePhoneCall() {
        val phoneNumber = binding.textViewTelephone.text
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("phone:$phoneNumber"))
        startActivity(intent)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Nouvelle image")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image depuis la caméra")
        imageUri = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK) {
            binding.cameraUser.setImageURI(imageUri)
        }
    }
}