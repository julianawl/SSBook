package com.julianawl.ssbook.ui.activity

import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.julianawl.ssbook.R
import com.julianawl.ssbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var controller: NavController

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setDecorFitsSystemWindows(false)
        setStatusBar()
        configNavController()
    }

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= 30) {
            val root = findViewById<ConstraintLayout>(R.id.container)
            ViewCompat.setOnApplyWindowInsetsListener(root) { view, windowInsets ->
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply {
                    leftMargin = insets.left
                    bottomMargin = insets.bottom
                    rightMargin = insets.right
                }
                WindowInsetsCompat.CONSUMED
            }
        }
    }

    private fun configNavController() {
        val navView = binding.mainNavView
        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        navView.setupWithNavController(navController)
        controller = navController
    }
}