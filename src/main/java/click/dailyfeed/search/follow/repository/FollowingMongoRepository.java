package click.dailyfeed.search.follow.repository;

import click.dailyfeed.search.follow.document.FollowingDocument;
import click.dailyfeed.search.follow.projection.FollowingCountProjection;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Set;

public interface FollowingMongoRepository extends MongoRepository<FollowingDocument, ObjectId> {

    // 특정 사용자(fromId)가 팔로우하는 전체 수 카운트
    @Query(value = "{ 'fromId': ?0 }", count = true)
    long countByFromId(Long fromId);

    // 특정 사용자(toId)를 팔로우하는 전체 수 카운트 (팔로워 수)
    @Query(value = "{ 'toId': ?0 }", count = true)
    long countByToId(Long toId);

    // 여러 사용자에 대한 팔로워 수를 한 번에 조회
    @Aggregation(pipeline = {
            "{ '$match': { 'toId': { '$in': ?0 } } }",
            "{ '$group': { '_id': '$toId', 'count': { '$sum': 1 } } }",
            "{ '$project': { 'toMemberId': '$_id', 'followingCount': '$count', '_id': 0 } }"
    })
    List<FollowingCountProjection> countFollowersByToIdList(Set<Long> toMemberIds);
}
