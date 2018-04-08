package com.wisdom.user.service.impl;

import com.wisdom.user.service.BossInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class BossInfoServiceImpl implements BossInfoService{

}
