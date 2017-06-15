package com.ramytech.piaxi.me;

import java.util.LinkedList;

import com.ramytech.android.adapter.SetLanguageAdapter;
import com.ramytech.android.adapter.SetTimeListAdapter;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.search.OtherLanguageFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class FragmentNewPlaySettingSecondStep extends Fragment implements OnClickListener{
	private View view;
	private ListView timeList;
	private GridView plotGrid;
	private GridView languageGrid;
	private final int[] plotImage = { R.drawable.spicygrey,
			R.drawable.sweetgrey, R.drawable.bittergrey, R.drawable.acidgrey,
			R.drawable.saltygrey, R.drawable.lightgrey };
	private final int[] plotImageSelected = { R.drawable.spicyred,
			R.drawable.sweetred, R.drawable.bitterred, R.drawable.acidred,
			R.drawable.saltyred, R.drawable.lightred };

	private final int[] languageImage = { R.drawable.cngrey, R.drawable.hkgrey,
			R.drawable.usgrey, R.drawable.kogrey, R.drawable.jpgrey,
			R.drawable.othersgrey };
	private final int[] languageSelected = { R.drawable.cn, R.drawable.hk,
			R.drawable.us, R.drawable.ko, R.drawable.jp, R.drawable.others };

	private ImageView minusActressButton;
	private ImageView addActressButton;
	private ImageView minusActorButton;
	private ImageView addActorButton;

	private TextView actressNumber;
	private TextView actorNumber;

	private ImageView saveButton;
	private static final String second_step_data = "second_step_data";

	private int plotType = -1;// (0--拉 1--甜 2--苦 3--酸 4--咸 5--淡)
	private int languageId = -1;// (0--国语 1--粤语 2--英语 3--汉语 4--日语 5--其它)
	private int novelLength = 0; // (0--超短篇 1--短篇 2--中篇 3--长篇 4--超长篇 )

	private SetTimeListAdapter setTimeListAdapter;
	private LinkedList<Integer> checkdPosition = new LinkedList<Integer>();
	private SetLanguageAdapter plotAdapter;
	private SetLanguageAdapter languageAdapter;
	private static final String OTHER_LANGUAGE = "other_language_for_second_step";

	private SeekBar up;
	private SeekBar down;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_new_play_setting_second_step, container,
				false);
		timeList = (ListView) view
				.findViewById(R.id.set_time_list_second_step_page);
		plotGrid = (GridView) view
				.findViewById(R.id.set_plot_grid_second_step_page);
		languageGrid = (GridView) view
				.findViewById(R.id.set_language_grid_second_step_page);
		saveButton = (ImageView) view
				.findViewById(R.id.button_save_filter_second_step_page);
		minusActressButton = (ImageView) view
				.findViewById(R.id.minus_actress_button_second_step_page);
		addActressButton = (ImageView) view
				.findViewById(R.id.add_actress_button_second_step_page);
		minusActorButton = (ImageView) view
				.findViewById(R.id.minus_actor_button_second_step_page);
		addActorButton = (ImageView) view
				.findViewById(R.id.add_actor_button_second_step_page);
		actressNumber = (TextView) view
				.findViewById(R.id.actress_number_second_step_page);
		actorNumber = (TextView) view
				.findViewById(R.id.actor_number_second_step_page);
		up = (SeekBar) view.findViewById(R.id.thub_pic);
		down = (SeekBar) view.findViewById(R.id.length_line);

		for (int i = 0; i < 6; i++) {
			checkdPosition.add(0);
		}
		setTimeListAdapter = new SetTimeListAdapter(getActivity()
				.getBaseContext(), checkdPosition);
		timeList.setAdapter(setTimeListAdapter);
		setTimeListAdapter.notifyDataSetChanged();

		plotAdapter = new SetLanguageAdapter(getActivity().getBaseContext(),
				plotImage, plotImageSelected);
		plotGrid.setAdapter(plotAdapter);
		plotGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		plotAdapter.notifyDataSetChanged();
		plotGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				plotType = arg2;
				plotAdapter.setSelectedPosition(arg2);
				plotAdapter.notifyDataSetChanged();
			}

		});

		languageAdapter = new SetLanguageAdapter(
				getActivity().getBaseContext(), languageImage, languageSelected);
		languageGrid.setAdapter(languageAdapter);
		languageGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		languageAdapter.notifyDataSetChanged();
		languageGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				languageId = arg2;
				languageAdapter.setSelectedPosition(arg2);
				languageAdapter.notifyDataSetChanged();
				if (arg2 == 5) {
					saveSearchSetting();
					goToNextPage(new FragmentOtherLanguage2());
				}
			}

		});

		minusActressButton.setOnClickListener(this);
		addActressButton.setOnClickListener(this);
		minusActorButton.setOnClickListener(this);
		addActorButton.setOnClickListener(this);

		saveButton.setOnClickListener(this);

		up.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				down.setProgress(progress);
				switch (progress) {
				case 0:
					up.setThumb(getActivity().getResources().getDrawable(
							R.drawable.iconultrashort));
					break;
				case 25:
					up.setThumb(getActivity().getResources().getDrawable(
							R.drawable.iconshort));
					break;
				case 50:
					up.setThumb(getActivity().getResources().getDrawable(
							R.drawable.iconnovella));
					break;
				case 75:
					up.setThumb(getActivity().getResources().getDrawable(
							R.drawable.iconnovel));
					break;
				case 100:
					up.setThumb(getActivity().getResources().getDrawable(
							R.drawable.iconnovelplus));
					break;
				}

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (seekBar.getProgress() <= 13) {
					seekBar.setProgress(0);
					down.setProgress(0);
				} else if (seekBar.getProgress() <= 38) {
					seekBar.setProgress(25);
					down.setProgress(25);
				} else if (seekBar.getProgress() <= 63) {
					seekBar.setProgress(50);
					down.setProgress(50);
				} else if (seekBar.getProgress() <= 88) {
					seekBar.setProgress(75);
					down.setProgress(75);
				} else {
					seekBar.setProgress(100);
					down.setProgress(100);
				}
			}

		});

		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("剧本设置(2/3)");

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.radar_menu, menu);
		menu.findItem(R.id.menu_radar).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_radar).setIcon(null).setTitle("重设");
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.menu_radar:
			resetSearchSetting();
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.minus_actress_button_second_step_page:
			minus(actressNumber);
			break;
		case R.id.add_actress_button_second_step_page:
			add(actressNumber);
			break;
		case R.id.minus_actor_button_second_step_page:
			minus(actorNumber);
			break;
		case R.id.add_actor_button_second_step_page:
			add(actorNumber);
			break;
		case R.id.button_save_filter_second_step_page:
			saveSearchSetting();
			goToNextPage(new FragmentNewPlaySettingFinalStep());
			break;
		}
	}

	private void add(TextView t) {
		t.setText("" + (Integer.parseInt(t.getText().toString()) + 1));
	}

	private void minus(TextView t) {
		if (Integer.parseInt(t.getText().toString()) > 1)
			t.setText("" + (Integer.parseInt(t.getText().toString()) - 1));
	}

	private void resetSearchSetting() {
		clearSavingData();
		for (int i = 0; i < timeList.getChildCount(); i++) {
			CheckBox checkBox = (CheckBox) timeList.getChildAt(i).findViewById(
					R.id.language_checkbox);
			if (checkBox.isChecked()) {
				checkBox.setChecked(false);
				checkBox.setTextColor(Color.BLACK);
			}
		}

		plotAdapter.setSelectedPosition(-1);
		plotAdapter.notifyDataSetChanged();

		languageAdapter.setSelectedPosition(-1);
		languageAdapter.notifyDataSetChanged();

		actressNumber.setText("1");
		actorNumber.setText("1");

		plotType = -1;// (0--拉 1--甜 2--苦 3--酸 4--咸 5--淡)
		languageId = -1;
		novelLength = 0;

		up.setProgress(0);
		down.setProgress(0);
		up.setThumb(getActivity().getResources().getDrawable(
				R.drawable.iconultrashort));
	}

	// 保存筛选信息
	private void saveSearchSetting() {
		// 清理上次数据
		clearSavingData();
		SharedPreferences sp = getActivity().getSharedPreferences(
				second_step_data, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean("isEmpty", false);
		int typeCount = 0;
		// 0--古风 1--民国 2--现代 3--国外 4--仙穿 5--耽百
		for (int i = 0; i < timeList.getChildCount(); i++) {
			CheckBox checkBox = (CheckBox) timeList.getChildAt(i).findViewById(
					R.id.language_checkbox);
			if (checkBox.isChecked()) {
				editor.putInt("type" + typeCount, i);
				typeCount++;
			}
		}

		editor.putInt("typeCount", typeCount);

		editor.putInt("plot", plotType);
		editor.putInt("languageId", languageId);
		switch (languageId) {
		case 0:
			editor.putString("language", "国语");
			break;
		case 1:
			editor.putString("language", "粤语");
			break;
		case 2:
			editor.putString("language", "英语");
			break;
		case 3:
			editor.putString("language", "汉语");
			break;
		case 4:
			editor.putString("language", "日语");
			break;
		case 5:
			editor.putString("language", getOtherLanguage());
			break;
		}
		editor.putString("actressNumber", actressNumber.getText().toString());
		editor.putString("actorNumber", actorNumber.getText().toString());
		switch (down.getProgress()) {
		case 0:
			editor.putInt("novelLength", 0);
			break;
		case 25:
			editor.putInt("novelLength", 25);
			break;
		case 50:
			editor.putInt("novelLength", 50);
			break;
		case 75:
			editor.putInt("novelLength", 75);
			break;
		case 100:
			editor.putInt("novelLength", 100);
			break;
		}
		editor.commit();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences sp = getActivity().getSharedPreferences(
				second_step_data, Context.MODE_PRIVATE);
		if (!(sp.getBoolean("isEmpty", true))) {
			int typeCount = sp.getInt("typeCount", 0);
			for (int i = 0, position = 0; i < typeCount; i++) {
				position = sp.getInt("type" + i, 0);
				checkdPosition.set(position, 1);
			}
			setTimeListAdapter.update(checkdPosition);
			setTimeListAdapter.notifyDataSetChanged();

			plotType = sp.getInt("plot", -1);
			System.out.println("plotPosisiton:"+plotType);
			plotAdapter.setSelectedPosition(plotType);
			plotAdapter.notifyDataSetChanged();

			
			int languageId = sp.getInt("languageId", -1);
			if(languageId == 5)
				if(getOtherLanguage().equals(""))
						languageId = -1;
			languageAdapter.setSelectedPosition(languageId);
			languageAdapter.notifyDataSetChanged();

			
			actressNumber.setText(sp.getString("actressNumber", "1"));
			actorNumber.setText(sp.getString("actorNumber", "1"));

			up.setProgress(sp.getInt("novelLength", 0));
			switch (sp.getInt("novelLength", 0)) {
			case 0:
				up.setThumb(getActivity().getResources().getDrawable(
						R.drawable.iconultrashort));
				break;
			case 25:
				up.setThumb(getActivity().getResources().getDrawable(
						R.drawable.iconshort));
				break;
			case 50:
				up.setThumb(getActivity().getResources().getDrawable(
						R.drawable.iconnovella));
				break;
			case 75:
				up.setThumb(getActivity().getResources().getDrawable(
						R.drawable.iconnovel));
				break;
			case 100:
				up.setThumb(getActivity().getResources().getDrawable(
						R.drawable.iconnovelplus));
				break;
			}
			down.setProgress(sp.getInt("novelLength", 0));
		}
	}

	private void clearSavingData() {
		SharedPreferences sp = getActivity().getSharedPreferences(
				second_step_data, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear().commit();
	}

	private void goToNextPage(Fragment fragment) {
		FragmentTransaction transaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.mainContainer, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	private String getOtherLanguage() {
		SharedPreferences sp = getActivity().getSharedPreferences(
				OTHER_LANGUAGE, Context.MODE_PRIVATE);
		return sp.getString(OTHER_LANGUAGE, "");
	}
	
	
}
