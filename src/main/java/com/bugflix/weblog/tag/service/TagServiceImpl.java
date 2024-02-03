package com.bugflix.weblog.tag.service;

import com.bugflix.weblog.tag.domain.Tag;
import com.bugflix.weblog.tag.dto.TagResponse;
import com.bugflix.weblog.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl {
    private final TagRepository tagRepository;

    public List<TagResponse> findTagsByPostId(Long postId) {
        return tagRepository.findTagsByPostPostId(postId)
                .stream().map(TagResponse::from).toList();
    }
}
