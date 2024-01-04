package post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import post.dto.PostRequest;
import post.dto.PostResponse;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class PostController {
    private final PostService postService;
    private final static String tokenHeader = "token";

    /**
     *  함수명 : savePost
     *  매개변수 : PostRequest postRequest
     *  리턴형 : ResponseEntity<Void>
     *  함수 설명:
     *   - Front-End 에서 전송한 Post 정보를 수신하여 Server DB 에 저장
     *   - 저장 성공 : 200 ok
     *   - 저장 실패 : 404 error
     * */
    @PostMapping("/post")
    public ResponseEntity<Void> savePost(@RequestParam PostRequest postRequest ){
        return (postService.savePost(postRequest)) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Todo: 시나리오에 맞는 다양한 매개변수 사용

    /**
     *  함수명 : getPost
     *  매개변수 : String token
     *  리턴형 : ResponseEntity<PostResponse>
     *  함수 설명:
     *   - Front-End 에서 요청한 Post 하나를 전송함.
     * */
    @GetMapping("/post")
    public ResponseEntity<PostResponse> getPost(@RequestHeader()String token){
        // Todo : 수정
        return ResponseEntity.ok().build();
    }

    /**
     *  함수명 : getPosts
     *  매개변수 : String token
     *  리턴형 : ResponseEntity<ArrayList<PostResponse>>
     *  함수 설명:
     *   - Front-End 에서 요청한 Post 목록을 전송함.
     * */
    @GetMapping("/post")
    public ResponseEntity<ArrayList<PostResponse>> getPosts(@RequestHeader()String token){
        // Todo : 수정
        return ResponseEntity.ok().build();
    }


}
