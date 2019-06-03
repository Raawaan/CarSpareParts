package com.example.carspareparts.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.carspareparts.R
import com.example.carspareparts.home.HomeFragment
import com.example.carspareparts.login.LoginActivity
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_home.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var mainViewModel: MainViewModel
    lateinit var homeFragment: HomeFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mainViewModel= MainViewModel()

        setUserNameAndEmailToNavDrawer()
        attachHomeFragment()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun attachHomeFragment() {
        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragmentPlaceholder, homeFragment, "a").commit()
    }

    private fun setUserNameAndEmailToNavDrawer() {
        val user = ParseUser.getCurrentUser()
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById(R.id.userNameNav) as TextView
        val navEmail = headerView.findViewById(R.id.emailNav) as TextView
        navUsername.text = user.username
        navEmail.text = user.email
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.home -> {
                replaceFragments(homeFragment)
            }
            R.id.nav_pending_orders -> {

            }
            R.id.nav_history -> {

            }
            R.id.nav_about -> {

            }
            R.id.nav_sign_out -> {
                mainViewModel.userSignOut()
                Intent(this,LoginActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun replaceFragments(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentPlaceholder, fragment).commit()
    }
}
