package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;

//Created By Laukendra on 11-11-19

public class CommandCodeNameMatrix {

    String[] CommandCodeArray=new String[43];
    String[] CommandNameArray=new String[43];

    public void initializedCommandCodeAndNameArray(){
        CommandCodeArray[0]="0141537F6C";  CommandNameArray[0]="GENERAL_STATUS_POLL";
        CommandCodeArray[1]="0141437F7C";  CommandNameArray[1]="CHECK_PRESET";
        CommandCodeArray[2]="0141527F6D";  CommandNameArray[2]="READ_TXN";
        CommandCodeArray[3]="0141547F6B";  CommandNameArray[3]="READ_VOL_TOTALIZER";
        CommandCodeArray[4]="01414D7F72";  CommandNameArray[4]="READ_AMOUNT_TOTALIZER";
        CommandCodeArray[5]="0141457F7A";  CommandNameArray[5]="CLEAR_PRESET";
        CommandCodeArray[6]="0141417F7E";  CommandNameArray[6]="AUTHORIZATION";
        CommandCodeArray[7]="0141447F7B";  CommandNameArray[7]="SUSPEND_SALE";
        CommandCodeArray[8]="0141557F6A";  CommandNameArray[8]="RESUME_SALE";
        CommandCodeArray[9]="01414F7F70";  CommandNameArray[9]="PUMP_START";
        CommandCodeArray[10]="01415A7F65";  CommandNameArray[10]="PUMP_STOP";
        CommandCodeArray[11]="0141477F78";  CommandNameArray[11]="GO_LOCAL";
        CommandCodeArray[12]="01415031";    CommandNameArray[12]="SET_PRESET_VOLUME";
        CommandCodeArray[13]="014146303030303030303030303030303030307F79";  CommandNameArray[13]="CLEAR_SALE";
        CommandCodeArray[14]="0141467F79";  CommandNameArray[14]="CLEAR_SALE_NO_TRANSACTION_ID";
        CommandCodeArray[15]="014142303035302E30307F56";  CommandNameArray[15]="SET_RATE";
        CommandCodeArray[16]="014149303030307F76";  CommandNameArray[16]="SET_DENSITY";
        CommandCodeArray[17]="01414C30303030303030307F73";  CommandNameArray[17]="SET_DENSITY_LIMIT";
        CommandCodeArray[18]="0141587F67";  CommandNameArray[18]="PRINT_START";
        CommandCodeArray[19]="0141597F66";  CommandNameArray[19]="PRINT_STOP";
        CommandCodeArray[20]="#*BZ11";  CommandNameArray[20]="BUZZER_ON_285";
        CommandCodeArray[21]="#*BZ10";  CommandNameArray[21]="BUZZER_OFF_285";
        CommandCodeArray[22]="#*SP21";  CommandNameArray[22]="LISTEN_XIBEE_RFID_285";
        CommandCodeArray[23]="#*SP41";  CommandNameArray[23]="BROADCAST_JERRY_CAN_285";
        CommandCodeArray[24]="#*SP31";  CommandNameArray[24]="LISTEN_ATG_1_285";
        CommandCodeArray[25]="#*RL31";  CommandNameArray[25]="OpenManHoleLockandRFID1";
        CommandCodeArray[26]="#*RL30";  CommandNameArray[26]="CLoseManHoleLockandRFID1";
        CommandCodeArray[27]="#*RL41";  CommandNameArray[27]="OpenManHoleLockandRFID2";
        CommandCodeArray[28]="#*RL40";  CommandNameArray[28]="CLoseManHoleLockandRFID2";
        CommandCodeArray[29]="#*RL11";  CommandNameArray[29]="OpenSolenoidValve1";
        CommandCodeArray[30]="#*RL10";  CommandNameArray[30]="CloseSolenoidValve1";
        CommandCodeArray[31]="#*RL21";  CommandNameArray[31]="OpenSolenoidValve2";
        CommandCodeArray[32]="#*RL20";  CommandNameArray[32]="CloseSolenoidValve2";
        CommandCodeArray[33]="#*GI01";  CommandNameArray[33]="CHECK_MANHOLE_CLOSED_RFID1_1";
        CommandCodeArray[34]="#*GI03";  CommandNameArray[34]="CHECK_MANHOLE_CLOSED_RFID1_2";
        CommandCodeArray[35]="#*GI02";  CommandNameArray[35]="CHECK_MANHOLE_CLOSED_RFID2_1";
        CommandCodeArray[36]="#*GI04";  CommandNameArray[36]="CHECK_MANHOLE_CLOSED_RFID2_2";
        CommandCodeArray[37]="#*SP10";  CommandNameArray[37]="LISTEN_SP0_285";
        CommandCodeArray[38]="#*SP11";  CommandNameArray[38]="LISTEN_SP1_285";
        CommandCodeArray[39]="#*SP21";  CommandNameArray[39]="LISTEN_SP2_285";
        CommandCodeArray[40]="#*SP31";  CommandNameArray[40]="LISTEN_SP3_285";
        CommandCodeArray[41]="#*SP41";  CommandNameArray[41]="LISTEN_SP4_285";
        CommandCodeArray[42]="#*SP41";  CommandNameArray[42]="LISTEN_ATG_2_285";
    }


    public String getCommandNameOfCode(String code){
        String returnedCodeName="";
        initializedCommandCodeAndNameArray();
        for (int i=0;i<CommandCodeArray.length;i++){
            if (CommandCodeArray[i]==code){
                returnedCodeName=CommandNameArray[i];
                return returnedCodeName;
            }
        }
        return returnedCodeName;
    }


}
