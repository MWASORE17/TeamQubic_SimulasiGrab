package com.qubic.grabsimulation.view.custom;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.qubic.grabsimulation.R;


public class WarningDialog extends CustomDialog {

    private static WarningDialog instance = null;
    private Context context;

    public WarningDialog(Context context, String message) {
        super(context);
        this.context = context;
        setMessage(message);
        setTextGravityCenter(true);
        this.singleButton.setTextColor(ContextCompat.getColor(context, R.color.link_color));
        setSingleButton(context.getString(R.string.ok), new OnClickListener() {
            @Override
            public void onClick() {

            }
        });
    }

    static public WarningDialog getInstance(Context context) {
        if (null == instance || instance.context != context) {
            instance = new WarningDialog(context, "");
        } else {
            instance.clear();
        }
        return instance;
    }

    @Override
    public void show() {

        super.show();
    }

    public void clear() {
        setMessage("");
        setButtonAction(new OnClickListener() {
            @Override
            public void onClick() {

            }
        });
    }

    public void setButtonAction(OnClickListener onClickListener) {
        setCancelable(false);
        setSingleButton(context.getString(R.string.ok), onClickListener);
    }

    public void setButtonAction(String buttonText, OnClickListener onClickListener) {
        setCancelable(false);
        setSingleButton(buttonText, onClickListener);
    }
}
