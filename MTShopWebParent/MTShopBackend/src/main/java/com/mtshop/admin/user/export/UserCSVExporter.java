package com.mtshop.admin.user.export;

import com.mtshop.admin.AbstractExporter;
import com.mtshop.common.entity.User;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserCSVExporter extends AbstractExporter {
    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setReponseHeader(response, "text/csv", ".csv","users_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "Email", "Tên", "Họ", "Vai trò", "Trạng thái"};
        String[] fieldMapping = {"id", "email", "firstName", "lastName", "roles", "enabled"};

        csvBeanWriter.writeHeader(csvHeader);

        for (User user : listUsers) {
            csvBeanWriter.write(user, fieldMapping);
        }

        csvBeanWriter.close();
    }
}
