package frc.team449.deepspacescoutingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frc.team449.deepspacescoutingapp.R;
import frc.team449.deepspacescoutingapp.activities.Prematch;
import frc.team449.deepspacescoutingapp.helpers.FileHelper;
import frc.team449.deepspacescoutingapp.model.Match;

public class EditPromptFragment extends DialogFragment {

    private List<String> allData = new ArrayList<>();
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

        String[] data;

        data = FileHelper.getFromFile("alldata.csv", getContext()).split("\n");

        List<String> matches = new ArrayList<>();
        String[] splitMatch;
        for (String match : data) {
            splitMatch = match.split(",");
            if (splitMatch[0].contains("REPLACE")) {
                int i = allData.indexOf(match.substring("REPLACE".length()));
                allData.remove(i);
                matches.remove(i);
            } else if (splitMatch.length > 1) {
                matches.add(0, "Match " + splitMatch[1] + ", Team " + splitMatch[0]);
                allData.add(0, match);
            }
        }

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),
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
        String match = allData.get(selectedMatch);
        Match.getInstance().loadFromString(match, getContext());
        dismiss();
        startActivity(new Intent(getContext(), Prematch.class));
    }

}