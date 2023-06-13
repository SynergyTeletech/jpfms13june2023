package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware;

/**
 * Created by Ved Yadav on 3/15/2019.
 */
public class CommonUtils {
    public static int findNextOccurance(String source, String findElement, int n) {
        int index = source.indexOf(findElement);
        if (index == -1) return -1;

        for (int i = 1; i < n; i++) {
            index = source.indexOf(findElement, index + 1);
            if (index == -1) return -1;
        }
        return index;
    }
}
