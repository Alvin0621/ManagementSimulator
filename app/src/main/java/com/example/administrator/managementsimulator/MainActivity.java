package com.example.administrator.managementsimulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button[] button;
    // final Player player = new Player();//(Player) getApplicationContext();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
        button = new Button[6];
        button[0] = (Button) findViewById(R.id.button1);
        button[1] = (Button) findViewById(R.id.button2);
        button[2] = (Button) findViewById(R.id.button3);
        button[3] = (Button) findViewById(R.id.button4);
        button[4] = (Button) findViewById(R.id.button5);
        button[5] = (Button) findViewById(R.id.button6);

        switch (((Player) this.getApplication()).getDate()) {
            case 12:
            case 1:
            case 2:
                relativeLayout.setBackgroundColor(0xFFFFFFFF);
                for (int i = 0; i < 6; i++) {
                    button[i].setBackgroundColor(0xFF696969);
                }
                break;
            case 3:
            case 4:
            case 5:
                relativeLayout.setBackgroundColor(0xFFFFF580);
                for (int i = 0; i < 6; i++) {
                    button[i].setBackgroundColor(0xFFFFD1AB);
                    button[i].setTextColor(0xFF000000);
                }
                break;
            case 6:
            case 7:
            case 8:
                relativeLayout.setBackgroundColor(0xFFC2FF5A);
                for (int i = 0; i < 6; i++) {
                    button[i].setBackgroundColor(0xFF69CE45);
                    button[i].setTextColor(0xFF000000);
                }
                break;
            case 9:
            case 10:
            case 11:
                relativeLayout.setBackgroundColor(0xFFFFB857);
                for (int i = 0; i < 6; i++) {
                    button[i].setBackgroundColor(0xFFFF9F2A);
                }
                break;
        }
        //button[1].setBackgroundColor();
        for (int i = 0; i < 6; i++) {
            final int s = i + 1;
            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, ListActivity.class);

                    intent.putExtra("type", s);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView officeImage = (ImageView) findViewById(R.id.imageView);
        TextView textView = (TextView) findViewById(R.id.textView);
        final int iLevel = ((Player) this.getApplication()).getLevel();
        final int iExp = ((Player) this.getApplication()).getExp();
        final int iYear = ((Player) this.getApplication()).getYear();
        final int iDate = ((Player) this.getApplication()).getDate();
        final int iMoney = ((Player) this.getApplication()).getMoney();
        textView.setText("Lv." + iLevel + "    " + iExp + "/" + (Player.EXPBASE + Player.EXPNEED * iLevel) + "   " + iYear + "년/" + iDate + "월   $" + iMoney + "원");

        if (iLevel == 15) {
            officeImage.setImageResource(R.drawable.office06);
        } else if (iLevel >= 13) {
            officeImage.setImageResource(R.drawable.office05);
        } else if (iLevel >= 10) {
            officeImage.setImageResource(R.drawable.office04);
        } else if (iLevel >= 7) {
            officeImage.setImageResource(R.drawable.office03);
        } else if (iLevel >= 4) {
            officeImage.setImageResource(R.drawable.office02);
        } else {
            officeImage.setImageResource(R.drawable.office01);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
