package com.example.memory_game.UI;

import com.example.memory_game.Model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

public class DataManager {
    private static String[] flags = new String[]{
            "https://www.carlogos.org/car-logos/tesla-logo-2007-full-640.png",
            "https://www.carlogos.org/logo/Mitsubishi-logo-640x550.jpg",
            "https://www.carlogos.org/car-logos/toyota-logo-2020-europe-640.png",
            "https://www.carlogos.org/car-logos/porsche-logo-2014-full-640.png",
            "https://www.carlogos.org/logo/Maybach-logo-640x353.jpg",
            "https://www.carlogos.org/car-logos/ferrari-logo-2002-640.png",
            "https://www.carlogos.org/car-logos/lamborghini-logo-1998-640.png",
            "https://www.carlogos.org/logo/Rolls-Royce-logo-640x550.jpg"
    };


    public ArrayList<Card> getCards() {
        ArrayList<Card> countries = new ArrayList<>();

        for (int i = 0 ; i < flags.length ; i++){
            Card c = new Card();
            c.setFlag(flags[i]);
            c.setId(UUID.randomUUID());
            c.setFlipped(false);

            countries.add(c);
            countries.add(c);
        }

        Collections.shuffle(countries);

        return countries;
    }
}
