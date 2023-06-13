package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces;

/**
 * Created by Ved Yadav on 1/15/2019.
 */
public interface RouterResponseListener {

    void OnRouter485QueueConnected();
    void OnRouter485StatusConnected();
    void OnRouter485TxnConnected();

    void OnRouter485QueueAborted();
    void OnRouter485StatusAborted();
    void OnRouter485TxnAborted();


    void OnRouter285Connected();
    void OnRouter285ATGConnected();
    void OnRouter285RfidConnected();


    void OnRouter285Aborted();
    void OnRouter285ATGAborted();
    void OnRouter285RfidAborted();


    void OnRfIdReceived(String rfId);
    void OnATGReceivedLK(String atg);
    void OnATGReceivedLK2(String atg);
}
