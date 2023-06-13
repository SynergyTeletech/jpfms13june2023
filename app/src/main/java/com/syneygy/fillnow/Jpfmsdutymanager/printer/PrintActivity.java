package com.syneygy.fillnow.Jpfmsdutymanager.printer;

/**
 * Created by Ved Yadav
 */

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.PrintData;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class PrintActivity extends Activity {
    private String TAG = "Main Activity";
    TextView message;
    Button btnPrint, btnBill;
    PrintData printData;

    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream outputStream;
    private String mFuelStartTime = "", mFuelEndTime = "", mAssetID = "", mDispenseQty = "", mMetreReading = "",
            mAssetOtherReading = "",mAssetOtherReading2 = "", mAtgStartReading = "", mAtgEndReading = "", mVolumeTotalizer = "", mNoOfStartStopEnd = "",
            mDispensIn = "", mRFIDStatus = "", mTransactionType = "", mTerminalID = "", mBatchNo = "", mLattitude = "",
            mLongitude = "", mLocationLatReading1 = "", mLocationLongReading2 = "", mGPSStaus = "", mDCVStatus = "", mFlag = "",
            mTransactionNo = "", mUnitPrice = "", mVehicleID = "", mTransactionID = "", mOrderID = "", mLocationName = "",
            mAmount = "", mDeliveredBy = "",mCustomerName = "", mFranchaiseName = "", mGSTIN = "", mRefuellerNo = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if (getIntent().getExtras() != null) {
                printData = (PrintData) getIntent().getExtras().getSerializable("printdata");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (printData != null) {
            mFuelStartTime = printData.getFueling_start_time();
            mFuelEndTime = printData.getFueling_stop_time();
            mAssetID = printData.getAssets_id();
            mDispenseQty = printData.getDispense_qty();
            mMetreReading = printData.getMeter_reading();
            mAssetOtherReading = printData.getAsset_other_reading();
            mAssetOtherReading2 = printData.getAsset_other_reading2();
            mAtgStartReading = printData.getAtg_tank_start_reading();
            mAtgEndReading = printData.getAtg_tank_end_reading();
            mVolumeTotalizer = printData.getVolume_totalizer();
            mNoOfStartStopEnd = printData.getNo_of_event_start_stop();
            mDispensIn = printData.getDispensed_in();
            mRFIDStatus = printData.getRfid_status();
            mTransactionType = printData.getTransaction_type();
            mTerminalID = printData.getTerminal_id();
            mBatchNo = printData.getBatch_no();
            mLattitude = printData.getLatitude();
            mLongitude = printData.getLongitude();
            mLocationLatReading1 = printData.getLocation_reading_1();
            mLocationLongReading2 = printData.getLocation_reading_2();
            mGPSStaus = printData.getGps_status();
            mDCVStatus = printData.getDcv_status();
            mFlag = printData.getFlag();
            mTransactionNo = printData.getTransaction_no();
            mUnitPrice = printData.getUnit_price();
            mVehicleID = printData.getVehicle_id();
            mTransactionID = printData.getTransaction_id();

            //Added
            mOrderID = printData.getTransaction_id();
            mLocationName = printData.getLocationName();
            mCustomerName = printData.getCustomer_name();
            mAmount = printData.getAmount();
            mDeliveredBy = SharedPref.getLoginResponse().getData().get(0).getName();


        }
//        message = (TextView) findViewById(R.id.txtMessage);
//        message.setText("Synergy Print Demo");
        btnBill = (Button) findViewById(R.id.btnBill);

        btnBill.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                AsyncTaskRunner runner = new AsyncTaskRunner();
//                runner.execute();
                printBill();
            }
        });
    }


    protected void printBill() {
        if (btsocket == null) {
            Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
//            Intent BTIntent = new Intent(getApplicationContext(), DeviceList2.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
        } else {
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = btsocket.getOutputStream();
                byte[] printformat = new byte[]{0x1B, 0x21, 0x03};
                outputStream.write(printformat);

//                printCustom("Synergy",3,1);
//                printPhoto(R.drawable.fill_logo1);
                printPhoto(R.drawable.fill_logo1);

                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("DISPENSE", 1, 1);
//                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);

                printNewLine();
                printCustom("Franchise Name: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getName(), 0, 0);
                printCustom("GSTIN: " + SharedPref.getLoginResponse().getFranchise_data().get(0).getGstNo(), 0, 0);
                printCustom("Refueller No: ", 0, 0);

//                String dateTime[] = getDateTime();
//                printText(leftRightAlign(dateTime[0], dateTime[1]));
//                printText(leftRightAlign("Qty: Name" , "Price"));
                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Transaction Details", 1, 1);
//                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printNewLine();

                try {
//                    printCustom("Date:" + String.valueOf(new SimpleDateFormat("dd-mm-yyyy").format(Calendar.getInstance().getTime())), 0, 0);
                    printCustom("Date: " + String.valueOf(new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime())), 0, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                printCustom("Order ID: " + mOrderID, 0, 0);
                printCustom("Customer Name: " + mCustomerName, 0, 0);
                printCustom("Payment Ref No: ", 0, 0);
                printCustom("Location Name: " + mLocationName, 0, 0);
                printCustom("Latitude: " + mLattitude, 0, 0);
                printCustom("Longitude: " + mLongitude, 0, 0);
                printCustom("GPS Status: " + mGPSStaus, 0, 0);
                printCustom("Terminal ID: " + mTerminalID, 0, 0);
                printCustom("Batch no: " + mBatchNo, 0, 0);

                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printCustom("SYNERGY GREEN DIESEL/DIESEL", 1, 1);
                printCustom(SharedPref.getLoginResponse().getVehicle_data().get(0).getProductName().toUpperCase(), 1, 1);
//                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printNewLine();

//                printCustom("Asset Name/ID:" + printData.getAssets_id(), 0, 0);
                printCustom("Asset Name/ID: " + mAssetID, 0, 0);
                printCustom("RFID Status: " + mRFIDStatus, 0, 0);
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Start Fueling Time: " + mFuelStartTime, 0, 0);
                printCustom("End Fueling Time: " + mFuelEndTime, 0, 0);
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Txn No: " + mTransactionNo, 0, 0);
                printCustom("Quantity (in Lts): " + mDispenseQty, 0, 0);
                printCustom("Rate (in INR/Litre): " + mUnitPrice, 0, 0);
                printCustom("Amount (in INR): " + mAmount, 0, 0);
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Meter Reading: " + mMetreReading, 0, 0);
                printCustom("Asset Other Reading1: " + mAssetOtherReading, 0, 0);
                printCustom("Asset Other Reading2: " + mAssetOtherReading2, 0, 0);

                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);

//                printCustom("<Repeat Asset if delivered to multiple assets, and show Total figs also>", 0, 0);
//                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
//                printCustom("Location Reading 1:" + mLocationLatReading1, 0, 0);
//                printCustom("Location Reading 2:" + mLocationLongReading2, 0, 0);

                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Delivered by:" + mDeliveredBy, 0, 0);

                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);

                printNewLine();
                printCustom("Thank you  !!!", 0, 1);

                printCustom(new String(new char[32]).replace("\0", "="), 0, 1);

                printNewLine();
                printNewLine();
//                printNewLine();
//                printNewLine();
//                printCustom(new String(new char[32]).replace("\0", "."),0,1);


                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    protected void printBillRaw() {
        if (btsocket == null) {
            Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
        } else {
            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            outputStream = opstream;

            //print command
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                outputStream = btsocket.getOutputStream();
                byte[] printformat = new byte[]{0x1B, 0x21, 0x03};
                outputStream.write(printformat);

                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
//                printCustom("Synergy",3,1);
                printPhoto(R.drawable.fill_logo1);
//                printCustomPhoto(R.drawable.fill_logo1);

                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);

                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("DISPENSE", 1, 1);
//                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);

                printNewLine();
                printCustom("Franchise Name:", 0, 0);
                printCustom("GSTIN: ", 0, 0);
                printCustom("Refueller No:", 0, 0);

//                String dateTime[] = getDateTime();
//                printText(leftRightAlign(dateTime[0], dateTime[1]));
//                printText(leftRightAlign("Qty: Name" , "Price"));
                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Transaction Details", 1, 1);
//                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printNewLine();

                printCustom("Date:", 0, 0);
                printCustom("Order ID:", 0, 0);
                printCustom("Payment Ref No:", 0, 0);
                printCustom("Location Name/ID:", 0, 0);
                printCustom("Latitude:", 0, 0);
                printCustom("Longitude:", 0, 0);
                printCustom("GPS Status:", 0, 0);
                printCustom("Terminal ID:", 0, 0);
                printCustom("Batch no:", 0, 0);

                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("SYNERGY GREEN DIESEL/DIESEL", 1, 1);
//                printNewLine();
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printNewLine();

//                printCustom("Asset Name/ID:" + printData.getAssets_id(), 0, 0);
                printCustom("Asset Name/ID:" + "GenSet", 0, 0);
                printCustom("RFID Status:", 0, 0);
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Start Fueling Time:", 0, 0);
                printCustom("End Fueling Time:", 0, 0);
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Txn No:", 0, 0);
                printCustom("Quantity (in Lts):", 0, 0);
                printCustom("Rate (in INR/Litre):", 0, 0);
                printCustom("Amount (in INR):", 0, 0);
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Meter Reading:", 0, 0);
                printCustom("Asset Other Reading:", 0, 0);
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("<Repeat Asset if delivered to multiple assets, and show Total figs also>", 0, 0);
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Location Reading 1:", 0, 0);
                printCustom("Location Reading 2:", 0, 0);
                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);
                printCustom("Delivered by:", 0, 0);

                printCustom(new String(new char[32]).replace("\0", "_"), 0, 1);

                printNewLine();
                printCustom("Thank you  !!!", 0, 1);

                printCustom(new String(new char[32]).replace("\0", "="), 0, 1);

                printNewLine();
                printNewLine();
//                printNewLine();
//                printNewLine();
//                printCustom(new String(new char[32]).replace("\0", "."),0,1);


                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B, 0x21, 0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B, 0x21, 0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B, 0x21, 0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B, 0x21, 0x10}; // 3- bold with large text
        try {
            switch (size) {
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align) {
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //print photo
    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), img);
            if (bmp != null) {
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
//                outputStream.write(PrinterCommands.ESC_HORIZONTAL_CENTERS);
                printText(command);
//                printCustomPhoto(command, 0, 1);
            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    public void printCustomPhoto(int img) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                img);
        PrintPic printPic = PrintPic.getInstance();
        printPic.init(bmp);
        byte[] bitmapdata = printPic.printDraw();

        try {
            outputStream.write(bitmapdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        printNewLine();

//        try {
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
//                    img);
//            if (bmp != null) {
//                byte[] command = Utils.decodeBitmap(bmp);
//                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
////                outputStream.write(PrinterCommands.ESC_HORIZONTAL_CENTERS);
//                printText(command);
////                printCustomPhoto(command, 0, 1);
//            } else {
//                Log.e("Print Photo error", "the file isn't exists");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("PrintTools", "the file isn't exists");
//        }

    }

    //print unicode
    public void printUnicode() {
        try {
            outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
            printText(Utils.UNICODE_TEXT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void resetPrint() {
        try {
            outputStream.write(PrinterCommands.ESC_FONT_COLOR_DEFAULT);
            outputStream.write(PrinterCommands.FS_FONT_ALIGN);
            outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
            outputStream.write(PrinterCommands.ESC_CANCEL_BOLD);
            outputStream.write(PrinterCommands.LF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print text
    private void printText(String msg) {
        try {
            // Print normal text
            outputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String leftRightAlign(String str1, String str2) {
        String ans = str1 + str2;
        if (ans.length() < 31) {
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }


// /*   private String[] getDateTime() {
//        final Calendar c = Calendar.getInstance();
//        String dateTime[] = new String[2];
//        dateTime[0] = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);
//        dateTime[1] = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
//        return dateTime;
//    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (btsocket != null) {
                outputStream.close();
                btsocket.close();
                btsocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            btsocket = DeviceList.getSocket();
            if (btsocket != null) {
//                printText(message.getText().toString());
//                printText("Synergy");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//   /* private class AsyncTaskRunner extends AsyncTask<String, String, String> {
//
//        private String resp;
////        ProgressDialog progressDialog;
//
//        @Override
//        protected String doInBackground(String... params) {
//
////            printPhoto(R.drawable.fillnow_2);
////            publishProgress("Sleeping..."); // Calls onProgressUpdate()
//            try {
//                printBill();
////                printBillRaw();
//            } catch (Exception e) {
//                e.printStackTrace();
//                resp = e.getMessage();
//            }
//            return resp;
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            // execution of result of Long time consuming operation
////            Toast.makeText(PrintActivity.this, "Hello Print", Toast.LENGTH_SHORT).show();
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//    }
//
//    public Bitmap createBitmap(Rect rectImage, int i, int j) {
//
//        Paint p = new Paint();
//        p.setStyle(Paint.Style.FILL_AND_STROKE);
//        p.setAntiAlias(true);
//        p.setFilterBitmap(true);
//        p.setDither(true);
//        p.setColor(Color.WHITE);
//        Bitmap bitmap = Bitmap.createBitmap(rectImage.width() * 2,
//                rectImage.height() * 2, Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(bitmap);
////      c.drawColor(Color.RED);
//        c.drawRect(rectImage.left, rectImage.top, rectImage.right,
//                rectImage.bottom, p);
//        return bitmap;
//
//    }
//
//    public static Bitmap mergeToPin(Bitmap left, Bitmap right) {
//        Bitmap result = Bitmap.createBitmap(left.getWidth(), left.getHeight(), left.getConfig());
//        Canvas canvas = new Canvas(result);
//        int widthleft = left.getWidth();
//        int widthright = right.getWidth();
//        canvas.drawBitmap(left, 0f, 0f, null);
//        canvas.drawBitmap(right, widthleft, 0f, null);
//        return result;
//    }*/
}