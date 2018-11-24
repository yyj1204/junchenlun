// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.widget.DropDownMenu;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InterviewRecordActivity_ViewBinding implements Unbinder {
  private InterviewRecordActivity target;

  private View view2131689766;

  @UiThread
  public InterviewRecordActivity_ViewBinding(InterviewRecordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public InterviewRecordActivity_ViewBinding(final InterviewRecordActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.dropDownMenu = Utils.findRequiredViewAsType(source, R.id.dropDownMenu, "field 'dropDownMenu'", DropDownMenu.class);
    view = Utils.findRequiredView(source, R.id.tb_IvReturn, "method 'MyOnclick'");
    view2131689766 = view;
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
    InterviewRecordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.dropDownMenu = null;

    view2131689766.setOnClickListener(null);
    view2131689766 = null;
  }
}
