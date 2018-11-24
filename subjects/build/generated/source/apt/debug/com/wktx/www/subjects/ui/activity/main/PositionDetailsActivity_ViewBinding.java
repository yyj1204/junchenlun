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

  private View view2131689822;

  private View view2131689836;

  private View view2131689766;

  private View view2131689833;

  @UiThread
  public PositionDetailsActivity_ViewBinding(PositionDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PositionDetailsActivity_ViewBinding(final PositionDetailsActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvPosition = Utils.findRequiredViewAsType(source, R.id.tv_position, "field 'tvPosition'", TextView.class);
    target.tvExperience = Utils.findRequiredViewAsType(source, R.id.tv_experience, "field 'tvExperience'", TextView.class);
    target.tvDemandSalary = Utils.findRequiredViewAsType(source, R.id.tv_demandSalary, "field 'tvDemandSalary'", TextView.class);
    target.tvHireType = Utils.findRequiredViewAsType(source, R.id.tv_hireType, "field 'tvHireType'", TextView.class);
    target.tvCategory = Utils.findRequiredViewAsType(source, R.id.tv_category, "field 'tvCategory'", TextView.class);
    target.tvPlatform = Utils.findRequiredViewAsType(source, R.id.tv_platform, "field 'tvPlatform'", TextView.class);
    target.tvPatternTitle = Utils.findRequiredViewAsType(source, R.id.tv_patternTitle, "field 'tvPatternTitle'", TextView.class);
    target.tvPattern = Utils.findRequiredViewAsType(source, R.id.tv_pattern, "field 'tvPattern'", TextView.class);
    target.ivHead = Utils.findRequiredViewAsType(source, R.id.iv_head, "field 'ivHead'", CircleImageView.class);
    target.tvCmpanyName = Utils.findRequiredViewAsType(source, R.id.tv_companyName, "field 'tvCmpanyName'", TextView.class);
    target.tvCmpanySite = Utils.findRequiredViewAsType(source, R.id.tv_companySite, "field 'tvCmpanySite'", TextView.class);
    target.tvStoreName = Utils.findRequiredViewAsType(source, R.id.tv_storeName, "field 'tvStoreName'", TextView.class);
    target.tvStoreUrl = Utils.findRequiredViewAsType(source, R.id.tv_storeUrl, "field 'tvStoreUrl'", TextView.class);
    target.tvCompanyRemark = Utils.findRequiredViewAsType(source, R.id.tv_companyRemark, "field 'tvCompanyRemark'", TextView.class);
    target.tvDemandTitle = Utils.findRequiredViewAsType(source, R.id.tv_demandTitle, "field 'tvDemandTitle'", TextView.class);
    target.tvDemandContent = Utils.findRequiredViewAsType(source, R.id.tv_demandContent, "field 'tvDemandContent'", TextView.class);
    view = Utils.findRequiredView(source, R.id.linear_collect, "field 'llCollect' and method 'MyOnclick'");
    target.llCollect = Utils.castView(view, R.id.linear_collect, "field 'llCollect'", LinearLayout.class);
    view2131689822 = view;
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
    view2131689836 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tb_IvReturn, "method 'MyOnclick'");
    view2131689766 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_company, "method 'MyOnclick'");
    view2131689833 = view;
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
    target.tvPosition = null;
    target.tvExperience = null;
    target.tvDemandSalary = null;
    target.tvHireType = null;
    target.tvCategory = null;
    target.tvPlatform = null;
    target.tvPatternTitle = null;
    target.tvPattern = null;
    target.ivHead = null;
    target.tvCmpanyName = null;
    target.tvCmpanySite = null;
    target.tvStoreName = null;
    target.tvStoreUrl = null;
    target.tvCompanyRemark = null;
    target.tvDemandTitle = null;
    target.tvDemandContent = null;
    target.llCollect = null;
    target.ivCollect = null;
    target.tvCollect = null;
    target.tvAddTime = null;
    target.tvEndTime = null;
    target.tvOverdueState = null;
    target.btInterview = null;

    view2131689822.setOnClickListener(null);
    view2131689822 = null;
    view2131689836.setOnClickListener(null);
    view2131689836 = null;
    view2131689766.setOnClickListener(null);
    view2131689766 = null;
    view2131689833.setOnClickListener(null);
    view2131689833 = null;
  }
}
