package click.dailyfeed.search.follow.api;


import click.dailyfeed.code.domain.member.follow.dto.FollowDto;
import click.dailyfeed.code.global.web.code.ResponseSuccessCode;
import click.dailyfeed.code.global.web.response.DailyfeedServerResponse;
import click.dailyfeed.search.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/search/follow")
@RestController
public class FollowController {
    private final FollowService followService;

    @PostMapping("/following/query/count/in")
    public DailyfeedServerResponse<List<FollowDto.FollowCountStatistics>> getCommentsLikeQueryInCount(
            @RequestBody FollowDto.FollowCountQueryBulkRequest request
            ){
        List<FollowDto.FollowCountStatistics> result = followService.findFollowingCountsIn(request);
        return DailyfeedServerResponse.<List<FollowDto.FollowCountStatistics>>builder()
                .data(result)
                .result(ResponseSuccessCode.SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }
}
