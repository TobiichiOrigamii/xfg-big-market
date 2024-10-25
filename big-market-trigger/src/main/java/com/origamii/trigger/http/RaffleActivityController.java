package com.origamii.trigger.http;

import com.alibaba.fastjson2.JSON;
import com.origamii.domain.activity.model.entity.ActivityAccountEntity;
import com.origamii.domain.activity.model.entity.UserRaffleOrderEntity;
import com.origamii.domain.activity.service.IRaffleActivityAccountQuotaService;
import com.origamii.domain.activity.service.IRaffleActivityPartakeService;
import com.origamii.domain.activity.service.armory.IActivityArmory;
import com.origamii.domain.award.model.entity.UserAwardRecordEntity;
import com.origamii.domain.award.model.valobj.AwardStateVO;
import com.origamii.domain.award.service.IAwardService;
import com.origamii.domain.rebate.model.entity.BehaviorEntity;
import com.origamii.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.origamii.domain.rebate.model.valobj.BehaviorTypeVO;
import com.origamii.domain.rebate.service.IBehaviorRebateService;
import com.origamii.domain.strategy.model.entity.RaffleAwardEntity;
import com.origamii.domain.strategy.model.entity.RaffleFactorEntity;
import com.origamii.domain.strategy.service.IRaffleStrategy;
import com.origamii.domain.strategy.service.armory.IStrategyArmory;
import com.origamii.trigger.api.IRaffleActivityService;
import com.origamii.trigger.api.dto.ActivityDrawRequestDTO;
import com.origamii.trigger.api.dto.ActivityDrawResponseDTO;
import com.origamii.trigger.api.dto.UserActivityAccountRequestDTO;
import com.origamii.trigger.api.dto.UserActivityAccountResponseDTO;
import com.origamii.types.enums.ResponseCode;
import com.origamii.types.exception.AppException;
import com.origamii.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Origami
 * @description 抽奖活动服务
 * @create 2024-10-15 13:24
 **/
@Slf4j
@RestController
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/activity")
public class RaffleActivityController implements IRaffleActivityService {

    @Autowired
    private IRaffleActivityPartakeService raffleActivityPartakeService;
    @Autowired
    private IRaffleActivityAccountQuotaService raffleActivityAccountQuotaService;
    @Autowired
    private IRaffleStrategy raffleStrategy;
    @Autowired
    private IAwardService awardService;
    @Autowired
    private IActivityArmory activityArmory;
    @Autowired
    private IStrategyArmory strategyArmory;
    @Autowired
    private IBehaviorRebateService behaviorRebateService;

