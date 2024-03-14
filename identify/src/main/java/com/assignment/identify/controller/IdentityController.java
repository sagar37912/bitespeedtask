package com.assignment.identify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.identify.service.IdentifyService;
import com.assignment.identify.vo.RequestVO;
import com.assignment.identify.vo.ResponseVO;

@RestController
@RequestMapping("/api")
public class IdentityController {
    @Autowired
	IdentifyService identifyService;
	
	@PostMapping("/identify")
	  public ResponseEntity<ResponseVO> identifyUser(@RequestBody RequestVO requestVO) {
	      return ResponseEntity.ok(identifyService.identifyUser(requestVO));
	  }
}
