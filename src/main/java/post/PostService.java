package post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import post.dto.PostRequest;
import post.dto.PostResponse;

import java.util.ArrayList;
import java.util.List;

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
        Post post = postRepository.findById(postId).orElseThrow();

        return new PostResponse(post);
    }

    public ArrayList<PostResponse> getPost(String url){
        List<Post> result = postRepository.findByPageUrl(url);
        ArrayList<PostResponse> result_ = new ArrayList<>();

        for (int i =0;i<result.size();i++){
            PostResponse postResponse = new PostResponse(result.get(i));
            result_.add(postResponse);
        }
        return result_;
    }

}
