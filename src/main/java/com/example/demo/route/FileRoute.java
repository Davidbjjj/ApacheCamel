package com.example.demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.FileConstants;
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
                //o componente pega o intercambio entre uma pasta e outro

                //log("Arquivo:${header.CamelFileName} - Path: ${header.CamelFilePath} fala os endereços do arquivo e a pasta onde o arquivo está enserido
                //aqui ele vai pear o nome do arquvo
                .log("Arquivo:${header.CamelFileName}")
                //aqui o log vai mostar o endereço da pasta
                .log("Pasta:${header.CamelFilePath}")
                //nome do arquivo
                .log("Proceso do arquivo: ${file:name}")
                //a ideia aqui é fazer com que o cabeçalho do arquivo seja enviado junto com ele quando for para o output, ou seja , o nome do arquivo vai mudar.
                .setHeader(FileConstants.FILE_NAME, simple("${date:now:HHmmss}_${file:name}"))
                .bean("fileComponent")
                //getmensagem é para verificar se tem uma mensagem de saída e não o getout
                .process(exchange -> System.out.println(exchange.getMessage().getBody(String.class)))
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
//usar o process sempre que chamar um método como uma classe interna anônima
//*/    from("activemq:myQueue").process(new Processor() { definição da instância dela
//        public void process(Exchange exchange) throws Exception {<===
//            String payload = exchange.getMessage().getBody(String.class);  <--
//            // do something with the payload and/or exchange here
//           exchange.getMessage().setBody("Changed body");
//       }
//    }).to("activemq:myOtherQueue");/