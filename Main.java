package com.company;

import com.google.gson.Gson;

import java.io.*;
import java.net.URL;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;

class Weather{
    int id;
    String name;
    Temp main;

    Weather(){ };

    Weather(int id, String name,Temp main)
    {
        this.id = id;
        this.name = name;
        this.main = main;
    }

    float getTemp(){
        return main.temp;
    }

    String print()
    {
        return name + ": " + String.format("%.1f",main.temp) + "°C";
    }
}

class Temp {
    float temp;

    Temp (float temp) {
        this.temp = temp;
    }
}

public class Main {
    static Weather[] w = new Weather[10];

    public static Weather getTempByCity(String id) {
        Gson gson = new Gson();
        String API_URL = "https://api.openweathermap.org/data/2.5/weather?id="+id+"&appid=6a891d41bf4662604916fb4386224043&units=metric";
        Weather weather = new Weather();
        try {
            URL url = new URL(API_URL);
            InputStream stream = (InputStream) url.getContent();
            InputStreamReader reader = new InputStreamReader(stream);
            weather = gson.fromJson(reader, Weather.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return weather;
    }

    public static void sortByTemp(Weather[] w) {
        Arrays.sort(w, new Comparator<Weather>() {
            public int compare(Weather a, Weather b) {
                if(a.getTemp()>b.getTemp())
                    return -1;
                else if (a.getTemp()<b.getTemp())
                    return 1;
                else
                    return 0;
            }
        });
        for (int i = 0; i < w.length; i++) {
            System.out.println(w[i].print());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("Cities.txt"));
        int k = 0;
        while (sc.hasNextLine()) {
            w[k++] = getTempByCity(sc.nextLine());
        }
        sortByTemp(w);
    }
}
