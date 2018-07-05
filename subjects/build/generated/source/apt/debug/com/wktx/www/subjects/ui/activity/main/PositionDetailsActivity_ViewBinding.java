// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PositionDetailsActivity_ViewBinding implements Unbinder {
  private PositionDetailsActivity target;

  private View view2131230954;

  private View view2131230775;

  private View view2131231164;

  private View view2131230955;

  @UiThread
  public PositionDetailsActivity_ViewBinding(PositionDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PositionDetailsActivity_ViewBinding(final PositionDetailsActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvDemandTitle = Utils.findRequiredViewAsType(source, R.id.tv_demandTitle, "field 'tvDemandTitle'", TextView.class);
    target.tvDemandSalary = Utils.findRequiredViewAsType(source, R.id.tv_demandSalary, "field 'tvDemandSalary'", TextView.class);
    target.tvCategory = Utils.findRequiredViewAsType(source, R.id.tv_category, "field 'tvCategory'", TextView.class);
    target.tvPlatform = Utils.findRequiredViewAsType(source, R.id.tv_platform, "field 'tvPlatform'", TextView.class);
    target.tvCmpanyName = Utils.findRequiredViewAsType(source, R.id.tv_companyName, "field 'tvCmpanyName'", TextView.class);
    target.tvCmpanySite = Utils.findRequiredViewAsType(source, R.id.tv_companySite, "field 'tvCmpanySite'", TextView.class);
    target.ivHead = Utils.findRequiredViewAsType(source, R.id.iv_head, "field 'ivHead'", CircleImageView.class);
    target.tvStoreName = Utils.findRequiredViewAsType(source, R.id.tv_storeName, "field 'tvStoreName'", TextView.class);
    target.tvDemandContent = Utils.findRequiredViewAsType(source, R.id.tv_demandContent, "field 'tvDemandContent'", TextView.class);
    view = Utils.findRequiredView(source, R.id.linear_collect, "field 'llCollect' and method 'MyOnclick'");
    target.llCollect = Utils.castView(view, R.id.linear_collect, "field 'llCollect'", LinearLayout.class);
    view2131230954 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    target.ivCollect = Utils.findRequiredViewAsType(source, R.id.iv_collect, "field 'ivCollect'", ImageView.class);
    target.tvCollect = Utils.findRequiredViewAsType(source, R.id.tv_collect, "field 'tvCollect'", TextView.class);
    target.tvAddTime = Utils.findRequiredViewAsType(source, R.id.tv_addTime, "field 'tvAddTime'", TextView.class);
    target.tvEndTime = Utils.findRequiredViewAsType(source, R.id.tv_endTime, "field 'tvEndTime'", TextView.class);
    target.tvOverdueState = Utils.findRequiredViewAsType(source, R.id.tv_overdueState, "field 'tvOverdueState'", TextView.class);
    view = Utils.findRequiredView(source, R.id.bt_interview, "field 'btInterview' and method 'MyOnclick'");
    target.btInterview = Utils.castView(view, R.id.bt_interview, "field 'btInterview'", Button.class);
    view2131230775 = view;
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
    view = Utils.findRequiredView(source, R.id.linear_company, "method 'MyOnclick'");
    view2131230955 = view;
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
    PositionDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvDemandTitle = null;
    target.tvDemandSalary = null;
    target.tvCategory = null;
    target.tvPlatform = null;
    target.tvCmpanyName = null;
    target.tvCmpanySite = null;
    target.ivHead = null;
    target.tvStoreName = null;
    target.tvDemandContent = null;
    target.llCollect = null;
    target.ivCollect = null;
    target.tvCollect = null;
    target.tvAddTime = null;
    target.tvEndTime = null;
    target.tvOverdueState = null;
    target.btInterview = null;

    view2131230954.setOnClickListener(null);
    view2131230954 = null;
    view2131230775.setOnClickListener(null);
    view2131230775 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
    view2131230955.setOnClickListener(null);
    view2131230955 = null;
  }
}
