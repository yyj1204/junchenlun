// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine.resume;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
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

public class WorkExperienceActivity_ViewBinding implements Unbinder {
  private WorkExperienceActivity target;

  private View view2131231204;

  private View view2131231233;

  private View view2131230782;

  private View view2131230773;

  private View view2131231164;

  private View view2131230948;

  @UiThread
  public WorkExperienceActivity_ViewBinding(WorkExperienceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WorkExperienceActivity_ViewBinding(final WorkExperienceActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.etPositionName = Utils.findRequiredViewAsType(source, R.id.et_positionName, "field 'etPositionName'", EditText.class);
    target.etCompanyName = Utils.findRequiredViewAsType(source, R.id.et_companyName, "field 'etCompanyName'", EditText.class);
    target.tvCategory = Utils.findRequiredViewAsType(source, R.id.tv_category, "field 'tvCategory'", TextView.class);
    target.etWorkContent = Utils.findRequiredViewAsType(source, R.id.et_workContent, "field 'etWorkContent'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tv_beginTime, "field 'tvBeginTime' and method 'MyOnclick'");
    target.tvBeginTime = Utils.castView(view, R.id.tv_beginTime, "field 'tvBeginTime'", TextView.class);
    view2131231204 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_endTime, "field 'tvEndTime' and method 'MyOnclick'");
    target.tvEndTime = Utils.castView(view, R.id.tv_endTime, "field 'tvEndTime'", TextView.class);
    view2131231233 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_save, "field 'btSave' and method 'MyOnclick'");
    target.btSave = Utils.castView(view, R.id.bt_save, "field 'btSave'", Button.class);
    view2131230782 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_delete, "field 'btDelete' and method 'MyOnclick'");
    target.btDelete = Utils.castView(view, R.id.bt_delete, "field 'btDelete'", Button.class);
    view2131230773 = view;
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
    view = Utils.findRequiredView(source, R.id.linear_category, "method 'MyOnclick'");
    view2131230948 = view;
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
    WorkExperienceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.etPositionName = null;
    target.etCompanyName = null;
    target.tvCategory = null;
    target.etWorkContent = null;
    target.tvBeginTime = null;
    target.tvEndTime = null;
    target.btSave = null;
    target.btDelete = null;

    view2131231204.setOnClickListener(null);
    view2131231204 = null;
    view2131231233.setOnClickListener(null);
    view2131231233 = null;
    view2131230782.setOnClickListener(null);
    view2131230782 = null;
    view2131230773.setOnClickListener(null);
    view2131230773 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
    view2131230948.setOnClickListener(null);
    view2131230948 = null;
  }
}
