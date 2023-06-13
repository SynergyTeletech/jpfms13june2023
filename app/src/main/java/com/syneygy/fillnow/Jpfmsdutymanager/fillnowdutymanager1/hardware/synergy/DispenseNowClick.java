package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.DispenserUnitK;
//import com.syneygy.fillnow.dutymanager.fillnowdutymanager.activity.FreshDispenserUnitK;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.OnAllCommandCompleted;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.CommandQueue;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils.PollStatus;


/**
 * Created by Ved Yadav on 1/31/2019.
 */
public class DispenseNowClick {

    private Context context;

    public DispenseNowClick(Context context) {
        this.context = context;
    }

    public void getPollState(final String response) {

        String op_code = getPollStatusBit(response, 2);
        Log.d("Opcode", String.valueOf(op_code));
        switch (op_code) {
            case "30":
                Log.d("pumpState:", "STATE IDLE");
                break;
            case "31":
                Log.d("pumpState:", "CALL STATUS");

                final String[] callStateCommands = new String[]{
                        Commands.PUMP_STOP.toString()
                };
                new CommandQueue(callStateCommands, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Keep the Nozzle back on the boot and press start dispense to continue")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
//                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((DispenserUnitK) context).dismissProgressBar();

//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).dismissProgressBar();
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
                            ((DispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(callStateCommands[currentCommand]));
//                            ((FreshDispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(callStateCommands[currentCommand]));
                        } catch (Exception e) {
                        }
                    }
                }).doCommandChaining();
                break;
            case "32":
                Log.d("pumpState:", "PRESET READY STATE");
                final String[] presetReady = new String[]
                        {
                                Commands.Cancel_PRESET.toString(),
                        };
                CommandQueue presetReadyQue = new CommandQueue(presetReady, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
                        } catch (Exception e) {
                        }
                    }
                });
                presetReadyQue.doCommandChaining();
                break;
            case "33":
                Log.d("pumpState:", "FUELING STATE");
                final String[] d = new String[]{
                        Commands.PUMP_STOP.toString()
                        , Commands.PUMP_START.toString()
                        , Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString()
                        /*, Commands.STATUS_POLL.toString()*/};
                CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));

//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                        Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {

                        } catch (Exception e) {

                        }
                    }
                });
                data.doCommandChaining();
                break;
            case "34":
                Log.d("pumpState:", "PAYABLE STATE");
                final String[] payableStateCommands = new String[]
                        {
//                                Commands.PUMP_STOP.toString(),
//                                Commands.PUMP_START.toString(),
                                Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString(),
                             /*   Commands.STATUS_POLL.toString()*/
                        };
                new CommandQueue(payableStateCommands, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        if (lastResponse.contains("597f")) {
                            Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();

                            ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                        } else {
                            try {


                                new DispenseNowClick(context).getPollState(response);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
                        } catch (Exception e) {
                        }
                    }
                }).doCommandChaining();
                break;
            case "35":
                Log.d("pumpState:", "SUSPENDED STATE");
                final String[] suspendedCommands = new String[]
                        {
                                Commands.PUMP_STOP.toString(),
                                Commands.PUMP_START.toString(),
                                Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString(),
                               /* Commands.STATUS_POLL.toString()*/};
                new CommandQueue(suspendedCommands, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                        Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
                        } catch (Exception e) {
                        }
                    }
                }).doCommandChaining();
                break;
            case "36":
                Log.d("pumpState:", "STOPPED STATE");

                final String[] stopStateCommands = new String[]
                        {
                                Commands.PUMP_START.toString(),
                                Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString(),
                                /*Commands.STATUS_POLL.toString()*/};
                new CommandQueue(stopStateCommands, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                        ((DispenserUnitK) context).dismissProgressBar();
//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).dismissProgressBar();
                        Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
                            ((DispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(stopStateCommands[currentCommand]));
//                            ((FreshDispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(stopStateCommands[currentCommand]));

                        } catch (Exception e) {

                        }
                    }
                }).doCommandChaining();
                break;
            case "38":
                Log.d("pumpState:", "IN-OPERATIVE STATE");
                Toast.makeText(BrancoApp.getContext(), "Machine is in IN-Operative state,Please Reset Or Power Off", Toast.LENGTH_SHORT).show();
                break;
            case "39":
                Log.d("pumpState:", "AUTHORIZE STATE");
