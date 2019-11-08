package com.tianbo.smartcity.modules.base.utils;

import com.tianbo.smartcity.modules.base.entity.Permission;
import com.tianbo.smartcity.modules.base.vo.MenuVo;

/**
 * @author Exrick
 */
public class DtoUtil {

    public static MenuVo permissionToMenuVo(Permission p) {

        MenuVo menuVo = new MenuVo();

        menuVo.setId(p.getId());
        menuVo.setParentId(p.getParentId());
        menuVo.setName(p.getName());
        menuVo.setType(p.getType());
        menuVo.setTitle(p.getTitle());
        menuVo.setComponent(p.getComponent());
        menuVo.setPath(p.getPath());
        menuVo.setIcon(p.getIcon());
        menuVo.setUrl(p.getUrl());
        menuVo.setButtonType(p.getButtonType());

        return menuVo;
    }
}
