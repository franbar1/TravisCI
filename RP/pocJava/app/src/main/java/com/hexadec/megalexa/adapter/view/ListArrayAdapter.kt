package com.hexadec.megalexa.adapter.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hexadec.megalexa.R
import android.widget.*


class ListArrayAdapter(private val context: Context, private val data: List<Pair<String,Int>>): BaseAdapter() {



    private val inflater:LayoutInflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val viewHolder:ViewHolderItem
        val rowView:View

        if(convertView ==null) {
            rowView = inflater.inflate(R.layout.recycler_view_item,parent,false)

            viewHolder=ViewHolderItem(rowView)
            rowView.tag= viewHolder
        }else{

            rowView=convertView
            viewHolder=rowView.tag as ViewHolderItem
        }



        //viewHolder.textViewItem.text=data[position].first
        //viewHolder.imageViewItem.setImageResource(data[position].second)



        return rowView
    }

    override fun getItem(position: Int): Pair<String,Int> {
       return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
       return data.size
    }

    class ViewHolderItem(view:View?) {

        //var textViewItem :TextView= view!!.findViewById(R.id.block_label)
        //var imageViewItem: ImageView = view!!.findViewById(R.id.block_logo)

    }
}


