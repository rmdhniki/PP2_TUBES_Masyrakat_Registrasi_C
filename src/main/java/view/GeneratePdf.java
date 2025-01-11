package view;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.User;
import model.JenisSampah;
import model.Kategori;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GeneratePdf {
    public static void generateUserReport(List<User> users, String filePath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            // Judul laporan
            Paragraph title = new Paragraph("Laporan Data Pengguna", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(6); // 6 kolom
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Header tabel
            table.addCell(new PdfPCell(new Phrase("ID", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Nama", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Email", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Alamat", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Tanggal Lahir", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Foto", headerFont)));


            for (User user : users) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(user.getId()), contentFont)));
                table.addCell(new PdfPCell(new Phrase(user.getName(), contentFont)));
                table.addCell(new PdfPCell(new Phrase(user.getEmail(), contentFont)));
                table.addCell(new PdfPCell(new Phrase(user.getAddress() != null ? user.getAddress() : "", contentFont)));
                table.addCell(new PdfPCell(new Phrase(user.getBirthDate() != null ? user.getBirthDate().toString() : "", contentFont)));
                table.addCell(new PdfPCell(new Phrase(user.getPhotoPath() != null ? user.getPhotoPath() : "", contentFont)));
            }

            document.add(table);
            document.close();
            System.out.println("Laporan PDF berhasil dibuat: " + filePath);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void generateCategoryReport(List<Kategori> categories, String filePath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            Paragraph title = new Paragraph("Laporan Data Kategori", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(3); // 3 kolom
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            table.addCell(new PdfPCell(new Phrase("ID", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Nama Kategori", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Deskripsi", headerFont)));


            for (Kategori category : categories) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(category.getId()), contentFont)));
                table.addCell(new PdfPCell(new Phrase(category.getName(), contentFont)));
                table.addCell(new PdfPCell(new Phrase(category.getDescription(), contentFont)));
            }

            document.add(table);
            document.close();
            System.out.println("Laporan PDF berhasil dibuat: " + filePath);

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void generateItemTypeReport(List<JenisSampah> itemTypes, String filePath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            Paragraph title = new Paragraph("Laporan Data Jenis Item", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4); // 4 kolom
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            table.addCell(new PdfPCell(new Phrase("ID", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Nama Jenis Item", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Kategori", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Deskripsi", headerFont)));


            for (JenisSampah itemType : itemTypes) {
                String categoryName = (itemType.getCategory() != null) ? itemType.getCategory().getName() : "Unknown Category";
                table.addCell(new PdfPCell(new Phrase(String.valueOf(itemType.getId()), contentFont)));
                table.addCell(new PdfPCell(new Phrase(itemType.getName(), contentFont)));
                table.addCell(new PdfPCell(new Phrase(categoryName, contentFont)));
                table.addCell(new PdfPCell(new Phrase(itemType.getDescription(), contentFont)));
            }

            document.add(table);
            document.close();
            System.out.println("Laporan PDF berhasil dibuat: " + filePath);

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
