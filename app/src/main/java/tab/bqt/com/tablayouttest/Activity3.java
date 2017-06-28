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
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity3 extends FragmentActivity implements View.OnClickListener {
	private List<String> list = new ArrayList<>(Arrays.asList(new String[]{"包青天", "白", "baiqiantao(注意大小写)", "1", "12", "123", "1234",}));

	private TabLayout tabLayout;
	private ViewPager viewPager;
	private int tag;
	private static final int OFFSET = 10086;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tag = getIntent().getIntExtra("tag", 0);
		initView();
		setCustomTabView();
		addOnTabSelectedListener();

		tabLayout.post(new Runnable() {
			@Override
			public void run() {
				setIndicator(tabLayout, 1, 1);//必须在setupWithViewPager之后（数据确定后）才可以操作
			}
		});
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

		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
		tabLayout.setupWithViewPager(viewPager);
	}

	private void setCustomTabView() {
		tabLayout.setSelectedTabIndicatorHeight(0);

		for (int i = 0; i < list.size(); i++) {//必须在setupWithViewPager之后（数据确定后）才可以操作
			View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);

			//注意：给view添加点击事件后，点击Tab后的点击事件会被这里的view消耗掉，所以Tab自身的点击事件不会响应
			//也即点击Tab后将不会触发OnTabSelectedListener，也不会自动切换VP，这些都需要我们自己手动处理
			if (i <= 3) {
				view.setTag(i + OFFSET);
				view.setOnClickListener(this);
			}

			TextView tv_tab_name = (TextView) view.findViewById(R.id.tv_tab_name);
			View line_indicator = view.findViewById(R.id.line_indicator);
			tv_tab_name.setText(list.get(i));
			if (i == 0) {
				view.setSelected(true);
				line_indicator.setSelected(true);
			} else {
				view.setSelected(false);
				line_indicator.setSelected(false);
			}

			TabLayout.Tab tab = tabLayout.getTabAt(i);//获得每一个tab
			if (tab != null) tab.setCustomView(view);//给每一个tab设置view
		}
	}

	private void addOnTabSelectedListener() {
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				if (tab.getCustomView() != null) {
					tab.getCustomView().setSelected(true);
					tab.getCustomView().findViewById(R.id.line_indicator).setSelected(true);
					viewPager.setCurrentItem(tab.getPosition());
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

	@Override
	public void onClick(View v) {
		if (tag != 7) {
			int currentTabPosition = (int) v.getTag() - OFFSET;
			if (currentTabPosition >= 0 && currentTabPosition < tabLayout.getTabCount()) {
				reSetTabLayout(currentTabPosition);
				//setCurrentItem时，当点击最边缘那个Tab后，TabLayout也会像之前那样，将被点击的那个Tab滚动到屏幕中间
				viewPager.setCurrentItem(currentTabPosition);
			}
		}
		Toast.makeText(this, "我们需要自己手动处理点击Tab后所有变化", Toast.LENGTH_SHORT).show();
	}

	private void reSetTabLayout(int currentTabPosition) {
		for (int j = 0, count = tabLayout.getTabCount(); j < count; j++) {
			TabLayout.Tab tab = tabLayout.getTabAt(j);
			if (tab != null) {
				View view = tab.getCustomView();
				if (view != null) {
					TextView tv_tab_name = (TextView) view.findViewById(R.id.tv_tab_name);
					View line_indicator = view.findViewById(R.id.line_indicator);
					tv_tab_name.setText(list.get(j));
					if (j == currentTabPosition) {
						view.setSelected(true);
						line_indicator.setSelected(true);
					} else {
						view.setSelected(false);
						line_indicator.setSelected(false);
					}
				}
			}
		}
	}
}