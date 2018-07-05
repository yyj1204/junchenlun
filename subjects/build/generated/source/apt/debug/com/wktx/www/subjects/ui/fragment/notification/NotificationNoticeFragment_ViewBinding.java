// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.fragment.notification;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NotificationNoticeFragment_ViewBinding implements Unbinder {
  private NotificationNoticeFragment target;

  @UiThread
  public NotificationNoticeFragment_ViewBinding(NotificationNoticeFragment target, View source) {
    this.target = target;

    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NotificationNoticeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.swipeRefreshLayout = null;
    target.recyclerView = null;
  }
}
