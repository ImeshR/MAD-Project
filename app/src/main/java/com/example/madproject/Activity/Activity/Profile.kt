package com.example.madproject.Activity.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.madproject.R
import com.example.madproject.databinding.ActivityProfileInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        //SignOut
        val profileLogoutBtn = view.findViewById<Button>(R.id.profileLogoutBtn)
        profileLogoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(activity, MainNavigator::class.java)
            startActivity(intent)
            activity?.finish()
        }

        //Navigate to Dashboard
        val dashBoard = view.findViewById<ImageView>(R.id.dashboardBtn)
        dashBoard.setOnClickListener {
            val intent = Intent(activity, ListingDashBoard::class.java)
            startActivity(intent)
        }

        //Navigate to Profile View
        val profileView = view.findViewById<Button>(R.id.profileBtn)
        profileView.setOnClickListener {
            val intent = Intent(activity, ProfileInfo::class.java)
            startActivity(intent)
        }

        val userProfile = view.findViewById<ImageView>(R.id.userProfileImg)
        userProfile.setOnClickListener {
            val intent = Intent(activity, ProfileInfo::class.java)
            startActivity(intent)
        }

        //Navigate to Update Profile
        val profileSettings = view.findViewById<Button>(R.id.profileSettingsBtn)
        profileSettings.setOnClickListener {
            val intent = Intent(activity, ProfileSettings::class.java)
            startActivity(intent)
        }

        //Navigate to Privacy
        val privacy = view.findViewById<Button>(R.id.profilePrivacyBtn)
        privacy.setOnClickListener {
            val intent = Intent(activity, Privacy::class.java)
            startActivity(intent)
        }

        //Navigate to Payment
        val payment = view.findViewById<ImageView>(R.id.walletBtn)
        payment.setOnClickListener {
//            val intent = Intent(activity, payment_profile::class.java)
//            val intent = Intent(activity, AddNewCard::class.java)
            val intent = Intent(activity, CardHandle::class.java)
            startActivity(intent)
        }

        // Fetch and display user's name
        val logUserName = view.findViewById<TextView>(R.id.logUserName)
        val userId = firebaseAuth.currentUser?.uid
        userId?.let {
            firebaseFirestore.collection("users").document(it).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val firstName = document.getString("firstName")
                        val lastName = document.getString("lastName")
                        val fullName = "$firstName $lastName"
                        logUserName.text = fullName
                    } else {
                        Log.d("Profile", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Profile", "get failed with ", exception)
                }
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}