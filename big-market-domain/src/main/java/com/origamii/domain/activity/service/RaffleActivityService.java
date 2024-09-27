package com.origamii.domain.activity.service;

import com.origamii.domain.activity.repository.IActivityRepository;
import org.springframework.stereotype.Service;

/**
 * @author Origami
 * @description 抽奖活动服务
 * @create 2024-09-27 15:59
 **/
@Service
public class RaffleActivityService extends AbstractRaffleActivity{

    /**
     * 构造函数
     * @param repository 活动仓库
     */
    public RaffleActivityService(IActivityRepository repository) {
        super(repository);
    }



}
