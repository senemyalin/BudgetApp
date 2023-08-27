package com.senemyalin.budgetapp.ui.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.senemyalin.budgetapp.data.BudgetModel
import com.senemyalin.budgetapp.databinding.ItemBudgetBinding

class BudgetAdapter(private val itemListener: ItemListener) :
    ListAdapter<BudgetModel, BudgetAdapter.BudgetViewHolder>(BudgetDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder =
        BudgetViewHolder(
            ItemBudgetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), itemListener
        )

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) =
        holder.bind(getItem(position))

    class BudgetViewHolder(
        private val binding: ItemBudgetBinding,
        private val listener: ItemListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(budget: BudgetModel) = with(binding) {

            tvDescription.text = budget.description
            tvTitle.text = budget.title

            if (budget.isIncome == true) {
                tvPrice.text = budget.price.toString()
                tvPrice.setTextColor(Color.GREEN)
            } else {
                tvPrice.text = budget.price.toString()
                tvPrice.setTextColor(Color.RED)
            }

            root.setOnClickListener {
                listener.onClicked(budget)
            }
        }

    }

    class BudgetDiffCallBack() : DiffUtil.ItemCallback<BudgetModel>() {
        override fun areItemsTheSame(oldItem: BudgetModel, newItem: BudgetModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BudgetModel, newItem: BudgetModel): Boolean {
            return oldItem == newItem
        }

    }
}

interface ItemListener {
    fun onClicked(budget: BudgetModel)
}