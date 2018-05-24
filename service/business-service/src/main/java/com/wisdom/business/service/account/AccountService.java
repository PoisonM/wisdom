package com.wisdom.business.service.account;

import com.wisdom.business.controller.account.AccountController;
import com.wisdom.business.mapper.account.AccountMapper;
import com.wisdom.common.dto.account.AccountDTO;
import com.wisdom.common.dto.system.PageParamDTO;
import com.wisdom.common.persistence.Page;
import com.wisdom.common.util.FrontUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Formatter;
import java.net.URLDecoder;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class AccountService {
    Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountMapper accountMapper;

    public List<AccountDTO> getUserAccountInfo(AccountDTO accountDTO){
        return accountMapper.getUserAccountInfo(accountDTO);
    }

    public void createUserAccount(AccountDTO accountDTO) {
        accountMapper.insertUserAccountInfo(accountDTO);
    }

    public PageParamDTO<List<AccountDTO>> queryAllUserBalance(PageParamDTO pageParamDTO) {
        logger.info("查询所有用户账户余额");
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
        logger.info("根据条件查询用户账户余额");
        PageParamDTO<List<AccountDTO>> page = new  PageParamDTO<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        int pageStartNo = (pageNo-1)*pageSize;
        int count = accountMapper.queryUserBalanceCountByParameters(phoneAndIdentify);
        logger.info("查询余额信息Count==={}" + count);
        page.setTotalCount(count);
        List<AccountDTO> accountDTOList = accountMapper.queryUserBalanceByParameters(
                phoneAndIdentify,isExportExcel,pageStartNo,pageSize);
        String nickNameW ="";
        for(AccountDTO accountDTO : accountDTOList){
            Formatter formatter = new Formatter();
            String b = formatter.format("%.2f", (accountDTO.getBalance()-accountDTO.getBalanceDeny())).toString();
            accountDTO.setBalanceYes(b);
            try {
                if(accountDTO.getNickName()!=null){
                    nickNameW = accountDTO.getNickName().replaceAll("%", "%25");
                    while(true){
                        if(nickNameW!=null&&nickNameW!=""){
                            if(nickNameW.contains("%25")){
                                nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                            }else{
                                nickNameW = URLDecoder.decode(nickNameW,"utf-8");
                                break;
                            }
                        }else{
                            break;

                        }
                    }

                }else{
                    nickNameW="未知用户";
                }

            } catch(Throwable e){
                nickNameW="特殊符号用户";
            }
            accountDTO.setNickName(nickNameW);
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
        if(total!=0){
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
        }
        return returnList;
    }
}
