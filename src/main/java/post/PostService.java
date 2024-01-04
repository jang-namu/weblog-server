package post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import post.dto.PostRequest;
import post.dto.PostResponse;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public Boolean savePost(PostRequest postRequest){
        postRepository.save(new Post(postRequest));
        return true;
    }
    public PostResponse getPost(Long postId){

        return new PostResponse();
    }
    public ArrayList<PostResponse> getPost(String url){

        return new ArrayList<>();
    }
}
