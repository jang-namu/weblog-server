package com.bugflix.weblog.tag;

import com.bugflix.weblog.postandtag.PostAndTag;
import com.bugflix.weblog.postandtag.PostAndTagRepository;
import com.bugflix.weblog.tag.dto.TagRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl {
    private final TagRepository tagRepository;
    private final PostAndTagRepository postAndTagRepository;
    public void saveTag(TagRequest tagRequest){
        tagRepository.save(new Tag(tagRequest));
    }

    // tag 반환
    public ArrayList<String> findTags(String url){
        List<PostAndTag> postAndTags = postAndTagRepository.findPostAndTagByPostPageUrl(url);
        ArrayList<String> arrayList = new ArrayList<>();
        for (PostAndTag postAndTag : postAndTags){
            arrayList.add(postAndTag.getTag().getTag());
        }

        return arrayList;
    }
}
