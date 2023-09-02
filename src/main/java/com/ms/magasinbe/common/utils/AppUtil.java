package com.ms.magasinbe.common.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class AppUtil {

    public static final Random RANDOM = new SecureRandom();

    public static String generateSalt() {
        byte[] salt = new byte[Constant.SALT_LENGTH];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String getFileExtension(MultipartFile file) {
        String extensionType = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extensionType != null) {
            extensionType = extensionType.toLowerCase();
        }
        return extensionType;
    }

    public static String getFileName(MultipartFile file) {
        String fileName = FilenameUtils.getName(file.getOriginalFilename());
        if (fileName != null) {
            fileName = fileName.toLowerCase();
        }
        return fileName;
    }
}
