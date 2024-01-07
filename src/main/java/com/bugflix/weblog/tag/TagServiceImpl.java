package com.bugflix.weblog.tag;

import com.bugflix.weblog.tag.dto.TagRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl {
    private final TagRepository tagRepository;

    public void saveTag(TagRequest tagRequest){
        tagRepository.save(new Tag(tagRequest));
    }
}
