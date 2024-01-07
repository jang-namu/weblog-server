package com.bugflix.weblog.tag;

import com.bugflix.weblog.tag.dto.TagRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String tag;

    public Tag(TagRequest tagRequest){
        tag = tagRequest.getTag();
    }
}
