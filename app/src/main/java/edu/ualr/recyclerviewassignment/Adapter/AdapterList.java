package edu.ualr.recyclerviewassignment.Adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

import edu.ualr.recyclerviewassignment.R;
import edu.ualr.recyclerviewassignment.model.Device;

public class AdapterList extends RecyclerView.Adapter {
    private List<Device> devices;

    public interface onItemClickListener {
        void onItemClick(View view, Device device, int position); //sends message from adapter to activity
    }

    private onItemClickListener myOnItemClickListener;

    public void setOnItemClickListener(final onItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }
    public AdapterList(List<Device> devices) {
        this.devices = devices;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View deviceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_item, parent, false);
        RecyclerView.ViewHolder vh = new DeviceViewHolder(deviceView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Device device = devices.get(position);
        final DeviceViewHolder deviceViewHolder = (DeviceViewHolder) holder;
        updateDevice(device, deviceViewHolder);
        TypedArray deviceImages = deviceViewHolder.context.getResources().obtainTypedArray(R.array.device_images);
        deviceViewHolder.deviceTypeImage.setImageResource(deviceImages.getResourceId(device.getDeviceType().ordinal(), -1));
        deviceImages.recycle();

        deviceViewHolder.connectButtonImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               myOnItemClickListener.onItemClick(v, devices.get(position), position);
               if (device.getDeviceStatus() == Device.DeviceStatus.Connected) {
                   device.setDeviceStatus(Device.DeviceStatus.Ready);
                   deviceViewHolder.connectButtonImage.setImageResource(R.drawable.ic_btn_connect);
               } else {
                   device.setDeviceStatus(Device.DeviceStatus.Connected);
                   deviceViewHolder.connectButtonImage.setImageResource(R.drawable.ic_btn_disconnect);
               }
               device.setLastConnection(new Date());
               updateDevice(device, deviceViewHolder);
               notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.devices.size();
    }
    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout deviceItemLayoutRoot;
        public ConstraintLayout deviceImageConstraintLayout;
        public ConstraintLayout connectButtonLayout;
        public ImageView deviceBackdropImage;
        public ImageView deviceConnStatusImage;
        public ImageView deviceTypeImage;
        public ImageView clockImageView;
        public TextView deviceNameTextView;
        public TextView connectionStatusTextView;
        public ImageButton connectButtonImage;
        public Context context;

        public DeviceViewHolder(@NonNull final View itemView) {
            super(itemView);
            context = itemView.getContext();
            deviceItemLayoutRoot = itemView.findViewById(R.id.deviceListItemRoot);
            deviceImageConstraintLayout = itemView.findViewById(R.id.deviceImagesConstraintLayout);
            connectButtonLayout = itemView.findViewById(R.id.connectButtonConstraintLayout);
            deviceBackdropImage = itemView.findViewById(R.id.deviceBackdropImageView);
            deviceConnStatusImage = itemView.findViewById(R.id.deviceConnStatusImageView);
            deviceTypeImage = itemView.findViewById(R.id.deviceTypeImageView);
            clockImageView = itemView.findViewById(R.id.connectionTimestampImageView);
            deviceNameTextView = itemView.findViewById(R.id.deviceNameTextView);
            connectionStatusTextView = itemView.findViewById(R.id.connectionTimestampTextView);
            connectButtonImage = itemView.findViewById(R.id.connectImageButton);
            deviceItemLayoutRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnItemClickListener.onItemClick(v, devices.get(getLayoutPosition()), getLayoutPosition());
                }
            });

        }
    }

    private void updateDevice(Device device, DeviceViewHolder deviceViewHolder) {
        deviceViewHolder.deviceNameTextView.setText(device.getName());
        switch (device.getDeviceStatus()) {
            case Connected:
               deviceViewHolder.deviceBackdropImage.setImageResource(R.drawable.thumbnail_background_solid);
               deviceViewHolder.deviceTypeImage.setColorFilter(ContextCompat.getColor(deviceViewHolder.context, R.color.grey_5), PorterDuff.Mode.SRC_IN);
               deviceViewHolder.deviceConnStatusImage.setImageResource(R.drawable.status_mark_connected);
               deviceViewHolder.deviceConnStatusImage.setVisibility(View.VISIBLE);
               deviceViewHolder.connectionStatusTextView.setText(R.string.currently_connected);
               deviceViewHolder.connectButtonImage.setImageResource(R.drawable.ic_btn_disconnect);
               deviceViewHolder.connectButtonImage.setColorFilter(ContextCompat.getColor(deviceViewHolder.context, R.color.grey_60), PorterDuff.Mode.SRC_IN);
               break;
            case Ready:
               deviceViewHolder.deviceBackdropImage.setImageResource(R.drawable.thumbnail_background_solid);
               deviceViewHolder.deviceTypeImage.setColorFilter(ContextCompat.getColor(deviceViewHolder.context, R.color.grey_5), PorterDuff.Mode.SRC_IN);
               deviceViewHolder.deviceConnStatusImage.setImageResource(R.drawable.status_mark_ready);
               deviceViewHolder.deviceConnStatusImage.setVisibility(View.VISIBLE);
               if (device.getLastConnection() != null)
                   deviceViewHolder.connectionStatusTextView.setText(R.string.recently);
               else
               deviceViewHolder.connectionStatusTextView.setText(R.string.never_connected);
               deviceViewHolder.connectButtonImage.setImageResource(R.drawable.ic_btn_connect);
               deviceViewHolder.connectButtonImage.setColorFilter(ContextCompat.getColor(deviceViewHolder.context, R.color.grey_60), PorterDuff.Mode.SRC_IN);
               break;
            case Linked:
            default:
               deviceViewHolder.deviceBackdropImage.setImageResource(R.drawable.thumbnail_background_wire);
               deviceViewHolder.deviceTypeImage.setColorFilter(ContextCompat.getColor(deviceViewHolder.context, R.color.grey_40), PorterDuff.Mode.SRC_IN);
               deviceViewHolder.deviceConnStatusImage.setVisibility(View.INVISIBLE);
               if (device.getLastConnection() != null)
                    deviceViewHolder.connectionStatusTextView.setText(R.string.recently);
                else
                    deviceViewHolder.connectionStatusTextView.setText(R.string.never_connected);
                deviceViewHolder.deviceConnStatusImage.setVisibility(View.INVISIBLE);
                break;
        }
    }

}
