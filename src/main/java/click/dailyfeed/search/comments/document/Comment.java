package click.dailyfeed.search.comments.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "comments")
public class Comment {
    @Id
    private ObjectId id;

    private Long pk;

    @Field("parent_pk")
    private Long parentPk;

    @Field("post_pk")
    private Long postPk;

    private String content;

    private String author;

    private String path;

    private Integer depth;

    @Field("created_at")
    private LocalDateTime createdAt;

    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Field("is_deleted")
    private Boolean isDeleted;

    @Field("reply_count")
    private Integer replyCount; // 자식 댓글 수

    @Field("total_replies")
    private Integer totalReplies; // 전체 하위 댓글 수
}
