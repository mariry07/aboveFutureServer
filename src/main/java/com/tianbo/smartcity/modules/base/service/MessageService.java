package com.tianbo.smartcity.modules.base.service;

import com.tianbo.smartcity.base.SmartCityBaseService;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.base.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 消息内容接口
 *
 * @author Exrick
 */
public interface MessageService extends SmartCityBaseService<Message, String> {

    /**
     * 多条件分页获取
     *
     * @param message
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<Message> findByCondition(Message message, SearchVo searchVo, Pageable pageable);

    /**
     * 通过创建发送标识获取
     *
     * @param createSend
     * @return
     */
    List<Message> findByCreateSend(Boolean createSend);
}