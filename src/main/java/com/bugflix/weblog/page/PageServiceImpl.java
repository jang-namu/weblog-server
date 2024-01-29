package com.bugflix.weblog.page;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageServiceImpl {
    private static PageRepository pageRepository;

    public Page getPage(String url) throws Exception{
        Optional<Page> pages = pageRepository.findByUrl(url);
        return pages.orElseThrow(()->{ log.debug("jox: not found page "); return new Exception("hello");});
    }
}
