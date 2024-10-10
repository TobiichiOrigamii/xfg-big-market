package com.origamii.domain.activity.service.partake;

import com.origamii.domain.activity.model.aggreate.CreatePartakeOrderAggregate;
import com.origamii.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Origami
 * @description 抽奖活动参与抽奖类
 * @create 2024-10-10 23:18
 **/
@Service
public class RaffleActivityPartakeService extends AbstractRaffleActivityPartake {

    @Resource
    private IActivityRepository repository;

    private final SimpleDateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM");

    private final SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected CreatePartakeOrderAggregate doFilterAccount(String userId, Long activityId, Date currentDate) {
        return null;
    }


}
