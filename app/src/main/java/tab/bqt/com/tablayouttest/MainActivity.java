package tab.bqt.com.tablayouttest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mjmedia.mediasdk.JJMediaCallback;
import com.mjmedia.mediasdk.JJMediaSDK;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends ListActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] array = {"0、不重写getPageTitle，会导致没有标题",
				"1、使用默认的MODE_FIXED模式",
				"2、代码中设置Tab样式",
				"3、XML中设置Tab样式",

				"4、和VP联动演示，自定义Tab，添加监听，自定义Tab间距",
				"5、不自定义Tab，不添加监听，不自定义Tab间距(注意：这里tabMinWidth的默认值会严重影响Tab的宽度)",
				"6、自定义Tab，添加监听，不自定义Tab间距",

				"7、添加点击事件，但不手动处理点击Tab后TabLayout和VP的变化",
				"8、添加点击事件，并手动处理点击Tab后TabLayout和VP的变化",
				"9、【动态添加、删除VP中的数据--正常情况】",
				"9、【动态添加、删除VP中的数据--非正常情况】",
				"9、【开始录音】",
				"9、【结束录音】",};
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(Arrays.asList(array))));

		JJMediaSDK.init(this, new JJMediaCallback() {
			@Override
			public void OnSpeakerStatus(int i, int i1, boolean b) {
			}

			@Override
			public void OnMediaMsgCode(int i) {
			}
		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
			case 0:
			case 1:
			case 2:
			case 3:
				Intent intent = new Intent(this, Activity1.class);
				intent.putExtra("tag", position);
				startActivity(intent);
				break;
			case 4:
			case 5:
			case 6:
				Intent intent2 = new Intent(this, Activity2.class);
				intent2.putExtra("tag", position);
				startActivity(intent2);
				break;
			case 7:
			case 8:
				Intent intent3 = new Intent(this, Activity3.class);
				intent3.putExtra("tag", position);
				startActivity(intent3);
				break;
			case 9:
				Intent intent4 = new Intent(this, Activity_VP1.class);
				intent4.putExtra("tag", position);
				startActivity(intent4);
				break;
			case 10:
				Intent intent5 = new Intent(this, Activity_VP2.class);
				intent5.putExtra("tag", position);
				startActivity(intent5);
				break;
			case 11:
				String pcmPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "a" + File.separator + "temp_" + ".pcm";
				String pcmPath2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "a" + File.separator + "temp2_" + ".pcm";

				boolean b = JJMediaSDK.startRecord(getVoicePcmPath(), getVoiceAccPath());
				Toast.makeText(this, b+"", Toast.LENGTH_SHORT).show();
				break;
			case 12:
				JJMediaSDK.stopRecord();
				break;
		}
	}


	public static final String VOICE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "voice";

	public static String getVoicePcmPath() {
		String data = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss SSS", Locale.getDefault()).format(new Date());
		return VOICE_PATH + File.separator + "temp_" + data + ".pcm";
	}

	public static String getVoiceAccPath() {
		String data = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss SSS", Locale.getDefault()).format(new Date());
		return VOICE_PATH + File.separator + "temp_" + data + ".aac";
	}

	public static String getXFVoiceSavePath() {
		String data = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss SSS", Locale.getDefault()).format(new Date());
		return VOICE_PATH + File.separator + "xf_" + data + ".wav";
	}
}