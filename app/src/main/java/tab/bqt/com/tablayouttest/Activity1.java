package tab.bqt.com.tablayouttest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity1 extends FragmentActivity {
	private List<String> list = new ArrayList<>(Arrays.asList(new String[]{"包青天", "白", "baiqiantao(注意大小写)", "1", "12", "123", "1234",}));
	private int tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tag = getIntent().getIntExtra("tag", 0);

		//3、XML中设置Tab样式
		TabLayout tabLayout;
		if (tag != 3) {
			tabLayout = (TabLayout) findViewById(R.id.tablayout1);
			findViewById(R.id.tablayout1).setVisibility(View.VISIBLE);
			findViewById(R.id.tablayout2).setVisibility(View.GONE);
		} else {
			tabLayout = (TabLayout) findViewById(R.id.tablayout2);
			findViewById(R.id.tablayout1).setVisibility(View.GONE);
			findViewById(R.id.tablayout2).setVisibility(View.VISIBLE);
		}

		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
		viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
				if (tag == 0) return super.getPageTitle(position);
					//0、不重写ViewPager的getPageTitle方法，会导致TabLayout没有标题
				else return list.get(position);//因为返回值是CharSequence，所以可以返回一个内容丰富的SpannableString
			}
		});
		tabLayout.setupWithViewPager(viewPager);

		//1、默认MODE_FIXED模式，会将每个tab都显示出来，tab的宽度一样；MODE_SCROLLABLE会根据tab中的内容自动调整tab宽度
		if (tag != 1) tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

		//2、代码中设置Tab样式
		if (tag == 2) {
			tabLayout.setTabGravity(Gravity.TOP);
			tabLayout.setTabTextColors(Color.RED, Color.GREEN);
			tabLayout.setSelectedTabIndicatorColor(Color.BLUE);
			tabLayout.setSelectedTabIndicatorHeight(15);
			tabLayout.setBackgroundColor(Color.YELLOW);
		}

	}
}