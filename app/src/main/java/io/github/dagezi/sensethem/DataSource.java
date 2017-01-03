package io.github.dagezi.sensethem;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Generic Data source
  */

public abstract class DataSource extends BaseObservable {
    public static class Value {
        public final long timestamp;
        public final float[] values;

        public Value(long timestamp, float[] values) {
            this.timestamp = timestamp;
            this.values = values;
        }
    }

    public abstract String getName();
    public abstract int getType();
    public abstract String getTypeString();
    public abstract String getMisc();

    @Bindable
    public abstract Value getLatestValue();

    @Bindable
    public abstract String getValueString();

    public abstract void startTracking();
    public abstract void stopTracking();

    @Bindable
    public abstract boolean isTracking();
}
