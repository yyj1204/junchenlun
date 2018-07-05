// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine.resume;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ResumePositionActivity_ViewBinding implements Unbinder {
  private ResumePositionActivity target;

  private View view2131230991;

  private View view2131230782;

  private View view2131230773;

  private View view2131231164;

  private View view2131230981;

  private View view2131230948;

  private View view2131230980;

  private View view2131230960;

  @UiThread
  public ResumePositionActivity_ViewBinding(ResumePositionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ResumePositionActivity_ViewBinding(final ResumePositionActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvPosition = Utils.findRequiredViewAsType(source, R.id.tv_position, "field 'tvPosition'", TextView.class);
    target.tvCategory = Utils.findRequiredViewAsType(source, R.id.tv_category, "field 'tvCategory'", TextView.class);
    target.tvPlatform = Utils.findRequiredViewAsType(source, R.id.tv_platform, "field 'tvPlatform'", TextView.class);
    target.tvExperience = Utils.findRequiredViewAsType(source, R.id.tv_experience, "field 'tvExperience'", TextView.class);
    view = Utils.findRequiredView(source, R.id.linear_style, "field 'llStyle' and method 'MyOnclick'");
    target.llStyle = Utils.castView(view, R.id.linear_style, "field 'llStyle'", LinearLayout.class);
    view2131230991 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    target.tvStyle = Utils.findRequiredViewAsType(source, R.id.tv_style, "field 'tvStyle'", TextView.class);
    target.llSpeed = Utils.findRequiredViewAsType(source, R.id.linear_speed, "field 'llSpeed'", LinearLayout.class);
    target.etSpeed = Utils.findRequiredViewAsType(source, R.id.et_speed, "field 'etSpeed'", EditText.class);
    target.etSalary = Utils.findRequiredViewAsType(source, R.id.et_salary, "field 'etSalary'", EditText.class);
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
    view = Utils.findRequiredView(source, R.id.linear_position, "method 'MyOnclick'");
    view2131230981 = view;
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
    view = Utils.findRequiredView(source, R.id.linear_platform, "method 'MyOnclick'");
    view2131230980 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_experience, "method 'MyOnclick'");
    view2131230960 = view;
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
    ResumePositionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvPosition = null;
    target.tvCategory = null;
    target.tvPlatform = null;
    target.tvExperience = null;
    target.llStyle = null;
    target.tvStyle = null;
    target.llSpeed = null;
    target.etSpeed = null;
    target.etSalary = null;
    target.btSave = null;
    target.btDelete = null;

    view2131230991.setOnClickListener(null);
    view2131230991 = null;
    view2131230782.setOnClickListener(null);
    view2131230782 = null;
    view2131230773.setOnClickListener(null);
    view2131230773 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
    view2131230981.setOnClickListener(null);
    view2131230981 = null;
    view2131230948.setOnClickListener(null);
    view2131230948 = null;
    view2131230980.setOnClickListener(null);
    view2131230980 = null;
    view2131230960.setOnClickListener(null);
    view2131230960 = null;
  }
}
