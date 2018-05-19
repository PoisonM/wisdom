package com.wisdom.beauty.core.service.stock;

import java.util.List;

import com.wisdom.beauty.api.dto.*;
import com.wisdom.beauty.api.extDto.ExtShopStoreDTO;
import com.wisdom.beauty.api.responseDto.ShopStockResponseDTO;
import com.wisdom.common.dto.account.PageParamVoDTO;
import com.wisdom.common.dto.system.PageParamDTO;

/**
 * FileName: ShopStockService
 *
 * @author: 张超
 * Date:     2018/4/23  14:06
 * Description: 仓库和库存相关
 */
public interface ShopStockService {

    /**
     * 查询仓库列表
     *
     * @param pageParamDTO 分页对象
     * @return
     */

    PageParamDTO<List<ExtShopStoreDTO>> findStoreListS(PageParamDTO<ExtShopStoreDTO> pageParamDTO);


    /**
     * 插入一条出/入库记录
     *
     * @param shopStockRecordDTO 出入库表实体对象
     * @return
     */
    int insertStockRecord(ShopStockRecordDTO shopStockRecordDTO);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 根据记录id获取产品入库单详情
     * @Date:2018/5/2 16:54
     */
    ShopStockResponseDTO getShopStock(ShopStockRecordDTO shopStockRecordDTO);

    /**
     * @Author:zhanghuan
     * @Param: bossId   shopStoreId
     * @Return:
     * @Description: 获取出库/入库记录
     * @Date:2018/5/2 17:57
     */
    List<ShopStockRecordDTO> getShopStockRecordList(PageParamVoDTO<ShopStockRecordDTO> pageParamVoDTO);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 获取boss和仓库的关系
     * @Date:2018/5/2 18:16
     */
    List<ShopStockBossRelationDTO> getShopStockBossRelationList(ShopStockBossRelationDTO shopStockBossRelation);

    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 根据条件获取库存
     * @Date:2018/5/3 14:40
     */
    List<ShopStockDTO> getShopStockList(ShopStockDTO ShopStockDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 批量插入ShopStockDTO, 接收json字符串数组，然后换成java对象
    *@Date:2018/5/3 16:47
    */
    int insertShopStockDTO(String shopStockDTOs);
    /**
    *@Author:zhanghuan
    *@Param: 仓库ID，产品ID
    *@Return:
    *@Description: 产品入库的时候需要调用这个接口更新库存量
    *@Date:2018/5/4 17:14
    */
    int updateStockNumber(ShopStockNumberDTO shopStockNumberDTO);
    /**
     *@Author:zhanghuan
     *@Param: 仓库ID，产品ID
     *@Return:
     *@Description: 根据条件查询ShopStockNumberDTO信息
     *@Date:2018/5/4 17:14
     */
    ShopStockNumberDTO getStockNumber(ShopStockNumberDTO shopStockNumberDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 保存ShopStockNumberDTO
    *@Date:2018/5/4 17:57
    */
    int saveStockNumber(ShopStockNumberDTO shopStockNumberDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description:  根据多个产品id查询ShopStockNumberDTO集合
    *@Date:2018/5/9 14:35
    */
    List<ShopStockNumberDTO> getShopStockNumberBy(List<String> productIds);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 批量插入ShopStockNumberDTO
    *@Date:2018/5/9 16:52
    */
    int  batchAddShopStockNumber(List<ShopStockNumberDTO> shopStockNumberDTO);
    /**
     * @Author:zhanghuan
     * @Param:
     * @Return:
     * @Description: 根据记录id获取产品入库单详情
     * @Date:2018/5/2 16:54
     */
    ShopStockResponseDTO getProductInfoAndStock(String shopStoreId, String shopProcId);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description: 获取库存详情
    *@Date:2018/5/18 15:28
    */
   List<ShopStockResponseDTO> getStockDetailList(PageParamVoDTO<ShopStockNumberDTO> pageParamVoDTO);
    /**
    *@Author:zhanghuan
    *@Param:
    *@Return:
    *@Description:
    *@Date:2018/5/18 19:11
    */
    ShopStockResponseDTO getProductStockDetail(ShopStockNumberDTO shopStockNumberDTO);



}
