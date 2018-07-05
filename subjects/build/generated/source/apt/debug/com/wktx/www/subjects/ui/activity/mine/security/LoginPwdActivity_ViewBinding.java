// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine.security;

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

public class LoginPwdActivity_ViewBinding implements Unbinder {
  private LoginPwdActivity target;

  private View view2131230774;

  private View view2131231164;

  @UiThread
  public LoginPwdActivity_ViewBinding(LoginPwdActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginPwdActivity_ViewBinding(final LoginPwdActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.etPwdOld = Utils.findRequiredViewAsType(source, R.id.et_pwdOld, "field 'etPwdOld'", EditText.class);
    target.etPwdNew1 = Utils.findRequiredViewAsType(source, R.id.et_pwdNew1, "field 'etPwdNew1'", EditText.class);
    target.etPwdNew2 = Utils.findRequiredViewAsType(source, R.id.et_pwdNew2, "field 'etPwdNew2'", EditText.class);
    view = Utils.findRequiredView(source, R.id.bt_editPwd, "field 'btEditPwd' and method 'MyOnclick'");
    target.btEditPwd = Utils.castView(view, R.id.bt_editPwd, "field 'btEditPwd'", Button.class);
    view2131230774 = view;
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
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginPwdActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.etPwdOld = null;
    target.etPwdNew1 = null;
    target.etPwdNew2 = null;
    target.btEditPwd = null;

    view2131230774.setOnClickListener(null);
    view2131230774 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
  }
}
