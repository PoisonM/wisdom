package com.wisdom.search.service.impl;


import com.wisdom.search.service.OpenSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class OpenSearchServiceImpl implements OpenSearchService {


}
