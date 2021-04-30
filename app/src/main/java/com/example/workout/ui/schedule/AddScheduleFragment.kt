package com.example.workout.ui.schedule

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
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
    //private var alarmMgr: AlarmManager? = null
    //private lateinit var alarmIntent: PendingIntent

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
        val typeTrackView: RadioGroup = root.findViewById(R.id.track_type)
        val cal = Calendar.getInstance()

        root.findViewById<Button>(R.id.button_add).setOnClickListener {view ->
            findNavController().navigate(R.id.action_navigation_add_schedule_to_navigation_schedule)
            var showtext: String
            var flag: Boolean = true
//            var titleNotif: String = "Running"
            val fieldKm: EditText = root.findViewById(R.id.field_km)
            val fieldStep: EditText = root.findViewById(R.id.field_steps)
            var msgNotif: String = fieldStep.text.toString()
            val radioType: RadioButton = root.findViewById(typeTrackView.checkedRadioButtonId)

            if (radioType.text.toString() == "Cycling"){
                //titleNotif = "Cycling"
                msgNotif = fieldKm.text.toString()
            }

            Log.d("MainActivity",msgNotif)
            if (msgNotif.isNotEmpty()) {
                when (radioView.checkedRadioButtonId) {
                    R.id.radio_once ->
                        if (pickDate.text.isEmpty() || pickStartTime.text.isEmpty() || pickEndTime.text.isEmpty()) {
                            flag = !flag
                        } else {
                            setOnceAlarm(requireContext(), pickDate.text.toString(), pickStartTime.text.toString(),
                                    radioType.text.toString(), msgNotif)
                            setOnceEndAlarm(requireContext(), pickDate.text.toString(), pickEndTime.text.toString(),
                                    radioType.text.toString())
                        }
                    R.id.radio_everyday ->
                        if (pickStartTime.text.isEmpty() || pickEndTime.text.isEmpty()) {
                            flag = !flag
                        } else {
                            setEverydayAlarm(requireContext(), pickStartTime.text.toString(),
                                    radioType.text.toString())
                            setEverydayEndAlarm(requireContext(), pickEndTime.text.toString(),
                                    radioType.text.toString())
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
                        } else {
                            if (root.findViewById<CheckBox>(R.id.checkbox_mon).isChecked) {
                                setParticularDayAlarm(requireContext(), pickStartTime.text.toString(),
                                        Calendar.MONDAY, radioType.text.toString(), msgNotif)
                                setParticularDayEndAlarm(requireContext(), pickEndTime.text.toString(),
                                        Calendar.MONDAY, radioType.text.toString(), msgNotif)
                            } else if (root.findViewById<CheckBox>(R.id.checkbox_tue).isChecked) {
                                setParticularDayAlarm(requireContext(), pickStartTime.text.toString(),
                                        Calendar.TUESDAY, radioType.text.toString(), msgNotif)
                                setParticularDayEndAlarm(requireContext(), pickEndTime.text.toString(),
                                        Calendar.TUESDAY, radioType.text.toString(), msgNotif)
                            } else if (root.findViewById<CheckBox>(R.id.checkbox_wed).isChecked) {
                                setParticularDayAlarm(requireContext(), pickStartTime.text.toString(),
                                        Calendar.WEDNESDAY, radioType.text.toString(), msgNotif)
                                setParticularDayEndAlarm(requireContext(), pickEndTime.text.toString(),
                                        Calendar.WEDNESDAY, radioType.text.toString(), msgNotif)
                            } else if (root.findViewById<CheckBox>(R.id.checkbox_thu).isChecked) {
                                setParticularDayAlarm(requireContext(), pickStartTime.text.toString(),
                                        Calendar.THURSDAY, radioType.text.toString(), msgNotif)
                                setParticularDayEndAlarm(requireContext(), pickEndTime.text.toString(),
                                        Calendar.THURSDAY, radioType.text.toString(), msgNotif)
                            } else if (root.findViewById<CheckBox>(R.id.checkbox_fri).isChecked) {
                                setParticularDayAlarm(requireContext(), pickStartTime.text.toString(),
                                        Calendar.FRIDAY, radioType.text.toString(), msgNotif)
                                setParticularDayEndAlarm(requireContext(), pickEndTime.text.toString(),
                                        Calendar.FRIDAY, radioType.text.toString(), msgNotif)
                            } else if (root.findViewById<CheckBox>(R.id.checkbox_sat).isChecked) {
                                setParticularDayAlarm(requireContext(), pickStartTime.text.toString(),
                                        Calendar.SATURDAY, radioType.text.toString(), msgNotif)
                                setParticularDayEndAlarm(requireContext(), pickEndTime.text.toString(),
                                        Calendar.SATURDAY, radioType.text.toString(), msgNotif)
                            } else if (root.findViewById<CheckBox>(R.id.checkbox_sun).isChecked) {
                                setParticularDayAlarm(requireContext(), pickStartTime.text.toString(),
                                        Calendar.SUNDAY, radioType.text.toString(), msgNotif)
                                setParticularDayEndAlarm(requireContext(), pickEndTime.text.toString(),
                                        Calendar.SUNDAY, radioType.text.toString(), msgNotif)
                            }
                        }
                }
            } else {
                flag = !flag
            }
            if (!flag) {
                showtext = "Invalid input"
            }
           else {
                showtext = "Schedule added!"
            }
            Snackbar.make(view, showtext, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

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
                pickDate.text = "${dayOfMonth}-${monthOfYear+1}-${year}"
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

    private fun setOnceAlarm(context: Context, date: String, startTime: String, sportType: String, target: String) {
        val parseStartTime = startTime.split(":").toTypedArray()
        val parseDate = date.split("-").toTypedArray()
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent: PendingIntent
        intent.removeExtra("message")
        intent.putExtra("message", "$sportType $target")
        if (sportType == "Cycling") {
            intent.putExtra("type", AlarmReceiver.CYCLING_START)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.CYCLING_START, intent, 0)
        } else {
            intent.putExtra("type", AlarmReceiver.RUNNING_START)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.RUNNING_START, intent, 0)
        }

        val tempCal: Calendar = Calendar.getInstance()
        tempCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parseDate[0]))
        tempCal.set(Calendar.MONTH, Integer.parseInt(parseDate[1]) - 1)
        tempCal.set(Calendar.YEAR, Integer.parseInt(parseDate[2]))
        tempCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parseStartTime[0]))
        tempCal.set(Calendar.MINUTE, Integer.parseInt(parseStartTime[1]))
        tempCal.set(Calendar.SECOND, 0)
        alarmMgr?.set(
                AlarmManager.RTC_WAKEUP,
                tempCal.timeInMillis,
                alarmIntent
        )
        Log.d("MainActivity","sending Start...")
        Log.d("MainActivity", tempCal.time.toString())
    }

    private fun setOnceEndAlarm(context: Context, date: String, endTime: String, sportType: String) {
        val parseStartTime = endTime.split(":").toTypedArray()
        val parseDate = date.split("-").toTypedArray()
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent: PendingIntent
        intent.removeExtra("message")
        intent.putExtra("message", "$sportType")
        if (sportType == "Cycling") {
            intent.putExtra("type", AlarmReceiver.CYCLING_END)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.CYCLING_END, intent, 0)
        } else {
            intent.putExtra("type", AlarmReceiver.RUNNING_END)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.RUNNING_END, intent, 0)
        }

        val tempCal: Calendar = Calendar.getInstance()
        tempCal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parseDate[0]))
        tempCal.set(Calendar.MONTH, Integer.parseInt(parseDate[1]) - 1)
        tempCal.set(Calendar.YEAR, Integer.parseInt(parseDate[2]))
        tempCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parseStartTime[0]))
        tempCal.set(Calendar.MINUTE, Integer.parseInt(parseStartTime[1]))
        tempCal.set(Calendar.SECOND, 0)
        alarmMgr?.set(
                AlarmManager.RTC_WAKEUP,
                tempCal.timeInMillis + (5 * 1000),
                alarmIntent
        )
        Log.d("MainActivity","sending End...")
        Log.d("MainActivity", tempCal.time.toString())
    }

    private fun setEverydayAlarm(context: Context, startTime: String, sportType: String) {
        val parseStartTime = startTime.split(":").toTypedArray()
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent: PendingIntent
        intent.removeExtra("message")
        intent.putExtra("message", "$sportType")
        if (sportType == "Cycling") {
            intent.putExtra("type", AlarmReceiver.CYCLING_START_ED)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.CYCLING_START_ED, intent, 0)
        } else {
            intent.putExtra("type", AlarmReceiver.RUNNING_START_ED)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.RUNNING_START_ED, intent, 0)
        }

        val tempCal: Calendar = Calendar.getInstance()
        tempCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parseStartTime[0]))
        tempCal.set(Calendar.MINUTE, Integer.parseInt(parseStartTime[1]))
        tempCal.set(Calendar.SECOND, 0)
        alarmMgr?.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                tempCal.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
        )
        Log.d("MainActivity","sending...")
        Log.d("MainActivity", tempCal.time.toString())
    }

    private fun setEverydayEndAlarm(context: Context, endTime: String, sportType: String) {
        val parseStartTime = endTime.split(":").toTypedArray()
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent: PendingIntent
        intent.removeExtra("message")
        intent.putExtra("message", "$sportType")
        if (sportType == "Cycling") {
            intent.putExtra("type", AlarmReceiver.CYCLING_END_ED)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.CYCLING_END_ED, intent, 0)
        } else {
            intent.putExtra("type", AlarmReceiver.RUNNING_END_ED)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.RUNNING_END_ED, intent, 0)
        }

        val tempCal: Calendar = Calendar.getInstance()
        tempCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parseStartTime[0]))
        tempCal.set(Calendar.MINUTE, Integer.parseInt(parseStartTime[1]))
        tempCal.set(Calendar.SECOND, 0)
        alarmMgr?.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                tempCal.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
        )
        Log.d("MainActivity","sending End...")
        Log.d("MainActivity", tempCal.time.toString())
    }

    private fun setParticularDayAlarm(context: Context, startTime: String, day: Int, sportType: String, targetSport: String) {
        val parseStartTime = startTime.split(":").toTypedArray()
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent: PendingIntent
        intent.removeExtra("message")
        intent.putExtra("message", "$sportType $targetSport")
        if (sportType == "Cycling") {
            intent.putExtra("type", AlarmReceiver.CYCLING_START_PD)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.CYCLING_START_PD, intent, 0)
        } else {
            intent.putExtra("type", AlarmReceiver.RUNNING_START_PD)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.RUNNING_START_PD, intent, 0)
        }

        val tempCal: Calendar = Calendar.getInstance()
        tempCal.set(Calendar.DAY_OF_WEEK, day)
        if (tempCal.timeInMillis < SystemClock.elapsedRealtime()) {
            tempCal.add(Calendar.DAY_OF_YEAR, 7)
        }
        tempCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parseStartTime[0]))
        tempCal.set(Calendar.MINUTE, Integer.parseInt(parseStartTime[1]))
        tempCal.set(Calendar.SECOND, 0)
        alarmMgr?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                tempCal.timeInMillis,
                AlarmManager.INTERVAL_DAY * 7,
                alarmIntent
        )
        Log.d("MainActivity","sending...")
        Log.d("MainActivity", tempCal.time.toString())
    }

    private fun setParticularDayEndAlarm(context: Context, endTime: String, day: Int, sportType: String, targetSport: String) {
        val parseStartTime = endTime.split(":").toTypedArray()
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent: PendingIntent
        intent.removeExtra("message")
        intent.putExtra("message", "$sportType $targetSport")
        if (sportType == "Cycling") {
            intent.putExtra("type", AlarmReceiver.CYCLING_END_PD)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.CYCLING_END_PD, intent, 0)
        } else {
            intent.putExtra("type", AlarmReceiver.RUNNING_END_PD)
            alarmIntent = PendingIntent.getBroadcast(context, AlarmReceiver.RUNNING_END_PD, intent, 0)
        }

        val tempCal: Calendar = Calendar.getInstance()
        tempCal.set(Calendar.DAY_OF_WEEK, day)
        if (tempCal.timeInMillis < SystemClock.elapsedRealtime()) {
            tempCal.add(Calendar.DAY_OF_YEAR, 7)
        }
        tempCal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parseStartTime[0]))
        tempCal.set(Calendar.MINUTE, Integer.parseInt(parseStartTime[1]))
        tempCal.set(Calendar.SECOND, 0)
        alarmMgr?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                tempCal.timeInMillis,
                AlarmManager.INTERVAL_DAY * 7,
                alarmIntent
        )
        Log.d("MainActivity","sending End...")
        Log.d("MainActivity", tempCal.time.toString())
    }


//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(AddScheduleViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}