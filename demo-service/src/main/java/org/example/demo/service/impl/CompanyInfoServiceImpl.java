package org.example.demo.service.impl;

import org.example.demo.service.CompanyInfoService;
import org.example.demo.service.api.CompanyInfoFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Joword
 * @date: 2024/2/28 23:26
 * @version: 0.1.0
 * @description: implement of company informations
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {
    
    @Autowired
    private CompanyInfoFeign companyInfoFeign;
    
    
    
}
