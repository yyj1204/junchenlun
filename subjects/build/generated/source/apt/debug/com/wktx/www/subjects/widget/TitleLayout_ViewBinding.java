// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.widget;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TitleLayout_ViewBinding implements Unbinder {
  private TitleLayout target;

  @UiThread
  public TitleLayout_ViewBinding(TitleLayout target) {
    this(target, target);
  }

  @UiThread
  public TitleLayout_ViewBinding(TitleLayout target, View source) {
    this.target = target;

    target.llTitle = Utils.findRequiredViewAsType(source, R.id.linear_titleSearch, "field 'llTitle'", LinearLayout.class);
    target.tvSearch = Utils.findRequiredViewAsType(source, R.id.tv_titleSearch, "field 'tvSearch'", TextView.class);
    target.ivRight = Utils.findRequiredViewAsType(source, R.id.iv_titleRight, "field 'ivRight'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TitleLayout target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.llTitle = null;
    target.tvSearch = null;
    target.ivRight = null;
  }
}
