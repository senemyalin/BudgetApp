package com.senemyalin.budgetapp.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.senemyalin.budgetapp.R
import com.senemyalin.budgetapp.common.viewBinding
import com.senemyalin.budgetapp.data.BudgetModel
import com.senemyalin.budgetapp.databinding.FragmentIncomeBinding
import com.senemyalin.budgetapp.ui.Adapter.BudgetAdapter
import com.senemyalin.budgetapp.ui.Adapter.ItemListener


class IncomeFragment : Fragment(R.layout.fragment_income), ItemListener {

    private val binding by viewBinding(FragmentIncomeBinding::bind)
    private val adapter by lazy { BudgetAdapter(this) }
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = Firebase.firestore

        with(binding) {
            recyclerView.adapter = adapter
        }

        getBudget()
    }

    private fun getBudget() {
        db.collection("income_expense").addSnapshotListener { snapshot, error ->
            val list = arrayListOf<BudgetModel>()
            var totalBudget: Double = 0.0
            snapshot?.forEach { document ->
                list.add(
                    BudgetModel(
                        document.id,
                        document.get("title") as String,
                        (document.get("price") as Number).toDouble(),
                        document.get("description") as String,
                        document.get("isIncome") as Boolean
                    )
                )

//                if (document.get("isIncome") as Boolean) {
////                    totalBudget += document.get("price") as Double
//                } else {
////                    totalBudget -= document.get("price") as Double
//                }

                with(binding) {
                    if (totalBudget > 0) {
                        tvPrice.text = "+${totalBudget} $"
                        tvPrice.setTextColor(Color.GREEN)
                    } else {
                        tvPrice.text = "${totalBudget} $"
                        tvPrice.setTextColor(Color.RED)
                    }
                }
            }
            adapter.submitList(list)
        }
    }

    override fun onClicked(budget: BudgetModel) {

    }

}