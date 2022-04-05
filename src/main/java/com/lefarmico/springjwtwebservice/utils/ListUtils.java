package com.lefarmico.springjwtwebservice.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtils {

    public static  <T> List<T> getRandomElementsFromList(List<T> list, int numberOfItems) {
        List<T> randomisedItemsList= new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberOfItems; i++) {
            T randomItem = list.get(random.nextInt(list.size()));
            list.remove(randomItem);
            randomisedItemsList.add(randomItem);
        }
        return  randomisedItemsList;
    }
}
