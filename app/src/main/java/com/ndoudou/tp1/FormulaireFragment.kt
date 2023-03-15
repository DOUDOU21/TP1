package com.ndoudou.tp1

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import com.google.android.material.textfield.TextInputEditText
import com.ndoudou.tp1.model.User
import de.hdodenhof.circleimageview.CircleImageView

class FormulaireFragment : Fragment() {
    private lateinit var communicator: Communicator
    lateinit var mNomEditText : TextInputEditText
    lateinit var mPrenomEditText : TextInputEditText
    lateinit var mVilleEditText : TextInputEditText
    lateinit var mPayeEditText : TextInputEditText
    lateinit var mFonctionEditText : TextInputEditText
    lateinit var mDescriptionEditText : TextInputEditText
    lateinit var mTelEditText : TextInputEditText
    lateinit var mPortablemEditText : TextInputEditText
    lateinit var mEmailEditText : TextInputEditText
    lateinit var mValiderButton : Button
    lateinit var mImageUser : CircleImageView

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

    fun init(view : View){
        communicator = activity as Communicator

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

        mValiderButton.setOnClickListener {
            communicator.passDataCom(user = User(mNomEditText.text.toString(),mPrenomEditText.text.toString(), mEmailEditText.text.toString(),
                mVilleEditText.text.toString(), mPayeEditText.text.toString(), mFonctionEditText.text.toString(), mDescriptionEditText.text.toString(),
                mTelEditText.text.toString(), mPortablemEditText.text.toString(),null))
        }

        mImageUser.setOnClickListener{
            // Vérifier si la permission n'est pas accordée
            if (checkSelfPermission(requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                // Demander la permission
                requestPermissions(
                    permissions,1
                )
            } else {
                // La permission est déjà accordée, ouvrir la galerie d'images
                openGallery()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission a été accordée, ouvrir la galerie d'images
                openGallery()
            } else {
                // La permission a été refusée, afficher un message d'erreur
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