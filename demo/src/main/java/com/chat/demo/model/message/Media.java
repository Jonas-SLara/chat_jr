package com.chat.demo.model.message;

import com.chat.demo.model.message.enums.MediaEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Media {
    private MediaEnum messageType;
    private String url;
    private String mimeType;
    private Long size;
    private Integer width;
    private Integer height;
    private Integer duration;
}
