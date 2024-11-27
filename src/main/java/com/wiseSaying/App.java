package com.wiseSaying;

import com.wiseSaying.controller.WiseSayingController;
import com.wiseSaying.repository.WiseSayingRepository;
import com.wiseSaying.service.WiseSayingService;

import java.util.Scanner;
// 역할 : 사용자 입력을 받고 그것이 WiseSayingController 에게 넘겨야 하는지 판단해서 맞으면 넘김
// (넘김의 의미 : 메서드 호출(인자와 함께))
// 스캐너 사용가능 / 출력 사용가능


public class App {
    private final WiseSayingController wiseSayingController;

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