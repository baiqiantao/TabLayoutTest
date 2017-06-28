package tab.bqt.com.tablayouttest;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

public class TabUtils {

	public static void setIndicatorPadding(TabLayout tabs, int leftDip, int rightDip) {
		try {
			Class<?> tabLayout = tabs.getClass();
			Field tabStrip = tabLayout.getDeclaredField("mTabStrip");
			tabStrip.setAccessible(true);

			int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
			int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

			LinearLayout llTab = (LinearLayout) tabStrip.get(tabs);
			for (int i = 0; i < llTab.getChildCount(); i++) {
				View child = llTab.getChildAt(i);
				child.setPadding(0, 0, 0, 0);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
				params.leftMargin = left;
				params.rightMargin = right;
				child.setLayoutParams(params);
				child.invalidate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
