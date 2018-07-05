// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.main.notification;

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

public class NotificationDetailsActivity_ViewBinding implements Unbinder {
  private NotificationDetailsActivity target;

  private View view2131231164;

  @UiThread
  public NotificationDetailsActivity_ViewBinding(NotificationDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NotificationDetailsActivity_ViewBinding(final NotificationDetailsActivity target,
      View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvMessageTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvMessageTitle'", TextView.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvTime'", TextView.class);
    target.tvContent = Utils.findRequiredViewAsType(source, R.id.tv_content, "field 'tvContent'", TextView.class);
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
    NotificationDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvMessageTitle = null;
    target.tvTime = null;
    target.tvContent = null;

    view2131231164.setOnClickListener(null);
    view2131231164 = null;
  }
}
