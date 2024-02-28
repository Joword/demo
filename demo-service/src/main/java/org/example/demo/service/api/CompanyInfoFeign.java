package org.example.demo.service.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Joword
 * @date: 2024/2/28 23:32
 * @version: 0.1.0
 * @description: to connect the company info
 */
@FeignClient(name = "${spring.application.prefix}-open-web",contextId = "CompanyInfoFeign",configuration = String.class)
public interface CompanyInfoFeign {
}
