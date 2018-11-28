package com.circularfashion.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

import com.circularfashion.R
import com.diegodobelo.expandingview.ExpandingItem
import com.diegodobelo.expandingview.ExpandingList

class ProductDetailsFragment : Fragment() {
    private var mExpandingList: ExpandingList? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState);
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)
        mExpandingList = view.findViewById(R.id.expanding_list_main)
        createItems()
        return view
    }

    private fun createItems() {
        addItem(
            "John",
            arrayOf("House", "Boat", "Candy", "Collection", "Sport", "Ball", "Head"),
            R.color.colorPurple, R.drawable.ic_nfc
        )
        addItem("Mary", arrayOf("Dog", "Horse", "Boat"), R.color.colorPurple, R.drawable.ic_nfc)
        addItem("Ana", arrayOf("Cat"), R.color.colorPurple, R.drawable.ic_nfc)
        addItem("Peter", arrayOf("Parrot", "Elephant", "Coffee"), R.color.colorPurple, R.drawable.ic_nfc)
        addItem("Joseph", arrayOf(), R.color.colorPurple, R.drawable.ic_nfc)
        addItem("Paul", arrayOf("Golf", "Football"), R.color.colorPurple, R.drawable.ic_nfc)
        addItem("Larry", arrayOf("Ferrari", "Mazda", "Honda", "Toyota", "Fiat"), R.color.colorPurple, R.drawable.ic_nfc)
        addItem("Moe", arrayOf("Beans", "Rice", "Meat"), R.color.colorPurple, R.drawable.ic_nfc)
        addItem("Bart", arrayOf("Hamburger", "Ice cream", "Candy"), R.color.colorPurple, R.drawable.ic_nfc)
    }

    private fun addItem(title: String, subItems: Array<String>, colorRes: Int, iconRes: Int) {
        //Let's create an item with R.layout.expanding_layout
        val item = mExpandingList!!.createNewItem(R.layout.expanding_layout)

        //If item creation is successful, let's configure it
        if (item != null) {
            item.setIndicatorColorRes(colorRes)
            item.setIndicatorIconRes(iconRes)
            //It is possible to get any view inside the inflated layout. Let's set the text in the item
            (item.findViewById<View>(R.id.title) as TextView).text = title

            //We can create items in batch.
            item.createSubItems(subItems.size)
            for (i in 0 until item.subItemsCount) {
                //Let's get the created sub item by its index
                val view = item.getSubItemView(i)

                //Let's set some values in
                configureSubItem(item, view, subItems[i])
            }
            item.findViewById<View>(R.id.add_more_sub_items).setOnClickListener { v ->
                showInsertDialog(object : OnItemCreated {
                    override fun itemCreated(title1: String) {
                        val newSubItem = item.createSubItem()
                        configureSubItem(item, newSubItem!!, title1)
                    }
                })
            }

            item.findViewById<View>(R.id.remove_item).setOnClickListener { v -> mExpandingList!!.removeItem(item) }
        }
    }

    private fun configureSubItem(item: ExpandingItem?, view: View, subTitle: String) {
        (view.findViewById<View>(R.id.sub_title) as TextView).text = subTitle
        view.findViewById<View>(R.id.remove_sub_item).setOnClickListener { v -> item!!.removeSubItem(view) }
    }

    private fun showInsertDialog(positive: OnItemCreated) {
        val text = EditText(activity)
        val builder = AlertDialog.Builder(activity!!)
        builder.setView(text)
        builder.setTitle("Enter Title")
        builder.setPositiveButton(android.R.string.ok) { dialog, which -> positive.itemCreated(text.text.toString()) }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.show()
    }

    internal interface OnItemCreated {
        fun itemCreated(title: String)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
