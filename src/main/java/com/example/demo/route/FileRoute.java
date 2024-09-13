package com.example.demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {
    private String pastaE="C:\\tmp\\entrada\\";
    // usamos o '?delete=true' para apagar a pasta .camel
    @Override
    public void configure() throws Exception {
        //na pasta, vai criar uma parta 'input' onde todos os arquivos daqui, vao parao to
        from("file://"+pastaE+"input?delete=true")
                //log()usamos para saber informações
                //log("${file:name}") para pegar o nome do arquivo.... file.getName está para compositor
                .log("${file:name}")
                //log("Arquivo:${header.CamelFileName} - Path: ${header.CamelFilePath} fala os endereços do arquivo e a pasta onde o arquivo está enserido
                //aqui ele vai pear o nome do arquvo
                .log("Arquivo:${header.CamelFileName}")
                //aqui o log vai mostar o endereço da pasta
                .log("Pasta:${header.CamelFilePath}")
                //ele pegar os arquivos de input e manda para o output
                .to("file://"+pastaE+"output");
    }
}