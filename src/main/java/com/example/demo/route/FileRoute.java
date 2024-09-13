package com.example.demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileRoute extends RouteBuilder {
    private String pastaE="C:\\tmp\\entrada\\";
    // usamos o '?delete=true' para apagar a pasta .camel
    @Override
    public void configure() throws Exception {
        //na pasta, vai criar uma parta 'input' onde todos os arquivos daqui, vao parao to
        //posso modificar pra onde ele vai mandar o arquivo e como eu quero o nome desse arquivo já movido "file://"+"input?move=${}/${file:name} na input vai ficar tipo um backup do arquivo que estava, vai ser movido para essa pasta de backup, passando o nome do arquivo"
        //noop=true faz com que ele não processe o mesmo arquivo 2 vezes, ""        from("file://"+pastaE+"input?noop=true")
        //recursive=true vai fazer com que as subpastas de input também sejam mapeada igual a ela.
        from("file://"+pastaE+"input?move=${file:name} copia/copia ${file:name}")
                //log()usamos para saber informações
                //log("${file:name}") para pegar o nome do arquivo.... file.getName está para compositor
                .log("${file:name}")
                //log("Arquivo:${header.CamelFileName} - Path: ${header.CamelFilePath} fala os endereços do arquivo e a pasta onde o arquivo está enserido
                //aqui ele vai pear o nome do arquvo
                .log("Arquivo:${header.CamelFileName}")
                //aqui o log vai mostar o endereço da pasta
                .log("Pasta:${header.CamelFilePath}")
                .bean("fileComponent")
                //ele pegar os arquivos de input e manda para o output
                .to("file://"+pastaE+"output");
    }
}
    //um metodo log em java que faz a mesma coisa, a dirença é que não precisamos ficar usando variavel sempre e sim apenas monitorando um caminho
    //nesses componente só pode te apenas ter um método
    @Component
    class FileComponent{
    public void log(File file){
        System.out.printf("FileComponent:"+file.getName()+"Esse é o bean");
    }
}