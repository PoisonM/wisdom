package com.wisdom.business.service.product;

import com.wisdom.common.dto.product.InvoiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zbm84 on 2017/7/24.
 */
@Service
@Transactional(readOnly = false)
public class InvoiceService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addInvoiceInfo(InvoiceDTO invoiceDTO) {
        mongoTemplate.insert(invoiceDTO,"invoice");
    }

    public InvoiceDTO getInvoiceDetailById(String userId, String transactionId) {
        Query query = new Query().addCriteria(Criteria.where("userId").is(userId)).addCriteria(Criteria.where("transactionId").is(transactionId));
        InvoiceDTO invoiceDTO = mongoTemplate.findOne(query, InvoiceDTO.class,"invoice");
        return invoiceDTO;
    }
}
