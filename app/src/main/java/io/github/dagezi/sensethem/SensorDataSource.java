package io.github.dagezi.sensethem;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SensorDataSource extends DataSource implements SensorEventListener {
    @NonNull
    private final SensorManager sensorManager;

    @NonNull
    private final Sensor sensor;

    @Nullable
    private Value latestValue;

    private boolean tracking = false;

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

    public SensorDataSource(@NonNull SensorManager sensorManager, @NonNull Sensor sensor) {
        this.sensorManager = sensorManager;
        this.sensor = sensor;
    }

    @Override
    public String getName() {
        return sensor.getName();
    }

    @Override
    public int getType() {
        return sensor.getType();
    }

    @Override
    @NonNull
    public String getTypeString() {
        int type = sensor.getType();
        if (STRINGS.containsKey(type)) {
            return "Sensor: " + STRINGS.get(type);
        }
        return "Sensor: unknown";
    }

    @Override
    public String getMisc() {
        return sensor.getVendor();
    }

    @Override
    @Nullable
    public Value getLatestValue() {
        return latestValue;
    }

    @Override
    public String getValueString() {
        if (latestValue == null) {
            return "N/A";
        }
        StringBuilder builder = new StringBuilder();
        String delimiter = "";
        for (int i = 0; i < latestValue.values.length; i++) {
            builder.append(delimiter).append(latestValue.values[i]);
            delimiter = ", ";
        }
        return builder.toString();
    }

    @Override
    public void startTracking() {
        if (!tracking) {
            tracking = true;
            notifyPropertyChanged(BR.tracking);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void stopTracking() {
        if (tracking) {
            notifyPropertyChanged(BR.tracking);
            sensorManager.unregisterListener(this);
            tracking = false;
        }
    }

    @Override
    public boolean isTracking() {
        return tracking;
    }

    // SensorEventListener
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        latestValue = new Value(sensorEvent.timestamp, sensorEvent.values);
        notifyPropertyChanged(BR.latestValue);
        notifyPropertyChanged(BR.valueString);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
