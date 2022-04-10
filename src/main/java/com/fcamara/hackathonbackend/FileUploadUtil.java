package com.fcamara.hackathonbackend;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String diretorioUpload, String nomeArquivo, MultipartFile multipartFile)
            throws IOException {
        Path uploadPath = Paths.get(diretorioUpload);

        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(nomeArquivo);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("O arquivo de imagem " + nomeArquivo + " não pode ser salvo,", e);
        }
    }
}
