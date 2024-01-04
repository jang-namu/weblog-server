package post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import post.dto.PostRequest;
import post.dto.PostResponse;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class PostController {
    private final PostServiceImpl postServiceImpl;

    /**
     *  Name : savePost
     *  Parameter :
     *   - PostRequest postRequest : Server 에 저장할 Post
     *  Return :
     *   - ResponseEntity<Void> :
     *     - Success : 200 ok
     *     - Failed : error
     *
     *  Explanation :
     *   - post 저장 요청 수신
     *   - 요청 처리 결과 반환
     *
     *   Scenario :
     *    - 1. extension 에서 저장 요청
     *    - 2. weblog page 에서 저장 요청
     * */

    @PostMapping("/post")
    public ResponseEntity<Void> savePost(@RequestParam PostRequest postRequest ){
        return (postServiceImpl.savePost(postRequest)) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Todo: 시나리오에 맞는 다양한 매개변수 사용

    // Todo : RequestHeader 내용 수정 : @RequestHeader({Header Field Name})
    /**
     *  Name : getPost
     *  Parameter :
     *   - Long postId : 확인 요청한 post
     *  Return :
     *   - ResponseEntity<Post> :
     *     - Success : 200 ok && Post
     *     - Failed : error
     *
     *  Explanation :
     *   - 특정 post 에 대한 접근 요청 수신
     *   - 응답 반환
     *
     *  Scenario :
     *   - 1. post 목록에서 post Click
     *   - 2. post_id 로 post 검색
     * */

    @GetMapping("/post")
    public ResponseEntity<PostResponse> getPost(@RequestParam Long postId){

        return new ResponseEntity<>(postServiceImpl.getPost(postId), HttpStatus.OK);
    }

    // Todo : RequestHeader 내용 수정 : @RequestHeader({Header Field Name})

    /**
     *  Name : getPosts
     *  Parameter :
     *   - String url: web page 주소
     *  Return :
     *   - ResponseEntity<ArrayList<Post>> :
     *     - Success : 200 ok && ArrayList<Post>
     *     - Failed : error
     *
     *  Explanation :
     *   - 특정 web page 에 있는 모든 post 에 대한 접근 요청 수신
     *   - 응답 반환
     *
     *  Scenario :
     *   - 1. extension 에서 post 보기 요청
     * */

    @GetMapping("/post")
    public ResponseEntity<ArrayList<PostResponse>> getPost(@RequestParam(name = "url") String url){

        return ResponseEntity.ok(postServiceImpl.getPost(url));
    }


}
