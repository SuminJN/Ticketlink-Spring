package com.hello.ticketlink.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

public class FileUtil {
    private static final String UPLOAD_DIR = "resources/images";

    public static String uploadFile(MultipartFile file, String oldFileName, HttpServletRequest request) {
        String filename = "";

        if (file != null && !file.isEmpty()) {
            String realPath = request.getServletContext().getRealPath(UPLOAD_DIR);
            File dir = new File(realPath);
            if (!dir.exists()) dir.mkdirs();
            System.out.println(dir.getAbsolutePath());

            filename = file.getOriginalFilename();
            File destFile = new File(realPath, filename);

            if (destFile.exists()) {
                String name = filename.substring(0, filename.lastIndexOf("."));
                String extension = filename.substring(filename.lastIndexOf("."));
                int counter = 1;
                while (destFile.exists()) {
                    filename = name + "_" + counter + extension;
                    destFile = new File(realPath, filename);
                    counter++;
                }
            }

            try {
                file.transferTo(destFile);

                if (oldFileName != null && !oldFileName.isEmpty()) {
                    File oldFile = new File(realPath, oldFileName);
                    if (oldFile.exists()) oldFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("File upload failed: " + e.getMessage());
            }
        } else {
            filename = oldFileName;
        }

        return filename;
    }
}