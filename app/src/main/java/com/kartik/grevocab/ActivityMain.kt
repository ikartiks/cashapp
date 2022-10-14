package com.kartik.grevocab

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.kartik.grevocab.base.ActivityBase
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import kotlinx.android.synthetic.main.activity_main.nav_host_fragment
import kotlinx.android.synthetic.main.activity_main.nav_view
import kotlinx.android.synthetic.main.include_toolbar.toolbar
import org.koin.core.component.KoinComponent

/**
 * @author kartikshah
 *
 */
class ActivityMain : ActivityBase(), NavigationView.OnNavigationItemSelectedListener, KoinComponent {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { openOrCloseDrawer() }

        nav_view.setNavigationItemSelectedListener(this)

        nav_host_fragment?.post {
            // avoids crash in findNavController
            navController = findNavController(nav_host_fragment)
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.fragmentSplash,
                    R.id.fragmentWordList
                ),
                drawerLayout
            )

            toolbar.setupWithNavController(navController, appBarConfiguration)
        }
    }

    fun setToolbarVisibility(visibility: Int) {
        toolbar.visibility = visibility
    }

    @SuppressLint("RtlHardcoded")
    fun openOrCloseDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = item.onNavDestinationSelected(navController) ||
        super.onOptionsItemSelected(item)

    override fun onSupportNavigateUp() = navController.navigateUp()

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(nav_host_fragment)
        when (item.itemId) {
            else -> {
                item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
            }
        }
        openOrCloseDrawer()
        return true
    }
}
