package com.assignment.identify.service;


import org.springframework.stereotype.Service;

import com.assignment.identify.vo.RequestVO;
import com.assignment.identify.vo.ResponseVO;

@Service
public interface IdentifyService {
    public ResponseVO identifyUser(RequestVO request);
}