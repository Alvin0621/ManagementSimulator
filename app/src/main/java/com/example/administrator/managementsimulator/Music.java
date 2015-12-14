package com.example.administrator.managementsimulator;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Administrator on 2015-12-09.
 */
public class Music {
    String name;
    String composerName;
    String writerName;
    String singerName;

    int melody = 0;
    int rhythm = 0;
    int harmony = 0;
    int lyrics = 0;
    int combination = 0;
    int impact = 1;
    float averageStat;

    int releaseDate = 101;

    int arrangeCount = 0;
    float arrangeBonus = 1;

    final static int ARRANGETIME = 3;
    final static int ARRANGEUNIT = 5;
    final static int MAXIMPACT = 33;
    final static float DIFFERENCE = 0.3f;
    final static float LYRICSCORRECTION = 2.5f;

    boolean hasLyrics = false;
    boolean isRecorded = false;
    boolean isReleased = false;
    boolean isSingle = true;
    boolean isOwnMusic = false;

    public Music() {

    }

    public Music(Music song) {
        this.name = song.name;
        this.composerName = song.composerName;
        this.writerName = song.writerName;
        this.singerName = song.singerName;
        this.melody = song.melody;
        this.rhythm = song.rhythm;
        this.harmony = song.harmony;
        this.lyrics = song.lyrics;
        this.combination = song.combination;
        this.impact = song.impact;
        this.releaseDate = song.releaseDate;
        this.arrangeCount = song.arrangeCount;
        this.arrangeBonus = song.arrangeBonus;
        this.hasLyrics = song.hasLyrics;
        this.isRecorded = song.isRecorded;
        this.isReleased = song.isReleased;
        this.isSingle = song.isSingle;
        this.isOwnMusic = song.isOwnMusic;
    }


    public Music compose(String string, Worker composer) {
        int element;
        Music song = new Music();
        if (string.isEmpty()) {
            song.name = new String();
        } else {
            name = new String(string);
        }
        composerName = composer.name;
        if (composer.job == Worker.COMPOSER) {
            element = composer.uniqueStat;
        } else if (composer.job <= Worker.ENTERTAINER) {
            element = composer.musicTalent;
        } else {
            return null;
        }

        song.melody = Math.round((float) Math.random() * 2 * DIFFERENCE * element + (1 - DIFFERENCE) * element); //0.7x ~ 1.3x => 0.6x * random + 0.7x (x = element)
        song.rhythm = Math.round((float) Math.random() * 2 * DIFFERENCE * element + (1 - DIFFERENCE) * element);
        song.harmony = Math.round((float) Math.random() * 2 * DIFFERENCE * element + (1 - DIFFERENCE) * element);

        song.impact += Math.round((float) Math.random() * MAXIMPACT);

        return song;
    }

    public void lyricsWriting(Worker writer) {
        int element;
        int lyric;
        float difference;
        if (writer.job == Worker.SONGWRITER) {
            element = writer.uniqueStat;
        } else if (writer.job <= Worker.ENTERTAINER) {
            element = writer.musicTalent;
        } else {
            return;
        }
        lyric = Math.round((float) Math.random() * 2 * DIFFERENCE * element + (1 - DIFFERENCE) * element);
        difference = ((float) melody - lyric) / melody;
        lyrics = (int) (LYRICSCORRECTION * lyric);
        combination = (int) (difference * melody);

        impact += Math.round((float) Math.random() * MAXIMPACT);
        hasLyrics = true;
    }

    public void arrange(Worker arranger) {
        int result;
        int penalty = ARRANGETIME - arrangeCount;
        int bonus = (arranger.uniqueStat - melody) / 10;
        if (arrangeCount >= Music.ARRANGETIME) {
            return;
        }

        if (isRecorded) {
            return;
        }

        result = Math.round((float) Math.random() * (2 * penalty * ARRANGEUNIT + bonus) - (penalty * ARRANGEUNIT + ((bonus >= 0) ? bonus : 0)));
        if (result > ARRANGETIME * ARRANGEUNIT) {
            result = ARRANGETIME * ARRANGEUNIT;
        } else if (result < -ARRANGETIME * ARRANGEUNIT) {
            result = -ARRANGETIME * ARRANGEUNIT;
        }

        arrangeBonus += (float) result / 100;

        arrangeCount++;
    }

