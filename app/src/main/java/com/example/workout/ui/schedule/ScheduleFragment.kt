package com.example.workout.ui.schedule

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.workout.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*

class ScheduleFragment : Fragment() {

    private lateinit var scheduleViewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scheduleViewModel =
            ViewModelProvider(this).get(ScheduleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_schedule, container, false)
        val textView: TextView = root.findViewById(R.id.text_schedule)
//        scheduleViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })


        root.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(R.id.action_navigation_schedule_to_addScheduleFragment)
        }

        textView.text = ""
        val dbHandler = DBHelper(requireContext(), null)
        val dbc: Cursor = dbHandler.readableDatabase.query("sch", null, null, null, null, null, null)
        Log.d("MainActivity", Arrays.toString(dbc.columnNames))
        val cursor = dbHandler.getAllSchedule()
        Log.d("MainActivity", cursor?.count.toString())

            cursor!!.moveToFirst()
            while (cursor.moveToNext()) {
                textView.append((cursor.getString(cursor.getColumnIndex("typeSport"))))
                textView.append(" - ")
                textView.append((cursor.getString(cursor.getColumnIndex("typeSchedule"))))
                textView.append("\n")
                textView.append((cursor.getString(cursor.getColumnIndex("scheduleDate"))))
                textView.append(" ")
                textView.append((cursor.getString(cursor.getColumnIndex("startTime"))))
                textView.append(" - ")
                textView.append((cursor.getString(cursor.getColumnIndex("endTime"))))
                textView.append("\n")
                textView.append("Repeat Days: ")
                textView.append((cursor.getString(cursor.getColumnIndex("repeatedDay"))))
                textView.append("\n")
                textView.append("Auto Track: ")
                textView.append((cursor.getString(cursor.getColumnIndex("autoStart"))))
                textView.append("\n")
                textView.append("Target: ")
                textView.append((cursor.getString(cursor.getColumnIndex("targetSport"))))
                textView.append("\n")
                textView.append("\n")
            }
            cursor.close()

        return root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}