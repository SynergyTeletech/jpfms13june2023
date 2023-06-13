package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces;

/**
 * Created by Ved Yadav on 2/5/2019.
 */
public interface OnTxnQueueCompleted {

    void onTxnQueueEmpty(boolean isEmpty, String lastResponse);
    void OnTxnQueueCommandCompleted(int currentCommand, String response);

}
