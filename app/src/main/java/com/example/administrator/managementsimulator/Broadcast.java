package com.example.administrator.managementsimulator;


import android.content.Context;
import android.content.res.Resources;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2015-12-09.
 */
public class Broadcast {
    String name;
    int level;
    int experience;
    int viewer;
    final int EXPVALUE = 30;
    final int EXPAVAILABLE = 20;

    public Broadcast() {

    }

    public Broadcast(String program, int lev, int fame) {
        name = new String(program);
        level = lev;
        experience = Math.round((float) Math.random() * EXPAVAILABLE + level * EXPVALUE);
        viewer = level * fame * 1000;
    }

    public Broadcast(Broadcast program) {
        this.name = program.name;
        this.level = program.level;
        this.experience = program.experience;
        this.viewer = program.viewer;
    }

    public int appearing(Worker cast) {
        cast.fan = cast.fan + (int) (viewer / (1000 - cast.attraction - 1.2 * cast.humor));
        return experience;
    }

    public static void saveBroadcastList(Context context, Broadcast[] broadcasts) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("broadcast.txt", Context.MODE_PRIVATE);
            for (int i = 0; i < broadcasts.length; i++) {
                String string = broadcasts[i].name + ";" + Integer.toString(broadcasts[i].level) + ";" + Integer.toString(broadcasts[i].experience) + ";" + Integer.toString(broadcasts[i].viewer) + ";\r\n";
                fileOutputStream.write(string.getBytes());
            }
            fileOutputStream.close();
        } catch (Exception e) {

        }
    }

    public static Broadcast[] loadBroadcastList(Context context) {
        Broadcast[] broadcasts = null;
        String[] array = null;

        try {
            FileInputStream fileInputStream = context.openFileInput("broadcast.txt");//("broadcast.txt");
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);

            String text = new String(bytes);
            array = text.split("\\r\\n");
            broadcasts = new Broadcast[array.length];
            fileInputStream.close();

        } catch (Exception e) {
            try {
                Resources res = context.getResources();
                InputStream inputStream = res.openRawResource(R.raw.broadcast);
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);

                String text = new String(bytes);
                array = text.split("\\r\\n");
                broadcasts = new Broadcast[array.length];
                inputStream.close();

            } catch (Exception e2) {

            }
        }

        for (int i = 0; i < array.length; i++) {
            String temp[] = array[i].split(";");
            if (temp.length != 4)
                return null;
            broadcasts[i] = new Broadcast();
            broadcasts[i].name = temp[0];
            broadcasts[i].level = Integer.parseInt(temp[1]);
            broadcasts[i].experience = Integer.parseInt(temp[2]);
            broadcasts[i].viewer = Integer.parseInt(temp[3]);
        }

        return broadcasts;
    }

    public static void resetBroadcastList(Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.broadcast);
            FileOutputStream fileOutputStream = context.openFileOutput("broadcast.txt", Context.MODE_PRIVATE);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            fileOutputStream.write(bytes);
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {

        }
    }
}