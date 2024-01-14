package ru.yadzuka.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yadzuka.exceptions.Error;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yadzuka.Constants.VIDEOS_PATTERN;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final ResourceLoader resourceLoader;

    public ResponseEntity<?> getAllVideoNames() {
        try {
            List<String> videoNames = Arrays.stream(ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
                    .getResources("classpath:video/*"))
                    .map(Resource::getFilename).collect(Collectors.toList());
            return new ResponseEntity<>(videoNames, HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>(
                    new Error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    public Mono<Resource> getVideo(String title) {
        return Mono.fromSupplier(() -> resourceLoader.getResource(String.format(VIDEOS_PATTERN, title)));
    }
}
