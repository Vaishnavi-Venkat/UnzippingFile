package com.bijlipay.Unzipping.File.controller;

import com.bijlipay.Unzipping.File.Util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.bijlipay.Unzipping.File.constants.Constants.*;

@RestController
public class UnzippingController {

    public static final Logger logger = LoggerFactory.getLogger(UnzippingController.class);

    private final long milliseconds = 1000L;
    private static final int BUFFER_SIZE = 4096;

    @GetMapping("/unzip")
    public ResponseEntity<?> unzipUtil() throws IOException {

            String fileName = LOCALFILEPATH_AXIS + BIJLIPAY_MIS_XLSX_AXIS + DateUtil.currentDate(ddMM) + ".zip";

            String excelFile = unzip(fileName, LOCALFILEPATH_AXIS);

            return new ResponseEntity<>(HttpStatus.OK);
    }



    public static String unzip(String zipFilePath, String destDirectory) throws IOException {

        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        String filePath = "";
        // iterates over entries in the zip file
        while (entry != null) {
            //filePath = destDirectory + File.separator + entry.getName();
            filePath = destDirectory + entry.getName();
            if (!entry.isDirectory()) {
                logger.info("Entry is a file : ------------- : {}", entry.getName());
                logger.info("File Path : ------------- : {}", filePath);
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                logger.info("Entry is a Directory : ------------- : {}", entry.getName());
                logger.info("File Path : ------------- : {}", filePath);
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        System.gc();
        return filePath;
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
        System.gc();
    }

}
