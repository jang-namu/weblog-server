package com.bugflix.weblog.page.service;

import com.bugflix.weblog.page.domain.Page;
import com.bugflix.weblog.page.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageServiceImpl {
    private final PageRepository pageRepository;

    public Page getPage(String url) throws Exception {
        Optional<Page> pages = pageRepository.findPageByUrl(url);
        return pages.orElseThrow(() -> {
            log.debug("jox: not found page ");
            return new Exception("hello");
        });
    }
}
