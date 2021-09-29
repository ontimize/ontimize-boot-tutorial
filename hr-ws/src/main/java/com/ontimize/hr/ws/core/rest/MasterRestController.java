package com.ontimize.hr.ws.core.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ontimize.hr.api.core.service.IMasterService;
import com.ontimize.jee.server.rest.ORestController;

@RestController
@RequestMapping("/master")
@ComponentScan(basePackageClasses = { com.ontimize.hr.api.core.service.IMasterService.class })
public class MasterRestController extends ORestController<IMasterService> {

 @Autowired
 private IMasterService masterService;

 @Override
 public IMasterService getService() {
  return this.masterService;
 }

}
