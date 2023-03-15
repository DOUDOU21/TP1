package com.ndoudou.tp1

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import com.ndoudou.tp1.model.User
import de.hdodenhof.circleimageview.CircleImageView

class InfosFragment : Fragment() {
    private lateinit var communicator: Communicator
    lateinit var mNomPrenomTextView : TextView
    lateinit var mDescriptionTextView : TextView
    lateinit var mLocationTextView : TextView
    lateinit var mTelephoneTextView : TextView
    lateinit var mPortableTextView : TextView
    lateinit var mEmailTextView : TextView
    lateinit var mImageUser : CircleImageView

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.fragment_infos, container, false)
        communicator = activity as Communicator
        init(view)

        val user = arguments?.getSerializable("user") as? User
        if (user != null) {
            displayData(user)
        }

        return view
    }

    fun init(view : View){
        mNomPrenomTextView = view.findViewById<TextView>(R.id.text_view_nom_and_prenom)
        mDescriptionTextView = view.findViewById<TextView>(R.id.text_view_description)
        mLocationTextView = view.findViewById<TextView>(R.id.text_view_location)
        mTelephoneTextView = view.findViewById<TextView>(R.id.text_view_telephone)
        mPortableTextView = view.findViewById<TextView>(R.id.text_view_portable)
        mEmailTextView = view.findViewById<TextView>(R.id.text_view_email)
        mImageUser = view.findViewById<CircleImageView>(R.id.camera_user)

        mTelephoneTextView.setOnClickListener {
            // Vérifier si la permission n'est pas accordée
            if (checkSelfPermission(requireContext(),
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                // Demander la permission
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 1)
            } else {
                // La permission est déjà accordée, passer un appel téléphonique
                makePhoneCall()
            }
        }

        mEmailTextView.setOnClickListener {
            val email = mEmailTextView.text.toString()
            val subject = "Sujet de l'e-mail"
            val body = "Corps de l'e-mail"
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:$email")
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, body)
            val chooser = Intent.createChooser(intent, "Choisir une application d'envoi de mail")
            startActivity(chooser)
        }

        mImageUser.setOnClickListener {
            // Vérifier si la permission n'est pas accordée
            if (checkSelfPermission(requireContext(),
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                // Demander la permission
                requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CODE)
            } else {
                // La permission est déjà accordée, prendre une photo
                openCamera()
            }
        }
    }

    fun displayData(user : User){
        mNomPrenomTextView.text = user.nom+ " "+ user.prenom
        mDescriptionTextView.text = user.description
        mLocationTextView.text = user.pays+ ", "+ user.ville
        mTelephoneTextView.text = user.tel
        mPortableTextView.text = user.portable
        mEmailTextView.text = user.email
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission a été accordée, passer un appel téléphonique
                makePhoneCall()
            } else {
                // La permission a été refusée, afficher un message d'erreur
                Toast.makeText(requireContext(), "Permission refusée", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission a été accordée, prendre une photo
                openCamera()
            } else {
                // La permission a été refusée, afficher un message d'erreur
                Toast.makeText(requireContext(), "Permission refusée", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun makePhoneCall() {
        val phoneNumber = mTelephoneTextView.text
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
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
            mImageUser.setImageURI(imageUri)
        }
    }
}