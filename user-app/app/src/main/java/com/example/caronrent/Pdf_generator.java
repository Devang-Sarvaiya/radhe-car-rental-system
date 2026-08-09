package com.example.caronrent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Pdf_generator extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 1;
    private static final int CREATE_PDF_REQUEST_CODE = 2;

    Button btn;

    private final ActivityResultLauncher<Intent> createPdfLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                Uri uri = data.getData();
                                createPdf(uri);
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_generator);
        btn = findViewById(R.id.button_pdf);
        btn.setOnClickListener(view -> {
            if (checkPermission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    createPdfWithStorageFramework();
                } else {
                    createPdf(null); // Pass appropriate Uri or other parameters
                }
            } else {
                requestPermission();
            }
        });
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")
                    == android.content.pm.PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"},
                    STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    createPdfWithStorageFramework();
                } else {
                    createPdf(null); // Pass appropriate Uri or other parameters
                }
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void createPdfWithStorageFramework() {
        String pdfFileName = "myPDF.pdf";

        // Use Storage Access Framework to let the user choose a directory
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, pdfFileName);

        createPdfLauncher.launch(intent);
    }
    private void createPdf(Uri uri) {
        try {
            OutputStream outputStream = getContentResolver().openOutputStream(uri);
            if (outputStream != null) {
                PdfWriter writer = new PdfWriter(outputStream);
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument);

                DeviceRgb invoiceGreen = new DeviceRgb(51, 204, 51);
                DeviceRgb invoiceGray = new DeviceRgb(220, 220, 220);

                float columnWidth[] = {140, 140, 140, 140};
                Table table1 = new Table(columnWidth);

                Drawable d1 = getDrawable(R.drawable.car);
                Bitmap bitmap1 = ((BitmapDrawable) d1).getBitmap();
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
                byte[] bitmapData1 = stream1.toByteArray();

                ImageData imageData1 = ImageDataFactory.create(bitmapData1);
                Image image1 = new Image(imageData1);
                image1.setWidth(100f);

                table1.addCell(new Cell(3, 1).add(image1).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell(1, 2).add(new Paragraph("Invoice").setFontSize(26f).setFontColor(ColorConstants.BLACK)).setBorder(Border.NO_BORDER));

                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("Invoice No.")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("#234565544")).setBorder(Border.NO_BORDER));

                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("Invoice Date:")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("10-03-23")).setBorder(Border.NO_BORDER));

                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("Account No.")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("22334455")).setBorder(Border.NO_BORDER));

                table1.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

                table1.addCell(new Cell().add(new Paragraph("To")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

                table1.addCell(new Cell().add(new Paragraph("Devang sarvaiya")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("Payment Method :").setBold()).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

                table1.addCell(new Cell().add(new Paragraph("E-601,suman sath")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell(1, 2).add(new Paragraph("RozerPay : payments@sccarrent.com")).setBorder(Border.NO_BORDER));

                table1.addCell(new Cell().add(new Paragraph("SURAT")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table1.addCell(new Cell(1, 2).add(new Paragraph("Card Payment : We accept Visa,Mastercard")).setBorder(Border.NO_BORDER));

                float columnWidth2[] = {62, 162, 112, 112, 112};
                Table table2 = new Table(columnWidth2);

                table2.addCell(new Cell().add(new Paragraph("Car. No.").setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK));
                table2.addCell(new Cell().add(new Paragraph("Car Name :").setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK));
                table2.addCell(new Cell().add(new Paragraph("RATE").setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK));
                table2.addCell(new Cell().add(new Paragraph("DAY").setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK));
                table2.addCell(new Cell().add(new Paragraph("PRICE").setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK));

                table2.addCell(new Cell().add(new Paragraph("1.")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("TOYOTA")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("2000")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("2")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("4000")).setBackgroundColor(invoiceGray));

                table2.addCell(new Cell().add(new Paragraph("2.")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("SKODA")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("5000")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("1")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("5000")).setBackgroundColor(invoiceGray));

                table2.addCell(new Cell().add(new Paragraph("3.")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("AUDI")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("15000")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("3")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("45000")).setBackgroundColor(invoiceGray));

                table2.addCell(new Cell().add(new Paragraph("4.")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("WAGENAR")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("1200")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("10")).setBackgroundColor(invoiceGray));
                table2.addCell(new Cell().add(new Paragraph("12000")).setBackgroundColor(invoiceGray));

                table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table2.addCell(new Cell().add(new Paragraph("Sub-Total ")).setFontColor(ColorConstants.WHITE).setBackgroundColor(ColorConstants.BLACK));
                table2.addCell(new Cell().add(new Paragraph("66000").setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK));

                table2.addCell(new Cell(1, 2).add(new Paragraph("Terms & Conditions: ")).setBorder(Border.NO_BORDER));
                table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table2.addCell(new Cell().add(new Paragraph("GST (12%)").setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK));
                table2.addCell(new Cell().add(new Paragraph("7920").setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK));

                table2.addCell(new Cell(1, 2).add(new Paragraph("Payble amount can not be returned ")).setBorder(Border.NO_BORDER));
                table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table2.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table2.addCell(new Cell().add(new Paragraph("Grand Total").setBold().setFontSize(16).setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK));
                table2.addCell(new Cell().add(new Paragraph("73920").setBold().setFontSize(16).setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK));

                float columnWidth3[] = {50, 250, 260};
                Table table3 = new Table(columnWidth3);

                Drawable d2 = getDrawable(R.drawable.contactus1);
                Bitmap bitmap2 = ((BitmapDrawable) d2).getBitmap();
                ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                byte[] bitmapData2 = stream2.toByteArray();

                ImageData imageData2 = ImageDataFactory.create(bitmapData2);
                Image image2 = new Image(imageData2);
                image2.setHeight(120);

                Drawable d3 = getDrawable(R.drawable.thankyou);
                Bitmap bitmap3 = ((BitmapDrawable) d3).getBitmap();
                ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
                bitmap3.compress(Bitmap.CompressFormat.PNG, 100, stream3);
                byte[] bitmapData3 = stream3.toByteArray();

                ImageData imageData3 = ImageDataFactory.create(bitmapData3);
                Image image3 = new Image(imageData3);
                image3.setHeight(120);
                image3.setHorizontalAlignment(HorizontalAlignment.RIGHT);

                table3.addCell(new Cell(3, 1).add(image2).setBorder(Border.NO_BORDER));
                table3.addCell(new Cell().add(new Paragraph("YOUR_EMAIL@gmail.com\nYOUR_SUPPORT_EMAIL@gmail.com")).setBorder(Border.NO_BORDER));
                table3.addCell(new Cell(3, 2).add(image3).setBorder(Border.NO_BORDER));

                table3.addCell(new Cell().add(new Paragraph("+91-XXXXXXXXXX\n+91-XXXXXXXXXX")).setBorder(Border.NO_BORDER));
                table3.addCell(new Cell().add(new Paragraph("ADDRESS-Moon garden,Vip Circle,\nSurat,India")).setBorder(Border.NO_BORDER));

                document.add(table1);
                document.add(new Paragraph("\n"));
                document.add(table2);
                document.add(new Paragraph("\n\n\n\n\n\n(Authorised Signatory)\n\n\n").setTextAlignment(TextAlignment.RIGHT));
                document.add(table3);

                document.close();
                Toast.makeText(this, "Pdf Created", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
