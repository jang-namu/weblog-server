package com.bugflix.weblog.page.service;

import com.bugflix.weblog.page.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageServiceImpl {
    private final PageRepository pageRepository;
    
}
