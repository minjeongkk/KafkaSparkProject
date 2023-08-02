package com.example.firstProject.controller;

import com.example.firstProject.dto.TopicDto;
import com.example.firstProject.entity.Status;
import com.example.firstProject.service.ConsumerService;
import com.example.firstProject.service.ConsumerTest;
import com.example.firstProject.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class KafkaController {
    private HashMap<Integer, ConsumerService> consumerServices =  new HashMap<Integer, ConsumerService>( );
    @Autowired
    private TopicService topicService;

    // 구독
    @GetMapping("/subscribe/{id}")
    public String subscribe(@PathVariable Long id){

        // 구독 (ip, port, topic이름 전달)
        int topicId = Integer.parseInt(id.toString());
        consumerServices.put(topicId, new ConsumerService());
        TopicDto topicDto = topicService.findById(id);
        consumerServices.get(topicId).subscribe(topicDto.getIp(), topicDto.getPort().toString(), topicDto.getTopicName());

        // 토픽 상태 Running으로 변경
        TopicDto topicDto1 = topicService.findById(id);
        topicService.updateStatus(id, topicDto1, Status.Running);

        return "subscribe";
    }

    // 조회
    @GetMapping(value="/getData/{id}")
    public ArrayList getMessage(@PathVariable Long id){
        int topicId = Integer.parseInt(id.toString());
        return consumerServices.get(topicId).test();
    }

    // 중지
    @GetMapping("/stop/{id}")
    public String stop(@PathVariable Long id){
        // 구독 중지
        int topicId = Integer.parseInt(id.toString());
        consumerServices.get(topicId).stop();

        // 토픽 상태 Stopped로 변경
        TopicDto topicDto1 = topicService.findById(id);
        topicService.updateStatus(id, topicDto1, Status.Stopped);

        return "stop";
    }
}