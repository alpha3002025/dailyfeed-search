package click.dailyfeed.search.follow.service;

import click.dailyfeed.code.domain.member.follow.dto.FollowDto;
import click.dailyfeed.search.follow.mapper.FollowMapper;
import click.dailyfeed.search.follow.projection.FollowingCountProjection;
import click.dailyfeed.search.follow.repository.FollowingMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowingMongoRepository followingMongoRepository;
    private final FollowMapper followMapper;

    public List<FollowDto.FollowCountStatistics> findFollowingCountsIn(FollowDto.FollowCountQueryBulkRequest request) {
        List<FollowingCountProjection> result = followingMongoRepository.countFollowersByToIdList(request.getToMemberIds());
        return result.stream().map(projection -> followMapper.toStatistics(projection)).toList();
    }
}
