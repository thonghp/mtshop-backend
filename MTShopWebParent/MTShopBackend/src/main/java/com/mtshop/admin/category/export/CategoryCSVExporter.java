package com.mtshop.admin.category.export;

import com.mtshop.admin.AbstractExporter;
import com.mtshop.common.entity.Category;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryCSVExporter extends AbstractExporter {
    public void export(List<Category> listCategories, HttpServletResponse response) throws IOException {
        super.setReponseHeader(response, "text/csv", ".csv", "categories_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "Tên"};
        String[] fieldMapping = {"id", "name"};

        csvBeanWriter.writeHeader(csvHeader);

        for (Category category : listCategories) {
            category.setName(category.getName().replace("--", "  "));
            csvBeanWriter.write(category, fieldMapping);
        }

        csvBeanWriter.close();
    }
}
