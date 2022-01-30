package com.marina.shoppinglist.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.marina.shoppinglist.R
import com.marina.shoppinglist.databinding.ActivityMainBinding
import com.marina.shoppinglist.dialogs.NewListDialog
import com.marina.shoppinglist.fragments.FragmentManager
import com.marina.shoppinglist.fragments.NoteFragment

class MainActivity : AppCompatActivity(), NewListDialog.Listener {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavListener()
    }

    private fun setBottomNavListener() {
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.settings -> {
                    Log.d("myLog", "Settings")
                }
                R.id.notes -> {
                    Log.d("myLog", "Notes")
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                }
                R.id.shop_list -> {
                    Log.d("myLog", "Shop List")
                }
                R.id.new_item -> {
//                    FragmentManager.currentFrag?.onClickNew()
                        NewListDialog.shopDialog(this, this)
                }
            }
            true
        }
    }

    override fun onClick(name: String) {
        Log.d("MyLogs", "Name: $name")
    }
}