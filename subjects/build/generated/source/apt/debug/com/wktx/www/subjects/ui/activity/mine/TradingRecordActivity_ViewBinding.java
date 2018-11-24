// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine;

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

public class TradingRecordActivity_ViewBinding implements Unbinder {
  private TradingRecordActivity target;

  private View view2131689766;

  private View view2131689912;

  private View view2131689887;

  @UiThread
  public TradingRecordActivity_ViewBinding(TradingRecordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TradingRecordActivity_ViewBinding(final TradingRecordActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvBalanceMoney = Utils.findRequiredViewAsType(source, R.id.tv_balanceMoney, "field 'tvBalanceMoney'", TextView.class);
    target.tvBalanceLine = Utils.findRequiredViewAsType(source, R.id.tv_balanceLine, "field 'tvBalanceLine'", TextView.class);
    target.tvTrusteeshipMoney = Utils.findRequiredViewAsType(source, R.id.tv_trusteeshipMoney, "field 'tvTrusteeshipMoney'", TextView.class);
    target.tvTrusteeshipLine = Utils.findRequiredViewAsType(source, R.id.tv_trusteeshipLine, "field 'tvTrusteeshipLine'", TextView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tb_IvReturn, "method 'MyOnclick'");
    view2131689766 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_balance, "method 'MyOnclick'");
    view2131689912 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_trusteeship, "method 'MyOnclick'");
    view2131689887 = view;
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
    TradingRecordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvBalanceMoney = null;
    target.tvBalanceLine = null;
    target.tvTrusteeshipMoney = null;
    target.tvTrusteeshipLine = null;
    target.swipeRefreshLayout = null;
    target.recyclerView = null;

    view2131689766.setOnClickListener(null);
    view2131689766 = null;
    view2131689912.setOnClickListener(null);
    view2131689912 = null;
    view2131689887.setOnClickListener(null);
    view2131689887 = null;
  }
}
