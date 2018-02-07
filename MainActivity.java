package com.suntech.rsmit2_demo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.suntech.rsmit.RSMIT2;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
	private Button Demo_StartBtn;
	private Button Demo_StopBtn;
	private TextView Demo_Error_TV;
	private EditText Demo_Error_Edt;
	private TextView Demo_Title_TV;
	private EditText Demo_Title_Edt;
	private TextView Demo_Action_TV;
	private EditText Demo_Action_Edt;
	private TextView Demo_Value1_TV;
	private EditText Demo_Value1_Edt;
	private TextView Demo_Value2_TV;
	private EditText Demo_Value2_Edt;
	private TextView Demo_Group_TV;
	private EditText Demo_Group_Edt;
	private TextView Demo_Type_TV;
	private EditText Demo_Type_Edt;
	private String webUrl; //新增
	//
	public static final int STATE_NOTHING = 0;
	public static final int STATE_RECORD = 1;
	public int state = STATE_NOTHING;
	private static RSMIT2 rsmit2;
	private Thread recThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		checkPermission();
		initViews();
		viewsLink();
	}
	
	
	private void initViews()
	{
		Demo_StartBtn = (Button) findViewById(R.id.Demo_StartBtn);
		Demo_StopBtn = (Button) findViewById(R.id.Demo_StopBtn);
		
		Demo_Error_TV = (TextView) findViewById(R.id.Demo_Error_TV);
		Demo_Error_Edt = (EditText) findViewById(R.id.Demo_Error_Edt);
		
		Demo_Title_TV = (TextView) findViewById(R.id.Demo_Title_TV);
		Demo_Title_Edt = (EditText) findViewById(R.id.Demo_Title_Edt);
		
		Demo_Action_TV = (TextView) findViewById(R.id.Demo_Action_TV);
		Demo_Action_Edt = (EditText) findViewById(R.id.Demo_Action_Edt);
		
		Demo_Value1_TV = (TextView) findViewById(R.id.Demo_Value1_TV);
		Demo_Value1_Edt = (EditText) findViewById(R.id.Demo_Value1_Edt);
		
		Demo_Value2_TV = (TextView) findViewById(R.id.Demo_Value2_TV);
		Demo_Value2_Edt = (EditText) findViewById(R.id.Demo_Value2_Edt);
		
		Demo_Group_TV = (TextView) findViewById(R.id.Demo_Group_TV);
		Demo_Group_Edt = (EditText) findViewById(R.id.Demo_Group_Edt);
		
		Demo_Type_TV = (TextView) findViewById(R.id.Demo_Type_TV);
		Demo_Type_Edt = (EditText) findViewById(R.id.Demo_Type_Edt);


	}


	/**
	 * 判斷相關權限是否開啟
	 */
	private boolean checkPermission()
	{
		boolean result = true;

		int permissionFineLocation = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION); // 取得精準位置
		int permissionCoarseLocation = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION); // 取得大約位置
		int permissionMicrophone = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO); // 麥克風
		//判斷麥克風權限是否開啟
		if (permissionMicrophone != PackageManager.PERMISSION_GRANTED ||
		permissionCoarseLocation != PackageManager.PERMISSION_GRANTED ||
		permissionFineLocation != PackageManager.PERMISSION_GRANTED)
		{
			// 要求授權
			ActivityCompat.requestPermissions
			(
			this,
			new String[]{android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
			111
			);

			result = false;
		}

		return result;
	}
	private void viewsLink()
	{
		Demo_StartBtn.setOnClickListener(isClicked);
		Demo_StartBtn.setText("Start");
		Demo_StartBtn.setTextColor(Color.BLACK);
		
		Demo_StopBtn.setOnClickListener(isClicked);
		Demo_StopBtn.setText("Stop");
		Demo_StopBtn.setTextColor(Color.BLACK);
		
		Demo_Error_TV.setText("Error：");
		Demo_Error_TV.setTextColor(Color.BLACK);
		
		Demo_Error_Edt.setTextColor(Color.BLACK);
		Demo_Error_Edt.setFocusable(false);
		Demo_Error_Edt.setClickable(false);
		
		Demo_Title_TV.setText("Title：");
		Demo_Title_TV.setTextColor(Color.BLACK);
		
		Demo_Title_Edt.setTextColor(Color.BLACK);
		Demo_Title_Edt.setFocusable(false);
		Demo_Title_Edt.setClickable(false);
		
		Demo_Action_TV.setText("Action：");
		Demo_Action_TV.setTextColor(Color.BLACK);
		
		Demo_Action_Edt.setTextColor(Color.BLACK);
		Demo_Action_Edt.setFocusable(false);
		Demo_Action_Edt.setClickable(false);
		
		Demo_Value1_TV.setText("Value1：");
		Demo_Value1_TV.setTextColor(Color.BLACK);
		
		Demo_Value1_Edt.setTextColor(Color.BLACK);
		Demo_Value1_Edt.setFocusable(false);
		Demo_Value1_Edt.setClickable(false);
		
		Demo_Value2_TV.setText("Value2：");
		Demo_Value2_TV.setTextColor(Color.BLACK);
		
		Demo_Value2_Edt.setTextColor(Color.BLACK);
		Demo_Value2_Edt.setFocusable(false);
		Demo_Value2_Edt.setClickable(false);
		
		Demo_Group_TV.setText("Group：");
		Demo_Group_TV.setTextColor(Color.BLACK);
		
		Demo_Group_Edt.setTextColor(Color.BLACK);
		Demo_Group_Edt.setFocusable(false);
		Demo_Group_Edt.setClickable(false);
		
		Demo_Type_TV.setText("Type：");
		Demo_Type_TV.setTextColor(Color.BLACK);
		
		Demo_Type_Edt.setTextColor(Color.BLACK);
		Demo_Type_Edt.setFocusable(false);
		Demo_Type_Edt.setClickable(false);


		
		
	}
	
	
	private OnClickListener isClicked = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			
			switch (v.getId())
			{
				//StartBtn
				case R.id.Demo_StartBtn:
					StartReceive();
					
					break;
					
				//StopBtn
				case R.id.Demo_StopBtn:
					StopReceive();
					break;
				
				default:
					break;
			}
			
		}
	};

	private void goWeb() {
		if (webUrl != null) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
			startActivity(browserIntent);
		} else {
			Toast.makeText(this, "請接收訊息", Toast.LENGTH_LONG);
		}
	}


	//開始接收
	private void StartReceive()
	{
		if( state == STATE_NOTHING )
		{
			Demo_Error_Edt.setText("");
			Demo_Title_Edt.setText("");
			Demo_Action_Edt.setText("");
			Demo_Value1_Edt.setText("");
			Demo_Value2_Edt.setText("");
			Demo_Group_Edt.setText("");
			Demo_Type_Edt.setText("");
			Demo_StartBtn.setEnabled(false);
			recThread = new Thread()
			{
				@Override
				public void run()
				{
					// TODO Auto-generated method stub
					state = STATE_RECORD;
					rsmit2 = new RSMIT2(MainActivity.this);

					final HashMap<String, String> result = rsmit2.receive();
					if( result != null )
					{
						runOnUiThread( new Runnable()
						{
							
							@Override
							public void run()
							{
								// TODO Auto-generated method stub
								if( rsmit2.errorMessage != null )
								{
									Demo_Error_Edt.setText(rsmit2.errorMessage);
								}
								else {
									Demo_Error_Edt.setText(result.get("errorMessage"));
								}
								Demo_Title_Edt.setText(result.get("title"));
								Demo_Action_Edt.setText(result.get("action"));
								Demo_Value1_Edt.setText(result.get("value1"));
								Demo_Value2_Edt.setText(result.get("value2"));
								Demo_Group_Edt.setText(result.get("group"));
								Demo_Type_Edt.setText(result.get("type"));

								webUrl = result.get("value1"); //新增
								// stop receiving when received data
								StopReceive();
								goWeb();
							}
						} );
					}
					else {
						if( rsmit2.errorMessage != null )
						{
							runOnUiThread( new Runnable()
							{
								
								@Override
								public void run()
								{
									// TODO Auto-generated method stub
									Demo_Error_Edt.setText(rsmit2.errorMessage);
									StopReceive();
								}
							} );
						}
					}
					
				}
			};
			recThread.start();
			
		}		

	}
	
	
	//停止接收
	private void StopReceive()
	{
		Demo_StartBtn.setEnabled(true);
		if( state == STATE_RECORD )
		{
			state = STATE_NOTHING;
			if( rsmit2 != null )
			{
				rsmit2.interruptReceive();

			}
			
			try
			{
				if( recThread != null )
				{
					recThread.interrupt();
					recThread.join();
				}
				
			} 
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				 e.printStackTrace();
			}
		}

	}


}