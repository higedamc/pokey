package com.koalasat.pokey.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.koalasat.pokey.R
import com.koalasat.pokey.database.SubscriptionEntity

class SubscriptionListAdapter(
    private var subscriptions: List<SubscriptionEntity>,
    private val onToggle: (SubscriptionEntity, Boolean) -> Unit,
    private val onEdit: (SubscriptionEntity) -> Unit,
    private val onDelete: (SubscriptionEntity) -> Unit,
) : RecyclerView.Adapter<SubscriptionListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val enabledSwitch: SwitchCompat = view.findViewById(R.id.subscriptionEnabled)
        val labelText: TextView = view.findViewById(R.id.subscriptionLabel)
        val valueText: TextView = view.findViewById(R.id.subscriptionValue)
        val menuButton: ImageButton = view.findViewById(R.id.subscriptionMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_subscription_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subscription = subscriptions[position]

        holder.enabledSwitch.setOnCheckedChangeListener(null)
        holder.enabledSwitch.isChecked = subscription.enabled == 1

        val displayLabel = if (subscription.label.isNullOrEmpty()) {
            getTypeLabel(subscription.value)
        } else {
            subscription.label
        }
        holder.labelText.text = displayLabel

        val displayValue = if (subscription.value.length > 30) {
            subscription.value.substring(0, 15) + "..." + subscription.value.takeLast(10)
        } else {
            subscription.value
        }
        holder.valueText.text = displayValue

        holder.enabledSwitch.setOnCheckedChangeListener { _, isChecked ->
            onToggle(subscription, isChecked)
        }

        holder.menuButton.setOnClickListener { view ->
            showPopupMenu(view, subscription)
        }

        holder.itemView.setOnClickListener {
            onEdit(subscription)
        }
    }

    override fun getItemCount(): Int = subscriptions.size

    fun updateData(newSubscriptions: List<SubscriptionEntity>) {
        subscriptions = newSubscriptions
        notifyDataSetChanged()
    }

    private fun getTypeLabel(value: String): String {
        return when {
            value.startsWith("npub", ignoreCase = true) -> "User"
            value.startsWith("nevent", ignoreCase = true) -> "Event"
            value.startsWith("#") -> "Hashtag"
            else -> "Subscription"
        }
    }

    private fun showPopupMenu(view: View, subscription: SubscriptionEntity) {
        val popup = PopupMenu(view.context, view)
        popup.menuInflater.inflate(R.menu.subscription_popup_menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_edit -> {
                    onEdit(subscription)
                    true
                }
                R.id.menu_delete -> {
                    onDelete(subscription)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}
