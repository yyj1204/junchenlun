// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine.security;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AccountSecurityActivity_ViewBinding implements Unbinder {
  private AccountSecurityActivity target;

  private View view2131689766;

  private View view2131689652;

  private View view2131689653;

  private View view2131689654;

  private View view2131689655;

  @UiThread
  public AccountSecurityActivity_ViewBinding(AccountSecurityActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AccountSecurityActivity_ViewBinding(final AccountSecurityActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvAlipayState = Utils.findRequiredViewAsType(source, R.id.tv_alipayState, "field 'tvAlipayState'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tb_IvReturn, "method 'MyOnclick'");
    view2131689766 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_loginPwd, "method 'MyOnclick'");
    view2131689652 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_payPwd, "method 'MyOnclick'");
    view2131689653 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_loginPhone, "method 'MyOnclick'");
    view2131689654 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_alipay, "method 'MyOnclick'");
    view2131689655 = view;
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
    AccountSecurityActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvAlipayState = null;

    view2131689766.setOnClickListener(null);
    view2131689766 = null;
    view2131689652.setOnClickListener(null);
    view2131689652 = null;
    view2131689653.setOnClickListener(null);
    view2131689653 = null;
    view2131689654.setOnClickListener(null);
    view2131689654 = null;
    view2131689655.setOnClickListener(null);
    view2131689655 = null;
  }
}
