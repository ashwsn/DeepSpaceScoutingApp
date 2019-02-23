package frc.team449.deepspacescoutingapp.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.IOException;
import java.util.List;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.helpers.BluetoothHelper;
import frc.team449.deepspacescoutingapp.helpers.PopupHelper;

public class BluetoothSetupFragment extends DialogFragment {

    private String master;

    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bluetooth_setup_fragment, container, false);

        Spinner masterSpinner = v.findViewById(R.id.masterName);
        List<String> paired = BluetoothHelper.getInstance().getPairedDevices();
        if (paired.size()>0) master = paired.get(0);
        else master = "";
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.dropdown,paired);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.dropdown);
        masterSpinner.setAdapter(spinnerArrayAdapter);

        masterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                master = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                master = parent.getItemAtPosition(0).toString();

            }
        });


        Button closeButton = v.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        progressBar = v.findViewById(R.id.progressBar);

        Button connectButton = v.findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect(v);
            }
        });

        return v;
    }

    public void connect(View v){
        progressBar.setVisibility(View.VISIBLE);
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BluetoothHelper.getInstance().initializeConnection(master);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (BluetoothHelper.getInstance().isConnected()) {
                    PopupHelper.info(getString(R.string.bluetooth_connected_title),String.format(getString(R.string.bluetooth_connected_prompt),master), (AppCompatActivity) getContext());
                } else {
                    PopupHelper.info(getString(R.string.bluetooth_connect_error_title),getString(R.string.bluetooth_connect_error_prompt), (AppCompatActivity) getContext());
                }
                dismiss();
            }
        }).start();

    }

}