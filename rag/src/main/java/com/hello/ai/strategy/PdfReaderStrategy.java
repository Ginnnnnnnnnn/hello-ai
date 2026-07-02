package com.hello.ai.strategy;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class PdfReaderStrategy implements DocumentReaderStrategy {

    @Override
    public boolean supports(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".pdf");
    }

    @Override
    public List<Document> read(File file) {
        Resource resource = new FileSystemResource(file);
        return new PagePdfDocumentReader(resource).get();
    }

}