package com.tianbo.smartcity.modules.base.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.base.entity.Message;

import java.util.List;

/**
 * 消息内容数据处理层
 *
 * @author Exrick
 */
public interface MessageDao extends SmartCityBaseDao<Message, String> {

    /**
     * 通过创建发送标识获取
     *
     * @param createSend
     * @return
     */
    List<Message> findByCreateSend(Boolean createSend);
}