// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine.works;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
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

public class WorksDetailsActivity_ViewBinding implements Unbinder {
  private WorksDetailsActivity target;

  private View view2131230782;

  private View view2131230773;

  private View view2131231164;

  private View view2131230957;

  private View view2131230948;

  private View view2131230965;

  @UiThread
  public WorksDetailsActivity_ViewBinding(WorksDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WorksDetailsActivity_ViewBinding(final WorksDetailsActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.tvDesignPattern = Utils.findRequiredViewAsType(source, R.id.tv_designPattern, "field 'tvDesignPattern'", TextView.class);
    target.tvCategory = Utils.findRequiredViewAsType(source, R.id.tv_category, "field 'tvCategory'", TextView.class);
    target.etWorksTitle = Utils.findRequiredViewAsType(source, R.id.et_worksTitle, "field 'etWorksTitle'", EditText.class);
    target.etWorksIntro = Utils.findRequiredViewAsType(source, R.id.et_worksIntro, "field 'etWorksIntro'", EditText.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
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
    view = Utils.findRequiredView(source, R.id.linear_designPattern, "method 'MyOnclick'");
    view2131230957 = view;
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
    view = Utils.findRequiredView(source, R.id.linear_imgAdd, "method 'MyOnclick'");
    view2131230965 = view;
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
    WorksDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvDesignPattern = null;
    target.tvCategory = null;
    target.etWorksTitle = null;
    target.etWorksIntro = null;
    target.recyclerView = null;
    target.btSave = null;
    target.btDelete = null;

    view2131230782.setOnClickListener(null);
    view2131230782 = null;
    view2131230773.setOnClickListener(null);
    view2131230773 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
    view2131230957.setOnClickListener(null);
    view2131230957 = null;
    view2131230948.setOnClickListener(null);
    view2131230948 = null;
    view2131230965.setOnClickListener(null);
    view2131230965 = null;
  }
}
