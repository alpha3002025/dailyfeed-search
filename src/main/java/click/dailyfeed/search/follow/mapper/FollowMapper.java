package click.dailyfeed.search.follow.mapper;

import click.dailyfeed.code.domain.member.follow.dto.FollowDto;
import click.dailyfeed.search.follow.projection.FollowingCountProjection;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {

    public FollowDto.FollowCountStatistics toStatistics(FollowingCountProjection projection) {
        return FollowDto.FollowCountStatistics.builder()
                .followingCount(projection.getFollowingCount())
                .toMemberId(projection.getToMemberId())
                .build();
    }
}
