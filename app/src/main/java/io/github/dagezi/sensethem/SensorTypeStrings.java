package io.github.dagezi.sensethem;

import android.hardware.Sensor;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Constant class
 */

public class SensorTypeStrings {
    private static final Map<Integer, String> STRINGS = new HashMap<>();

    // It's ok to use constant not defined by minSdkLevel (16) because
    // these constants are expanded on compile.
    static {
        STRINGS.put(Sensor.TYPE_ACCELEROMETER, "ACCELEROMETER");
        STRINGS.put(Sensor.TYPE_AMBIENT_TEMPERATURE, "AMBIENT_TEMPERATURE");
        STRINGS.put(Sensor.TYPE_GAME_ROTATION_VECTOR, "GAME_ROTATION_VECTOR");
        STRINGS.put(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, "GEOMAGNETIC_ROTATION_VECTOR");
        STRINGS.put(Sensor.TYPE_GRAVITY, "GRAVITY");
        STRINGS.put(Sensor.TYPE_GYROSCOPE, "GYROSCOPE");
        STRINGS.put(Sensor.TYPE_GYROSCOPE_UNCALIBRATED, "GYROSCOPE_UNCALIBRATED");
        STRINGS.put(Sensor.TYPE_HEART_BEAT, "HEART_BEAT");
        STRINGS.put(Sensor.TYPE_HEART_RATE, "HEART_RATE");
        STRINGS.put(Sensor.TYPE_LIGHT, "LIGHT");
        STRINGS.put(Sensor.TYPE_LINEAR_ACCELERATION, "LINEAR_ACCELERATION");
        STRINGS.put(Sensor.TYPE_MAGNETIC_FIELD, "MAGNETIC_FIELD");
        STRINGS.put(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, "MAGNETIC_FIELD_UNCALIBRATED");
        STRINGS.put(Sensor.TYPE_MOTION_DETECT, "MOTION_DETECT");
        STRINGS.put(Sensor.TYPE_ORIENTATION, "ORIENTATION");
        STRINGS.put(Sensor.TYPE_POSE_6DOF, "POSE_6DOF");
        STRINGS.put(Sensor.TYPE_PRESSURE, "PRESSURE");
        STRINGS.put(Sensor.TYPE_PROXIMITY, "PROXIMITY");
        STRINGS.put(Sensor.TYPE_RELATIVE_HUMIDITY, "RELATIVE_HUMIDITY");
        STRINGS.put(Sensor.TYPE_ROTATION_VECTOR, "ROTATION_VECTOR");
        STRINGS.put(Sensor.TYPE_SIGNIFICANT_MOTION, "SIGNIFICANT_MOTION");
        STRINGS.put(Sensor.TYPE_STATIONARY_DETECT, "STATIONARY_DETECT");
        STRINGS.put(Sensor.TYPE_STEP_COUNTER, "STEP_COUNTER");
        STRINGS.put(Sensor.TYPE_STEP_DETECTOR, "STEP_DETECTOR");
        STRINGS.put(Sensor.TYPE_TEMPERATURE, "TEMPERATURE");
    }

    @NonNull
    public static String getString(int type) {
        if (STRINGS.containsKey(type)) {
            return STRINGS.get(type);
        }
        return "UNKNOWN";
    }
}
