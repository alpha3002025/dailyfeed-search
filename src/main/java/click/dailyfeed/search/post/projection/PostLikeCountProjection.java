package click.dailyfeed.search.posts.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeCountProjection {
    private Long postPk;
    private Long likeCount;
}