package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.Complaint;
import com.example.pipayshopapi.mapper.ComplaintMapper;
import com.example.pipayshopapi.service.ComplaintService;
import org.springframework.stereotype.Service;

@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {

}
