// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import com.wktx.www.subjects.widget.MyScrollView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MineFragment_ViewBinding implements Unbinder {
  private MineFragment target;

  private View view2131689799;

  private View view2131689958;

  private View view2131689959;

  private View view2131689960;

  private View view2131689974;

  private View view2131689944;

  private View view2131689912;

  private View view2131689964;

  private View view2131689853;

  private View view2131689962;

  private View view2131689966;

  private View view2131689967;

  private View view2131689968;

  private View view2131689969;

  private View view2131689970;

  private View view2131689971;

  private View view2131689972;

  private View view2131689973;

  @UiThread
  public MineFragment_ViewBinding(final MineFragment target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.scrollview = Utils.findRequiredViewAsType(source, R.id.scrollview, "field 'scrollview'", MyScrollView.class);
    target.relaTop = Utils.findRequiredViewAsType(source, R.id.rela_top, "field 'relaTop'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.civ_head, "field 'ivHead' and method 'MyOnclick'");
    target.ivHead = Utils.castView(view, R.id.civ_head, "field 'ivHead'", CircleImageView.class);
    view2131689799 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_userName, "field 'tvUserName' and method 'MyOnclick'");
    target.tvUserName = Utils.castView(view, R.id.tv_userName, "field 'tvUserName'", TextView.class);
    view2131689958 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_certification, "field 'llCertification' and method 'MyOnclick'");
    target.llCertification = Utils.castView(view, R.id.linear_certification, "field 'llCertification'", LinearLayout.class);
    view2131689959 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_certification1, "field 'llCertification1' and method 'MyOnclick'");
    target.llCertification1 = Utils.castView(view, R.id.linear_certification1, "field 'llCertification1'", LinearLayout.class);
    view2131689960 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    target.tvCertificationState = Utils.findRequiredViewAsType(source, R.id.tv_certificationState, "field 'tvCertificationState'", TextView.class);
    target.tvBalance = Utils.findRequiredViewAsType(source, R.id.tv_balance, "field 'tvBalance'", TextView.class);
    target.tvFrozenMoney = Utils.findRequiredViewAsType(source, R.id.tv_frozenMoney, "field 'tvFrozenMoney'", TextView.class);
    target.llAccount = Utils.findRequiredViewAsType(source, R.id.linear_account, "field 'llAccount'", LinearLayout.class);
    target.llLogin = Utils.findRequiredViewAsType(source, R.id.linear_login, "field 'llLogin'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.bt_logout, "field 'btLogout' and method 'MyOnclick'");
    target.btLogout = Utils.castView(view, R.id.bt_logout, "field 'btLogout'", Button.class);
    view2131689974 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_message, "method 'MyOnclick'");
    view2131689944 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_balance, "method 'MyOnclick'");
    view2131689912 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_frozenMoney, "method 'MyOnclick'");
    view2131689964 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_login, "method 'MyOnclick'");
    view2131689853 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_register, "method 'MyOnclick'");
    view2131689962 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_myResume, "method 'MyOnclick'");
    view2131689966 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_myCollect, "method 'MyOnclick'");
    view2131689967 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_tradeRecord, "method 'MyOnclick'");
    view2131689968 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_interviewRecord, "method 'MyOnclick'");
    view2131689969 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_employRecord, "method 'MyOnclick'");
    view2131689970 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_aboutApp, "method 'MyOnclick'");
    view2131689971 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_accountSecurity, "method 'MyOnclick'");
    view2131689972 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_contactService, "method 'MyOnclick'");
    view2131689973 = view;
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
    target.tvFrozenMoney = null;
    target.llAccount = null;
    target.llLogin = null;
    target.btLogout = null;

    view2131689799.setOnClickListener(null);
    view2131689799 = null;
    view2131689958.setOnClickListener(null);
    view2131689958 = null;
    view2131689959.setOnClickListener(null);
    view2131689959 = null;
    view2131689960.setOnClickListener(null);
    view2131689960 = null;
    view2131689974.setOnClickListener(null);
    view2131689974 = null;
    view2131689944.setOnClickListener(null);
    view2131689944 = null;
    view2131689912.setOnClickListener(null);
    view2131689912 = null;
    view2131689964.setOnClickListener(null);
    view2131689964 = null;
    view2131689853.setOnClickListener(null);
    view2131689853 = null;
    view2131689962.setOnClickListener(null);
    view2131689962 = null;
    view2131689966.setOnClickListener(null);
    view2131689966 = null;
    view2131689967.setOnClickListener(null);
    view2131689967 = null;
    view2131689968.setOnClickListener(null);
    view2131689968 = null;
    view2131689969.setOnClickListener(null);
    view2131689969 = null;
    view2131689970.setOnClickListener(null);
    view2131689970 = null;
    view2131689971.setOnClickListener(null);
    view2131689971 = null;
    view2131689972.setOnClickListener(null);
    view2131689972 = null;
    view2131689973.setOnClickListener(null);
    view2131689973 = null;
  }
}
