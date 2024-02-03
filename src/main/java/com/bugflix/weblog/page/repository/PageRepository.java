package com.bugflix.weblog.page.repository;

import com.bugflix.weblog.page.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageRepository extends JpaRepository<Page, Long> {
    Optional<Page> findPageByUrl(String url);
}
