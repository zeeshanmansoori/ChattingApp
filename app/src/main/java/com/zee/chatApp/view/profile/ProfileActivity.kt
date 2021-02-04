package com.zee.chatApp.view.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.zee.chatApp.R
import com.zee.chatApp.databinding.ActivityProfileBinding
import java.io.ByteArrayOutputStream

const val  STORAGE_REF = "user_images"
const val USER_REF = "Users"
const val TAKE_IMAGE = 223
const val USER_IMAGE = "image"
class ProfileActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private lateinit var binding:ActivityProfileBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile)
        firestore = FirebaseFirestore.getInstance()
        storageRef = FirebaseStorage.getInstance().getReference(STORAGE_REF)

    }

    private fun takePhoto() {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"

        startActivityForResult(intent, TAKE_IMAGE)
            }



    private fun uploadImage(userId: String) {
        imageUri?.let {
            val imageRef = storageRef.child("${userId}.jpg")
            it.let {
                imageRef.putFile(it).addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        firestore.collection(USER_REF).document(userId).update(
                            USER_IMAGE, uri.toString()
                        )
                            .addOnSuccessListener {
                                Snackbar.make(
                                    binding.root,
                                    "Image uploaded",
                                    Snackbar.LENGTH_SHORT
                                ).show()

                                imageUri = null
                            }.addOnFailureListener {
                                Snackbar.make(binding.root, it.toString(), Snackbar.LENGTH_SHORT)
                                    .show()

                            }

                    }
                }
            }
        }

        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TAKE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            binding.userImage.setImageURI(imageUri)
        }

    }
}