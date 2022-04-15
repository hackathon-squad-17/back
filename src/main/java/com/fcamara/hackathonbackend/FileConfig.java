package com.fcamara.hackathonbackend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("usuario-fotos", registry);
    }

    private void exposeDirectory(String nomeDiretorio, ResourceHandlerRegistry registry) {
        Path diretorioUpload = Paths.get(nomeDiretorio);
        String caminhoUpload = diretorioUpload.toFile().getAbsolutePath();

        if (nomeDiretorio.startsWith("../"))
            nomeDiretorio = nomeDiretorio.replace("../", "");

        registry.addResourceHandler("/" + nomeDiretorio + "/**").addResourceLocations("file:/" + caminhoUpload + "/");
    }
}
