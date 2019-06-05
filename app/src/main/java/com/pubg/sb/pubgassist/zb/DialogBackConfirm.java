package com.pubg.sb.pubgassist.zb;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.pubg.sb.pubgassist.R;


public class DialogBackConfirm extends Dialog {
    public DialogBackConfirm(@NonNull Context context) {
        super(context, R.style.CustomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commnet_delete_confirm);

        findViewById(R.id.tvCancel).setOnClickListener(v -> dismiss());
    }
}
