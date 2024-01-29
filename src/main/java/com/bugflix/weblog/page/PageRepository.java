package com.bugflix.weblog.page;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageRepository extends JpaRepository<Page, Long> {
    public Optional<Page> findPageByUrl(String url);
}
