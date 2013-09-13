package com.shadow.hhtest;

import java.util.Calendar;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class RequestActivity extends Activity implements OnClickListener{

	private static final int IDD_RESPONSE = 0;
	private static final int IDD_BIRTHDATE = 1;
	private static final int IDA_RESPONSE_ACTIVITY = 101;
	
	private String ResponseResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try
		{
			setContentView(R.layout.request_activity);
			
			Spinner spinnerSex = (Spinner) findViewById(R.id.spinner_sex);
			ArrayAdapter<CharSequence> sexArrayAdapter = ArrayAdapter.createFromResource(this, R.array.sex_items, android.R.layout.simple_spinner_item);
			sexArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerSex.setAdapter(sexArrayAdapter);
			
			Button requestButton = (Button)findViewById(R.id.button_send_request);
			requestButton.setOnClickListener(this);
			
			((EditText)findViewById(R.id.editText_birthdate)).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showDialog(IDD_BIRTHDATE);
					
				}
			});
		}
		catch(Exception e)
		{
			Toast.makeText(this, "RequestActivity.onCreate: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public void onClick(View v) {
		try
		{
			Intent requestIntent = new Intent();
			requestIntent.setClass(this, ResponseActivity.class);
			
			String param = ((EditText)findViewById(R.id.editText_fullName)).getText().toString();
			if (!param.isEmpty())
				requestIntent.putExtra("com.shadow.hhtest.FULLNAME", param);
			else
			{
				Toast.makeText(this, R.string.fullName_alarm, Toast.LENGTH_LONG).show();
				return;
			}
			
			param = ((EditText)findViewById(R.id.editText_birthdate)).getText().toString();
			if (!param.isEmpty())
				requestIntent.putExtra("com.shadow.hhtest.BIRTHDATE", param);
			else
			{
				Toast.makeText(this, R.string.birthdate_alarm, Toast.LENGTH_LONG).show();
				return;
			}
			
			param = ((EditText)findViewById(R.id.editText_post)).getText().toString();
			if (!param.isEmpty())
				requestIntent.putExtra("com.shadow.hhtest.POST", param);
			else
			{
				Toast.makeText(this, R.string.post_alarm, Toast.LENGTH_LONG).show();
				return;
			}
			
			param = ((EditText)findViewById(R.id.editText_salary)).getText().toString();
			if (!param.isEmpty() && Pattern.matches("[0-9]+", param))
				requestIntent.putExtra("com.shadow.hhtest.SALARY", param);
			else
			{
				Toast.makeText(this, R.string.salary_alarm, Toast.LENGTH_LONG).show();
				return;
			}
			
			param = ((EditText)findViewById(R.id.editText_phone)).getText().toString();
			if (!param.isEmpty() && Pattern.matches("\\+?[0-9]{5,}", param))
				requestIntent.putExtra("com.shadow.hhtest.PHONE", param);
			else
			{
				Toast.makeText(this, R.string.phone_alarm, Toast.LENGTH_LONG).show();
				return;
			}
			
			param = ((EditText)findViewById(R.id.editText_email)).getText().toString();
			if (!param.isEmpty() && Pattern.matches("[a-zA-Z]+\\@[a-zA-Z]+\\.[a-zA-Z]+", param))
				requestIntent.putExtra("com.shadow.hhtest.EMAIL", param);
			else
			{
				Toast.makeText(this, R.string.email_alarm, Toast.LENGTH_LONG).show();
				return;
			}
			
			requestIntent.putExtra("com.shadow.hhtest.SEX", ((Spinner)findViewById(R.id.spinner_sex)).getSelectedItem().toString());
			
			startActivityForResult(requestIntent, IDA_RESPONSE_ACTIVITY);
		}
		catch(Exception e)
		{
			Toast.makeText(this, "RequestActivity.onClick: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try
		{
			switch (requestCode)
			{
				case IDA_RESPONSE_ACTIVITY:
					if (resultCode == Activity.RESULT_OK)
					{
						ResponseResult = data.getExtras().getString("com.shadow.hhtest.RESPONSE_TEXT");
						showDialog(IDD_RESPONSE);
					}
					else
						Toast.makeText(this, "resultCode not RESULT_OK", Toast.LENGTH_LONG).show();
					break;
				default:
					Toast.makeText(this, "requestCode default action", Toast.LENGTH_LONG).show();
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
		catch(Exception e)
		{
			Toast.makeText(this, "RequestActivity.onActivityResult: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		try 
		{
			switch (id)
			{
				case IDD_RESPONSE:
					AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
					dialogBuilder.setTitle(R.string.dialog_reply_title).setNeutralButton(R.string.dialog_reply_button, 
							new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
					dialogBuilder.setMessage("Message from onCreateDialog");
					return dialogBuilder.create();
				case IDD_BIRTHDATE:
					Calendar c = Calendar.getInstance();
					DatePickerDialog birthdateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
						
						@Override
						public void onDateSet(DatePicker view, int year, int monthOfYear,
								int dayOfMonth) {
							((EditText)findViewById(R.id.editText_birthdate)).setText(
									new StringBuilder().append(dayOfMonth)
														.append("-")
														.append(monthOfYear+1)
														.append("-")
														.append(year));
							
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
					return birthdateDialog;
				default:
					return super.onCreateDialog(id);
			}
		} 
		catch (Exception e) 
		{
			Toast.makeText(this, "RequestActivity.onCreateDialog: " + e.getMessage(), Toast.LENGTH_LONG).show();
			return super.onCreateDialog(id);
		}
	}
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		try
		{
			switch (id)
			{
				case IDD_RESPONSE:
					((AlertDialog)dialog).setMessage(ResponseResult.toString());					
					break;
				case IDD_BIRTHDATE:
					break;
				default:
					super.onPrepareDialog(id, dialog);
			}
		}
		catch(Exception e)
		{
			Toast.makeText(this, "RequestActivity.onPrepareDialog: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
}
