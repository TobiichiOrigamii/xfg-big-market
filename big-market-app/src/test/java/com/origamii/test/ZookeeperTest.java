package com.origamii.test;


import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeeperTest {

    @Autowired
    private CuratorFramework curatorFramework;

    String path = "/big-market/docs/config/downgradeSwitch/test/a";
    String data = "0";

    @Test
    public void createNode() throws Exception {
        if (null == curatorFramework.checkExists().forPath(path)) {
            curatorFramework.create().creatingParentContainersIfNeeded().forPath(path);
        }
    }

    @Test
    public void setDate() throws Exception {
        curatorFramework.setData().forPath(path, data.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void getData() throws Exception {
        byte[] data = curatorFramework.getData().forPath(path);
        log.info("data: " + new String(data, StandardCharsets.UTF_8));
    }


}
