package tab.bqt.com.tablayouttest;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity2 extends FragmentActivity {
	private List<String> list = new ArrayList<>(Arrays.asList(new String[]{"包青天", "白", "baiqiantao(注意大小写)", "1", "12", "123", "1234",}));
	private TabLayout tabLayout;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		int tag = getIntent().getIntExtra("tag", 0);

		initView();

		if (tag != 5) {//是否自定义Tab，是否添加监听
			setCustomTabView();
			addOnTabSelectedListener();
		}
		if (tag != 6) {//是否自定义Tab间距
			tabLayout.post(new Runnable() {
				@Override
				public void run() {
					setIndicator(tabLayout, 1, 1);//必须在setupWithViewPager之后（数据确定后）才可以操作
				}
			});
		}
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		tabLayout = (TabLayout) findViewById(R.id.tablayout1);
		findViewById(R.id.tablayout1).setVisibility(View.VISIBLE);

		PagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return MyFragment.newInstance(list.get(position));
			}

			@Override
			public int getCount() {
				return list.size();
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return list.get(position);
			}
		};
		viewPager.setAdapter(adapter);

		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//可滚动模式，另一个是固定宽度模式
		tabLayout.setupWithViewPager(viewPager);
	}

	/**
	 * 使用自定义的Tab，设置后要自己控制每个Tab的文字以及【默认】选中Tab的样式（比如默认第一个Tab要显示indicator等）
	 */
	private void setCustomTabView() {
		tabLayout.setSelectedTabIndicatorHeight(0);

		for (int i = 0; i < list.size(); i++) {//必须在setupWithViewPager之后（即被绑定VP的数据确定后）才可以操作
			View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);

			TextView tv_tab_name = (TextView) view.findViewById(R.id.tv_tab_name);
			View line_indicator = view.findViewById(R.id.line_indicator);
			tv_tab_name.setText(list.get(i));//控制每个Tab的文字
			if (i == 0) {//控制默认选中Tab的样式
				view.setSelected(true);//背景样式
				line_indicator.setSelected(true);//指示器样式
			} else {
				view.setSelected(false);
				line_indicator.setSelected(false);
			}

			TabLayout.Tab tab = tabLayout.getTabAt(i);//获得每一个tab
			if (tab != null) tab.setCustomView(view);//给每一个tab设置view
		}
	}

	/**
	 * 添加监听，当使用自定义的Tab时，要自己控制选中Tab【改变】时Tab的样式，以及ViewPager的当前Item
	 */
	private void addOnTabSelectedListener() {
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				if (tab.getCustomView() != null) {
					tab.getCustomView().setSelected(true);//要自己控制选中Tab改变时Tab的样式
					tab.getCustomView().findViewById(R.id.line_indicator).setSelected(true);
					viewPager.setCurrentItem(tab.getPosition());//要自己控制ViewPager的当前Item
				}
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				if (tab.getCustomView() != null) {
					tab.getCustomView().setSelected(false);
					tab.getCustomView().findViewById(R.id.line_indicator).setSelected(false);
				}
			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {
			}
		});
	}

	/**
	 * 自定义Indicator左右间距。适用于自定义Tab的情况下，在使用默认Tab时，通过tabPadding设置既可以了
	 * 注意：除了tabPadding外，tabMinWidth的值也会影响Tab的宽度，特别是1-2两字符时，会非常明显
	 */
	public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
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