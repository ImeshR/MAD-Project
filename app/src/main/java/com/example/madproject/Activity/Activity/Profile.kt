package com.example.madproject.Activity.Activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.madproject.R
import com.google.firebase.auth.FirebaseAuth

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

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