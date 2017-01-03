package io.github.dagezi.sensethem;

/**
 * Generic Data source
  */

public abstract class DataSource {
    public abstract String getName();
    public abstract int getType();
    public abstract String getTypeString();
    public abstract String getMisc();
}
