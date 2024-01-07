package com.bugflix.weblog.post;

import com.bugflix.weblog.page.Page;
import com.bugflix.weblog.page.PageRepository;
import com.bugflix.weblog.post.dto.PostRequest;
import com.bugflix.weblog.post.dto.PostResponse;
import com.bugflix.weblog.tag.TagRepository;
import com.bugflix.weblog.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PageRepository pageRepository;

    /**
     *  Name : savePost
     *  Parameter :
     *   - PostRequest postRequest : Frontend 의 저장 요청 Post
     *  Return :
     *   - void
     *
     *  Explanation :
     *   - 1. postRequest 의 url 정보로 Page Table 에 Entity 탐색
     *   - 2. Page Entity 존재하면, page와 post 관계 mapping
     *   - 3. Page Entity 존재하지 않으면, postRequest의 url으로 Page Entity 생성하여 저장 & post와 관계 mapping
     *   - 4. Todo Spring Security Context의 user 정보를 불러와 post와 관계 mapping
     *   - 5. Post Entity 저장
     * */
    public void savePost(PostRequest postRequest){
        String url = postRequest.getUrl();
        Optional<Page> existingPage =  pageRepository.findPageByUrl(url);

        Page page = existingPage.orElseGet(()-> {
            Page newPage = new Page(url);
            return pageRepository.save(newPage);
        });
        // Todo
        User user = new User();
        Post post = new Post(postRequest,user,page);

        postRepository.save(post);
    }
    /**
     *  Name : updatePost
     *  Parameter :
     *   - PostRequest postRequest
     *   - Long postId
     *  Return :
     *   - void
     *
     *  Explanation :
     *   - 1. postId 가 존재하지 않으면 예외처리
     *   - 2. postId 가 존재하면 사용자가 요청한 정보로 update하여 저장
     * */
    public void updatePost(PostRequest postRequest,Long postId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(()->new Exception(""));
        post.updateTitle(post.getTitle());
        post.updateMemo(post.getMemo());
        post.updateContent(post.getContent());

        // Todo image_url 추가

        postRepository.save(post);
    }

    /**
     *  Name : getPost
     *  Parameter :
     *   - Long postId
     *  Return :
     *   - PostResponse
     *
     *  Explanation :
     *   - postId 로 Post Entity 탐색
     *   - Post 존재하지 않으면 예외처리, 존재하면 출력
     * */
    public PostResponse getPost(Long postId) throws Exception{
        Post post = postRepository.findById(postId).orElseThrow(()->new Exception(""));

        return new PostResponse(post);
    }

    /**
     *  Name : getPost
     *  Parameter :
     *   - Long postId
     *  Return :
     *   - PostResponse
     *
     *  Explanation :
     *   - postId 로 Post Entity 탐색
     *   - Post 존재하지 않으면 예외처리, 존재하면 출력
     * */
    public ArrayList<PostResponse> getPosts(String url) throws Exception {

        List<Post> resultList = postRepository.findByPageUrl(url);
        ArrayList<PostResponse> resultArrayList = new ArrayList<>();

        for (Post post : resultList) {
            PostResponse postResponse = new PostResponse(post);
            resultArrayList.add(postResponse);
        }

        return resultArrayList;
    }

}
