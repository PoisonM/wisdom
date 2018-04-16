package com.wisdom.beauty.core.service.impl;

import com.aliyun.oss.ServiceException;
import com.wisdom.beauty.api.dto.ShopUserArchivesCriteria;
import com.wisdom.beauty.api.dto.ShopUserArchivesDTO;
import com.wisdom.beauty.core.mapper.ShopUserArchivesMapper;
import com.wisdom.beauty.core.service.ShopCustomerArchivesServcie;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * ClassName: ShopCustomerArchivesServcieImpl
 *
 * @Author： huan
 * @Description:
 * @Date:Created in 2018/4/3 16:41
 * @since JDK 1.8
 */
@Service("shopCustomerArchivesServcie")
public class ShopCustomerArchivesServcieImpl implements ShopCustomerArchivesServcie {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShopUserArchivesMapper shopUserArchivesMapper;

    @Override
    public int getArchivesCount(ShopUserArchivesDTO shopUserArchivesDTO) {
        logger.info("getArchivesCount方法传入的参数={}", shopUserArchivesDTO.getSysShopId());
        if (StringUtils.isBlank(shopUserArchivesDTO.getSysShopId())) {
            throw new ServiceException("SysShopId为空");
        }
        ShopUserArchivesCriteria criteria = new ShopUserArchivesCriteria();
        ShopUserArchivesCriteria.Criteria c = criteria.createCriteria();
        ShopUserArchivesCriteria.Criteria or = criteria.createCriteria();

        //参数
        c.andSysShopIdEqualTo(shopUserArchivesDTO.getSysShopId());
        if (StringUtils.isNotBlank(shopUserArchivesDTO.getPhone())) {
            c.andPhoneLike("%" + shopUserArchivesDTO.getPhone() + "%");
        }

        or.andSysShopIdEqualTo(shopUserArchivesDTO.getSysShopId());
        if (StringUtils.isNotBlank(shopUserArchivesDTO.getSysUserName())) {
            or.andSysUserNameLike("%" + shopUserArchivesDTO.getSysUserName() + "%");
        }
        criteria.or(or);
        int count = shopUserArchivesMapper.countByCriteria(criteria);
        return count;
    }

    @Override
    public List<ShopUserArchivesDTO> getArchivesList(PageParamVoDTO<ShopUserArchivesDTO> shopCustomerArchivesDTO) {
        logger.info("getArchivesList方法传入的参数={}", shopCustomerArchivesDTO);

        ShopUserArchivesCriteria criteria = new ShopUserArchivesCriteria();
        ShopUserArchivesCriteria.Criteria c = criteria.createCriteria();
        ShopUserArchivesCriteria.Criteria or = criteria.createCriteria();

        // 排序
        criteria.setOrderByClause("sys_user_name");
        // 分页
        if(shopCustomerArchivesDTO.getPageNo()!=0) {
            criteria.setLimitStart(shopCustomerArchivesDTO.getPageNo());
        }
        if(shopCustomerArchivesDTO.getPageSize()!=0) {
            criteria.setPageSize(shopCustomerArchivesDTO.getPageSize());
        }
        //参数
        if(StringUtils.isNotBlank(shopCustomerArchivesDTO.getRequestData().getSysShopId())){
            c.andSysShopIdEqualTo(shopCustomerArchivesDTO.getRequestData().getSysShopId());
        }
        if(StringUtils.isNotBlank(shopCustomerArchivesDTO.getRequestData().getId())){
            c.andIdEqualTo(shopCustomerArchivesDTO.getRequestData().getId());
        }
        if (StringUtils.isNotBlank(shopCustomerArchivesDTO.getRequestData().getPhone())) {
            c.andPhoneLike("%" + shopCustomerArchivesDTO.getRequestData().getPhone() + "%");
        }
        if(StringUtils.isNotBlank(shopCustomerArchivesDTO.getRequestData().getSysShopId())){
            or.andSysShopIdEqualTo(shopCustomerArchivesDTO.getRequestData().getSysShopId());
         }
        if (StringUtils.isNotBlank(shopCustomerArchivesDTO.getRequestData().getSysUserName())) {
            or.andSysUserNameLike("%" + shopCustomerArchivesDTO.getRequestData().getSysUserName() + "%");
        }
        criteria.or(or);
        List<ShopUserArchivesDTO> shopCustomerArchiveslist = shopUserArchivesMapper.selectByCriteria(criteria);

        return shopCustomerArchiveslist;
    }

    /**
     * 查询某个店某一时间段建档的个数
     *
     * @param shopId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public int getShopBuildArchivesNumbers(String shopId, Date startDate, Date endDate) {

        logger.info("查询某个店某一时间段建档的个数传入参数={}", "shopId = [" + shopId + "], startDate = [" + startDate + "], endDate = [" + endDate + "]");

        ShopUserArchivesCriteria archivesCriteria = new ShopUserArchivesCriteria();
        ShopUserArchivesCriteria.Criteria criteria = archivesCriteria.createCriteria();

        if (StringUtils.isNotBlank(shopId)) {
            criteria.andSysShopIdEqualTo(shopId);
        }
        if (startDate != null && endDate != endDate) {
            criteria.andCreateDateBetween(startDate, endDate);
        }

        int count = shopUserArchivesMapper.countByCriteria(archivesCriteria);
        logger.debug("查询某个店某一时间段建档的个数为， {}", count);
        return count;
    }

    /**
     * 保存用户的建档案信息
     *
     * @param shopUserArchivesDTO
     * @return
     */
    @Override
    public int saveShopUserArchivesInfo(ShopUserArchivesDTO shopUserArchivesDTO) {

        logger.info("保存用户的建档案信息传入参数={}", "shopUserArchivesDTO = [" + shopUserArchivesDTO + "]");

        if (CommonUtils.objectIsEmpty(shopUserArchivesDTO)) {
            logger.error("保存用户的建档案信息,传入参数为空{}", "shopUserArchivesDTO = [" + shopUserArchivesDTO + "]");
            return 0;
        }

        return shopUserArchivesMapper.insert(shopUserArchivesDTO);
    }

    /**
     * 更新用户的档案信息
     *
     * @param shopUserArchivesDTO
     * @return
     */
    @Override
    public int updateShopUserArchivesInfo(ShopUserArchivesDTO shopUserArchivesDTO) {

        logger.info("更新用户的档案信息传入参数={}", "shopUserArchivesDTO = [" + shopUserArchivesDTO + "]");

        if (CommonUtils.objectIsEmpty(shopUserArchivesDTO)) {
            logger.error("更新用户的档案信息,传入参数为空{}", "shopUserArchivesDTO = [" + shopUserArchivesDTO + "]");
            return 0;
        }

        return shopUserArchivesMapper.updateByPrimaryKeySelective(shopUserArchivesDTO);
    }

    /**
     * 删除用户的档案信息
     *
     * @param archivesId
     * @return
     */
    @Override
    public int deleteShopUserArchivesInfo(String archivesId) {

        logger.info("保存用户的建档案信息传入参数={}", "archivesId = [" + archivesId + "]");

        if (StringUtils.isBlank(archivesId)) {
            logger.error("保存用户的建档案信息,传入参数为空{}", "archivesId = [" + archivesId + "]");
            return 0;
        }

        return shopUserArchivesMapper.deleteByPrimaryKey(archivesId);
    }
}
