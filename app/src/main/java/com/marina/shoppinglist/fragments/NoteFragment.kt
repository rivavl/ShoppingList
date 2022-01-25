package com.marina.shoppinglist.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.marina.shoppinglist.activities.MainApp
import com.marina.shoppinglist.activities.NewNoteActivity
import com.marina.shoppinglist.database.MainViewModel
import com.marina.shoppinglist.databinding.FragmentNoteBinding

class NoteFragment : BaseFragment() {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun onEditResult() {
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                Log.d("MyLog", "title: ${it.data?.getStringExtra(TITLE_KEY)}")
                Log.d("MyLog", "description: ${it.data?.getStringExtra(DESC_KEY)}")
            }
        }
    }

    companion object {
        const val TITLE_KEY = "title_key"
        const val DESC_KEY = "desc_key"
        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}