// Generated code from Butter Knife. Do not modify!
package com.wktx.www.subjects.ui.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.wktx.www.subjects.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DropDownListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private DropDownListAdapter.ViewHolder target;

  @UiThread
  public DropDownListAdapter$ViewHolder_ViewBinding(DropDownListAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.mText = Utils.findRequiredViewAsType(source, R.id.text, "field 'mText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DropDownListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mText = null;
  }
}
