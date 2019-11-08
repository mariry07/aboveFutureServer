package com.tianbo.smartcity.modules.base.dao;

import com.tianbo.smartcity.base.SmartCityBaseDao;
import com.tianbo.smartcity.modules.base.entity.MessageSend;

/**
 * 消息发送数据处理层
 *
 * @author Exrick
 */
public interface MessageSendDao extends SmartCityBaseDao<MessageSend, String> {

    /**
     * 通过消息id删除
     *
     * @param messageId
     */
    void deleteByMessageId(String messageId);
}