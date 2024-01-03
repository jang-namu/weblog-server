package post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import post.dto.PostRequest;
import post.dto.PostResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class PostController {
    /**
     *  함수명 : savePost
     *  매개변수 : PostRequest postRequest
     *  리턴형 : void
     *  함수 설명:
     *   - Front-End 에서 전송한 Post 정보를 수신하여 Server DB 에 저장
     * */
    @PostMapping("/post")
    public void savePost(@RequestParam PostRequest postRequest ){

    }
}
