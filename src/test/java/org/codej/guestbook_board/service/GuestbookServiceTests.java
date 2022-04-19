package org.codej.guestbook_board.service;

import org.codej.dto.GuestbookDTO;
import org.codej.dto.PageRequestDTO;
import org.codej.dto.PageResultDTO;
import org.codej.guestbook_board.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("등록 테스트")
                .content("등록 테스트")
                .writer("테스트 USER")
                .build();

        Long register = service.register(guestbookDTO);
        System.out.println(register);
    }
    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        List<GuestbookDTO> list = resultDTO.getDtoList();

        list.stream().forEach(guestbookDTO -> System.out.println(guestbookDTO));
    }
}