//                ((DispenserUnitK) context).setProgressBarMessage("Setting Pump To Idle State From AUTHORIZE STATE");
//                ((DispenserUnitK) context).showProgressBar();
//                ((FreshDispenserUnitK) context).setProgressBarMessage("Setting Pump To Idle State From AUTHORIZE STATE");
//                ((FreshDispenserUnitK) context).showProgressBar();
//                final String[] authorizeStateCommands = new String[]{Commands.PUMP_STOP.toString(), Commands.PUMP_START.toString(), Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString(), Commands.STATUS_POLL.toString()};
                final String[] authorizeStateCommands = new String[]{Commands.PUMP_STOP.toString()
//                        , Commands.PUMP_START.toString(), Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString()
                        /*, Commands.STATUS_POLL.toString()*/};
                new CommandQueue(authorizeStateCommands, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                        ((DispenserUnitK) context).dismissProgressBar();
//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).dismissProgressBar();

                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
//                            ((DispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(authorizeStateCommands[currentCommand]));
//                            ((DispenserUnitK) context).showProgressBar();
//                            ((FreshDispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(authorizeStateCommands[currentCommand]));
//                            ((FreshDispenserUnitK) context).showProgressBar();
                        } catch (Exception e) {
                            ((DispenserUnitK) context).dismissProgressBar();
//                            ((FreshDispenserUnitK) context).dismissProgressBar();
                        }
                    }
                }).doCommandChaining();
                break;
            case "3d":
                Toast.makeText(context, "SUSPEND STARTED STATE", Toast.LENGTH_SHORT).show();
                break;
            case "3e":
                Toast.makeText(context, "WAIT FOR PRESET STATE", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void getPollState1(final String response) {

        switch (response) {
            case "30":
                Log.d("pumpState:", "STATE IDLE");
                break;
            case "CALL STATUS":
                Log.d("pumpState:", "CALL STATUS");

                final String[] callStateCommands = new String[]{
                        Commands.PUMP_STOP.toString(),
                        Commands.PUMP_START.toString()
                };
                new CommandQueue(callStateCommands, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Keep the Nozzle back on the boot and press start dispense to continue")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {


                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                        ((DispenserUnitK) context).dismissProgressBar();

//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).dismissProgressBar();
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
                            ((DispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(callStateCommands[currentCommand]));
//                            ((FreshDispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(callStateCommands[currentCommand]));
                        } catch (Exception e) {
                        }
                    }
                }).doCommandChaining();
                break;
            case "PRESET READY STATE":
                Log.d("pumpState:", "PRESET READY STATE");
                final String[] presetReady = new String[]
                        {
                                Commands.PUMP_STOP.toString(),
                                Commands.PUMP_START.toString(),
                               /* Commands.STATUS_POLL.toString()*/
                        };
                CommandQueue presetReadyQue = new CommandQueue(presetReady, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
                        } catch (Exception e) {
                        }
                    }
                });
                presetReadyQue.doCommandChaining();
                break;
            case "FUELING STATE":
                Log.d("FUELING STATE:", "FUELING STATE");
                final String[] d = new String[]{
                        Commands.PUMP_STOP.toString()
                        , Commands.PUMP_START.toString()
                        , Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString()
                        /*, Commands.STATUS_POLL.toString()*/};
                CommandQueue data = new CommandQueue(d, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                        Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {

                        } catch (Exception e) {

                        }
                    }
                });
                data.doCommandChaining();
                break;
            case "PAYABLE STATE":
                Log.d("pumpState:", "PAYABLE STATE");
                final String[] payableStateCommands = new String[]
                        {
//                                Commands.PUMP_STOP.toString(),
//                                Commands.PUMP_START.toString(),
                                Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString(),
                               /* Commands.STATUS_POLL.toString()*/
                        };
                new CommandQueue(payableStateCommands, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
                        } catch (Exception e) {
                        }
                    }
                }).doCommandChaining();
                break;
            case "SUSPENDED STATE":
                Log.d("pumpState:", "SUSPENDED STATE");
                final String[] suspendedCommands = new String[]
                        {
                                Commands.PUMP_STOP.toString(),
                                Commands.PUMP_START.toString(),
                                Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString(),
                               /* Commands.STATUS_POLL.toString()*/};
                new CommandQueue(suspendedCommands, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                        Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
                        } catch (Exception e) {
                        }
                    }
                }).doCommandChaining();
                break;
            case "STOPPED STATE":
                Log.d("pumpState:", "STOPPED STATE");

                final String[] stopStateCommands = new String[]
                        {
                                Commands.PUMP_START.toString(),
                                Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString(),
                               // Commands.STATUS_POLL.toString()
                              };
                new CommandQueue(stopStateCommands, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                        ((DispenserUnitK) context).dismissProgressBar();
//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).dismissProgressBar();
                        Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
                            ((DispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(stopStateCommands[currentCommand]));
//                            ((FreshDispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(stopStateCommands[currentCommand]));

                        } catch (Exception e) {

                        }
                    }
                }).doCommandChaining();
                break;
            case "IN-OPERATIVE STATE":
                Log.d("pumpState:", "IN-OPERATIVE STATE");
                Toast.makeText(BrancoApp.getContext(), "Machine is in IN-Operative state,Please Reset Or Power Off", Toast.LENGTH_SHORT).show();
                break;
            case "AUTHORIZE STATE":
                Log.d("pumpState:", "AUTHORIZE STATE");
