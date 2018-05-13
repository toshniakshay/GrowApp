package com.android.growappdemo.UI.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.android.growappdemo.R
import com.android.growappdemo.UI.Fragments.ProductListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            loadFragment(ProductListFragment(), ProductListFragment.CLASSTAG!!, false)
        }
    }

    /**
     * Method to load fragment in Main Activity container
     */
    public fun loadFragment(fragnent : Fragment, tag:String, addToBackStack:Boolean) {

        val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction()

        if (addToBackStack) {
            fragmentTransaction.add(R.id.mainActivity_container,  fragnent)
            fragmentTransaction.addToBackStack(tag)
        } else {
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.replace(R.id.mainActivity_container,  fragnent)
        }

        fragmentTransaction.commit()
    }
}
