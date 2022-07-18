package com.mtshop.admin.user.export;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mtshop.admin.AbstractExporter;
import com.mtshop.common.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPDFExporter extends AbstractExporter {
    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setReponseHeader(response, "application/pdf", ".pdf", "users_");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph paragraph = new Paragraph("Danh sách User", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{1.2f, 3.5f, 2.7f, 3.0f, 3.0f, 2.0f});

        writeTableHeader(table);
        writeTableData(table, listUsers);

        document.add(table);

        document.close();
    }

    private void writeTableData(PdfPTable table, List<User> listUsers) {
        for (User user : listUsers) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getEmail());
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());
            table.addCell(user.getRoles().toString());
            table.addCell(String.valueOf(user.isEnabled()));
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Tên", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Họ", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Vai trò", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Trạng thái", font));
        table.addCell(cell);
    }
}
