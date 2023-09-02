package com.ms.magasinbe.common.utils;

import com.ms.magasinbe.common.enums.FileType;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
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

    public static FileType getFileType(String extension) {
        FileType type =  FileType.IMAGE;
        if (Constant.PDF.equals(extension) || Constant.DOCX.equals(extension) || Constant.DOC.equals(extension)) {
            type =  FileType.DOCUMENT;
        }
        return type;
    }

    public static String getFilePath(String path, String fileName, String extension) {
        return path + fileName + "." + extension;
    }
}
