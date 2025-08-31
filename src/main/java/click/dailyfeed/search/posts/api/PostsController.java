package click.dailyfeed.search.posts.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/search/posts")
@RestController
public class PostsController {

    @GetMapping("/contents")
    public List<String> getPosts(){
        return List.of();
    }
}
