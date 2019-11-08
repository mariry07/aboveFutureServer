package com.tianbo.smartcity.modules.your.controller;

import cn.hutool.core.util.StrUtil;
import com.tianbo.smartcity.base.SmartCityBaseController;
import com.tianbo.smartcity.common.utils.IdWorker;
import com.tianbo.smartcity.common.utils.PageUtil;
import com.tianbo.smartcity.common.utils.ResultUtil;
import com.tianbo.smartcity.common.vo.PageVo;
import com.tianbo.smartcity.common.vo.Result;
import com.tianbo.smartcity.common.vo.SearchVo;
import com.tianbo.smartcity.modules.base.entity.UserRole;
import com.tianbo.smartcity.modules.your.entity.Floor;
import com.tianbo.smartcity.modules.your.service.FloorService;
import com.tianbo.smartcity.modules.your.service.IFloorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Pangzy
 */
@Slf4j
@RestController
@Api(description = "主机故障管理接口")
@RequestMapping("/smartcity/floor")
@Transactional
public class FloorController extends SmartCityBaseController<Floor, String> {

    @Autowired
    private FloorService floorService;

    @Autowired
    private IFloorService ifloorService;

    @Override
    public FloorService getService() {
        return floorService;
    }


    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Floor>> getByCondition(@ModelAttribute Floor floor,
                                                            @ModelAttribute SearchVo searchVo,
                                                            @ModelAttribute PageVo pageVo){

        Page<Floor> page = floorService.findByCondition(floor, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Floor>>().setData(page);
    }

    /**
     * 查询楼层信息树状图
     * @param u
     * @return
     */
    @RequestMapping(value = "/getAllList")
    @ApiOperation(value = "获取楼层信息")
    public Result<List<Floor>> getByCondition(Floor u){
        //List<Floor> list = floorService.getAll();

        List<Floor> list = ifloorService.getAllByCompanyIdAndDepartmentId(u);
        List<Floor> listTree=getTreeLists(list,"0");
        return new ResultUtil<List<Floor>>().setData(listTree);
       /* Map<String,Floor> floorMap = new HashMap<String,Floor>();
        for (Floor floor: list) {
            if(floor.getLevel() == 1){
                floorMap.put(floor.getId(),floor);
            }else{
                if(floorMap.get(floor.getParentId()) != null){
                    List<Floor> children= new ArrayList<>();
                    children.add(floor);
                    floorMap.get(floor.getParentId()).setChildren();
                }
            }
        }
        List<Floor> floorLists = new ArrayList<Floor>(floorMap.values());*/
    }

    /**
     * 添加楼层或建筑信息
     * @param u
     * @return
     */
    @RequestMapping(value = "/addFloor", method = RequestMethod.POST)
    @ApiOperation(value = "添加楼层或建筑信息")
    public Result<Floor> addFloor(@ModelAttribute Floor u){
        List<Floor> floorList = ifloorService.getAllByCompanyIdAndDepartmentId(u);
        if(floorList.size() > 0){
            return new ResultUtil<Floor>().setErrorMsg("添加建筑楼层信息失败.建筑楼层名称已存在!");
        }
        if(!"0".equals(u.getParentId())){
            String id = IdWorker.generateShortUuid();
            u.setId(u.getParentId() +id);
        }
        u.setName(u.getTitle());
        floorService.save(u);
        return new ResultUtil<Floor>().setSuccessMsg("添加成功");
    }

    /**
     * 编辑楼层或建筑信息
     * @param u
     * @return
     */
    @RequestMapping(value = "/editFloor", method = RequestMethod.POST)
    @ApiOperation(value = "编辑楼层或建筑信息")
    public Result<Floor> editFloor(@ModelAttribute Floor u){
        if("0".equals(u.getParentId())){
            u.setName(u.getParentTitle());
        }
        Floor floor =  floorService.update(u);
        if(floor==null){
            return new ResultUtil<Floor>().setErrorMsg("修改失败");
        }
        return new ResultUtil<Floor>().setSuccessMsg("编辑成功");
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> delAllByIds(@PathVariable String[] ids){

        for(String id:ids){
            Floor floor = floorService.get(id);
            floor.setDelFlag(1);
            floorService.update(floor);
        }
        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }



    /**
     * 查询楼层信息树状图（递归）
     * @param treeLists
     * @param parentId
     * @return
     */
    private List<Floor> getTreeLists(List<Floor> treeLists,String parentId) {
        // 查找所有菜单
        List<Floor> childrenList = new ArrayList();
        for (Floor floor: treeLists) {
            String id = floor.getId();
            String pid = floor.getParentId();
            floor.setTitle(floor.getName());
            floor.setImg(floor.getImg());
            if (parentId.equals(pid)) {
                List<Floor> treeList = getTreeLists(treeLists,id);
                for (Floor tree: treeList) {
                    tree.setParentTitle(floor.getName());

                }
                floor.setChildren(treeList);
                childrenList.add(floor);
            }

        }

        return childrenList;
    }

}
