package edu.ualr.recyclerviewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import edu.ualr.recyclerviewassignment.Adapter.AdapterList;
import edu.ualr.recyclerviewassignment.data.DataGenerator;
import edu.ualr.recyclerviewassignment.databinding.ActivityMainBinding;
import edu.ualr.recyclerviewassignment.model.Device;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private AdapterList adapterList;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Device> devices = DataGenerator.getDevicesDataset(5);

        initRecyclerView();
    }

    private void initRecyclerView(){
        // TODO. Create and initialize the RecyclerView instance here

        List<Device> devices = DataGenerator.getDevicesDataset(5);
        devices.addAll(DataGenerator.getDevicesDataset(5));


        recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        AdapterList adapterList = new AdapterList(devices);
        recyclerView.setAdapter(adapterList);

        adapterList.setOnItemClickListener(new AdapterList.onItemClickListener() {

            @Override
            public void onItemClick(View view, Device device, int position) {
                Log.d(TAG, String.format("User tapped on %s", device.getName()));
            }
        });

    }
}
