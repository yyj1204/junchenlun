package com.wktx.www.emperor.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.wktx.www.emperor.R;


/**
 * 数字输入键盘
 */
public class CustomKeyboardView extends FrameLayout implements View.OnClickListener {

    private CustomPwdEditView editView;
    private Listener listener;

    public CustomKeyboardView(Context context) {
        super(context);
        init();
    }

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setEditView(CustomPwdEditView editView) {
        this.editView = editView;
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.widget_custom_keyboard, null);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(view);
        view.findViewById(R.id.tv_keyboard0).setOnClickListener(this);
        view.findViewById(R.id.tv_keyboard1).setOnClickListener(this);
        view.findViewById(R.id.tv_keyboard2).setOnClickListener(this);
        view.findViewById(R.id.tv_keyboard3).setOnClickListener(this);
        view.findViewById(R.id.tv_keyboard4).setOnClickListener(this);
        view.findViewById(R.id.tv_keyboard5).setOnClickListener(this);
        view.findViewById(R.id.tv_keyboard6).setOnClickListener(this);
        view.findViewById(R.id.tv_keyboard7).setOnClickListener(this);
        view.findViewById(R.id.tv_keyboard8).setOnClickListener(this);
        view.findViewById(R.id.tv_keyboard9).setOnClickListener(this);
        view.findViewById(R.id.rela_hide).setOnClickListener(this);
        view.findViewById(R.id.rela_delete).setOnClickListener(this);
    }

    /**
     * 隐藏键盘
     */
    public void hide() {
        setVisibility(GONE);
    }

    /**
     * 显示键盘
     */
    public void show() {
        setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View v) {
        final String tag = (String) v.getTag();
        if (tag != null) {
            switch (tag) {
                case "hide":
                    hide();
                    if (listener != null) {
                        listener.onHide();
                    }
                    break;
                case "delete":
                    if (editView != null) {
                        editView.delete();
                    }
                    if (listener != null) {
                        listener.onDelete();
                    }
                    break;
                default:
                    if (editView != null) {
                        editView.input(tag);
                    }
                    if (listener != null) {
                        listener.onInput(tag);
                    }
                    break;
            }
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {

        public void onHide();//隐藏

        public void onInput(String s);//输入

        public void onDelete();//删除

    }

}
