package com.wisdom.business.service.account;

import com.wisdom.business.mapper.account.AccountMapper;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.FrontUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class AccountService {

    @Autowired
    private AccountMapper accountMapper;

    public List<AccountDTO> getUserAccountInfo(AccountDTO accountDTO){
        return accountMapper.getUserAccountInfo(accountDTO);
    }

    public void createUserAccount(AccountDTO accountDTO) {
        accountMapper.insertUserAccountInfo(accountDTO);
    }

    public PageParamDTO<List<AccountDTO>> queryAllUserBalance(PageParamDTO pageParamDTO) {

        String currentPage = String.valueOf(pageParamDTO.getPageNo());
        String pageSize = String.valueOf(pageParamDTO.getPageSize());
        Page<AccountDTO> page = FrontUtils.generatorPage(currentPage, pageSize);
        Page<AccountDTO> pageData = accountMapper.queryAllUserBalance(page);
        for(AccountDTO accountDTO : pageData.getList()){
            try {
                accountDTO.setNickName(URLDecoder.decode(accountDTO.getNickName(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        pageParamDTO.setTotalCount(pageParamDTO.getPageSize()*pageData.getTotalPage());
        pageParamDTO.setResponseData(pageData.getList());
        return pageParamDTO;
    }

    public PageParamDTO<List<AccountDTO>> queryUserBalanceByParameters(String phoneAndIdentify,String isExportExcel, Integer pageNo, Integer pageSize) {

        PageParamDTO<List<AccountDTO>> page = new  PageParamDTO<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        int pageStartNo = (pageNo-1)*pageSize;
        int count = accountMapper.queryUserBalanceCountByParameters(phoneAndIdentify);
        page.setTotalCount(count);
        List<AccountDTO> accountDTOList = accountMapper.queryUserBalanceByParameters(
                phoneAndIdentify,isExportExcel,pageStartNo,pageSize);
        for(AccountDTO accountDTO : accountDTOList){
            try {
                accountDTO.setNickName(URLDecoder.decode(accountDTO.getNickName(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        page.setResponseData(accountDTOList);

        return page;
    }

    public void updateUserAccountInfo(AccountDTO accountDTO) {
        accountMapper.updateUserAccountInfo(accountDTO);
    }


    /**
     * 分页工具
     * @param  preyPageDate 需要分页的list对象
     * @param  pageStartNo 页数
     * @param  pageSize 每页的大小
     * @return
     * */
    public List pagerUtil(List preyPageDate,List returnList,Integer pageStartNo, Integer pageSize){

        Integer total = preyPageDate.size();
        int pageSum;
        if(total%pageSize == 0){
             pageSum = total/pageSize;
        }else{
             pageSum = total/pageSize+1;
        }
        if(pageSum >= pageStartNo){
            for(int i=(pageStartNo-1)*pageSize;i<pageStartNo*pageSize;i++){
                if(i<total){
                    returnList.add(preyPageDate.get(i));
                }else{
                    break;
                }
            }
        }
        return returnList;
    }
}
