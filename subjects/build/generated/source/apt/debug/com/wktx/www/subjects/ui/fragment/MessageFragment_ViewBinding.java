// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MessageFragment_ViewBinding implements Unbinder {
  private MessageFragment target;

  private View view2131689948;

  private View view2131689949;

  private View view2131689853;

  private View view2131689947;

  @UiThread
  public MessageFragment_ViewBinding(final MessageFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.tb_TvBarTitle1, "field 'tv_title1' and method 'MyOnclick'");
    target.tv_title1 = Utils.castView(view, R.id.tb_TvBarTitle1, "field 'tv_title1'", TextView.class);
    view2131689948 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tb_TvBarTitle2, "field 'tv_title2' and method 'MyOnclick'");
    target.tv_title2 = Utils.castView(view, R.id.tb_TvBarTitle2, "field 'tv_title2'", TextView.class);
    view2131689949 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    target.llNothing = Utils.findRequiredViewAsType(source, R.id.linear_nothing, "field 'llNothing'", LinearLayout.class);
    target.linear_tab1 = Utils.findRequiredViewAsType(source, R.id.linear_tab1, "field 'linear_tab1'", LinearLayout.class);
    target.smartTab1 = Utils.findRequiredViewAsType(source, R.id.layout_smartTab1, "field 'smartTab1'", SmartTabLayout.class);
    target.viewPager1 = Utils.findRequiredViewAsType(source, R.id.viewpager1, "field 'viewPager1'", ViewPager.class);
    target.linear_tab2 = Utils.findRequiredViewAsType(source, R.id.linear_tab2, "field 'linear_tab2'", LinearLayout.class);
    target.smartTab2 = Utils.findRequiredViewAsType(source, R.id.layout_smartTab2, "field 'smartTab2'", SmartTabLayout.class);
    target.viewPager2 = Utils.findRequiredViewAsType(source, R.id.viewpager2, "field 'viewPager2'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.tv_login, "method 'MyOnclick'");
    view2131689853 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_selectWork, "method 'MyOnclick'");
    view2131689947 = view;
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
    MessageFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_title1 = null;
    target.tv_title2 = null;
    target.llNothing = null;
    target.linear_tab1 = null;
    target.smartTab1 = null;
    target.viewPager1 = null;
    target.linear_tab2 = null;
    target.smartTab2 = null;
    target.viewPager2 = null;

    view2131689948.setOnClickListener(null);
    view2131689948 = null;
    view2131689949.setOnClickListener(null);
    view2131689949 = null;
    view2131689853.setOnClickListener(null);
    view2131689853 = null;
    view2131689947.setOnClickListener(null);
    view2131689947 = null;
  }
}
