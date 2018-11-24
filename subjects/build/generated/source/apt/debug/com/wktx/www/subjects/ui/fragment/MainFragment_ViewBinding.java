// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.widget.DropDownMenu;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainFragment_ViewBinding implements Unbinder {
  private MainFragment target;

  private View view2131689943;

  private View view2131690180;

  private View view2131690182;

  @UiThread
  public MainFragment_ViewBinding(final MainFragment target, View source) {
    this.target = target;

    View view;
    target.dropDownMenu = Utils.findRequiredViewAsType(source, R.id.dropDownMenu, "field 'dropDownMenu'", DropDownMenu.class);
    view = Utils.findRequiredView(source, R.id.iv_browsingHistory, "field 'ivBrowsingHistory' and method 'MyOnclick'");
    target.ivBrowsingHistory = Utils.castView(view, R.id.iv_browsingHistory, "field 'ivBrowsingHistory'", ImageView.class);
    view2131689943 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_titleSearch, "method 'MyOnclick'");
    view2131690180 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_titleRight, "method 'MyOnclick'");
    view2131690182 = view;
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
    MainFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.dropDownMenu = null;
    target.ivBrowsingHistory = null;

    view2131689943.setOnClickListener(null);
    view2131689943 = null;
    view2131690180.setOnClickListener(null);
    view2131690180 = null;
    view2131690182.setOnClickListener(null);
    view2131690182 = null;
  }
}
