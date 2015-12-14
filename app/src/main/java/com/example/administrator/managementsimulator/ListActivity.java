package com.example.administrator.managementsimulator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ListView(this));

    }

    public class ListView extends View {
        private String title;
        private Intent intent = getIntent();

        private int intentNum = intent.getIntExtra("type", -1);
        private int contentNum = 0;
        private int textSize = 0;
        private int listIndicator;
        private int programNo = -1;
        //boolean buttonShown = false;

        final int TOP = 300;
        final int HEIGHT = 200;

        final static int CASTING = 1;
        final static int BROADCAST = 2;
        final static int MUSIC = 3;
        final static int REST = 4;
        final static int MANAGE = 5;
        final static int PROCEED = 6;

        final static int STCASTING = 11;
        final static int ENCASTING = 12;
        final static int TRCASTING = 13;

        final static int BRAPPEAR = 21;

        final static int TRMUSIC = 31;
        final static int PREMUSIC = 32;
        final static int REMUSIC = 33;
        final static int MAMUSIC = 34;

        final static int COMUSIC = 321;
        final static int WRMUSIC = 322;
        final static int ARMUSIC = 323;
        final static int RECMUSIC = 324;

        final static int SINGLE = 331;
        final static int ALBUM = 332;

        final static int COMPOSE = 101;
        final static int WRITE = 102;
        final static int ARRANGE = 103;
        final static int RECORD = 104;

        private Paint paint;

        //private boolean[] buttonClicked;
        private int[] contentOrigin;
        private Rect[] contentButton;
        private String[] contentList;
        private Broadcast[] broadcastList;
        private Worker[] workerList;
        private Music[] musicList;


        public ListView(Context context) {
            super(context);
            setBackgroundColor(Color.BLACK);
            paint = new Paint();

            musicList = new Music[4];
            for (int i = 0; i < 4; i++) {
                musicList[i] = new Music();
            }

            musicList[0].name = "a10";

            int indicator = 1;
            switch (intentNum) {
                case CASTING:
                    title = "캐스팅";
                    contentNum = 3;
                    textSize = 300;
                    contentButton = new Rect[contentNum];
                    contentList = new String[contentNum];
                    for (int i = 0; i < contentNum; i++) {

                        contentButton[i] = new Rect();
                    }
                    contentList[0] = "스태프 캐스팅";
                    contentList[1] = "연예인 캐스팅";
                    contentList[2] = "연습생 캐스팅";
                    break;
                case BROADCAST:
                    title = "방송";
                    contentNum = 9;
                    listIndicator = 0;
                    textSize = 200;
                    contentButton = new Rect[contentNum];
                    contentList = new String[contentNum];
                    contentOrigin = new int[contentNum];
                    broadcastList = Broadcast.loadBroadcastList(context);
                    for (int i = 0; i < contentNum; i++) {
                        contentButton[i] = new Rect();
                        contentList[i] = new String();
                    }
                    contentList[0] = "이름   레벨   경험치   시청자";
                    for (int i = 0; i < broadcastList.length; i++) {
                        contentList[i + 1] = broadcastList[i].name + " " + broadcastList[i].level + " " + broadcastList[i].experience + " " + broadcastList[i].viewer;
                        contentOrigin[i + 1] = i;
                    }

                    break;
                case MUSIC:
                    title = "음악";
                    contentNum = 4;
                    textSize = 200;
                    contentButton = new Rect[contentNum];
                    contentList = new String[contentNum];
                    for (int i = 0; i < contentNum; i++) {
                        contentButton[i] = new Rect();
                    }
                    contentList[0] = "트레이닝";
                    contentList[1] = "곡 준비";
                    contentList[2] = "출시";
                    contentList[3] = "저장된 곡 관리";
                    break;
                case REST:
                    title = "휴식";
                    textSize = 200;

                    break;
                case MANAGE:
                    title = "관리";
                    textSize = 200;
                    contentNum = 9;
                    listIndicator = 0;
                    contentButton = new Rect[contentNum];
                    contentList = new String[contentNum];
                    contentOrigin = new int[contentNum];
                    workerList = Worker.loadWorkerList(context);
                    for (int i = 0; i < contentNum; i++) {
                        contentButton[i] = new Rect();
                        contentList[i] = new String();
                    }
                    contentList[0] = "이름   직업      성별   월급";
                    for (int i = 0; i < workerList.length; i++) {
                        if (workerList[i].inOurCompany) {
                            String jobName = new String();
                            switch (workerList[i].job) {
                                case Worker.MANAGER:
                                    jobName = "매니저";
                                    break;
                                case Worker.COMPOSER:
                                    jobName = "작곡가";
                                    break;
                                case Worker.SONGWRITER:
                                    jobName = "작사가";
                                    break;
                                case Worker.TRAINER:
                                    jobName = "트레이너";
                                    break;
                                case Worker.MARKETER:
                                    jobName = "마케터";
                                    break;
                                case Worker.ENTERTAINER:
                                    jobName = "가수";
                                    break;
                                case Worker.TRAINEE:
                                    jobName = "연습생";
                                    break;
                            }
                            contentList[indicator] = workerList[i].name + "  " + jobName + "     " + ((workerList[i].gender) ? "M" : "F") + "    " + workerList[i].salary;
                            contentOrigin[indicator] = i;
                            indicator++;
                        }
                    }

                    break;
                case PROCEED:
                    title = "진행하시겠습니까?";
                    textSize = 900;
                    contentNum = 2;
                    contentButton = new Rect[contentNum];
                    contentList = new String[contentNum];
                    contentButton[0] = new Rect();
                    contentButton[1] = new Rect();
                    contentList[0] = "예";
                    contentList[1] = "아니오";
                    break;

                case STCASTING:
                    title = "스태프 캐스팅";
                    textSize = 650;
                    contentNum = 9;
                    listIndicator = 0;
                    contentButton = new Rect[contentNum];
                    contentList = new String[contentNum];
                    contentOrigin = new int[contentNum];
                    workerList = Worker.loadWorkerList(context);
                    for (int i = 0; i < contentNum; i++) {
                        contentButton[i] = new Rect();
                        contentList[i] = new String();
                    }
                    contentList[0] = "이름   직업      월급   능력치";
                    for (int i = 0; i < workerList.length; i++) {
                        if (!workerList[i].inOurCompany) {
                            if (workerList[i].job != Worker.ENTERTAINER && workerList[i].job != Worker.TRAINEE) {
                                String jobName = new String();
                                switch (workerList[i].job) {
                                    case Worker.MANAGER:
                                        jobName = "매니저";
                                        break;
                                    case Worker.COMPOSER:
                                        jobName = "작곡가";
                                        break;
                                    case Worker.SONGWRITER:
                                        jobName = "작사가";
                                        break;
                                    case Worker.TRAINER:
                                        jobName = "트레이너";
                                        break;
                                    case Worker.MARKETER:
                                        jobName = "마케터";
                                        break;
                                }
                                contentList[indicator] = workerList[i].name + " " + jobName + " " + workerList[i].salary + "    " + workerList[i].uniqueStat;
                                contentOrigin[indicator] = i;
                                indicator++;
                            }
                        }

                    }
                    listIndicator = indicator;
                    break;
                case ENCASTING:
                    title = "연예인 캐스팅";
                    textSize = 650;
                    contentNum = 9;
                    listIndicator = 0;
                    contentButton = new Rect[contentNum];
                    contentList = new String[contentNum];
                    contentOrigin = new int[contentNum];
                    workerList = Worker.loadWorkerList(context);
                    for (int i = 0; i < contentNum; i++) {
                        contentButton[i] = new Rect();
                        contentList[i] = new String();
                    }
                    contentList[0] = "이름   월급   가창력   팬";
                    for (int i = 0; i < workerList.length; i++) {
                        if (!workerList[i].inOurCompany) {
                            if (workerList[i].job == Worker.ENTERTAINER) {
                                contentList[indicator] = workerList[i].name + "   " + workerList[i].salary + "   " + workerList[i].sing + "    " + workerList[i].fan;
                                contentOrigin[indicator] = i;
                                indicator++;
                            }
                        }

                    }
                    listIndicator = indicator;
                    break;
                case TRCASTING:
                    title = "연습생 캐스팅";
                    textSize = 650;
                    contentNum = 9;
                    listIndicator = 0;
                    contentButton = new Rect[contentNum];
                    contentList = new String[contentNum];
                    contentOrigin = new int[contentNum];
                    workerList = Worker.loadWorkerList(context);
                    for (int i = 0; i < contentNum; i++) {
                        contentButton[i] = new Rect();
                        contentList[i] = new String();
                    }
                    contentList[0] = "이름   비용   가창력   매력";
                    for (int i = 0; i < workerList.length; i++) {
                        if (!workerList[i].inOurCompany) {
                            if (workerList[i].job == Worker.TRAINEE) {
                                contentList[indicator] = workerList[i].name + "   " + workerList[i].salary + "   " + workerList[i].sing + "    " + workerList[i].attraction;
                                contentOrigin[indicator] = i;
                                indicator++;
                            }
                        }

                    }
                    listIndicator = indicator;
                    break;

                case BRAPPEAR:
                    title = "출연자 선택";
                    textSize = 550;

                    programNo = intent.getIntExtra("number", -1);
                    if (programNo == -1)
                        finish();
                    contentNum = 9;
                    listIndicator = 0;
                    contentButton = new Rect[contentNum];
                    contentList = new String[contentNum];
                    contentOrigin = new int[contentNum];
                    broadcastList = Broadcast.loadBroadcastList(context);
                    workerList = Worker.loadWorkerList(context);
                    for (int i = 0; i < contentNum; i++) {
                        contentButton[i] = new Rect();
                        contentList[i] = new String();
                    }
                    contentList[0] = "이름   성별   매력   팬";
                    for (int i = 0; i < workerList.length; i++) {
                        if (workerList[i].inOurCompany) {
                            if (workerList[i].job == Worker.ENTERTAINER) {
                                contentList[indicator] = workerList[i].name + "   " + ((workerList[i].gender) ? "M" : "F") + "   " + workerList[i].attraction + "    " + workerList[i].fan;
                                contentOrigin[indicator] = i;
                                indicator++;
                            }
                        }

                    }
                    listIndicator = indicator;
                    break;

                case TRMUSIC:
                    title = "트레이닝";
                    textSize = 400;

                    break;
                case PREMUSIC:
                    title = "곡 준비";
                    contentNum = 4;
                    textSize = 350;
                    contentButton = new Rect[contentNum];
                    contentList = new String[contentNum];
                    for (int i = 0; i < contentNum; i++) {
                        contentButton[i] = new Rect();
                    }
                    contentList[0] = "작곡";
                    contentList[1] = "작사";
                    contentList[2] = "편곡";
                    contentList[3] = "녹음";
                    break;
                case REMUSIC:
                    title = "출시";
                    textSize = 200;
                    contentNum = 2;
                    contentButton = new Rect[contentNum];
                    contentList = new String[contentNum];
                    for (int i = 0; i < contentNum; i++) {
                        contentButton[i] = new Rect();
                    }
                    contentList[0] = "디지털 싱글";
                    contentList[1] = "앨범";
                    break;
                case MAMUSIC:
                    title = "저장된 곡 관리";
                    textSize = 700;
                    break;

                case SINGLE:
                    title = "디지털 싱글";
                    break;
                case ALBUM:
                    break;

                case COMUSIC:
                    title = "작곡";
                    textSize = 200;
                    break;
                case WRMUSIC:
                    title = "작사";
                    textSize = 200;
                    break;
                case ARMUSIC:
                    title = "편곡";
                    textSize = 200;
                    break;
                case RECMUSIC:
                    title = "녹음";
                    textSize = 200;
                    break;

                case COMPOSE:
                    title = "작곡";
                    textSize = 200;
                    break;
                case WRITE:
                    title = "작사";
                    textSize = 200;
                    break;
                case ARRANGE:
                    title = "편곡";
                    textSize = 200;
                    break;
                case RECORD:
                    title = "녹음";
                    textSize = 200;
                    break;
            }

        }

        protected void onDraw(Canvas canvas) {

            paint.setColor(Color.WHITE);
            canvas.drawRect(0, TOP - HEIGHT, 1440, TOP, paint);
            paint.setColor(Color.BLACK);
            ListActivity.setTextSizeForWidth(paint, textSize, title);
            canvas.drawText(title, 75, TOP - 60, paint);

            if (contentNum > 0) {
                int tempHeight;
                if (listTypeCheck(intentNum)) {
                    tempHeight = 60;
                } else {
                    tempHeight = 80;
                }

                for (int i = 0; i < contentNum; i++) {
                    paint.setColor(Color.WHITE);
                    canvas.drawRect(0, TOP + i * HEIGHT, 1440, TOP + HEIGHT + i * HEIGHT, paint);
                    paint.setColor(Color.BLACK);
                    canvas.drawRect(0, TOP + i * HEIGHT, 1440, TOP + HEIGHT + i * HEIGHT - 10, paint);
                    paint.setColor(Color.WHITE);
                    ListActivity.setTextSizeForWidth(paint, contentList[i].length() * tempHeight, contentList[i]);
                    canvas.drawText(contentList[i], 100, TOP + HEIGHT + i * HEIGHT - 60, paint);
                    contentButton[i].set(0, TOP + i * HEIGHT, 1440, TOP + HEIGHT + i * HEIGHT);

                }

            }

            if (listTypeCheck(intentNum)) {
                Path left = new Path();
                Path right = new Path();
                Paint paint = new Paint();
                paint.setColor(Color.GRAY);
                left.setFillType(Path.FillType.EVEN_ODD);
                right.setFillType(Path.FillType.EVEN_ODD);
                left.moveTo(620, 2100);
                right.moveTo(820, 2100);
                left.lineTo(620, 2300);
                right.lineTo(820, 2300);
                left.lineTo(448, 2200);
                right.lineTo(992, 2200);
                left.close();
                right.close();
                canvas.drawPath(left, paint);
                canvas.drawPath(right, paint);
            }


        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    float tempX = event.getX();
                    float tempY = event.getY();
                    buttonAction(tempX, tempY);
                    break;
                case MotionEvent.ACTION_UP:

            }
            return true;
        }

        void buttonAction(float x, float y) {
            switch (intentNum) {
                case CASTING:
                case MUSIC:
                case PREMUSIC:
                case REMUSIC:
                    for (int i = 0; i < contentNum; i++) {
                        final int s = 10 * intentNum + i + 1;
                        if (buttonPressed(x, y, contentButton[i])) {
                            Intent intent = new Intent(ListActivity.this, ListActivity.class);
                            intent.putExtra("type", s);

                            startActivity(intent);
                        }
                    }
                    break;
                case MANAGE:
                case STCASTING:
                case ENCASTING:
                case TRCASTING:
                    for (int i = 0; i < contentNum; i++) {
                        if (buttonPressed(x, y, contentButton[i])) {
                            //workerList[contentOrigin[i]].inOurCompany = true;
                            workerList[contentOrigin[i]].inOurCompany = !workerList[contentOrigin[i]].inOurCompany;
                            Worker.saveWorkerList(getContext(), workerList);
                            finish();
                        }
                    }
                    break;
                case BROADCAST:
                    for (int i = 0; i < contentNum; i++) {
                        if (buttonPressed(x, y, contentButton[i])) {
                            Intent intent = new Intent(ListActivity.this, ListActivity.class);
                            intent.putExtra("type", BRAPPEAR);
                            intent.putExtra("number", i - 1);
                            startActivity(intent);
                        }
                    }
                    break;
                case BRAPPEAR:
                    for (int i = 0; i < contentNum; i++) {
                        if (buttonPressed(x, y, contentButton[i])) {

                            ListActivity.this.proceed(BRAPPEAR, broadcastList[programNo].appearing(workerList[i]), totalCost());
                        }
                    }
                    break;
                case PROCEED:
                    for (int i = 0; i < contentNum; i++) {
                        if (buttonPressed(x, y, contentButton[i])) {

                            ListActivity.this.proceed(0, 0, totalCost());
                        }
                    }
                    break;
            }
            /*for (int i = 0; i < contentNum; i++) {
                if (buttonPressed(x, y, contentButton[i])) {
                    buttonClicked[i] = true;
                }
            }*/
        }

        boolean buttonPressed(float x, float y, Rect button) {
            if (button.contains((int) x, (int) y)) {
                return true;
            }
            return false;
        }

        private boolean listTypeCheck(int intentNum) {
            switch (intentNum) {
                case BROADCAST:
                case MANAGE:
                case BRAPPEAR:
                case STCASTING:
                case ENCASTING:
                case TRCASTING:
                    return true;
            }
            return false;
        }

        private int totalCost() {
            int cost = 0;
            workerList = Worker.loadWorkerList(getContext());
            for (int i = 0; i < workerList.length; i++) {
                if (workerList[i].inOurCompany)
                    cost += workerList[i].salary;
            }
            return cost;
        }

    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==0) {
            if(data!=null) {

            }
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
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

    private static void setTextSizeForWidth(Paint paint, float desiredWidth, String text) {
        final float testTextSize = 48f;

        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        float desiredTextSize = testTextSize * desiredWidth / bounds.width();

        paint.setTextSize(desiredTextSize);
    }

    private void proceed(int type, int value, int cost) {
        switch (type) {
            case ListView.BRAPPEAR:
                ((Player) this.getApplication()).levelUpCheck(value);
                break;
        }

        ((Player) this.getApplication()).addDate(cost);

        Intent intent = new Intent(ListActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
