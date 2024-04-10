package com.bugflix.weblog.tag.service;

import com.bugflix.weblog.tag.domain.Tag;
import com.bugflix.weblog.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl {
    private final TagRepository tagRepository;

    public List<String> findTagsByPostId(Long postId) {
        List<Tag> tags = tagRepository.findTagsByPostPostId(postId);
        return tags.stream().map(Tag::getTagContent).collect(toList());
    }
}
