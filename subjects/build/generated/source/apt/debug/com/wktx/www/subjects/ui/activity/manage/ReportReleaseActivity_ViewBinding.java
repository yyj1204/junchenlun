// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.manage;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReportReleaseActivity_ViewBinding implements Unbinder {
  private ReportReleaseActivity target;

  private View view2131230785;

  private View view2131231164;

  private View view2131230965;

  @UiThread
  public ReportReleaseActivity_ViewBinding(ReportReleaseActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ReportReleaseActivity_ViewBinding(final ReportReleaseActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvStoreName = Utils.findRequiredViewAsType(source, R.id.tv_storeName, "field 'tvStoreName'", TextView.class);
    target.tvReportTime = Utils.findRequiredViewAsType(source, R.id.tv_reportTime, "field 'tvReportTime'", TextView.class);
    target.etWorkContent = Utils.findRequiredViewAsType(source, R.id.et_workContent, "field 'etWorkContent'", EditText.class);
    target.etStoreState = Utils.findRequiredViewAsType(source, R.id.et_storeState, "field 'etStoreState'", EditText.class);
    target.etOperationPlan = Utils.findRequiredViewAsType(source, R.id.et_operationPlan, "field 'etOperationPlan'", EditText.class);
    target.etNeedHelp = Utils.findRequiredViewAsType(source, R.id.et_needHelp, "field 'etNeedHelp'", EditText.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.bt_sureRelease, "field 'btSureRelease' and method 'MyOnclick'");
    target.btSureRelease = Utils.castView(view, R.id.bt_sureRelease, "field 'btSureRelease'", Button.class);
    view2131230785 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tb_IvReturn, "method 'MyOnclick'");
    view2131231164 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_imgAdd, "method 'MyOnclick'");
    view2131230965 = view;
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
    ReportReleaseActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvStoreName = null;
    target.tvReportTime = null;
    target.etWorkContent = null;
    target.etStoreState = null;
    target.etOperationPlan = null;
    target.etNeedHelp = null;
    target.recyclerView = null;
    target.btSureRelease = null;

    view2131230785.setOnClickListener(null);
    view2131230785 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
    view2131230965.setOnClickListener(null);
    view2131230965 = null;
  }
}
