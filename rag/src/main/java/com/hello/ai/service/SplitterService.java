package com.hello.ai.service;

import org.springframework.ai.document.Document;

import java.util.List;

public interface SplitterService {

    List<Document> call(String path);

}
