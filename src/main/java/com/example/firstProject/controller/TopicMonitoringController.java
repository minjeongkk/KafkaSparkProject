package com.example.firstProject.controller;

import com.example.firstProject.dto.TopicDto;
import com.example.firstProject.entity.Status;
import com.example.firstProject.kafka.Consumer;
import com.example.firstProject.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TopicMonitoringController {
    private Consumer consumer;
    private TopicService topicService;

    @Autowired
    public TopicMonitoringController(Consumer consumer, TopicService topicService) {
        this.consumer = consumer;
        this.topicService = topicService;
    }

    // 서버 확인
    @GetMapping("/checkServer/{id}")
    public ResponseEntity<String> checkServer(@PathVariable Long id) {
        TopicDto topicDto = topicService.findById(id);

        // 서버 작동
        boolean isChecked = consumer.checkServer(topicDto.getIp(), topicDto.getPort().toString());
        if (isChecked) {
            return new ResponseEntity<>(id.toString() + ":ok", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(id.toString() + ":not found", HttpStatus.NOT_FOUND);
        }
    }

    // 구독
    @PostMapping("/subscribe/{id}")
    public ResponseEntity<String> subscribe(@PathVariable Long id) {
        TopicDto topicDto = topicService.findById(id);
        // 구독 (ip, port, topic이름 전달)
        consumer.subscribe(id, topicDto.getIp(), topicDto.getPort().toString(), topicDto.getTopicName());
        // topic 상태 변경
        topicService.updateStatus(id, topicDto, Status.Running);
        return new ResponseEntity<>(id.toString() + ":subscribe", HttpStatus.OK);
    }

    // 조회
    @GetMapping(value = "/getData/{id}")
    public List getMessage(@PathVariable Long id) {
        return consumer.getData(id);
    }

    // 중지
    @PostMapping("/stop/{id}")
    public ResponseEntity<String> stop(@PathVariable Long id) {
        // 구독 중지
        consumer.stop(id);

        // 토픽 상태 Stopped로 변경
        TopicDto topicDto = topicService.findById(id);
        topicService.updateStatus(id, topicDto, Status.Stopped);

        return new ResponseEntity<>(id.toString() + ":stop", HttpStatus.OK);
    }
}
