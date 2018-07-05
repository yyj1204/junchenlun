// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PersonInfoActivity_ViewBinding implements Unbinder {
  private PersonInfoActivity target;

  private View view2131230784;

  private View view2131231164;

  private View view2131230963;

  private View view2131230988;

  private View view2131230958;

  private View view2131230946;

  private View view2131230953;

  @UiThread
  public PersonInfoActivity_ViewBinding(PersonInfoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PersonInfoActivity_ViewBinding(final PersonInfoActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    target.ivHead = Utils.findRequiredViewAsType(source, R.id.civ_head, "field 'ivHead'", CircleImageView.class);
    target.ivBoy = Utils.findRequiredViewAsType(source, R.id.iv_sex_boy, "field 'ivBoy'", ImageView.class);
    target.ivGirl = Utils.findRequiredViewAsType(source, R.id.iv_sex_girl, "field 'ivGirl'", ImageView.class);
    target.tvEducation = Utils.findRequiredViewAsType(source, R.id.tv_education, "field 'tvEducation'", TextView.class);
    target.tvBirth = Utils.findRequiredViewAsType(source, R.id.tv_birth, "field 'tvBirth'", TextView.class);
    target.tvCity = Utils.findRequiredViewAsType(source, R.id.tv_city, "field 'tvCity'", TextView.class);
    target.etName = Utils.findRequiredViewAsType(source, R.id.et_name, "field 'etName'", EditText.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.etQQ = Utils.findRequiredViewAsType(source, R.id.et_qq, "field 'etQQ'", EditText.class);
    target.etWechat = Utils.findRequiredViewAsType(source, R.id.et_wechat, "field 'etWechat'", EditText.class);
    target.etCharacter = Utils.findRequiredViewAsType(source, R.id.et_character, "field 'etCharacter'", EditText.class);
    view = Utils.findRequiredView(source, R.id.bt_sureEdit, "field 'btSureEdit' and method 'MyOnclick'");
    target.btSureEdit = Utils.castView(view, R.id.bt_sureEdit, "field 'btSureEdit'", Button.class);
    view2131230784 = view;
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
    view = Utils.findRequiredView(source, R.id.linear_head, "method 'MyOnclick'");
    view2131230963 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_sex, "method 'MyOnclick'");
    view2131230988 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_education, "method 'MyOnclick'");
    view2131230958 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_birth, "method 'MyOnclick'");
    view2131230946 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_city, "method 'MyOnclick'");
    view2131230953 = view;
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
    PersonInfoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.ivHead = null;
    target.ivBoy = null;
    target.ivGirl = null;
    target.tvEducation = null;
    target.tvBirth = null;
    target.tvCity = null;
    target.etName = null;
    target.etPhone = null;
    target.etQQ = null;
    target.etWechat = null;
    target.etCharacter = null;
    target.btSureEdit = null;

    view2131230784.setOnClickListener(null);
    view2131230784 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
    view2131230963.setOnClickListener(null);
    view2131230963 = null;
    view2131230988.setOnClickListener(null);
    view2131230988 = null;
    view2131230958.setOnClickListener(null);
    view2131230958 = null;
    view2131230946.setOnClickListener(null);
    view2131230946 = null;
    view2131230953.setOnClickListener(null);
    view2131230953 = null;
  }
}
