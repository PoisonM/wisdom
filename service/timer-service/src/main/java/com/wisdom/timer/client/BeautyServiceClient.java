package com.wisdom.timer.client;

import com.wisdom.common.dto.account.PayRecordDTO;
import com.wisdom.common.dto.transaction.BusinessOrderDTO;
import com.wisdom.common.dto.transaction.MonthTransactionRecordDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;
import java.util.List;

@FeignClient("beauty-service")
public interface BeautyServiceClient {

}
