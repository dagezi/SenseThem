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

import java.util.ArrayList;
import java.util.List;

import io.github.dagezi.sensethem.databinding.ActivityDashboardBinding;
import io.github.dagezi.sensethem.databinding.ListitemDatasourceBinding;

public class DashboardActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private ActivityDashboardBinding binding;
    private DataSourcesAdaptor dataSourcesAdaptor;
    private List<DataSource> dataSources = new ArrayList<>();

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
        binding.dataSourceList.setLayoutManager(new LinearLayoutManager(this));
        enumerateSensors();
    }

    public void enumerateSensors() {
        for (Sensor sensor : sensorManager.getSensorList(Sensor.TYPE_ALL)) {
            dataSources.add(new SensorDataSource(sensorManager, sensor));
        }

        dataSourcesAdaptor = new DataSourcesAdaptor(this, dataSources);
        binding.dataSourceList.setAdapter(dataSourcesAdaptor);
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

    private static class DataSourcesAdaptor extends RecyclerView.Adapter<DataSourcesAdaptor.ViewHolder> {
        static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private final ListitemDatasourceBinding binding;
            public ViewHolder(ListitemDatasourceBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void populate(DataSource dataSource, int position) {
                binding.setSource(dataSource);
                binding.getRoot().setBackgroundResource(position % 2 == 0 ?
                        0 : R.color.sensor_list_item_background);
                binding.getRoot().setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                DataSource source = binding.getSource();
                if (source.isTracking()) {
                    source.stopTracking();
                } else {
                    source.startTracking();
                }
            }
        }

        private List<DataSource> dataSources;
        private final Activity activity;

        public DataSourcesAdaptor(Activity activity, List<DataSource> dataSources) {
            this.activity = activity;
            this.dataSources = dataSources;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ListitemDatasourceBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(activity), R.layout.listitem_datasource, parent, false);

            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.populate(dataSources.get(position), position);
        }

        @Override
        public int getItemCount() {
            return dataSources.size();
        }
    }
}
