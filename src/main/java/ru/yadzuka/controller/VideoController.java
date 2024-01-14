package ru.yadzuka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.yadzuka.services.VideoService;

@RestController("Video")
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping(value = "/videos/{name}", produces = "video/mp4")
    public Mono<Resource> getVideo(@PathVariable String name,
                                   @RequestHeader(value = "Range", required = false) String range) {
        return videoService.getVideo(name);
    }

    @GetMapping("/videos")
    public ResponseEntity<?> getAllVideoNames() {
        return videoService.getAllVideoNames();
    }
}
