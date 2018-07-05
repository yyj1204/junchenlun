// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.activity.mine.resume;

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
import java.lang.IllegalStateException;
import java.lang.Override;

public class ResumeUploadActivity_ViewBinding implements Unbinder {
  private ResumeUploadActivity target;

  private View view2131230943;

  private View view2131230786;

  private View view2131231164;

  private View view2131230903;

  @UiThread
  public ResumeUploadActivity_ViewBinding(ResumeUploadActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ResumeUploadActivity_ViewBinding(final ResumeUploadActivity target, View source) {
    this.target = target;

    View view;
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tb_TvBarTitle, "field 'tvTitle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.linear_add_resume, "field 'llResume' and method 'MyOnclick'");
    target.llResume = Utils.castView(view, R.id.linear_add_resume, "field 'llResume'", LinearLayout.class);
    view2131230943 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.MyOnclick(p0);
      }
    });
    target.rlResume = Utils.findRequiredViewAsType(source, R.id.rela_resume, "field 'rlResume'", RelativeLayout.class);
    target.ivResume = Utils.findRequiredViewAsType(source, R.id.iv_resume, "field 'ivResume'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.bt_sureUpload, "field 'btSureUpload' and method 'MyOnclick'");
    target.btSureUpload = Utils.castView(view, R.id.bt_sureUpload, "field 'btSureUpload'", Button.class);
    view2131230786 = view;
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
    view = Utils.findRequiredView(source, R.id.iv_delete_resume, "method 'MyOnclick'");
    view2131230903 = view;
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
    ResumeUploadActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.llResume = null;
    target.rlResume = null;
    target.ivResume = null;
    target.btSureUpload = null;

    view2131230943.setOnClickListener(null);
    view2131230943 = null;
    view2131230786.setOnClickListener(null);
    view2131230786 = null;
    view2131231164.setOnClickListener(null);
    view2131231164 = null;
    view2131230903.setOnClickListener(null);
    view2131230903 = null;
  }
}
