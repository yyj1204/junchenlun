// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine.certification;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CertificationDetailsActivity_ViewBinding implements Unbinder {
  private CertificationDetailsActivity target;

  private View view2131230942;

  private View view2131230902;

  private View view2131230944;

  private View view2131230904;

  private View view2131230783;

  private View view2131231164;

  @UiThread
  public CertificationDetailsActivity_ViewBinding(CertificationDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CertificationDetailsActivity_ViewBinding(final CertificationDetailsActivity target,
      View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.llCause = Utils.findRequiredViewAsType(source, R.id.linear_cause, "field 'llCause'", LinearLayout.class);
    target.tvCause = Utils.findRequiredViewAsType(source, R.id.tv_cause, "field 'tvCause'", TextView.class);
    view = Utils.findRequiredView(source, R.id.linear_add_front, "field 'llFront' and method 'MyOnclick'");
    target.llFront = Utils.castView(view, R.id.linear_add_front, "field 'llFront'", LinearLayout.class);
    view2131230942 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    target.rlFront = Utils.findRequiredViewAsType(source, R.id.rela_photo_front, "field 'rlFront'", RelativeLayout.class);
    target.ivPhotoFront = Utils.findRequiredViewAsType(source, R.id.iv_photo_front, "field 'ivPhotoFront'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.iv_delete_front, "field 'ivDeleteFront' and method 'MyOnclick'");
    target.ivDeleteFront = Utils.castView(view, R.id.iv_delete_front, "field 'ivDeleteFront'", ImageView.class);
    view2131230902 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_add_reverse, "field 'llReverse' and method 'MyOnclick'");
    target.llReverse = Utils.castView(view, R.id.linear_add_reverse, "field 'llReverse'", LinearLayout.class);
    view2131230944 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    target.rlReverse = Utils.findRequiredViewAsType(source, R.id.rela_photo_reverse, "field 'rlReverse'", RelativeLayout.class);
    target.ivPhotoReverse = Utils.findRequiredViewAsType(source, R.id.iv_photo_reverse, "field 'ivPhotoReverse'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.iv_delete_reverse, "field 'ivDeleteReverse' and method 'MyOnclick'");
    target.ivDeleteReverse = Utils.castView(view, R.id.iv_delete_reverse, "field 'ivDeleteReverse'", ImageView.class);
    view2131230904 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    target.etName = Utils.findRequiredViewAsType(source, R.id.et_name, "field 'etName'", EditText.class);
    target.etIdNumber = Utils.findRequiredViewAsType(source, R.id.et_idNumber, "field 'etIdNumber'", EditText.class);
    view = Utils.findRequiredView(source, R.id.bt_submitCertificate, "field 'btSubmitCertificate' and method 'MyOnclick'");
    target.btSubmitCertificate = Utils.castView(view, R.id.bt_submitCertificate, "field 'btSubmitCertificate'", Button.class);
    view2131230783 = view;
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
  }

  @Override
  @CallSuper
  public void unbind() {
    CertificationDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.llCause = null;
    target.tvCause = null;
    target.llFront = null;
    target.rlFront = null;
    target.ivPhotoFront = null;
    target.ivDeleteFront = null;
    target.llReverse = null;
    target.rlReverse = null;
    target.ivPhotoReverse = null;
    target.ivDeleteReverse = null;
    target.etName = null;
    target.etIdNumber = null;
    target.btSubmitCertificate = null;

    view2131230942.setOnClickListener(null);
    view2131230942 = null;
    view2131230902.setOnClickListener(null);
    view2131230902 = null;
    view2131230944.setOnClickListener(null);
    view2131230944 = null;
    view2131230904.setOnClickListener(null);
    view2131230904 = null;
    view2131230783.setOnClickListener(null);
    view2131230783 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
  }
}
