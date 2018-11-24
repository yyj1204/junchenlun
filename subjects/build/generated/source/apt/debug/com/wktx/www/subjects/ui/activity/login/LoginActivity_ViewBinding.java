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

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131689782;

  private View view2131689766;

  private View view2131689781;

  private View view2131689784;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.etPwd = Utils.findRequiredViewAsType(source, R.id.et_pwd, "field 'etPwd'", EditText.class);
    view = Utils.findRequiredView(source, R.id.bt_login, "field 'btLogin' and method 'MyOnclick'");
    target.btLogin = Utils.castView(view, R.id.bt_login, "field 'btLogin'", Button.class);
    view2131689782 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tb_IvReturn, "method 'MyOnclick'");
    view2131689766 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_forget_pwd, "method 'MyOnclick'");
    view2131689781 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_regiest, "method 'MyOnclick'");
    view2131689784 = view;
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
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.etPhone = null;
    target.etPwd = null;
    target.btLogin = null;

    view2131689782.setOnClickListener(null);
    view2131689782 = null;
    view2131689766.setOnClickListener(null);
    view2131689766 = null;
    view2131689781.setOnClickListener(null);
    view2131689781 = null;
    view2131689784.setOnClickListener(null);
    view2131689784 = null;
  }
}
