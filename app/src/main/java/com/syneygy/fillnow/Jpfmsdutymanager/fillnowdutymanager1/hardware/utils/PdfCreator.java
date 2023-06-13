package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



/**
 * Created by Ved Yadav on 2/19/2019.
 */
public class PdfCreator {
    private static String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Synergy/FillNow";

    public static void createPDF() {
        File root = new File(rootPath);
        if (!root.exists()) {
            root.mkdirs();
        }


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        String fileName = String.format("%s %s.pdf", "FILL_NOW_RECEIPT", df.format(c));

        File pdfFile = new File(root, fileName);
        try {
            if (!pdfFile.exists()) {
                try {
                    pdfFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Document document = new Document();
// Location to save
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

// Open to write
            document.open();

            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 26.0f;
            BaseFont urName = BaseFont.createFont("assets/OpenSans_Regular.ttf", "UTF-8", BaseFont.EMBEDDED);
            // LINE SEPARATOR

            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

            // Title Order Details...
            // Adding Title....
            Font mOrderDetailsTitleFont = new Font(urName, 36.0f, Font.NORMAL, BaseColor.BLACK);

            // Creating Chunk
            Chunk mOrderDetailsTitleChunk = new Chunk("Transaction Detail", mOrderDetailsTitleFont);
            // Creating Paragraph to add...
            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);

            // Setting Alignment for Heading
            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);

            // Finally Adding that Chunk
            document.add(mOrderDetailsTitleParagraph);


            // Fields of Order Details...
            // Adding Chunks for Title and value
            Font mOrderIdFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
            Chunk mOrderIdtxtChunk = new Chunk("Order No:", mOrderIdFont);
            Chunk mOrderIdChunk = new Chunk("44323900", mOrderIdFont);
            try {
                // get input stream
                InputStream ims = BrancoApp.getContext().getAssets().open("fill_now_logo.jpg");
                Bitmap bmp = BitmapFactory.decodeStream(ims);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());
                document.add(image);
            } catch (IOException ex) {
                return;
            }


            Paragraph mOrderIdParagraph = new Paragraph(mOrderIdChunk);
            document.add(mOrderIdParagraph);

            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
            document.add(new Paragraph(""));

            document.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
