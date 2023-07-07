package com.duke.mfa.poc.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Kazi
 */
@Component
public class FileDownloadManager {
    /**
     * 
     * @param response Response object should be passed from controller as it writes
     *                 the byte buffers to response object directly.
     * @param file     The file to be downloaded
     * @param preview  - <code>true</code> will make the browser open known file
     *                 types in a new tab, <code>false</code> will download the file
     *                 directly
     * @throws IOException
     */
    public void downloadFile(HttpServletResponse response, File file, boolean preview) throws IOException {
        downloadFile(response, file, preview, null);
    }

    /**
     * 
     * @param response Response object should be passed from controller as it writes
     *                 the byte buffers to response object directly.
     * @param file     The file to be downloaded
     * @param preview  - <code>true</code> will make the browser open known file
     *                 types in a new tab, <code>false</code> will download the file
     *                 directly
     * 
     * @param fileName - Custom name of the file to be download.
     * @throws IOException
     */
    public void downloadFile(HttpServletResponse response, File file, boolean preview, String fileName)
            throws IOException {

        InputStream is = new FileInputStream(file);
        String finalFileName = fileName != null && !fileName.isEmpty() ? fileName : file.getName();
        if (preview) {
            response.setHeader("Content-Disposition", "inline;filename=" + finalFileName);
        } else {
            response.setHeader("Content-Disposition", "attachment;filename=" + finalFileName);
        }

        String contentType = Files.probeContentType(file.toPath());
        response.setContentType(contentType);
        response.setContentLengthLong(file.length());
        int read = 0;
        byte[] bytes = new byte[100000];
        OutputStream os = response.getOutputStream();

        while ((read = is.read(bytes)) != -1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        os.close();
        is.close();

    }
}
