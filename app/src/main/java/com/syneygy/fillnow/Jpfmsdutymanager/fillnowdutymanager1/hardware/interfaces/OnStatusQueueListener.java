package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces;

/**
 * Created by Ved Yadav on 2/5/2019.
 */
public interface OnStatusQueueListener {

    void onStatusQueueQueueEmpty(boolean isEmpty, String lastResponse);
    void OnStatusQueueCommandCompleted(int currentCommand, String response);

}
