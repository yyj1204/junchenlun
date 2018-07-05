// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SearchActivity_ViewBinding implements Unbinder {
  private SearchActivity target;

  private View view2131231225;

  private View view2131231164;

  private View view2131231094;

  private View view2131231090;

  private View view2131231290;

  @UiThread
  public SearchActivity_ViewBinding(SearchActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SearchActivity_ViewBinding(final SearchActivity target, View source) {
    this.target = target;

    View view;
    target.etSearch = Utils.findRequiredViewAsType(source, R.id.et_search, "field 'etSearch'", EditText.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.tvPosition = Utils.findRequiredViewAsType(source, R.id.tv_position, "field 'tvPosition'", TextView.class);
    target.tvPositionLine = Utils.findRequiredViewAsType(source, R.id.tv_positionLine, "field 'tvPositionLine'", TextView.class);
    target.tvCompany = Utils.findRequiredViewAsType(source, R.id.tv_company, "field 'tvCompany'", TextView.class);
    target.tvCompanyLine = Utils.findRequiredViewAsType(source, R.id.tv_companyLine, "field 'tvCompanyLine'", TextView.class);
    target.llHistory = Utils.findRequiredViewAsType(source, R.id.linear_history, "field 'llHistory'", LinearLayout.class);
    target.tagflowHistory = Utils.findRequiredViewAsType(source, R.id.tagflow_history, "field 'tagflowHistory'", TagFlowLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_delete, "field 'tvDelete' and method 'MyOnclick'");
    target.tvDelete = Utils.castView(view, R.id.tv_delete, "field 'tvDelete'", TextView.class);
    view2131231225 = view;
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
    view = Utils.findRequiredView(source, R.id.rela_position, "method 'MyOnclick'");
    view2131231094 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rela_company, "method 'MyOnclick'");
    view2131231090 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_search, "method 'MyOnclick'");
    view2131231290 = view;
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
    SearchActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etSearch = null;
    target.swipeRefreshLayout = null;
    target.recyclerView = null;
    target.tvPosition = null;
    target.tvPositionLine = null;
    target.tvCompany = null;
    target.tvCompanyLine = null;
    target.llHistory = null;
    target.tagflowHistory = null;
    target.tvDelete = null;

    view2131231225.setOnClickListener(null);
    view2131231225 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
    view2131231094.setOnClickListener(null);
    view2131231094 = null;
    view2131231090.setOnClickListener(null);
    view2131231090 = null;
    view2131231290.setOnClickListener(null);
    view2131231290 = null;
  }
}
