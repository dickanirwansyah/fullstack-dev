package com.app.demoservice.model;

import com.app.demoservice.constant.ConstantMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse {

    private Integer status;
    private String message;
    private Object data;

    public static final RestResponse isOk(Object data){
        return RestResponse.builder()
                .data(data)
                .message(ConstantMessage.MESSAGE_SUCCESS)
                .status(HttpStatus.OK.value())
                .build();
    }

    public static final RestResponse isFailed(String message, Integer status){
        return RestResponse.builder()
                .message(message)
                .status(status)
                .build();
    }
}


