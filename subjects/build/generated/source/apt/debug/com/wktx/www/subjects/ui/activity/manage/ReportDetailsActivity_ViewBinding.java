// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.manage;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.hedgehog.ratingbar.RatingBar;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReportDetailsActivity_ViewBinding implements Unbinder {
  private ReportDetailsActivity target;

  private View view2131231164;

  @UiThread
  public ReportDetailsActivity_ViewBinding(ReportDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ReportDetailsActivity_ViewBinding(final ReportDetailsActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvStoreName = Utils.findRequiredViewAsType(source, R.id.tv_storeName, "field 'tvStoreName'", TextView.class);
    target.tvReportTime = Utils.findRequiredViewAsType(source, R.id.tv_reportTime, "field 'tvReportTime'", TextView.class);
    target.tvWorkContent = Utils.findRequiredViewAsType(source, R.id.tv_workContent, "field 'tvWorkContent'", TextView.class);
    target.tvStoreState = Utils.findRequiredViewAsType(source, R.id.tv_storeState, "field 'tvStoreState'", TextView.class);
    target.tvOperationPlan = Utils.findRequiredViewAsType(source, R.id.tv_operationPlan, "field 'tvOperationPlan'", TextView.class);
    target.tvNeedHelp = Utils.findRequiredViewAsType(source, R.id.tv_needHelp, "field 'tvNeedHelp'", TextView.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.llScore = Utils.findRequiredViewAsType(source, R.id.linear_score, "field 'llScore'", LinearLayout.class);
    target.llEvaluate = Utils.findRequiredViewAsType(source, R.id.linear_evaluate, "field 'llEvaluate'", LinearLayout.class);
    target.tvEvaluateTime = Utils.findRequiredViewAsType(source, R.id.tv_evaluateTime, "field 'tvEvaluateTime'", TextView.class);
    target.rbServiceAttitude = Utils.findRequiredViewAsType(source, R.id.rb_serviceAttitude, "field 'rbServiceAttitude'", RatingBar.class);
    target.rbWorkAbility = Utils.findRequiredViewAsType(source, R.id.rb_workAbility, "field 'rbWorkAbility'", RatingBar.class);
    target.rbResponseSpeed = Utils.findRequiredViewAsType(source, R.id.rb_responseSpeed, "field 'rbResponseSpeed'", RatingBar.class);
    target.tvEvaluate = Utils.findRequiredViewAsType(source, R.id.tv_evaluate, "field 'tvEvaluate'", TextView.class);
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
    ReportDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvStoreName = null;
    target.tvReportTime = null;
    target.tvWorkContent = null;
    target.tvStoreState = null;
    target.tvOperationPlan = null;
    target.tvNeedHelp = null;
    target.recyclerView = null;
    target.llScore = null;
    target.llEvaluate = null;
    target.tvEvaluateTime = null;
    target.rbServiceAttitude = null;
    target.rbWorkAbility = null;
    target.rbResponseSpeed = null;
    target.tvEvaluate = null;

    view2131231164.setOnClickListener(null);
    view2131231164 = null;
  }
}
