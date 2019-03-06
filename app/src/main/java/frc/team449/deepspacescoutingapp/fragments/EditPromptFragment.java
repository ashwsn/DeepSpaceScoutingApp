package frc.team449.deepspacescoutingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.activities.Prematch;
import frc.team449.deepspacescoutingapp.helpers.BluetoothHelper;
import frc.team449.deepspacescoutingapp.helpers.FileHelper;
import frc.team449.deepspacescoutingapp.helpers.PopupHelper;
import frc.team449.deepspacescoutingapp.model.Match;

public class EditPromptFragment extends DialogFragment {

    private String[] allData;
    private int selectedMatch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_prompt_fragment, container, false);

        Spinner matchSpinner = v.findViewById(R.id.match);

        String[] allData;
        try {
            allData = FileHelper.getFromFile("alldata.csv", getContext()).split("\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            allData = new String[]{};
        }

        List<String> matches = new ArrayList<>();
        String[] splitMatch;
        for (String match : allData) {
            splitMatch = match.split(",");
            matches.add(0,"Match " + splitMatch[1] + ", Team " + splitMatch[0]);
        }


        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.dropdown, matches);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.dropdown);
        matchSpinner.setAdapter(spinnerArrayAdapter);

        matchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedMatch = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMatch = 0;
            }
        });


        v.findViewById(R.id.closeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        v.findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        return v;
    }

    public void edit(){
        String match = allData[allData.length - selectedMatch];
        Match.getInstance().loadFromString(match, getContext());
        dismiss();
        startActivity(new Intent(getContext(), Prematch.class));
    }

}