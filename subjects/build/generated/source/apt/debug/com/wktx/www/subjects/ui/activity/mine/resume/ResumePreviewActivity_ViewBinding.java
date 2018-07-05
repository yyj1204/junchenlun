// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine.resume;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ResumePreviewActivity_ViewBinding implements Unbinder {
  private ResumePreviewActivity target;

  private View view2131231164;

  @UiThread
  public ResumePreviewActivity_ViewBinding(ResumePreviewActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ResumePreviewActivity_ViewBinding(final ResumePreviewActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.recycleView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recycleView'", RecyclerView.class);
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
    ResumePreviewActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.recycleView = null;

    view2131231164.setOnClickListener(null);
    view2131231164 = null;
  }
}
