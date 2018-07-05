// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine.resume;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CategoryPickActivity_ViewBinding implements Unbinder {
  private CategoryPickActivity target;

  private View view2131231210;

  private View view2131231303;

  @UiThread
  public CategoryPickActivity_ViewBinding(CategoryPickActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CategoryPickActivity_ViewBinding(final CategoryPickActivity target, View source) {
    this.target = target;

    View view;
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tv_cancel, "method 'MyOnclick'");
    view2131231210 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_sure, "method 'MyOnclick'");
    view2131231303 = view;
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
    CategoryPickActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;

    view2131231210.setOnClickListener(null);
    view2131231210 = null;
    view2131231303.setOnClickListener(null);
    view2131231303 = null;
  }
}
