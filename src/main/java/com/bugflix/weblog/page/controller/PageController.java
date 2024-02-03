package com.bugflix.weblog.page.controller;

import com.bugflix.weblog.page.domain.Page;
import com.bugflix.weblog.page.service.PageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PageController {
    private final PageServiceImpl pageService;

    @GetMapping("/v1/page")
    public Page getPage(@RequestParam(name = "url") String url) throws Exception {

        return pageService.getPage(url);
    }
}
