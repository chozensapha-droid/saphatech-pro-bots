package com.saphatech.probot;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.*;
import android.graphics.Color;
import android.view.ViewGroup;

public class MainActivity extends Activity {
    private TextView status;
    private Spinner symbol;
    private EditText lot, target;
    private CheckBox autoEntry, autoExit, autoReentry;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        buildUi();
        if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 20);
    }

    private TextView tv(String s, int sp) { TextView t=new TextView(this); t.setText(s); t.setTextSize(sp); t.setPadding(0,8,0,8); return t; }
    private LinearLayout row() { LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.HORIZONTAL); r.setPadding(0,4,0,4); return r; }

    private void buildUi() {
        ScrollView scroll=new ScrollView(this);
        LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(24,20,24,24);
        TextView title=tv("SAPHATECH PRO BOT",26); title.setTextColor(Color.rgb(13,71,161)); title.setGravity(17); title.setTypeface(null,1); root.addView(title);
        TextView sub=tv("PHONE-ONLY LIVE TRADING ENGINE",13); sub.setGravity(17); root.addView(sub);
        status=tv("● ENGINE OFF — no broker connection",14); status.setTextColor(Color.DKGRAY); root.addView(status);

        root.addView(tv("SYMBOL",12));
        symbol=new Spinner(this); String[] syms={"XAUUSD","GBPUSD","GBPJPY","AUDUSD","USOIL","BTCUSD","XAUAUD"};
        symbol.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, syms)); root.addView(symbol);

        root.addView(tv("TIMEFRAMES",12));
        TextView tf=tv("M1  ✓   M5  ✓   M15  ✓   M30  ✓   H1  ✓   H4  ✓   D1  ✓",14); root.addView(tf);

        root.addView(tv("LOT SIZE",12)); lot=new EditText(this); lot.setText("0.01"); lot.setInputType(2|8192); root.addView(lot);
        root.addView(tv("PER-POSITION PROFIT TARGET (USD)",12)); target=new EditText(this); target.setText("1.02"); target.setInputType(2|8192); root.addView(target);

        autoEntry=new CheckBox(this); autoEntry.setText("AUTO ENTRY"); autoEntry.setChecked(true); root.addView(autoEntry);
        autoExit=new CheckBox(this); autoExit.setText("AUTO EXIT / QUICK PROFIT MONITOR"); autoExit.setChecked(true); root.addView(autoExit);
        autoReentry=new CheckBox(this); autoReentry.setText("AUTO RE-ENTRY"); autoReentry.setChecked(true); root.addView(autoReentry);

        Button start=new Button(this); start.setText("START PHONE-ONLY ENGINE"); root.addView(start);
        Button stop=new Button(this); stop.setText("STOP ENGINE"); root.addView(stop);
        LinearLayout trades=row();
        Button buy=new Button(this); buy.setText("BUY"); Button sell=new Button(this); sell.setText("SELL");
        trades.addView(buy,new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1)); trades.addView(sell,new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1)); root.addView(trades);
        Button battery=new Button(this); battery.setText("ALLOW BACKGROUND RUNNING"); root.addView(battery);

        start.setOnClickListener(v -> { Intent i=new Intent(this, TradingEngineService.class); i.setAction(TradingEngineService.START); if(Build.VERSION.SDK_INT>=26) startForegroundService(i); else startService(i); status.setText("● ENGINE RUNNING — waiting for broker connection"); });
        stop.setOnClickListener(v -> { stopService(new Intent(this, TradingEngineService.class)); status.setText("● ENGINE OFF"); });
        buy.setOnClickListener(v -> status.setText("BUY REQUEST QUEUED — broker adapter not configured"));
        sell.setOnClickListener(v -> status.setText("SELL REQUEST QUEUED — broker adapter not configured"));
        battery.setOnClickListener(v -> { try { startActivity(new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)); } catch(Exception ignored){} });

        root.addView(tv("SAFETY: This build contains the phone-only engine architecture and broker interface. It does not fake a direct Exness API connection. Live order execution requires a supported broker/API adapter and valid credentials.",11));
        scroll.addView(root); setContentView(scroll);
    }
}
