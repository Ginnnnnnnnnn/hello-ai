package com.hello.ai.service;

import org.springframework.ai.document.Document;

import java.util.List;

public interface ReaderService {

    List<Document> call(String path);

}
