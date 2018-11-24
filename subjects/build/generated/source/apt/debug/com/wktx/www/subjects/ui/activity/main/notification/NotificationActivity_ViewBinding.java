// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.main.notification;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NotificationActivity_ViewBinding implements Unbinder {
  private NotificationActivity target;

  private View view2131689766;

  @UiThread
  public NotificationActivity_ViewBinding(NotificationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NotificationActivity_ViewBinding(final NotificationActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'viewPager'", ViewPager.class);
    target.smartTab = Utils.findRequiredViewAsType(source, R.id.layout_smartTab, "field 'smartTab'", SmartTabLayout.class);
    view = Utils.findRequiredView(source, R.id.tb_IvReturn, "method 'MyOnclick'");
    view2131689766 = view;
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
    NotificationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.viewPager = null;
    target.smartTab = null;

    view2131689766.setOnClickListener(null);
    view2131689766 = null;
  }
}
