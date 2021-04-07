package com.zacharee1.systemuituner.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.zacharee1.systemuituner.R
import com.zacharee1.systemuituner.interfaces.IListPreference
import kotlinx.coroutines.launch

class SecureListDialog : BaseOptionDialog() {
    companion object {
        fun newInstance(key: String): SecureListDialog {
            return SecureListDialog().apply {
                arguments = Bundle().apply {
                    putString(ARG_KEY, key)
                }
            }
        }
    }

    private val listPref: IListPreference
        get() = preference as IListPreference

    private var clickedIndex = -1

    override val layoutRes: Int = 0

    override fun onBindDialogView(view: View) {
        super.onBindDialogView(view)

        val checkedIndex = listPref.findIndexOfValue(listPref.value)
        val entries = listPref.entries

        View.inflate(requireContext(), R.layout.list_dialog, view.findViewById(R.id.wrapper))

        val list = view.findViewById<RecyclerView>(R.id.select_dialog_listview)

        val adapter = Adapter(entries?.mapIndexed { index, charSequence -> ItemInfo(charSequence, index == checkedIndex) } ?: ArrayList()) {
            clickedIndex = it
            onClick(dialog!!, DialogInterface.BUTTON_POSITIVE)

            val preference = listPref
            val value = preference.entryValues?.get(clickedIndex)?.toString()
            if (preference.callChangeListener(value)) {
                preference.value = value

                launch {
                    preference.onValueChanged(value, preference.writeKey)
                }
            }
        }

        list.adapter = adapter
    }

    private data class ItemInfo(
        val label: CharSequence?,
        var isChecked: Boolean
    )

    private class Adapter(private val items: List<ItemInfo>, private val clickCallback: (index: Int) -> Unit) : RecyclerView.Adapter<Adapter.VH>() {
        init {
            setHasStableIds(true)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(parent: ViewGro