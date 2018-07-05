// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.widget.MyScrollView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MineFragment_ViewBinding implements Unbinder {
  private MineFragment target;

  private View view2131230801;

  private View view2131231312;

  private View view2131230950;

  private View view2131230951;

  private View view2131230777;

  private View view2131230910;

  private View view2131230945;

  private View view2131231254;

  private View view2131231282;

  private View view2131230975;

  private View view2131230974;

  private View view2131230995;

  private View view2131230967;

  private View view2131230938;

  private View view2131230940;

  private View view2131230956;

  @UiThread
  public MineFragment_ViewBinding(final MineFragment target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.scrollview = Utils.findRequiredViewAsType(source, R.id.scrollview, "field 'scrollview'", MyScrollView.class);
    target.relaTop = Utils.findRequiredViewAsType(source, R.id.rela_top, "field 'relaTop'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.civ_head, "field 'ivHead' and method 'MyOnclick'");
    target.ivHead = Utils.castView(view, R.id.civ_head, "field 'ivHead'", ImageView.class);
    view2131230801 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_userName, "field 'tvUserName' and method 'MyOnclick'");
    target.tvUserName = Utils.castView(view, R.id.tv_userName, "field 'tvUserName'", TextView.class);
    view2131231312 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_certification, "field 'llCertification' and method 'MyOnclick'");
    target.llCertification = Utils.castView(view, R.id.linear_certification, "field 'llCertification'", LinearLayout.class);
    view2131230950 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_certification1, "field 'llCertification1' and method 'MyOnclick'");
    target.llCertification1 = Utils.castView(view, R.id.linear_certification1, "field 'llCertification1'", LinearLayout.class);
    view2131230951 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    target.tvCertificationState = Utils.findRequiredViewAsType(source, R.id.tv_certificationState, "field 'tvCertificationState'", TextView.class);
    target.tvBalance = Utils.findRequiredViewAsType(source, R.id.tv_balance, "field 'tvBalance'", TextView.class);
    target.llAccount = Utils.findRequiredViewAsType(source, R.id.linear_account, "field 'llAccount'", LinearLayout.class);
    target.llLogin = Utils.findRequiredViewAsType(source, R.id.linear_login, "field 'llLogin'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.bt_logout, "field 'btLogout' and method 'MyOnclick'");
    target.btLogout = Utils.castView(view, R.id.bt_logout, "field 'btLogout'", Button.class);
    view2131230777 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_message, "method 'MyOnclick'");
    view2131230910 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_balance, "method 'MyOnclick'");
    view2131230945 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_login, "method 'MyOnclick'");
    view2131231254 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_register, "method 'MyOnclick'");
    view2131231282 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_myResume, "method 'MyOnclick'");
    view2131230975 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_myCollect, "method 'MyOnclick'");
    view2131230974 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_tradeRecord, "method 'MyOnclick'");
    view2131230995 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_interviewRecord, "method 'MyOnclick'");
    view2131230967 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_aboutApp, "method 'MyOnclick'");
    view2131230938 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_accountSecurity, "method 'MyOnclick'");
    view2131230940 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_contactService, "method 'MyOnclick'");
    view2131230956 = view;
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
    MineFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.scrollview = null;
    target.relaTop = null;
    target.ivHead = null;
    target.tvUserName = null;
    target.llCertification = null;
    target.llCertification1 = null;
    target.tvCertificationState = null;
    target.tvBalance = null;
    target.llAccount = null;
    target.llLogin = null;
    target.btLogout = null;

    view2131230801.setOnClickListener(null);
    view2131230801 = null;
    view2131231312.setOnClickListener(null);
    view2131231312 = null;
    view2131230950.setOnClickListener(null);
    view2131230950 = null;
    view2131230951.setOnClickListener(null);
    view2131230951 = null;
    view2131230777.setOnClickListener(null);
    view2131230777 = null;
    view2131230910.setOnClickListener(null);
    view2131230910 = null;
    view2131230945.setOnClickListener(null);
    view2131230945 = null;
    view2131231254.setOnClickListener(null);
    view2131231254 = null;
    view2131231282.setOnClickListener(null);
    view2131231282 = null;
    view2131230975.setOnClickListener(null);
    view2131230975 = null;
    view2131230974.setOnClickListener(null);
    view2131230974 = null;
    view2131230995.setOnClickListener(null);
    view2131230995 = null;
    view2131230967.setOnClickListener(null);
    view2131230967 = null;
    view2131230938.setOnClickListener(null);
    view2131230938 = null;
    view2131230940.setOnClickListener(null);
    view2131230940 = null;
    view2131230956.setOnClickListener(null);
    view2131230956 = null;
  }
}
