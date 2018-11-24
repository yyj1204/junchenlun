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

public class MyCollectActivity_ViewBinding implements Unbinder {
  private MyCollectActivity target;

  private View view2131689766;

  @UiThread
  public MyCollectActivity_ViewBinding(MyCollectActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyCollectActivity_ViewBinding(final MyCollectActivity target, View source) {
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
    MyCollectActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.dropDownMenu = null;

    view2131689766.setOnClickListener(null);
    view2131689766 = null;
  }
}
