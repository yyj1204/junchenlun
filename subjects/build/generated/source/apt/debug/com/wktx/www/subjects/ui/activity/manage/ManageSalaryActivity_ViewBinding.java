// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.manage;

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

public class ManageSalaryActivity_ViewBinding implements Unbinder {
  private ManageSalaryActivity target;

  private View view2131231164;

  @UiThread
  public ManageSalaryActivity_ViewBinding(ManageSalaryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ManageSalaryActivity_ViewBinding(final ManageSalaryActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvSalaryTotal = Utils.findRequiredViewAsType(source, R.id.tv_salaryTotal, "field 'tvSalaryTotal'", TextView.class);
    target.tvReceiveMoney = Utils.findRequiredViewAsType(source, R.id.tv_receiveMoney, "field 'tvReceiveMoney'", TextView.class);
    target.tvReceiveLine = Utils.findRequiredViewAsType(source, R.id.tv_receiveLine, "field 'tvReceiveLine'", TextView.class);
    target.tvTrusteeshipMoney = Utils.findRequiredViewAsType(source, R.id.tv_trusteeshipMoney, "field 'tvTrusteeshipMoney'", TextView.class);
    target.tvTrusteeshipLine = Utils.findRequiredViewAsType(source, R.id.tv_trusteeshipLine, "field 'tvTrusteeshipLine'", TextView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
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
    ManageSalaryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvSalaryTotal = null;
    target.tvReceiveMoney = null;
    target.tvReceiveLine = null;
    target.tvTrusteeshipMoney = null;
    target.tvTrusteeshipLine = null;
    target.swipeRefreshLayout = null;
    target.recyclerView = null;

    view2131231164.setOnClickListener(null);
    view2131231164 = null;
  }
}