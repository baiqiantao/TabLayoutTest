package tab.bqt.com.tablayouttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Activity_VP1 extends FragmentActivity {
	/**
	 * 此Demo演示：不保存Fragment的引用，当adapter调用getItem时去【重新创建】新的Fragment
	 */
	private List<String> list = new ArrayList<>(Arrays.asList(new String[]{"白", "1", "2", "3", "4", "5", "6", "7", "8",}));
	private PagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.tv_add).setVisibility(View.VISIBLE);
		findViewById(R.id.tv_remove).setVisibility(View.VISIBLE);
		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

		//1、必须使用【FragmentStatePagerAdapter】，而不能使用FragmentPagerAdapter
		adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return MyFragment.newInstance(list.get(position));//每次都是创建新的Fragment
			}

			@Override
			public int getCount() {
				return list.size();
			}

			//2、必须重写getItemPosition，并且返回【PagerAdapter.POSITION_NONE】
			@Override
			public int getItemPosition(Object object) {
				return PagerAdapter.POSITION_NONE;
			}
		};
		viewPager.setAdapter(adapter);
	}

	public void add(View view) {
		list.add(0, new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault()).format(new Date()));
		adapter.notifyDataSetChanged();
	}

	public void remove(View view) {
		if (list.size() > 0) list.remove(0);
		adapter.notifyDataSetChanged();
	}
}