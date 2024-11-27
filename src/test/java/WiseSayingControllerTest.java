import com.wiseSaying.controller.WiseSayingController;
import com.wiseSaying.repository.WiseSayingRepository;
import com.wiseSaying.service.WiseSayingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {

    private WiseSayingController wiseSayingController;
    private WiseSayingService wiseSayingService;

    @BeforeEach
    void beforeEach() {
        wiseSayingService = new WiseSayingService(new WiseSayingRepository());
        wiseSayingController = new WiseSayingController(wiseSayingService);
    }

    @Test
    @DisplayName("등록 명령 정상실행")
    void t1(){
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        Scanner scanner = TestUtil.genScanner(
                """
                        등록
                        명언1
                        작가1
                        종료
                        """);
        wiseSayingController.handleCommand("등록", TestUtil.genScanner("명언1\n작가1\n"));
        String result = output.toString();
        TestUtil.clearSetOutToByteArray(output);

        assertThat(result)
                .contains("명언 : ")
                .contains("작가 : ")
                .contains("1번 명언이 등록되었습니다.");

    }


@Test
@DisplayName("목록 명령 정상실행")
void t2() {
    wiseSayingController.handleCommand("등록", TestUtil.genScanner("명언2\n작가2\n"));
    wiseSayingController.handleCommand("등록", TestUtil.genScanner("명언3\n작가3\n"));

    ByteArrayOutputStream output = TestUtil.setOutToByteArray();
    Scanner scanner = TestUtil.genScanner("목록\n종료\n");

    wiseSayingController.handleCommand("목록", scanner);

    String result = output.toString();
    TestUtil.clearSetOutToByteArray(output);

    assertThat(result)
            .contains("번호 / 작가 / 명언")
            .contains("1번 / 작가1 / 명언1")
            .contains("2번 / 작가2 / 명언2")
            .contains("3번 / 작가3 / 명언3");
}

    @Test
    @DisplayName("삭제 명령 정상실행")
    void t3() {
        wiseSayingController.handleCommand("등록", TestUtil.genScanner("명언4\n작가4\n"));
        wiseSayingController.handleCommand("등록", TestUtil.genScanner("명언5\n작가5\n"));

        // 삭제 전 목록 체크
        ByteArrayOutputStream outputBeforeDelete = TestUtil.setOutToByteArray();
        Scanner scannerBeforeDelete = TestUtil.genScanner("목록\n종료\n");
        wiseSayingController.handleCommand("목록", scannerBeforeDelete);
        String resultBeforeDelete = outputBeforeDelete.toString();
        TestUtil.clearSetOutToByteArray(outputBeforeDelete);

        // 삭제 명령 실행
        Scanner scanner = TestUtil.genScanner("삭제?id=1\n종료\n");
        wiseSayingController.handleCommand("삭제?id=1", scanner);

        // 삭제 후 목록 확인
        ByteArrayOutputStream outputAfterDelete = TestUtil.setOutToByteArray();
        Scanner scannerAfterDelete = TestUtil.genScanner("목록\n종료\n");
        wiseSayingController.handleCommand("목록", scannerAfterDelete);
        String resultAfterDelete = outputAfterDelete.toString();
        TestUtil.clearSetOutToByteArray(outputAfterDelete);


        assertThat(resultBeforeDelete)
                .contains("번호 / 작가 / 명언")
                .contains("1번 / 작가1 / 명언1")
                .contains("2번 / 작가2 / 명언2");

        assertThat(resultAfterDelete)
                .contains("번호 / 작가 / 명언")
                .doesNotContain("1번 / 작가1 / 명언1") // 삭제된 명언이 목록에 없어야 함
                .contains("2번 / 작가2 / 명언2"); // 남아있는 명언은 여전히 목록에 있어야 함
    }

    @Test
    @DisplayName("수정 명령 정상실행")
    void t4() {
        wiseSayingController.handleCommand("등록", TestUtil.genScanner("명언6\n작가6\n"));
        wiseSayingController.handleCommand("등록", TestUtil.genScanner("명언7\n작가7\n"));

        Scanner scanner = TestUtil.genScanner("수정된 명언\n수정된 작가\n종료\n");
        wiseSayingController.handleCommand("수정?id=7", scanner);

        // 수정 후 목록 확인
        ByteArrayOutputStream outputAfterModify = TestUtil.setOutToByteArray();
        Scanner scannerAfterModify = TestUtil.genScanner("목록\n종료\n");
        wiseSayingController.handleCommand("목록", scannerAfterModify);
        String resultAfterModify = outputAfterModify.toString();
        TestUtil.clearSetOutToByteArray(outputAfterModify);

        // 수정 결과 확인
        assertThat(resultAfterModify)
                .contains("번호 / 작가 / 명언")
                .contains("6번 / 작가6 / 명언6") // 여섯 번째 명언은 변경되지 않아야 함
                .contains("7번 / 수정된 작가 / 수정된 명언"); // 수정된 내용이 반영되어야 함
    }

    @Test
    @DisplayName("빌드 명령 정상실행")
    void t5() throws IOException {
        wiseSayingController.handleCommand("등록", TestUtil.genScanner("명언8\n작가8\n"));
        wiseSayingController.handleCommand("등록", TestUtil.genScanner("명언9\n작가9\n"));

        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        Scanner scanner = TestUtil.genScanner("빌드\n종료\n");
        wiseSayingController.handleCommand("빌드", scanner);

        // 빌드 후 data.json 파일 내용 확인
        String dataJsonContent = new String(Files.readAllBytes(Paths.get("src/main/resources/db/wiseSaying/data.json")));

        // 출력 결과 확인
        String result = output.toString();
        TestUtil.clearSetOutToByteArray(output);

        // 빌드 결과 확인
        assertThat(result)
                .contains("data.json 파일의 내용이 갱신되었습니다.");

        // data.json 파일의 내용이 올바른지 확인
        assertThat(dataJsonContent)
                .contains("명언8")
                .contains("작가8")
                .contains("명언9")
                .contains("작가9");
    }


}