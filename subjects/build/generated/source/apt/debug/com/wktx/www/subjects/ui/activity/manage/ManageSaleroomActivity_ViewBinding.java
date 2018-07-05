// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.manage;

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

public class ManageSaleroomActivity_ViewBinding implements Unbinder {
  private ManageSaleroomActivity target;

  private View view2131230979;

  private View view2131230782;

  private View view2131231164;

  private View view2131230912;

  @UiThread
  public ManageSaleroomActivity_ViewBinding(ManageSaleroomActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ManageSaleroomActivity_ViewBinding(final ManageSaleroomActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.etSaleroomTotal = Utils.findRequiredViewAsType(source, R.id.et_saleroomTotal, "field 'etSaleroomTotal'", EditText.class);
    target.etSaleroomUlt = Utils.findRequiredViewAsType(source, R.id.et_saleroomUlt, "field 'etSaleroomUlt'", EditText.class);
    view = Utils.findRequiredView(source, R.id.linear_photo_add, "field 'llPhotoAdd' and method 'MyOnclick'");
    target.llPhotoAdd = Utils.castView(view, R.id.linear_photo_add, "field 'llPhotoAdd'", LinearLayout.class);
    view2131230979 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    target.rlPhoto = Utils.findRequiredViewAsType(source, R.id.rela_photo, "field 'rlPhoto'", RelativeLayout.class);
    target.ivPhoto = Utils.findRequiredViewAsType(source, R.id.iv_photo, "field 'ivPhoto'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.bt_save, "field 'btSave' and method 'MyOnclick'");
    target.btSave = Utils.castView(view, R.id.bt_save, "field 'btSave'", Button.class);
    view2131230782 = view;
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
    view = Utils.findRequiredView(source, R.id.iv_photo_delete, "method 'MyOnclick'");
    view2131230912 = view;
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
    ManageSaleroomActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.etSaleroomTotal = null;
    target.etSaleroomUlt = null;
    target.llPhotoAdd = null;
    target.rlPhoto = null;
    target.ivPhoto = null;
    target.btSave = null;

    view2131230979.setOnClickListener(null);
    view2131230979 = null;
    view2131230782.setOnClickListener(null);
    view2131230782 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
    view2131230912.setOnClickListener(null);
    view2131230912 = null;
  }
}
