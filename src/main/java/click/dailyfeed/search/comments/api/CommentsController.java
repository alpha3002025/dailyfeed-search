package click.dailyfeed.search.comments.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/search/comments")
@RestController
public class CommentsController {

    @GetMapping("/contents")
    public List<String> getComments() {
        return List.of();
    }
}
