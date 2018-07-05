// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.manage;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FireDetailsActivity_ViewBinding implements Unbinder {
  private FireDetailsActivity target;

  private View view2131231198;

  private View view2131231280;

  private View view2131231164;

  @UiThread
  public FireDetailsActivity_ViewBinding(FireDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FireDetailsActivity_ViewBinding(final FireDetailsActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.ivHead = Utils.findRequiredViewAsType(source, R.id.iv_head, "field 'ivHead'", CircleImageView.class);
    target.tvCompanyName = Utils.findRequiredViewAsType(source, R.id.tv_companyName, "field 'tvCompanyName'", TextView.class);
    target.tvFireState = Utils.findRequiredViewAsType(source, R.id.tv_fireState, "field 'tvFireState'", TextView.class);
    target.tvFireTime = Utils.findRequiredViewAsType(source, R.id.tv_fireTime, "field 'tvFireTime'", TextView.class);
    target.tvSalary = Utils.findRequiredViewAsType(source, R.id.tv_salary, "field 'tvSalary'", TextView.class);
    target.tvSalaryPay = Utils.findRequiredViewAsType(source, R.id.tv_salaryPay, "field 'tvSalaryPay'", TextView.class);
    target.tvFireCause = Utils.findRequiredViewAsType(source, R.id.tv_fireCause, "field 'tvFireCause'", TextView.class);
    target.tvFireResult = Utils.findRequiredViewAsType(source, R.id.tv_fireResult, "field 'tvFireResult'", TextView.class);
    target.llFire = Utils.findRequiredViewAsType(source, R.id.linear_fire, "field 'llFire'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_accept, "field 'tvAccept' and method 'MyOnclick'");
    target.tvAccept = Utils.castView(view, R.id.tv_accept, "field 'tvAccept'", TextView.class);
    view2131231198 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_refuse, "field 'tvRefuse' and method 'MyOnclick'");
    target.tvRefuse = Utils.castView(view, R.id.tv_refuse, "field 'tvRefuse'", TextView.class);
    view2131231280 = view;
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
  }

  @Override
  @CallSuper
  public void unbind() {
    FireDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.ivHead = null;
    target.tvCompanyName = null;
    target.tvFireState = null;
    target.tvFireTime = null;
    target.tvSalary = null;
    target.tvSalaryPay = null;
    target.tvFireCause = null;
    target.tvFireResult = null;
    target.llFire = null;
    target.tvAccept = null;
    target.tvRefuse = null;

    view2131231198.setOnClickListener(null);
    view2131231198 = null;
    view2131231280.setOnClickListener(null);
    view2131231280 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
  }
}
