package com.ndoudou.tp1.presentation.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.ndoudou.tp1.R
import com.ndoudou.tp1.data.local.entity.UserEntity
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
@AndroidEntryPoint
class FormFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()

    //private var userViewModel: UserViewModel? = null

    //private lateinit var communicator: Communicator

    lateinit var mNomEditText: TextInputEditText
    lateinit var mPrenomEditText: TextInputEditText
    lateinit var mVilleEditText: TextInputEditText
    lateinit var mPayeEditText: TextInputEditText
    lateinit var mFonctionEditText: TextInputEditText
    lateinit var mDescriptionEditText: TextInputEditText
    lateinit var mTelEditText: TextInputEditText
    lateinit var mPortablemEditText: TextInputEditText
    lateinit var mEmailEditText: TextInputEditText
    lateinit var mValiderButton: Button
    lateinit var mImageUser: CircleImageView

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
        val view = layoutInflater.inflate(R.layout.fragment_formulaire, container, false)

        init(view)

        return view
    }


    fun init(view: View) {

        //userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        //communicator = activity as Communicator
        mNomEditText = view.findViewById<TextInputEditText>(R.id.edit_text_nom)
        mPrenomEditText = view.findViewById<TextInputEditText>(R.id.edit_text_prenom)
        mVilleEditText = view.findViewById<TextInputEditText>(R.id.edit_text_ville)
        mPayeEditText = view.findViewById<TextInputEditText>(R.id.edit_text_pays)
        mFonctionEditText = view.findViewById<TextInputEditText>(R.id.edit_text_fonction)
        mDescriptionEditText = view.findViewById<TextInputEditText>(R.id.edit_text_description)
        mTelEditText = view.findViewById<TextInputEditText>(R.id.edit_text_telephone)
        mPortablemEditText = view.findViewById<TextInputEditText>(R.id.edit_text_portable)
        mEmailEditText = view.findViewById<TextInputEditText>(R.id.edit_text_email)
        mValiderButton = view.findViewById<Button>(R.id.button_valider)
        mImageUser = view.findViewById<CircleImageView>(R.id.image_user)

        mActionbarBack = view.findViewById(R.id.actionbar_back)
        mTitleActionBar = view.findViewById(R.id.actionbar_title)

        mActionbarBack.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        mValiderButton.setOnClickListener {
//            communicator.passDataCom(user = User(mNomEditText.text.toString(),mPrenomEditText.text.toString(), mEmailEditText.text.toString(),
//                mVilleEditText.text.toString(), mPayeEditText.text.toString(), mFonctionEditText.text.toString(), mDescriptionEditText.text.toString(),
//                mTelEditText.text.toString(), mPortablemEditText.text.toString(),null))

//            userViewModel?.setData(user = User(mNomEditText.text.toString(),mPrenomEditText.text.toString(), mEmailEditText.text.toString(),
//                mVilleEditText.text.toString(), mPayeEditText.text.toString(), mFonctionEditText.text.toString(), mDescriptionEditText.text.toString(),
//                mTelEditText.text.toString(), mPortablemEditText.text.toString(),null)
//            )
//
//            val fragmentManager = fragmentManager
//            val fragmentTransaction = fragmentManager!!.beginTransaction()
//            fragmentTransaction.replace(R.id.container, InfosFragment())
//            fragmentTransaction.commit()

            viewModel.insertUser(
                UserEntity(
                    2,
                    mEmailEditText.text.toString(),
                    mNomEditText.text.toString(),
                    mPrenomEditText.text.toString(),
                    mVilleEditText.text.toString(),
                    mPayeEditText.text.toString(),
                    mFonctionEditText.text.toString(),
                    mDescriptionEditText.text.toString(),
                    mTelEditText.text.toString(),
                    mPortablemEditText.text.toString(),
                    ""
                )
            )
            Toast.makeText(requireContext(), "Ajouté avec succès", Toast.LENGTH_SHORT).show()

        }

        mImageUser.setOnClickListener {
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