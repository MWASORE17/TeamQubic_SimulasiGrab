package com.qubic.grabsimulation.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.qubic.grabsimulation.R;
import com.qubic.grabsimulation.helper.DimensionHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomDialog extends Dialog {

    @BindView(R.id.custom_dialog_header)
    protected TextView headerMessage;
    @BindView(R.id.custom_dialog_message)
    protected TextView messageView;
    @BindView(R.id.custom_dialog_positive_button)
    protected Button positiveButton;
    @BindView(R.id.custom_dialog_negative_button)
    protected Button negativeButton;
    @BindView(R.id.custom_dialog_single_button)
    protected Button singleButton;
    @BindView(R.id.custom_dialog_single_button_container)
    protected LinearLayout singleButtonContainer;
    @BindView(R.id.custom_dialog_double_button_container)
    protected LinearLayout doubleButtonContainer;

    private Context context;

    public CustomDialog(Context context) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent)));
        setContentView(R.layout.custom_dialog);
        ButterKnife.bind(this);

        window.setLayout(DimensionHelper.getScreenWidth(getContext()) * 6 / 7, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void setMessage(CharSequence message) {
        this.messageView.setText(message);
    }

    public void setPositiveButton(String text, final OnClickListener listener) {
        singleButtonContainer.setVisibility(View.GONE);
        doubleButtonContainer.setVisibility(View.VISIBLE);
        positiveButton.setText(text);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onClick();
            }
        });
    }

    public void coloredPositiveButton(boolean isColored) {
        if (isColored) {
            positiveButton.setTextColor(ContextCompat.getColor(context, R.color.link_color));
        } else {
            positiveButton.setTextColor(ContextCompat.getColor(context, R.color.default_text_color));
        }
    }

    public void setNegativeButton(String text, final OnClickListener listener) {
        singleButtonContainer.setVisibility(View.GONE);
        doubleButtonContainer.setVisibility(View.VISIBLE);
        negativeButton.setText(text);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onClick();
            }
        });
    }

    public void coloredNegativeButton(boolean isColored) {
        if (isColored) {
            negativeButton.setTextColor(ContextCompat.getColor(context, R.color.link_color));
        } else {
            negativeButton.setTextColor(ContextCompat.getColor(context, R.color.default_text_color));
        }
    }

    public void setSingleButton(String text, final OnClickListener listener) {
        singleButtonContainer.setVisibility(View.VISIBLE);
        doubleButtonContainer.setVisibility(View.GONE);
        singleButton.setText(text);
        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                listener.onClick();
            }
        });
    }

    public void setSingleNonDismissButton(String text, final OnClickListener listener) {
        singleButtonContainer.setVisibility(View.VISIBLE);
        doubleButtonContainer.setVisibility(View.GONE);
        singleButton.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        singleButton.setText(text);
        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick();
            }
        });
    }

    public void setHeaderMessage(CharSequence message) {
        headerMessage.setText(message);
        headerMessage.setVisibility(View.VISIBLE);
    }

    public void showHeaderMessage(boolean show) {
        if (show) {
            headerMessage.setVisibility(View.VISIBLE);
        } else {
            headerMessage.setVisibility(View.GONE);
        }
    }

    public void setTextGravityCenter(boolean center) {
        if (center) {
            headerMessage.setGravity(Gravity.CENTER);
            messageView.setGravity(Gravity.CENTER);
        } else {
            messageView.setGravity(Gravity.LEFT);
        }
    }

    public TextView getMessageView() {
        return messageView;
    }

    public Button getPositiveButton() {
        return positiveButton;
    }

    public Button getNegativeButton() {
        return negativeButton;
    }

    public interface OnClickListener {
        public void onClick();
    }
}
