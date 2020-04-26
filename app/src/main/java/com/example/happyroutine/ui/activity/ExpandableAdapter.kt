package com.example.happyroutine.ui.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.happyroutine.R
import java.util.*
import kotlin.collections.ArrayList

class ExpandableAdapter(
    private var  ctx: Context,
    childList: ArrayList<ArrayList<String>>,
    private val parents: Array<String>
) : BaseExpandableListAdapter() {

    init {
        childLists = childList
    }

    override fun getGroupCount(): Int {
        return parents.size
    }

    override fun getChildrenCount(parent: Int): Int {
        return childLists[parent].size
    }

    override fun getGroup(parent: Int): String {
        return parents[parent]
    }

    override fun getChild(parent: Int, child: Int): Any {
        return childLists[parent][child]
    }

    override fun getGroupId(parent: Int): Long {
        return parent.toLong()
    }

    override fun getChildId(parent: Int, child: Int): Long {
        return child.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(parent: Int, isExpanded: Boolean, convertView: View?, parentview: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.parent_layout, parentview, false)
        }
        val parentTextView = convertView!!.findViewById(R.id.parent_txt) as TextView
        parentTextView.text = getGroup(parent)

        return convertView
    }

    override fun getChildView(
        parent: Int,
        child: Int,
        isLastChild: Boolean,
        convertView: View?,
        parentview: ViewGroup
    ): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.child_layout, parentview, false)

        }

        val childTextView = convertView!!.findViewById(R.id.child_txt) as TextView
        childTextView.text = getChild(parent, child).toString()

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    companion object {
        lateinit var childLists: ArrayList<ArrayList<String>>
    }

}

private class GroupHolder {
    var img: ImageView? = null
    var textView: TextView? = null
}