package com.example.workout.ui.tracker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.properties.Delegates
import kotlin.math.log as log1

class Compass : SensorEventListener {

    interface CompassListener{
        fun onUpdateAzimuth(azimuth: Float);
    }

    private lateinit var listener : CompassListener;

    private var sensorManager : SensorManager;
    private var gravitySensor : Sensor;
    private var magnetSensor : Sensor;

    private var dummy = 0.01F;

    private var mGravity = FloatArray(3);
    private var mMagnetic = FloatArray(3);
    private var R = FloatArray(9);
    private var I = FloatArray(9);

    private var azimuth = Math.toDegrees(0.00).toFloat();
    private var azimuthFix = Math.toDegrees(0.00).toFloat();

    public constructor(context: Context){
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public fun start(){
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnetSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public fun stop(){
        sensorManager.unregisterListener(this);
    }

    public fun setListener(inputlistener: CompassListener) {
        listener = inputlistener
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val alpha = 0.97f

        synchronized(this) {

            if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0]
                mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1]
                mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2]
            }

            if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                mMagnetic[0] = alpha * mMagnetic.get(0) + (1 - alpha) * event.values[0]
                mMagnetic[1] = alpha * mMagnetic.get(1) + (1 - alpha) * event.values[1]
                mMagnetic[2] = alpha * mMagnetic.get(2) + (1 - alpha) * event.values[2]
            }


            if (SensorManager.getRotationMatrix(R, I, mGravity, mMagnetic)) {
                val orientation = FloatArray(3);
                SensorManager.getOrientation(R, orientation);
                azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
                azimuth = (azimuth + azimuthFix + 360) % 360
                if (listener != null) {
                    listener.onUpdateAzimuth(azimuth)
                }
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        dummy = 1.001F;
    }


}