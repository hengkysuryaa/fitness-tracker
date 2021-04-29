package com.example.workout.ui.schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.workout.R
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class AddScheduleFragment : Fragment() {

    private lateinit var addScheduleViewModel: AddScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addScheduleViewModel =
            ViewModelProvider(this).get(AddScheduleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_schedule, container, false)
        val textView: TextView = root.findViewById(R.id.text_add_schedule)
        addScheduleViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val pickDate = root.findViewById<TextView>(R.id.edit_date)
        val pickStartTime = root.findViewById<TextView>(R.id.edit_time)
        val pickEndTime = root.findViewById<TextView>(R.id.edit_end_time)
        val radioView: RadioGroup = root.findViewById(R.id.radio_group)
        val cal = Calendar.getInstance()

        root.findViewById<Button>(R.id.button_add).setOnClickListener {view ->
            findNavController().navigate(R.id.action_navigation_add_schedule_to_navigation_schedule)
            var showtext: String = ""
            var flag: Boolean = true
            when(radioView.checkedRadioButtonId) {
                R.id.radio_once ->
                    if (pickDate.text.isEmpty() || pickStartTime.text.isEmpty() || pickEndTime.text.isEmpty()) {
                        flag = !flag
                    }
                R.id.radio_everyday ->
                    if (pickStartTime.text.isEmpty() || pickEndTime.text.isEmpty()) {
                        flag = !flag
                    }
                R.id.radio_specific ->
                    if ((!root.findViewById<CheckBox>(R.id.checkbox_mon).isChecked &&
                            !root.findViewById<CheckBox>(R.id.checkbox_mon).isChecked &&
                            !root.findViewById<CheckBox>(R.id.checkbox_tue).isChecked &&
                            !root.findViewById<CheckBox>(R.id.checkbox_wed).isChecked &&
                            !root.findViewById<CheckBox>(R.id.checkbox_thu).isChecked &&
                            !root.findViewById<CheckBox>(R.id.checkbox_fri).isChecked &&
                            !root.findViewById<CheckBox>(R.id.checkbox_sat).isChecked &&
                            !root.findViewById<CheckBox>(R.id.checkbox_sun).isChecked) ||
                            pickStartTime.text.isEmpty() || pickEndTime.text.isEmpty()
                    ) {
                        flag = !flag
                    }
            }
            if (!flag) {
                showtext = "Invalid input"
            }
           else {
                showtext = pickDate.text.toString() + " " + pickStartTime.text.toString() + " " + pickEndTime.text.toString()
            }
            Snackbar.make(view, showtext, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val typeTrackView: RadioGroup = root.findViewById(R.id.track_type)

        typeTrackView.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { _, i ->
                    val radio: RadioButton = root.findViewById(i)
                            val runView: LinearLayout = root.findViewById(R.id.input_steps)
                            val cyclingView: LinearLayout = root.findViewById(R.id.input_km)
                            if (radio.text == "Running") {
                                cyclingView.visibility = View.GONE
                                runView.visibility = View.VISIBLE
                            } else {
                                cyclingView.visibility = View.VISIBLE
                                runView.visibility = View.GONE
                            }
                }
        )

        radioView.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { _, i ->
                val radio: RadioButton = root.findViewById(i)
                val editDay: EditText = root.findViewById(R.id.edit_date)
                val dayList: LinearLayout = root.findViewById(R.id.day_list)
                if (radio.text == "once") {
                    editDay.visibility = View.VISIBLE
                    dayList.visibility = View.GONE
                } else if (radio.text == "everyday") {
                    editDay.visibility = View.GONE
                    dayList.visibility = View.GONE
                } else {
                    dayList.visibility = View.VISIBLE
                    editDay.visibility = View.GONE
                }
            }
        )

        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute =  cal.get(Calendar.MINUTE)

        pickDate.setOnClickListener{
            val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener {
                _, year, monthOfYear, dayOfMonth ->
                pickDate.text = "${dayOfMonth}-${monthOfYear}-${year}"
            }, year, month, day)
            datePicker.show()
        }

        pickStartTime.setOnClickListener {
            val time = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                pickStartTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            val timePicker = TimePickerDialog(requireContext(), time, hour, minute, true)
            timePicker.show()
        }

        pickEndTime.setOnClickListener {
            val time = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                pickEndTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            val timePicker = TimePickerDialog(requireContext(), time, hour, minute, true)
            timePicker.show()
        }

        return root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(AddScheduleViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}