    private final SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 活动装配 - 数据预热 | 把活动配置的对应的 sku 一起装配
     *
     * @param activityId 活动ID
     * @return 装配结果
     * <p>
     * 接口：<a href="http://localhost:8091/api/v1/raffle/activity/armory">/api/v1/raffle/activity/armory</a>
     * 入参：{"activityId":100001,"userId":"origami"}
     * curl --request GET \
     * --url 'http://localhost:8091/api/v1/raffle/activity/armory?activityId=100301'
     */
    @Override
    @GetMapping("armory")
    public Response<Boolean> armory(@RequestParam Long activityId) {

        try {
            log.info("活动装配 数据预热 开始 activityId:{}", activityId);
            // 1.活动装配
            activityArmory.assembleActivitySkuByActivityId(activityId);
            // 2.策略装配
            strategyArmory.assembleLotteryStrategyByActivityId(activityId);
            // 3.数据预热
            Response<Boolean> response = Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(true)
                    .build();
            log.info("活动装配 数据预热 结束 activityId:{}", activityId);
            return response;
        } catch (Exception e) {
            log.error("活动装配 数据预热 失败 activityId:{}", activityId, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 抽奖接口
     *
     * @param request 请求对象
     * @return 抽奖结果
     * <p>
     * 接口：<a href="http://localhost:8091/api/v1/raffle/activity/draw">/api/v1/raffle/activity/draw</a>
     * 入参：{"activityId":100001,"userId":"origami"}
     * <p>
     * curl --request POST \
     * --url http://localhost:8091/api/v1/raffle/activity/draw \
     * --header 'content-type: application/json' \
     * --data '{
     * "userId":"origami",
     * "activityId": 100301
     * }'
     */
    @Override
    @PostMapping("draw")
    public Response<ActivityDrawResponseDTO> draw(@RequestBody ActivityDrawRequestDTO request) {
        try {
            log.info("活动抽奖 user:{} activityId:{}", request.getUserId(), request.getActivityId());
            // 1.参数校验
            if (StringUtils.isBlank(request.getUserId()) || null == request.getActivityId())
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());

            // 2.参与活动 - 创建参与记录订单
            UserRaffleOrderEntity orderEntity = raffleActivityPartakeService.createOrder(request.getUserId(), request.getActivityId());
            log.info("活动抽奖 创建订单 userId:{} activityId:{} orderId:{}",
                    request.getUserId(),
                    request.getActivityId(),
                    orderEntity.getOrderId()
            );

            // 3.抽奖策略 - 执行抽奖
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(RaffleFactorEntity.builder()
                    .userId(request.getUserId())
                    .strategyId(orderEntity.getStrategyId())
                    .endDateTime(orderEntity.getEndDateTime())
                    .build());

            // 4.存放结果 - 写入中奖记录
            UserAwardRecordEntity userAwardRecord = UserAwardRecordEntity.builder()
                    .userId(orderEntity.getUserId())
                    .activityId(orderEntity.getActivityId())
                    .orderId(orderEntity.getOrderId())
                    .awardId(raffleAwardEntity.getAwardId())
                    .strategyId(orderEntity.getStrategyId())
                    .awardTitle(raffleAwardEntity.getAwardTitle())
                    .awardTime(new Date())
                    .awardState(AwardStateVO.create)
                    .awardConfig(raffleAwardEntity.getAwardConfig())
                    .build();
            awardService.saveUserAwardRecord(userAwardRecord);

            // 5.返回结果
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(ActivityDrawResponseDTO.builder()
                            .awardId(raffleAwardEntity.getAwardId())
                            .awardTitle(raffleAwardEntity.getAwardTitle())
                            .awardIndex(raffleAwardEntity.getSort())
                            .build())
                    .build();
        } catch (AppException e) {
            log.error("活动抽奖失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("活动抽奖失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<ActivityDrawResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 活动签到返利
     * <p>
     * 接口：<a href="http://localhost:8091/api/v1/raffle/activity/calendar_sign_rebate">/api/v1/raffle/activity/calendar_sign_rebate</a>
     * 入参：{"userId":"origami"}
     * <p>
     *
     * @param userId 用户ID
     * @return 签到结果
     */
    @Override
    @PostMapping("calendar_sign_rebate")
    public Response<Boolean> calendarSignRebate(String userId) {
        try {
            log.info("活动签到返利开始 userId:{}", userId);
            BehaviorEntity behaviorEntity = BehaviorEntity.builder()
                    .userId(userId)
                    .behaviorTypeVO(BehaviorTypeVO.SIGN)
                    .outBusinessNo(dateFormatDay.format(new Date()))
                    .build();
            List<String> orderIds = behaviorRebateService.createOrder(behaviorEntity);
            log.info("活动签到返利完成 userId:{} orderIds:{}", userId, JSON.toJSONString(orderIds));
            return Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(true)
                    .build();
        } catch (AppException e) {
            log.error("活动签到返利异常 userId:{}", userId, e);
            return Response.<Boolean>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("活动签到返利失败 userId:{}", userId, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 判断是否完成日历签到返利
     *
     * @param userId 用户ID
     * @return 是否完成日历签到返利
     */
    @Override
    @PostMapping("is_calendar_sign_rebate")
    public Response<Boolean> isCalendarSignRebate(String userId) {
        try {
            log.info("查询用户是否完成日历签到返利开始 userId:{}", userId);
            String outBusinessNo = dateFormatDay.format(new Date());
            List<BehaviorRebateOrderEntity> behaviorRebateOrderEntities = behaviorRebateService.queryOrderByOutBusinessNo(userId, outBusinessNo);
            log.info("查询用户是否完成日历签到返利完成 userId:{} orders.size:{}", userId, behaviorRebateOrderEntities.size());
            return Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(!behaviorRebateOrderEntities.isEmpty())
                    .build();
        } catch (Exception e) {
            log.error("查询用户是否完成日历签到返利失败 userId:{}", userId, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 查询用户活动账户信息
     *
     * @param request 查询请求
     * @return 用户活动账户信息
     */
    @Override
    @PostMapping("query_activity_account")
    public Response<UserActivityAccountResponseDTO> queryUserActivityAccount(UserActivityAccountRequestDTO request) {
        try {
            log.info("查询用户活动账户信息开始 userId:{} activityId:{}", request.getUserId(), request.getActivityId());
            if (StringUtils.isBlank(request.getUserId()) || null == request.getActivityId())
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
            ActivityAccountEntity activityAccountEntity = raffleActivityAccountQuotaService.queryActivityAccount(request.getActivityId(), request.getUserId());
            UserActivityAccountResponseDTO userActivityAccountResponseDTO = UserActivityAccountResponseDTO.builder()
                    .totalCount(activityAccountEntity.getTotalCount())
                    .totalCountSurplus(activityAccountEntity.getTotalCountSurplus())
                    .dayCount(activityAccountEntity.getDayCount())
                    .dayCountSurplus(activityAccountEntity.getDayCountSurplus())
                    .monthCount(activityAccountEntity.getMonthCount())
                    .monthCountSurplus(activityAccountEntity.getMonthCountSurplus())
                    .build();
            log.info("查询用户活动账户信息完成 userId:{} activityId:{} DTO:{}", request.getUserId(), request.getActivityId(), JSON.toJSONString(userActivityAccountResponseDTO));
            return Response.<UserActivityAccountResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(userActivityAccountResponseDTO)
                    .build();
        } catch (AppException e) {
            log.error("查询用户活动账户信息失败 userId:{} activityId:{}", request.getUserId(), request.getActivityId(), e);
            return Response.<UserActivityAccountResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }


}
