// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.message;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InviteDetailsActivity_ViewBinding implements Unbinder {
  private InviteDetailsActivity target;

  private View view2131231223;

  private View view2131231198;

  private View view2131231280;

  private View view2131231164;

  @UiThread
  public InviteDetailsActivity_ViewBinding(InviteDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public InviteDetailsActivity_ViewBinding(final InviteDetailsActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.ivHead = Utils.findRequiredViewAsType(source, R.id.iv_head, "field 'ivHead'", CircleImageView.class);
    target.tvCompanyName = Utils.findRequiredViewAsType(source, R.id.tv_companyName, "field 'tvCompanyName'", TextView.class);
    target.tvPositionName = Utils.findRequiredViewAsType(source, R.id.tv_positionName, "field 'tvPositionName'", TextView.class);
    target.tvHireTime = Utils.findRequiredViewAsType(source, R.id.tv_hireTime, "field 'tvHireTime'", TextView.class);
    target.tvCompanySite = Utils.findRequiredViewAsType(source, R.id.tv_companySite, "field 'tvCompanySite'", TextView.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvTime'", TextView.class);
    target.tvDemandContent = Utils.findRequiredViewAsType(source, R.id.tv_demandContent, "field 'tvDemandContent'", TextView.class);
    target.tvDemandSalary = Utils.findRequiredViewAsType(source, R.id.tv_demandSalary, "field 'tvDemandSalary'", TextView.class);
    target.tvPushMoney = Utils.findRequiredViewAsType(source, R.id.tv_pushMoney, "field 'tvPushMoney'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_contact, "field 'tvContact' and method 'MyOnclick'");
    target.tvContact = Utils.castView(view, R.id.tv_contact, "field 'tvContact'", TextView.class);
    view2131231223 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
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
    InviteDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.ivHead = null;
    target.tvCompanyName = null;
    target.tvPositionName = null;
    target.tvHireTime = null;
    target.tvCompanySite = null;
    target.tvTime = null;
    target.tvDemandContent = null;
    target.tvDemandSalary = null;
    target.tvPushMoney = null;
    target.tvContact = null;
    target.tvAccept = null;
    target.tvRefuse = null;

    view2131231223.setOnClickListener(null);
    view2131231223 = null;
    view2131231198.setOnClickListener(null);
    view2131231198 = null;
    view2131231280.setOnClickListener(null);
    view2131231280 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
  }
}
