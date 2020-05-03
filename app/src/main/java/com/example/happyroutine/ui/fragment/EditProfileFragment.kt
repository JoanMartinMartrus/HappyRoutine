package com.example.happyroutine.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.happyroutine.R
import com.example.happyroutine.ui.activity.MainActivity
import com.example.happyroutine.ui.activity.log_in
import com.example.happyroutine.ui.activity.user_information
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*

/**
 * A simple [Fragment] subclass.
 */


class EditProfileFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance().collection("users")
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View=inflater.inflate(R.layout.fragment_edit_profile, container, false)
        // Inflate the layout for this fragment
        //val circleImageView:CircleImageView =v.findViewById(R.id.profile_image)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edit_profile_f.setOnClickListener {
            activity?.let {
                val intent = Intent(it, user_information::class.java)
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
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                it.startActivityForResult(intent,10)
                val path: Uri? =intent.data
                //profile_image.setImageURI(path)
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
                db.document(uid).delete()
                FirebaseAuth.getInstance().currentUser!!.delete()
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(it, MainActivity::class.java)
                it?.startActivity(intent)
            }
        }

    }
}
