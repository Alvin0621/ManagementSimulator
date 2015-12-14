package com.example.administrator.managementsimulator;

import android.content.Context;
import android.content.res.Resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2015-12-09.
 */
public class Worker {
    String name;

    final static int MANAGER = 2;
    final static int COMPOSER = 3;
    final static int SONGWRITER = 4;
    final static int TRAINER = 5;
    final static int MARKETER = 6;
    final static int ENTERTAINER = 11;
    final static int TRAINEE = 12;

    int job = 0;
    int age = 20;
    int salary = 0;
    boolean gender = true;
    boolean inOurCompany = false;

    int attraction = 0;
    int sing = 0;
    int musicTalent = 0;
    int fan = 0;
    int humor = 0;
    String genre = "ballad";

    int uniqueStat = 0;


    public boolean training(Worker trainer) {
        boolean isTrainee = (job == TRAINEE);
        int bonus = 5 + ((isTrainee) ? 2 : 0);
        if (trainer.job == Worker.TRAINER) {
            return false;
        }

        if (trainer.uniqueStat > sing) {
            sing = sing + (trainer.uniqueStat - sing) / 20 + bonus;
        }

        if (trainer.uniqueStat > musicTalent) {
            musicTalent = musicTalent + (trainer.uniqueStat - musicTalent) / 20 + bonus;
        }

        return true;
    }

    public static void saveWorkerList(Context context, Worker[] workers) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("worker.txt", Context.MODE_PRIVATE);
            for (int i = 0; i < workers.length; i++) {
                String string = workers[i].name + ";" + Integer.toString(workers[i].job) + ";" + Integer.toString(workers[i].age) + ";"
                        + ((workers[i].gender) ? "1" : "0") + ";" + ((workers[i].inOurCompany) ? "1" : "0") + ";" + Integer.toString(workers[i].salary)
                        + ";" + Integer.toString(workers[i].attraction) + ";" + Integer.toString(workers[i].sing) + ";" + Integer.toString(workers[i].musicTalent)
                        + ";" + Integer.toString(workers[i].fan) + ";" + Integer.toString(workers[i].humor) + ";" + workers[i].genre
                        + ";" + Integer.toString(workers[i].uniqueStat) + ";\r\n";
                fileOutputStream.write(string.getBytes());
            }
            fileOutputStream.close();
        } catch (Exception e) {

        }
    }

    public static Worker[] loadWorkerList(Context context) {
        Worker[] workers = null;
        String[] array = null;

        try {
            FileInputStream fileInputStream = context.openFileInput("worker.txt");
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);

            String text = new String(bytes);
            array = text.split("\\r\\n");
            workers = new Worker[array.length];
            fileInputStream.close();

        } catch (Exception e) {
            try {
                Resources res = context.getResources();
                InputStream inputStream = res.openRawResource(R.raw.worker);
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);

                String text = new String(bytes);
                array = text.split("\\r\\n");
                workers = new Worker[array.length];
                inputStream.close();

            } catch (Exception e2) {

            }
        }

        for (int i = 0; i < array.length; i++) {
            String temp[] = array[i].split(";");
            if (temp.length != 13)
                return null;
            workers[i] = new Worker();
            workers[i].name = temp[0];
            workers[i].job = Integer.parseInt(temp[1]);
            workers[i].age = Integer.parseInt(temp[2]);
            workers[i].gender = Integer.parseInt(temp[3]) == 1;
            workers[i].inOurCompany = Integer.parseInt(temp[4]) == 1;
            workers[i].salary = Integer.parseInt(temp[5]);
            if (workers[i].job >= ENTERTAINER) {
                workers[i].attraction = Integer.parseInt(temp[6]);
                workers[i].sing = Integer.parseInt(temp[7]);
                workers[i].musicTalent = Integer.parseInt(temp[8]);
                workers[i].fan = Integer.parseInt(temp[9]);
                workers[i].humor = Integer.parseInt(temp[10]);
                workers[i].genre = temp[11];
            } else {
                workers[i].uniqueStat = Integer.parseInt(temp[12]);
            }
        }

        return workers;
    }

    public static void resetWorkerList(Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.worker);
            FileOutputStream fileOutputStream = context.openFileOutput("worker.txt", Context.MODE_PRIVATE);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            fileOutputStream.write(bytes);
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {

        }
    }

}
