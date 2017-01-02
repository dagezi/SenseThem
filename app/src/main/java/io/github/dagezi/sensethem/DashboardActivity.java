package io.github.dagezi.sensethem;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.List;

import io.github.dagezi.sensethem.databinding.ActivityDashboardBinding;
import io.github.dagezi.sensethem.databinding.ListitemSensorsBinding;

public class DashboardActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private ActivityDashboardBinding binding;
    private SensorsAdaptor sensorsAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        binding.sensorList.setLayoutManager(new LinearLayoutManager(this));
        enumerateSensors();
    }

    public void enumerateSensors() {
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        sensorsAdaptor = new SensorsAdaptor(this, sensors);
        binding.sensorList.setAdapter(sensorsAdaptor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class SensorsAdaptor extends RecyclerView.Adapter<SensorsAdaptor.ViewHolder> {
        static class ViewHolder extends RecyclerView.ViewHolder {
            private final ListitemSensorsBinding binding;
            public ViewHolder(ListitemSensorsBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void populate(Sensor sensor) {
                binding.setSensor(sensor);
            }
        }

        private List<Sensor> sensors;
        private final Activity activity;

        public SensorsAdaptor(Activity activity, List<Sensor> sensors) {
            this.activity = activity;
            this.sensors = sensors;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ListitemSensorsBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(activity), R.layout.listitem_sensors, parent, false);

            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.populate(sensors.get(position));
        }

        @Override
        public int getItemCount() {
            return sensors.size();
        }
    }
}
