package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;

import android.text.Editable;
import android.text.TextWatcher;

public class AadharCardFormatWatcher implements TextWatcher {

    private static final char space = ' ';

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {

//        if (s.length() > 0 && (s.length() % 5) == 0) {
//            final char c = s.charAt(s.length() - 1);
//            if (space == c) {
//                s.delete(s.length() - 1, s.length());
//            }
//        }
//        if (s.length() > 0 && (s.length() % 5) == 0) {
//            char c = s.charAt(s.length() - 1);
//            if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
//                s.insert(s.length() - 1, String.valueOf(space));
//            }
//        }

        // Remove all spacing char
        int pos = 0;
        while (true) {
            if (pos >= s.length()) break;
            if (space == s.charAt(pos) && (((pos + 1) % 5) != 0 || pos + 1 == s.length())) {
                s.delete(pos, pos + 1);
            } else {
                pos++;
            }
        }

        // Insert char where needed.
        pos = 4;
        while (true) {
            if (pos >= s.length()) break;
            final char c = s.charAt(pos);
            // Only if its a digit where there should be a space we insert a space
            if ("0123456789".indexOf(c) >= 0) {
                s.insert(pos, "" + space);
            }
            pos += 5;
        }
    }
}