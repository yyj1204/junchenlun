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

  private View view2131689799;

  private View view2131689812;

  private View view2131689766;

  private View view2131689798;

  private View view2131689801;

  private View view2131689804;

  private View view2131689806;

  private View view2131689808;

  @UiThread
  public PersonInfoActivity_ViewBinding(PersonInfoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PersonInfoActivity_ViewBinding(final PersonInfoActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.civ_head, "field 'ivHead' and method 'MyOnclick'");
    target.ivHead = Utils.castView(view, R.id.civ_head, "field 'ivHead'", CircleImageView.class);
    view2131689799 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
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
    view2131689812 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tb_IvReturn, "method 'MyOnclick'");
    view2131689766 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_head, "method 'MyOnclick'");
    view2131689798 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_sex, "method 'MyOnclick'");
    view2131689801 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_education, "method 'MyOnclick'");
    view2131689804 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_birth, "method 'MyOnclick'");
    view2131689806 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.linear_city, "method 'MyOnclick'");
    view2131689808 = view;
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

    view2131689799.setOnClickListener(null);
    view2131689799 = null;
    view2131689812.setOnClickListener(null);
    view2131689812 = null;
    view2131689766.setOnClickListener(null);
    view2131689766 = null;
    view2131689798.setOnClickListener(null);
    view2131689798 = null;
    view2131689801.setOnClickListener(null);
    view2131689801 = null;
    view2131689804.setOnClickListener(null);
    view2131689804 = null;
    view2131689806.setOnClickListener(null);
    view2131689806 = null;
    view2131689808.setOnClickListener(null);
    view2131689808 = null;
  }
}
