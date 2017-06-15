package com.ramytech.android.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.media.MediaRecorder;
import android.os.Environment;

public class RecordButtonUtil {
    public static final String AUDOI_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/tmp/audio/"; // 录音音频保存根路径
 
    private String mAudioPath; // 要播放的声音的路径
    private boolean mIsRecording;// 是否正在录音   
    
    private MediaRecorder mRecorder;
    
    public RecordButtonUtil() {
    	File dir = new File(AUDOI_DIR);
    	if (!dir.exists()) dir.mkdirs();
    	this.mAudioPath = new File(dir, new Date().getTime() + ".aac").getAbsolutePath();
    	this.initRecorder();
    }
    
    public String getAudioPath() {return this.mAudioPath;}
    
    // 初始化 录音器
    private void initRecorder() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mRecorder.setOutputFile(mAudioPath);
        mIsRecording = true;
    }
 
    /** 开始录音，并保存到文件中 */
    public void recordAudio() {
        initRecorder();
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecorder.start();
    }
 
    /** 获取音量值，只是针对录音音量 */
    public int getVolumn() {
        int volumn = 0;
        // 录音
        if (mRecorder != null && mIsRecording) {
            volumn = mRecorder.getMaxAmplitude();
            if (volumn != 0)
                volumn = (int) (10 * Math.log(volumn) / Math.log(10)) / 7;
        }
        return volumn;
    }
 
    /** 停止录音 */
    public void stopRecord() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            mIsRecording = false;
        }
    }
}