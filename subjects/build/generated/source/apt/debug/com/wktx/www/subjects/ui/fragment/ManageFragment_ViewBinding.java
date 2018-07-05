// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ManageFragment_ViewBinding implements Unbinder {
  private ManageFragment target;

  private View view2131231266;

  private View view2131230910;

  private View view2131231291;

  @UiThread
  public ManageFragment_ViewBinding(final ManageFragment target, View source) {
    this.target = target;

    View view;
    target.llNothing = Utils.findRequiredViewAsType(source, R.id.linear_nothing, "field 'llNothing'", LinearLayout.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tv_onlineExperience, "field 'tvExperience' and method 'MyOnclick'");
    target.tvExperience = Utils.castView(view, R.id.tv_onlineExperience, "field 'tvExperience'", TextView.class);
    view2131231266 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_message, "method 'MyOnclick'");
    view2131230910 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_selectWork, "method 'MyOnclick'");
    view2131231291 = view;
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
    ManageFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.llNothing = null;
    target.swipeRefreshLayout = null;
    target.recyclerView = null;
    target.tvExperience = null;

    view2131231266.setOnClickListener(null);
    view2131231266 = null;
    view2131230910.setOnClickListener(null);
    view2131230910 = null;
    view2131231291.setOnClickListener(null);
    view2131231291 = null;
  }
}
