package com.wisdom.beauty.client;

import com.wisdom.common.dto.system.ResponseDTO;
import com.wisdom.common.dto.system.SuggestionDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("system-service")
public interface FeedBackServiceClient {

    /**
     * 意见反馈
     * @param suggestion
     * @param userId
     * @param type
     * @return
     */
    @RequestMapping(value = "/feedback/feedback",method= RequestMethod.POST)
    ResponseDTO<SuggestionDto> addSuggestion(@RequestParam(name = "suggestion", required = false) String suggestion,
                              @RequestParam(name = "userId") String userId,
                              @RequestParam(name = "type") String type);

}
