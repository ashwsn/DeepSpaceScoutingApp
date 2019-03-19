package frc.team449.deepspacescoutingapp.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import frc.team449.deepspacescoutingapp.R;

public class PopupFragment  extends DialogFragment {

    private TextView titleTV;
    private TextView bodyTV;
    private Button button1;
    private Button button2;

    private String title;
    private String body;
    private String button1Text;
    private String button2Text;
    private Runnable action1;
    private Runnable action2;

    public static PopupFragment newInstance(String title, String body, String buttonText) {
        return newInstance(title,body,buttonText,"",null,null);
    }

    public static PopupFragment newInstance(String title, String body, String button1Text, String button2Text,
                                            Runnable action1, Runnable action2) {
        PopupFragment popupFragment = new PopupFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("body", body);
        args.putString("button1Text", button1Text);
        args.putString("button2Text", button2Text);
        popupFragment.setArguments(args);

        popupFragment.setActions(action1,action2);

        return popupFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Dialog);

        title = getArguments().getString("title");
        body = getArguments().getString("body");
        button1Text = getArguments().getString("button1Text");
        button2Text = getArguments().getString("button2Text");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popup_fragment, container, false);

        titleTV = v.findViewById(R.id.title);
        bodyTV = v.findViewById(R.id.body);
        button1 = v.findViewById(R.id.button1);
        button2 = v.findViewById(R.id.button2);

        setup();

        return v;
    }

    public void setup() {

        titleTV.setText(title);
        bodyTV.setText(body);

        button1.setText(button1Text);

        if (button2Text.equals("")) {
            button2.setVisibility(View.GONE);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (action1 != null) action1.run();
                    dismiss();
                }
            });
        } else {
            button2.setVisibility(View.VISIBLE);
            button2.setText(button2Text);

            if (action1!=null) {
                addClickListeners();
            }
        }
    }

    public void setActions(Runnable action1, Runnable action2) {
        this.action1 = action1;
        this.action2 = action2;
    }

    private void addClickListeners() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action1.run();
                dismiss();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action2.run();
                dismiss();
            }
        });
    }
}

