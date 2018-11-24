// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.fragment.notification;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NotificationRemindFragment_ViewBinding implements Unbinder {
  private NotificationRemindFragment target;

  private View view2131689769;

  private View view2131689771;

  private View view2131689975;

  @UiThread
  public NotificationRemindFragment_ViewBinding(final NotificationRemindFragment target,
      View source) {
    this.target = target;

    View view;
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tv_beginTime, "field 'tvBeginTime' and method 'MyOnclick'");
    target.tvBeginTime = Utils.castView(view, R.id.tv_beginTime, "field 'tvBeginTime'", TextView.class);
    view2131689769 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_endTime, "field 'tvEndTime' and method 'MyOnclick'");
    target.tvEndTime = Utils.castView(view, R.id.tv_endTime, "field 'tvEndTime'", TextView.class);
    view2131689771 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_query, "field 'tvQuery' and method 'MyOnclick'");
    target.tvQuery = Utils.castView(view, R.id.tv_query, "field 'tvQuery'", TextView.class);
    view2131689975 = view;
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
    NotificationRemindFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.swipeRefreshLayout = null;
    target.recyclerView = null;
    target.tvBeginTime = null;
    target.tvEndTime = null;
    target.tvQuery = null;

    view2131689769.setOnClickListener(null);
    view2131689769 = null;
    view2131689771.setOnClickListener(null);
    view2131689771 = null;
    view2131689975.setOnClickListener(null);
    view2131689975 = null;
  }
}