    public void recording(Worker singer) {
        float difference = 1;
        if (composerName.equals(singer.name)) {
            isOwnMusic = true;
        }

        difference += (averageStat - singer.sing) / 250;
        melody = (int) (melody * difference);
        rhythm = (int) (rhythm * difference);
        harmony = (int) (harmony * difference);
        lyrics = (int) (lyrics * difference);
        combination = (int) (combination * difference);

        impact += Math.round((float) Math.random() * MAXIMPACT);
        isRecorded = true;
    }

    public int release(Music song, Worker singer) {
        releaseDate++;
        int sales = (int) ((averageStat / 1000) - (releaseDate / (impact + singer.attraction / 100))) * singer.fan;
        return sales;
    }

    public void getAverageStat() {
        averageStat = ((melody + rhythm + harmony) * arrangeBonus + lyrics + combination) / 5;
    }

    public static void saveMusicList(Music[] musics) {

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new File("\\res\\data\\music.txt"));
        } catch (Exception e) {

        }

        for (int i = 0; i < musics.length; i++) {
            String namePart = musics[i].name + ";" + musics[i].composerName + ";" + musics[i].writerName + ";" + musics[i].singerName + ";";
            String statPart = musics[i].melody + ";" + musics[i].rhythm + ";" + musics[i].harmony + ";" + musics[i].lyrics + ";" + musics[i].combination + ";" + musics[i].impact + ";";
            String bonusPart = musics[i].releaseDate + ";" + musics[i].arrangeBonus + ";" + musics[i].arrangeCount + ";";
            String booleanPart[] = new String[5];
            booleanPart[0] = (musics[i].hasLyrics) ? "1;" : "0;";
            booleanPart[0] = (musics[i].isRecorded) ? "1;" : "0;";
            booleanPart[0] = (musics[i].isReleased) ? "1;" : "0;";
            booleanPart[0] = (musics[i].isSingle) ? "1;" : "0;";
            booleanPart[0] = (musics[i].isOwnMusic) ? "1;" : "0;";

            String s = namePart + statPart + bonusPart + booleanPart[0] + booleanPart[1] + booleanPart[2] + booleanPart[3] + booleanPart[4];
            printWriter.println(s);
        }
        printWriter.close();
    }

    public static Music[] loadMusicList() {
        Music[] musics;
        Scanner scanner = null;
        Scanner scanner2 = null;
        int i = 0;
        try {
            scanner = new Scanner(new File("\\res\\data\\music.txt"));
            scanner2 = new Scanner(new File("\\res\\data\\music.txt"));
        } catch (Exception e) {

        }

        if (scanner2 != null) {
            while (scanner2.hasNext()) {
                i++;
            }
        }
        scanner2.close();
        musics = new Music[i];
        if (scanner != null) {
            int j = 0;
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] array;
                Music song = new Music();
                array = s.split(";");
                song.name = array[0];
                song.composerName = array[1];
                song.writerName = array[2];
                song.singerName = array[3];
                song.melody = Integer.parseInt(array[4]);
                song.rhythm = Integer.parseInt(array[5]);
                song.harmony = Integer.parseInt(array[6]);
                song.lyrics = Integer.parseInt(array[7]);
                song.combination = Integer.parseInt(array[8]);
                song.impact = Integer.parseInt(array[9]);
                song.releaseDate = Integer.parseInt(array[10]);
                song.arrangeCount = Integer.parseInt(array[11]);
                song.arrangeBonus = Integer.parseInt(array[12]);
                song.hasLyrics = (Integer.parseInt(array[13]) == 1);
                song.isRecorded = (Integer.parseInt(array[14]) == 1);
                song.isReleased = (Integer.parseInt(array[15]) == 1);
                song.isSingle = (Integer.parseInt(array[16]) == 1);
                song.isOwnMusic = (Integer.parseInt(array[17]) == 1);

                musics[j] = new Music(song);
                j++;
            }
        }

        scanner.close();
        return musics;
    }

}
