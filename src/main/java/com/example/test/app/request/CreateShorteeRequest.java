package com.example.test.app.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class CreateShorteeRequest {
    private MultipartFile file;
}
