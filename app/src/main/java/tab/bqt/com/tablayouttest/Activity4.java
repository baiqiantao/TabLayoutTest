package tab.bqt.com.tablayouttest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Activity4 extends FragmentActivity {

	private List<Fragment> fragments;
	private ViewPager viewPager;
	private PagerAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		findViewById(R.id.tv_add).setVisibility(View.VISIBLE);
		findViewById(R.id.tv_remove).setVisibility(View.VISIBLE);
		init();
	}

	private void init() {
		String[] array = new String[]{"baiqiantao", "白", "包青天", "白乾涛", "1", "2", "3", "4", "5", "6", "7", "8",};
		fragments = new ArrayList<>();
		for (String string : array) {
			fragments.add(MyFragment.newInstance(string));
		}

		adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return fragments.get(position);
			}

			@Override
			public int getCount() {
				return fragments.size();
			}
		};
		viewPager.setAdapter(adapter);
	}

	public void add(View view) {
		String data = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault()).format(new Date());
		fragments.add(0, MyFragment.newInstance(data));
		Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();

		adapter.notifyDataSetChanged();
	}

	public void remove(View view) {
		if (fragments.size() > 0) {
			fragments.remove(0);
		}
		Toast.makeText(this, "remove", Toast.LENGTH_SHORT).show();
		adapter.notifyDataSetChanged();
	}
}