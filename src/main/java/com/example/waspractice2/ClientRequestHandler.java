package com.example.waspractice2;

import com.example.waspractice2.calculator.Calculator;
import com.example.waspractice2.calculator.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientRequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientRequestHandler.class) ;

    private final Socket clientSocket;

    public ClientRequestHandler(Socket clientSocket) { // 외부로부터 주입
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
            /**
             * Step2 - 사용자 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
             */
            logger.info("[ClientRequestHandler] new client {} started.", Thread.currentThread().getName());
            try(InputStream in = clientSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                DataOutputStream dos = new DataOutputStream(out);

                HttpRequest httpRequest = new HttpRequest(br);

            // Get /calculate?operand1=&operator=*&operand2=55 HTTP/1.1
            if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) { // get 요청이면서 path가 /calculate 와 일치하면
                QueryStrings queryStrings = httpRequest.getQueryStrings(); // 쿼리스트링을 가져온다.

                int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                String operator = queryStrings.getValue("operator");
                int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
                byte[] body = String.valueOf(result).getBytes();

                // response 구현
                HttpResponse response = new HttpResponse(dos);
                response.response200Header("application/json", body.length);
                response.responseBody(body);
            }
        } catch (IOException e) {
                logger.error(e.getMessage());
            }
    }
}
