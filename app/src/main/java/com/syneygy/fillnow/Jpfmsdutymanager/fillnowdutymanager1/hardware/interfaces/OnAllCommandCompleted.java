package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces;

/**
 * Created by Ved Yadav on 2/5/2019.
 * */

public interface OnAllCommandCompleted {

    void commandsAllQueueEmpty(boolean isEmpty, String lastResponse);
    void onAllCommandCompleted(int currentCommand, String response);

}
