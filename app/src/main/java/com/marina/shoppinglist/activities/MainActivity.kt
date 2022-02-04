package com.marina.shoppinglist.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.marina.shoppinglist.R
import com.marina.shoppinglist.databinding.ActivityMainBinding
import com.marina.shoppinglist.dialogs.NewListDialog
import com.marina.shoppinglist.fragments.FragmentManager
import com.marina.shoppinglist.fragments.NoteFragment
import com.marina.shoppinglist.fragments.ShopListNamesFragment
import com.marina.shoppinglist.settings.SettingsActivity

class MainActivity : AppCompatActivity(), NewListDialog.Listener {

    lateinit var binding: ActivityMainBinding
    private lateinit var defPref: SharedPreferences
    private var currentMenuItemId = R.id.shop_list
    private var currentTheme = ""
    private var iAd: InterstitialAd? = null
    private var adShowCounter = 0
    private var adShowCounterMax = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedTheme())

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentTheme = defPref.getString("theme_key", "blue").toString()
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
        setBottomNavListener()
        loadInterAd()
    }

    private fun loadInterAd() {
        val request = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            getString(R.string.inter_ad_id),
            request,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    iAd = ad
                }

                override fun onAdFailedToLoad(ad: LoadAdError) {
                    iAd = null
                }
            })
    }

    private fun showInterAd(adListener: AdListener) {
        if (iAd != null && adShowCounter > adShowCounterMax) {
            iAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                    adListener.onFinish()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    iAd = null
                    loadInterAd()
                }

                override fun onAdShowedFullScreenContent() {
                    iAd = null
                    loadInterAd()
                }
            }
            adShowCounter = 0
            iAd?.show(this)
        } else {
            adShowCounter++
            adListener.onFinish()
        }
    }

    private fun setBottomNavListener() {
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    showInterAd(object : AdListener {
                        override fun onFinish() {
                            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                        }
                    })
                }
                R.id.notes -> {
                    showInterAd(object : AdListener {
                        override fun onFinish() {
                            currentMenuItemId = R.id.notes
                            Log.d("myLog", "Notes")
                            FragmentManager.setFragment(NoteFragment.newInstance(), this@MainActivity)
                        }
                    })
                }
                R.id.shop_list -> {
                    currentMenuItemId = R.id.shop_list
                    Log.d("myLog", "Shop List")
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
                }
                R.id.new_item -> {
                    FragmentManager.currentFrag?.onClickNew()
                }
            }
            true
        }
    }

    private fun getSelectedTheme(): Int {
        return if (defPref.getString("theme_key", "blue") == "blue") {
            R.style.Theme_ShoppingListBlue
        } else {
            R.style.Theme_ShoppingListGreen
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bNav.selectedItemId = currentMenuItemId
        if (defPref.getString("theme_key", "blue") != currentTheme) recreate()
    }

    override fun onClick(name: String) {
        Log.d("MyLogs", "Name: $name")
    }

    interface AdListener {
        fun onFinish()
    }
}