package com.mtshop.admin.brand.export;

import com.mtshop.admin.AbstractExporter;
import com.mtshop.common.entity.Brand;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BrandCSVExporter extends AbstractExporter {
    public void export(List<Brand> listBrands, HttpServletResponse response) throws IOException {
        super.setReponseHeader(response, "text/csv", ".csv", "brands_");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "Tên"};
        String[] fieldMapping = {"id", "name"};

        csvBeanWriter.writeHeader(csvHeader);

        for (Brand brand : listBrands) {
            csvBeanWriter.write(brand, fieldMapping);
        }

        csvBeanWriter.close();
    }
}
