package com.example.demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {
    private String pastaE="C:\\tmp\\entrada\\";
    @Override
    public void configure() throws Exception {
        from("file://"+pastaE+"input")
                .to("file://"+pastaE+"output");

    }
}
