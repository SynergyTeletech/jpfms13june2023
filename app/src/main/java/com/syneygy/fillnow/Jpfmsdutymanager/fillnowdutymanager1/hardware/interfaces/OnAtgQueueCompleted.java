package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces;

public interface OnAtgQueueCompleted {

    void onAtgQueueEmpty(boolean isEmpty, String lastResponse);
    void OnAtgQueueCommandCompleted(int currentCommand, String response);

}
