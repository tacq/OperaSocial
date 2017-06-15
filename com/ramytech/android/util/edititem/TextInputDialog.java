package com.ramytech.android.util.edititem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.ramytech.piaxi.R;

public class TextInputDialog extends DialogFragment implements OnEditorActionListener, OnClickListener {

    public interface EditDialogListener {
        void onFinishEditDialog(String inputText);
    }
    
    private EditDialogListener listener;
    
    public void setEditDialogListener(EditDialogListener listener) {
    	this.listener = listener;
    }

    private EditText mEditText;

    public TextInputDialog() {
        // Empty constructor required for DialogFragment
    }
    private String title, defstr;
    
    public static TextInputDialog newInstance(String title, String defstr) {
    	return TextInputDialog.newInstance(title, defstr, null);
    }
    
    public static TextInputDialog newInstance(String title, String defstr, EditDialogListener listener) {
    	TextInputDialog f = new TextInputDialog();
    	f.title = title;f.defstr = defstr;f.listener = listener;
        return f;
    }
    
    public void onResume() {
    	super.onResume();
    	getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    	mEditText.requestFocus();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); 
    	LayoutInflater inflater = getActivity().getLayoutInflater();  	
    	View view = inflater.inflate(R.layout.fragment_textinput, null);
    	 mEditText = (EditText) view.findViewById(R.id.txt_your_name);
         
         TextView lbl_your_name = (TextView) view.findViewById(R.id.lbl_your_name);
         lbl_your_name.setVisibility(View.GONE);
         
         mEditText.setText(defstr);
         // Show soft keyboard automatically
         mEditText.requestFocus();         
         mEditText.setOnEditorActionListener(this);    	
        
        builder.setView(view).setTitle(title)//.setMessage("")
        .setPositiveButton("确定", this)
        .setNegativeButton("取消", this);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme);
        return builder.create();
    }
           
    private void doSubmit() {
    	if (listener != null)
        	listener.onFinishEditDialog(mEditText.getText().toString());
        this.dismiss();
    }
    private void doCancel() {
    	this.dismiss();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            doSubmit();
            return true;
        }
        return false;
    }

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == AlertDialog.BUTTON_NEGATIVE) {
			this.doCancel();
		} else if (which == AlertDialog.BUTTON_POSITIVE) {
			this.doSubmit();
		}
		
	}
}