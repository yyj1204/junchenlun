// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine;

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

public class ContactServiceActivity_ViewBinding implements Unbinder {
  private ContactServiceActivity target;

  private View view2131231164;

  private View view2131230937;

  private View view2131230978;

  @UiThread
  public ContactServiceActivity_ViewBinding(ContactServiceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ContactServiceActivity_ViewBinding(final ContactServiceActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvQQNumber = Utils.findRequiredViewAsType(source, R.id.tv_QQNumber, "field 'tvQQNumber'", TextView.class);
    target.tvServiceTime1 = Utils.findRequiredViewAsType(source, R.id.tv_serviceTime1, "field 'tvServiceTime1'", TextView.class);
    target.tvPhoneNumber = Utils.findRequiredViewAsType(source, R.id.tv_phoneNumber, "field 'tvPhoneNumber'", TextView.class);
    target.tvServiceTime2 = Utils.findRequiredViewAsType(source, R.id.tv_serviceTime2, "field 'tvServiceTime2'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tb_IvReturn, "method 'MyOnclick'");
    view2131231164 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_QQ, "method 'MyOnclick'");
    view2131230937 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_phone, "method 'MyOnclick'");
    view2131230978 = view;
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
    ContactServiceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvQQNumber = null;
    target.tvServiceTime1 = null;
    target.tvPhoneNumber = null;
    target.tvServiceTime2 = null;

    view2131231164.setOnClickListener(null);
    view2131231164 = null;
    view2131230937.setOnClickListener(null);
    view2131230937 = null;
    view2131230978.setOnClickListener(null);
    view2131230978 = null;
  }
}
