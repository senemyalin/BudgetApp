package com.senemyalin.budgetapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.senemyalin.budgetapp.R
import com.senemyalin.budgetapp.common.click
import com.senemyalin.budgetapp.common.toastMessage
import com.senemyalin.budgetapp.common.viewBinding
import com.senemyalin.budgetapp.data.BudgetModel
import com.senemyalin.budgetapp.databinding.FragmentAddOrEditBinding


class AddOrEditFragment : BottomSheetDialogFragment(R.layout.fragment_add_or_edit) {

    private val binding by viewBinding(FragmentAddOrEditBinding::bind)
    private lateinit var db: FirebaseFirestore
    private val args: AddOrEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = Firebase.firestore

        with(binding) {
            btnAdd.text = "Add"

            args.budgetModel?.let {
                etTitle.setText(it.title)
                etPrice.setText(it.price.toString())
                etDescription.setText(it.description)
                btnAdd.text = "Edit"
            }


            btnAdd.click {
                if (btnAdd.text == "Add") {
                    val title = etTitle.text.toString()
                    val price = etPrice.text.toString()
                    val description = etDescription.text.toString()
                    val isIncome = cbIncome.isChecked

                    if (title.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty()) {
                        addBudget(title, price.toDouble(), description, isIncome)
                    } else {
                        requireContext().toastMessage("Failed")
                    }
                } else {
                    val title = etTitle.text.toString()
                    val price = etPrice.text.toString()
                    val description = etDescription.text.toString()
                    val isIncome = cbIncome.isChecked

                    if (title.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty()) {
                        editBudget(
                            (args.budgetModel?.id ?: ""),
                            title,
                            price.toDouble(),
                            description,
                            isIncome
                        )
                    } else {
                        //
                    }

                }

            }
        }
    }

    private fun addBudget(title: String, price: Double, description: String, isIncome: Boolean) {
        val budget = BudgetModel(
            id = null,
            title = title,
            price = price,
            description = description,
            isIncome = isIncome
        )
        db.collection("income_expense").document(title).set(budget).addOnSuccessListener {
            val action = AddOrEditFragmentDirections.actionAddOrEditFragmentToSummaryFragment()
            findNavController().navigate(action)
        }.addOnFailureListener {

        }
    }

    private fun editBudget(
        docId: String,
        title: String,
        price: Double,
        description: String,
        isIncome: Boolean
    ) {

        db.collection("income_expense").document(docId)
            .update(
                mapOf(
                    "title" to title,
                    "price" to price,
                    "description" to description,
                    "isIncome" to isIncome
                )
            )
            .addOnSuccessListener {
                val action = AddOrEditFragmentDirections.actionAddOrEditFragmentToSummaryFragment()
                findNavController().navigate(action)
            }
            .addOnFailureListener {

            }
    }

}