package com.example.happyroutine.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.happyroutine.R
import com.example.happyroutine.ui.activity.EditUserInformation
import com.example.happyroutine.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */


class EditProfileFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance().collection("users")
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private var selectedPhotoUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View=inflater.inflate(R.layout.fragment_edit_profile, container, false)
        // Inflate the layout for this fragment
        loadProfileImage()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(loadUrl()).into(profile_image)
        edit_profile_f.setOnClickListener {
            activity?.let {
                val intent = Intent(it, EditUserInformation::class.java)
                it.startActivity(intent)
            }
        }
        edit_food.setOnClickListener {
            fragmentManager?.let {
                it.beginTransaction().replace(R.id.frame_layout_navigation_bar,FoodDontWantFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            }
        }
        edit_photo.setOnClickListener {
            activity?.let {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)
            }
        }
        log_out.setOnClickListener {
            activity?.let {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
            }
        }
        delete_profile.setOnClickListener {
            activity.let {
                val fragmentActivity = it
                //Deletes user messages
                FirebaseFirestore.getInstance().collection("user-messages").document(uid).delete()
                FirebaseFirestore.getInstance().collection("latest-messages")
                    .document(uid).collection(uid).get().addOnSuccessListener {
                        for(doc in it){
                            FirebaseFirestore.getInstance().collection("latest-messages")
                                .document(doc.id).collection(doc.id).document(uid).delete()
                        }
                        //Deletes user profile and account
                        db.document(uid).delete()
                        FirebaseAuth.getInstance().currentUser!!.delete()
                        FirebaseAuth.getInstance().signOut()
                        val intent = Intent(fragmentActivity, MainActivity::class.java)
                        fragmentActivity?.startActivity(intent)
                    }
            }
        }
    }

    private fun uploadImageToDatabase(){
        if(selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("profileImages/$filename")
        println(selectedPhotoUri.toString())
        ref.putFile(selectedPhotoUri!!).addOnSuccessListener {
            println(it.metadata?.path)
            ref.downloadUrl.addOnSuccessListener {
                Log.d("Upload profile photo", "File location: $it")
                saveUrl(it.toString())
                updateProfileImage(it.toString())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedPhotoUri)
            profile_image.setImageBitmap(bitmap)
            uploadImageToDatabase()
        }
    }

    private fun updateProfileImage(profileImageUrl: String){
        if (uid != null) {
            db.document(uid).update("profileImageUrl", profileImageUrl)
        }
        else{
            Toast.makeText(context, "Error connecting to the database", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadProfileImage(){
        db.document(uid).get().addOnCompleteListener {
            if(it.isSuccessful){
                val doc = it.result
                if(doc!!.exists()){
                    val imageUrl = doc["profileImageUrl"]
                    Picasso.get().load("$imageUrl").into(profile_image)
                }
            }
        }
    }

    private fun saveUrl(url: String){
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString("url", url)
        editor?.apply()
    }

    private fun loadUrl() : String? {
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val default = "https://firebasestorage.googleapis.com/v0/b/happyroutine-1a42d.appspot.com/o/" +
                "profileImages%2Fcffbf657-375d-4617-9817-4dca71bddeaf?alt=media&token=77068c0c-706a-" +
                "46ee-8fcf-023d402cfa88"

        if (sharedPreferences != null) {
            return sharedPreferences.getString("url", "default")
        }
        else{
            return default
        }
    }
}
