package com.wiseSaying;

import com.wiseSaying.controller.WiseSayingController;
import com.wiseSaying.repository.WiseSayingRepository;
import com.wiseSaying.service.WiseSayingService;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private WiseSayingController wiseSayingController;

    public App() {
        wiseSayingController = new WiseSayingController(new WiseSayingService(new WiseSayingRepository()));
    }

    public void run() {
        System.out.println("== 명언 앱 ==");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine();

            if (cmd.equals("종료")) {
                break;
            } else {
                wiseSayingController.handleCommand(cmd, scanner);
            }
        }
    }
}