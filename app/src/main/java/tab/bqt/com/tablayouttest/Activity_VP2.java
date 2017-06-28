package tab.bqt.com.tablayouttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Activity_VP2 extends FragmentActivity {
	/**
	 * 此Demo演示：保存所有Fragment的引用，当adapter调用getItem时去【获取】已经创建的Fragment
	 */
	private List<Fragment> fragments;
	private PagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.tv_add).setVisibility(View.VISIBLE);
		findViewById(R.id.tv_remove).setVisibility(View.VISIBLE);
		ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

		String[] array = new String[]{"baiqiantao", "白", "包青天", "白乾涛", "1", "2", "3", "4", "5", "6", "7", "8",};
		fragments = new ArrayList<>();
		for (String string : array) {
			fragments.add(MyFragment.newInstance(string));
		}

		//1、必须使用【FragmentStatePagerAdapter】，而不能使用FragmentPagerAdapter
		adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

			@Override
			public Fragment getItem(int position) {
				return fragments.get(position);//这里的Fragment都是已经创建好的，而不是getItem时重新创建的
			}

			@Override
			public int getCount() {
				return fragments.size();
			}

			//2、必须重写getItemPosition，并且返回【PagerAdapter.POSITION_NONE】
			@Override
			public int getItemPosition(Object object) {
				return POSITION_NONE;
			}
		};
		viewPager.setAdapter(adapter);
	}

	public void add(View view) {
		String data = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault()).format(new Date());
		fragments.add(0, MyFragment.newInstance(data));
		setFragments(fragments);
	}

	public void remove(View view) {
		if (fragments.size() > 0) fragments.remove(0);
		setFragments(fragments);
	}

	public void setFragments(List<Fragment> fragments) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		for (Fragment f : fragments) {
			ft.remove(f);
		}
		ft.commit();
		getSupportFragmentManager().executePendingTransactions();
		this.fragments = fragments;
		adapter.notifyDataSetChanged();
	}
}