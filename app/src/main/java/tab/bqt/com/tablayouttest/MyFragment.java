package tab.bqt.com.tablayouttest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class MyFragment extends Fragment {
	private String tag;

	public static MyFragment newInstance(String tag) {
		Bundle args = new Bundle();
		args.putString("tag", tag);
		MyFragment fragment = new MyFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tag = getArguments().getString("tag");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		TextView textView = new TextView(getContext());
		textView.setText(tag);
		textView.setGravity(Gravity.CENTER);
		textView.setBackgroundColor(0xFF000000 + new Random().nextInt(0xFFFFFF));
		//注意，Integer.MAX_VALUE= 0x7fffffff，超过这个值就是负数了，所以通过随机数获取int值时，传入的值要小于0x7fffffff
		return textView;
	}
}