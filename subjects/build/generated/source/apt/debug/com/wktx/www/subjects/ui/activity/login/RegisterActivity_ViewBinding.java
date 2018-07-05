// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.login;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view2131231245;

  private View view2131230780;

  private View view2131231164;

  private View view2131231254;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_getcode, "field 'tvGetCode' and method 'MyOnclick'");
    target.tvGetCode = Utils.castView(view, R.id.tv_getcode, "field 'tvGetCode'", TextView.class);
    view2131231245 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.etCode = Utils.findRequiredViewAsType(source, R.id.et_code, "field 'etCode'", EditText.class);
    target.etPwd1 = Utils.findRequiredViewAsType(source, R.id.et_pwd1, "field 'etPwd1'", EditText.class);
    target.etPwd2 = Utils.findRequiredViewAsType(source, R.id.et_pwd2, "field 'etPwd2'", EditText.class);
    view = Utils.findRequiredView(source, R.id.bt_register, "field 'btRegister' and method 'MyOnclick'");
    target.btRegister = Utils.castView(view, R.id.bt_register, "field 'btRegister'", Button.class);
    view2131230780 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tb_IvReturn, "method 'MyOnclick'");
    view2131231164 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_login, "method 'MyOnclick'");
    view2131231254 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvGetCode = null;
    target.etPhone = null;
    target.etCode = null;
    target.etPwd1 = null;
    target.etPwd2 = null;
    target.btRegister = null;

    view2131231245.setOnClickListener(null);
    view2131231245 = null;
    view2131230780.setOnClickListener(null);
    view2131230780 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
    view2131231254.setOnClickListener(null);
    view2131231254 = null;
  }
}
