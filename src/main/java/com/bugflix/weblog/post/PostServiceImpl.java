package com.bugflix.weblog.post;

import com.bugflix.weblog.like.LikeRepository;
import com.bugflix.weblog.page.Page;
import com.bugflix.weblog.page.PageRepository;
import com.bugflix.weblog.post.dto.PostPreview;
import com.bugflix.weblog.post.dto.PostRequest;
import com.bugflix.weblog.post.dto.PostResponse;
import com.bugflix.weblog.postandtag.PostAndTag;
import com.bugflix.weblog.postandtag.PostAndTagRepository;
import com.bugflix.weblog.tag.Tag;
import com.bugflix.weblog.tag.TagRepository;
import com.bugflix.weblog.tag.TagServiceImpl;
import com.bugflix.weblog.user.User;
import com.bugflix.weblog.user.UserServiceImpl;
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
    private final PageRepository pageRepository;
    private final PostAndTagRepository postAndTagRepository;
    private final LikeRepository likeRepository;
    private final TagServiceImpl tagService;
    private final UserServiceImpl userService;
    private final TagRepository tagRepository;


    /**
     *  Name : savePost
     *  Parameter :
     *   - PostRequest postRequest : Frontend 의 저장 요청 Post
     *  Return :
     *   - void
     *
     *  Explanation :
     *   - postRequest 의 url 정보로 Page Table 에 Entity 탐색
     *   - (1) 동일한 url을 가지는 Page Entity 존재 여부 확인
     *          - 존재 O : page와 post 관계 mapping
     *          - 존재 X : Page Entity 존재하지 않으면, postRequest의 url으로 Page Entity 생성하여 저장 & post와 관계 mapping
     *   - (2) 동일한 Tag 내용을 가지는 Tag Entity 존재 여부 확인
     *          - 존재 O : tag_id 와 post_id 를 Foreign Key 로 가지는 PostAndTag Entity 저장
     *          - 존재 X : Tag Entity 생성 후 위와 동일한 과정 진행
     *   - Todo Spring Security Context의 user 정보를 불러와 post와 관계 mapping
     *   - Like Table
     *   - Memo-Tag Table
     *   - Tag Table
     * */
    public void savePost(PostRequest postRequest){
        // (1)
        String url = postRequest.getUrl();
        Optional<Page> existingPage =  pageRepository.findPageByUrl(url);
        Page page = existingPage.orElseGet(()-> {
            Page newPage = new Page(url);
            return pageRepository.save(newPage);
        });

        // Todo : JWT 의 User 정보로 교체 + 1,2 번 삭제
        User user = new User("aaa","bbb","jo"); // 1
        userService.saveUser(user); // 2

        Post post = new Post(postRequest,user,page);
        Post savedPost = postRepository.save(post);

        // (2)
        for (String tagContent : postRequest.getTags()){
            Optional<Tag> existingTag = tagRepository.findTagByTagContent(tagContent);
            Tag tag = existingTag.orElseGet(() -> {
                Tag newTag = new Tag(tagContent);
                return tagRepository.save(newTag);
            });

            postAndTagRepository.save(new PostAndTag(savedPost,tag));
        }

        log.debug(String.format("save post, post_id is : %d",savedPost.getId()));
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
    public ArrayList<PostResponse> getPosts(String url) {

        List<Post> resultList = postRepository.findByPageUrl(url);
        ArrayList<PostResponse> resultArrayList = new ArrayList<>();

        for (Post post : resultList) {
            PostResponse postResponse = new PostResponse(post);
            resultArrayList.add(postResponse);
        }

        return resultArrayList;
    }

    /**
     *  Name : getPostPreview
     *  Parameter :
     *   - String url
     *  Return :
     *   - ArrayList<PostPreview>
     *
     *  Explanation :
     *   - 페이지 내의 모든 post 미리 보기 목록 반환
     *
     *  Work Flow :
     *   1. Post와 Page Entity를 join 하여 url 을 통해 Post 검색
     *   2. 반환된 Post 를 PostPreview 객체로 변환
     *   3. 변환된 PostPreview ArrayList 반환
     * */
    public ArrayList<PostPreview> getPostPreview(String url){
        List<Post> postList = postRepository.findByPageUrl(url);
        ArrayList<PostPreview> postPreviews = new ArrayList<>();
        ArrayList<Tag> tags = tagService.findTagsByURL(url);

        Long postId;

        User user = new User();
        for (Post post : postList){
            postId = post.getId();
            PostPreview postPreview = new PostPreview(post);
            postPreview.setTags(tags);
/*            // 내가 좋아요 눌렀는지
            List<Like> likes = likeRepository.findById_PostIdAndId_UserId(postId,user.getId());
            if (likes.isEmpty()) postPreview.setLike(false);

            // post 의 좋아요 개수
            likes = likeRepository.findById_PostId(postId);
            postPreview.setLikeCount((long)likes.size());*/

            postPreviews.add(postPreview);
        }
        return postPreviews;
    }
}
