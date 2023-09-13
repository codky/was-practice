package com.example.waspractice2;

import com.example.waspractice2.calculator.Calculator;
import com.example.waspractice2.calculator.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

// tomcat 만들어보기
// Step1 - 사용자 요청을 메인 Thread가 처리하도록 한다.
public class CustomWebApplicationServer {

    private final int port;

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            logger.info("[CustomWebApplicationServer] waiting for client.");

            while ((clientSocket = serverSocket.accept()) != null) {
                logger.info("[CustomWebApplicationServer] client connected!");

                /**
                 * Step2 - 사용자 요청이 들어올 때마다 Thread를 새로 생성해서 사용자 요청을 처리하도록 한다.
                 * 제한없이 요청마다 스레드를 생성하면 서버에 부하가 커진다. -> pool 로 해결해야함.
                 */
                new Thread(new ClientRequestHandler(clientSocket)).start();
            }
        }
    }
}
