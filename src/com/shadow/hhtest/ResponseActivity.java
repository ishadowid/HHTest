package com.shadow.hhtest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResponseActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try
		{
			setContentView(R.layout.response_activity);
			
			Bundle extras = getIntent().getExtras();
			
			((TextView)findViewById(R.id.textView_fullName_result)).setText(extras.getString("com.shadow.hhtest.FULLNAME"));
			((TextView)findViewById(R.id.textView_birthdate_result)).setText(extras.getString("com.shadow.hhtest.BIRTHDATE"));
			((TextView)findViewById(R.id.textView_sex_result)).setText(extras.getString("com.shadow.hhtest.SEX"));
			((TextView)findViewById(R.id.textView_post_result)).setText(extras.getString("com.shadow.hhtest.POST"));
			((TextView)findViewById(R.id.textView_salary_result)).setText(extras.getString("com.shadow.hhtest.SALARY"));
			((TextView)findViewById(R.id.textView_phone_result)).setText(extras.getString("com.shadow.hhtest.PHONE"));
			((TextView)findViewById(R.id.textView_email_result)).setText(extras.getString("com.shadow.hhtest.EMAIL"));
			
			Linkify.addLinks((TextView)findViewById(R.id.textView_phone_result), Linkify.PHONE_NUMBERS);
			Linkify.addLinks((TextView)findViewById(R.id.textView_email_result), Linkify.EMAIL_ADDRESSES);
			Button responseButton = (Button)findViewById(R.id.button_send_response);
			responseButton.setOnClickListener(this);
		}
		catch (Exception e)
		{
			Toast.makeText(this, "ResponseActivity.onCreate: " + e.getMessage(), Toast.LENGTH_LONG).show();
			finish();
		}
	}
	@Override
	public void onClick(View v) {
		try
		{
			Intent data = new Intent();
			
			String param = ((EditText)findViewById(R.id.editText_response_text)).getText().toString();
			if (!param.isEmpty())
				data.putExtra("com.shadow.hhtest.RESPONSE_TEXT", param);
			else
			{
				Toast.makeText(this, R.string.responsetext_alarm, Toast.LENGTH_LONG).show();
				return;
			}
			
			
			setResult(RESULT_OK, data);
		}
		catch (Exception e)
		{
			Toast.makeText(this, "ResponseActivity.OnClick: " + e.getMessage(), Toast.LENGTH_LONG).show();
			setResult(RESULT_CANCELED);
		}
		finish();
	}
	@Override
	public void onBackPressed() {
		return;
	}
}

