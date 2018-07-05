// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.manage;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.widget.CustomCalendar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ManageAttendanceActivity_ViewBinding implements Unbinder {
  private ManageAttendanceActivity target;

  private View view2131230779;

  private View view2131231164;

  @UiThread
  public ManageAttendanceActivity_ViewBinding(ManageAttendanceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ManageAttendanceActivity_ViewBinding(final ManageAttendanceActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvChuqin = Utils.findRequiredViewAsType(source, R.id.tv_chuqinDay, "field 'tvChuqin'", TextView.class);
    target.tvQueqin = Utils.findRequiredViewAsType(source, R.id.tv_queqinDay, "field 'tvQueqin'", TextView.class);
    target.customCalendar = Utils.findRequiredViewAsType(source, R.id.customCalendar, "field 'customCalendar'", CustomCalendar.class);
    view = Utils.findRequiredView(source, R.id.bt_qiandao, "field 'btQiandao' and method 'MyOnclick'");
    target.btQiandao = Utils.castView(view, R.id.bt_qiandao, "field 'btQiandao'", Button.class);
    view2131230779 = view;
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
    ManageAttendanceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvChuqin = null;
    target.tvQueqin = null;
    target.customCalendar = null;
    target.btQiandao = null;

    view2131230779.setOnClickListener(null);
    view2131230779 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
  }
}
