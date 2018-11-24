// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ScreeningSalaryActivity_ViewBinding implements Unbinder {
  private ScreeningSalaryActivity target;

  private View view2131689899;

  private View view2131689687;

  private View view2131689688;

  @UiThread
  public ScreeningSalaryActivity_ViewBinding(ScreeningSalaryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ScreeningSalaryActivity_ViewBinding(final ScreeningSalaryActivity target, View source) {
    this.target = target;

    View view;
    target.etMinSalary = Utils.findRequiredViewAsType(source, R.id.et_minSalary, "field 'etMinSalary'", EditText.class);
    target.etMaxSalary = Utils.findRequiredViewAsType(source, R.id.et_maxSalary, "field 'etMaxSalary'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv, "method 'MyOnclick'");
    view2131689899 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_cancel, "method 'MyOnclick'");
    view2131689687 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_sure, "method 'MyOnclick'");
    view2131689688 = view;
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
    ScreeningSalaryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etMinSalary = null;
    target.etMaxSalary = null;

    view2131689899.setOnClickListener(null);
    view2131689899 = null;
    view2131689687.setOnClickListener(null);
    view2131689687 = null;
    view2131689688.setOnClickListener(null);
    view2131689688 = null;
  }
}
