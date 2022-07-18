package com.mtshop.admin;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstractExporter {
    public void setReponseHeader(HttpServletResponse response, String contentType, String extension, String prefix) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormater.format(new Date());
        String fileName = prefix + timestamp + extension;

        response.setContentType(contentType);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; fileName=" + fileName;
        response.setHeader(headerKey, headerValue);
    }
}
