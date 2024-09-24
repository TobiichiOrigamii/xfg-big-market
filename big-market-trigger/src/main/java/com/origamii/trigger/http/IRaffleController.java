package com.origamii.trigger.http;

import com.alibaba.fastjson.JSON;
import com.origamii.domain.strategy.service.armory.IStrategyArmory;
import com.origamii.trigger.api.IRaffleService;
import com.origamii.trigger.api.dto.RaffleAwardListRequestDTO;
import com.origamii.trigger.api.dto.RaffleAwardListResponseDTO;
import com.origamii.trigger.api.dto.RaffleRequestDTO;
import com.origamii.trigger.api.dto.RaffleResponseDTO;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Origami
 * @description 抽奖服务
 * @create 2024-09-24 10:35
 **/
@Slf4j
@RestController
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle")
public class IRaffleController implements IRaffleService {

    @Autowired
    private IStrategyArmory strategyArmory;

    /**
     * 策略装配接口
     *
     * @param strategyId 策略ID
     * @return 装配结果
     */
    @Override
    @GetMapping("strategy-amory")
    public Response<Boolean> strategyAmory(Long strategyId) {

        try {
            log.info("抽奖策略装配开始 strategyId:{}", strategyId);
            boolean armoryStatus = strategyArmory.assembleLotteryStrategy(strategyId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(armoryStatus)
                    .build();
            log.info("抽奖策略装配完成 strategyId:{} response:{}", strategyId, JSON.toJSONString(response));
            return response;

        } catch (Exception e) {
            log.error("抽奖策略装配失败 strategyId:{}", strategyId);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }


    /**
     * 查询抽奖奖品列表配置
     *
     * @param requestDTO 抽奖奖品列表查询请求参数
     * @return 奖品列表数据
     */
    @Override
    public Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO requestDTO) {
        return null;
    }


    /**
     * 随机抽奖接口
     *
     * @param requestDTO 请求参数
     * @return 抽奖结果
     */
    @Override
    public Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO) {
        return null;
    }
}
