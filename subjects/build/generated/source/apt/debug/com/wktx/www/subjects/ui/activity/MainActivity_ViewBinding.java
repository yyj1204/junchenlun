// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import com.yinglan.alphatabs.AlphaTabsIndicator;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(MainActivity target, View source) {
    this.target = target;

    target.mViewPger = Utils.findRequiredViewAsType(source, R.id.mViewPager, "field 'mViewPger'", ViewPager.class);
    target.alphaTabsIndicator = Utils.findRequiredViewAsType(source, R.id.alphaIndicator, "field 'alphaTabsIndicator'", AlphaTabsIndicator.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPger = null;
    target.alphaTabsIndicator = null;
  }
}