//                ((DispenserUnitK) context).setProgressBarMessage("Setting Pump To Idle State From AUTHORIZE STATE");
//                ((DispenserUnitK) context).showProgressBar();
//                ((FreshDispenserUnitK) context).setProgressBarMessage("Setting Pump To Idle State From AUTHORIZE STATE");
//                ((FreshDispenserUnitK) context).showProgressBar();
//                final String[] authorizeStateCommands = new String[]{Commands.PUMP_STOP.toString(), Commands.PUMP_START.toString(), Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString(), Commands.STATUS_POLL.toString()};
                final String[] authorizeStateCommands = new String[]{Commands.PUMP_STOP.toString()
//                        , Commands.PUMP_START.toString(), Commands.CLEAR_SALE_NO_TRANSACTION_ID.toString()
                        , Commands.STATUS_POLL.toString()};
                new CommandQueue(authorizeStateCommands, new OnAllCommandCompleted() {
                    @Override
                    public void commandsAllQueueEmpty(boolean isEmpty, String lastResponse) {
                        Toast.makeText(BrancoApp.getContext(), "Pump Set To Idle Successfully", Toast.LENGTH_SHORT).show();
                        ((DispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
                        ((DispenserUnitK) context).dismissProgressBar();
//                        ((FreshDispenserUnitK) context).setStatusMessage(PollStatus.getPollState(response));
//                        ((FreshDispenserUnitK) context).dismissProgressBar();

                    }

                    @Override
                    public void onAllCommandCompleted(int currentCommand, String response) {
                        try {
//                            ((DispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(authorizeStateCommands[currentCommand]));
//                            ((DispenserUnitK) context).showProgressBar();
//                            ((FreshDispenserUnitK) context).setProgressBarMessage(Commands.getEnumByString(authorizeStateCommands[currentCommand]));
//                            ((FreshDispenserUnitK) context).showProgressBar();
                        } catch (Exception e) {
                            ((DispenserUnitK) context).dismissProgressBar();
//                            ((FreshDispenserUnitK) context).dismissProgressBar();
                        }
                    }
                }).doCommandChaining();
                break;
            case "3d":
                Toast.makeText(context, "SUSPEND STARTED STATE", Toast.LENGTH_SHORT).show();
                break;
            case "3e":
                Toast.makeText(context, "WAIT FOR PRESET STATE", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    private static String getPollStatusBit(String hexResponse, Integer index) {
        if (hexResponse.toLowerCase().contains("7f")) {
            try {
                Log.d("responseBit", hexResponse.substring(hexResponse.indexOf("7f") - 3, hexResponse.indexOf("7f")).trim());
                return hexResponse.substring(hexResponse.indexOf("7f") - 3, hexResponse.indexOf("7f")).trim();
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }
}
