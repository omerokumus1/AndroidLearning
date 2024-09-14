package com.example.androidlearning

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.androidlearning.databinding.ActivityMainBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()
    private lateinit var chipViewModel: ChipViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chipViewModel = ViewModelProvider(this)[ChipViewModel::class.java]

        // Listen for selection changes in both ChipGroups
        binding.run {
            chipGroup1.setOnCheckedChangeListener { group, checkedId ->
                chipViewModel.selectChip(checkedId)
            }

            chipGroup2.setOnCheckedChangeListener { group, checkedId ->
                chipViewModel.selectChip(checkedId)
            }
        }

        // Observe the selected chip and apply it to both ChipGroups (Problem!)
        chipViewModel.selectedChipLiveData.observe(this) { chipId ->
            binding.chipGroup1.check(chipId)
            binding.chipGroup2.check(chipId)
        }

    }


}
class ChipGroupViewModel : ViewModel() {
    // LiveData to hold the selected chip ID
    private val _selectedChipId = MutableLiveData<Int>()
    val selectedChipId: LiveData<Int> = _selectedChipId

    fun selectChip(chipId: Int) {
        _selectedChipId.value = chipId
    }
}


class ChipGroupViewModelFactory(private val key: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChipGroupViewModel::class.java)) {
            // Pass key if needed or any other initialization
            return ChipGroupViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class ExampleFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chipGroup1 = view.findViewById<ChipGroup>(R.id.chipGroup1)
        val chipGroup2 = view.findViewById<ChipGroup>(R.id.chipGroup2)

        // Use distinct keys to get separate ViewModel instances
        val chipGroup1ViewModel = ViewModelProvider(this, ChipGroupViewModelFactory("ChipGroup1"))
            .get("ChipGroup1", ChipGroupViewModel::class.java)

        val chipGroup2ViewModel = ViewModelProvider(this, ChipGroupViewModelFactory("ChipGroup2"))
            .get("ChipGroup2", ChipGroupViewModel::class.java)

        // Set listeners for ChipGroup1
        chipGroup1.setOnCheckedChangeListener { _, checkedId ->
            chipGroup1ViewModel.selectChip(checkedId)
        }

        // Observe selected chip state in ChipGroup1
        chipGroup1ViewModel.selectedChipId.observe(viewLifecycleOwner) { selectedChipId ->
            chipGroup1.check(selectedChipId)
        }

        // Set listeners for ChipGroup2
        chipGroup2.setOnCheckedChangeListener { _, checkedId ->
            chipGroup2ViewModel.selectChip(checkedId)
        }

        // Observe selected chip state in ChipGroup2
        chipGroup2ViewModel.selectedChipId.observe(viewLifecycleOwner) { selectedChipId ->
            chipGroup2.check(selectedChipId)
        }
    }
}


