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
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.lifecycle.ViewModelProviders
import com.example.carspareparts.AboutFragment
import com.example.carspareparts.categories.CategoriesFragment
import com.example.carspareparts.login.LoginActivity
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_home.*

import com.example.carspareparts.R
import com.example.carspareparts.cart.CartActivity
import com.example.carspareparts.order.OrdersFragment
import com.parse.ParseObject


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var mainViewModel: MainViewModel
    private val categoriesFragment: CategoriesFragment by lazy {
        CategoriesFragment.newInstance()
    }
    private val ordersFragment: OrdersFragment by lazy {
        OrdersFragment.newInstance()
    }
    private val aboutFragment:AboutFragment by lazy {
        AboutFragment.newInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        cartView?.getAllInCart()
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setSupportActionBar(toolbar)
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

        cartView.setOnClickListener {
            Intent(this, CartActivity::class.java).also {
                startActivity(it)
            }
        }
    }


    private fun attachHomeFragment() {
        supportFragmentManager.
            beginTransaction()
            .replace(R.id.fragmentPlaceholder, categoriesFragment, "a").commit()
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
            R.id.nav_home -> {
                replaceFragments(categoriesFragment)
            }
            R.id.nav_orders -> {
                replaceFragments(ordersFragment)
            }
            R.id.nav_about -> {
                replaceFragments(aboutFragment)
            }
            R.id.nav_sign_out -> {
                mainViewModel.userSignOut()
                Intent(this, LoginActivity::class.java).apply {
                    startActivity(this)
                }
                ParseObject.unpinAllInBackground()
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun replaceFragments(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.popBackStack("home",POP_BACK_STACK_INCLUSIVE)
        fragmentTransaction.replace(R.id.fragmentPlaceholder, fragment).commit()
    }
    override fun onResume() {
        super.onResume()
        toolbar?.title = "Available Categories"
        cartView?.getAllInCart()
    }

}
