package com.example.workout.ui.tracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.workout.R
import kotlin.properties.Delegates

class TrackerFragment : Fragment() {

//    private lateinit var thiscontext: Context;
//    private lateinit var thiscontainer: ViewGroup;
//    private lateinit var thisinflater: LayoutInflater;
//
//    private lateinit var compass : Compass;
//    private lateinit var kompasView : ImageView;
//
//    private var currAzz by Delegates.notNull<Float>();
//
//    public fun initCompass(){
//        compass = Compass(thiscontext)
//        val cl: Compass.CompassListener = this.getCompassListener() as Compass.CompassListener
//        compass.setListener(cl)
//    }
//
//    private fun getCompassListener(): Any {
//        return object : Compass.CompassListener {
//            override fun onUpdateAzimuth(azimuth: Float) {
//                fun onUpdateAzimuth(azimuth: Float) {
//                    runOnUiThread(Runnable {
//                        val animate: Animation = RotateAnimation(-currAzz, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
//                        currAzz = azimuth
//
//                        animate.duration = 500
//                        animate.repeatCount = 0
//                        animate.fillAfter = true
//
//                        kompasView.startAnimation(animate)
//                    })
//                }
//            }
//        }
//    }
//
//
//    private fun runOnUiThread(runnable: Runnable) {
//        runnable.run();
//    }
//
//    override fun onPause() {
//        super.onPause();
//        compass.stop();
//    }
//
//
//    override fun onResume() {
//        super.onResume();
//        compass.start();
//    }
//
//    override fun onStop() {
//        super.onStop();
//        compass.stop();
//    }

    private lateinit var trackerViewModel: TrackerViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        trackerViewModel =
                ViewModelProvider(this).get(TrackerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tracker, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        trackerViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}