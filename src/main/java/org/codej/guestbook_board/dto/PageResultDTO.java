package org.codej.guestbook_board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO,EN> {

    private List<DTO> dtoList;//Page를 화면에서 사용하기 쉬운 DTO리스트로 변환 시켜줘야 합니다.

    private int totalPage;

    private int page;

    private int size;

    private int start;

    private int end;

    private boolean prev,next;

    private List<Integer> pageList;
    //Integer을 사용한 이유
    //1. 비지니스 로직상 null 이 발생 가능한 경우
    //2. 비지니스 로직에 꼭 null이 꼭 발생해야 할 경우

    public PageResultDTO(Page<EN>result, Function<EN,DTO>fn) {

        this.dtoList = result.stream().map(fn).collect(Collectors.toList());
        this.totalPage = result.getTotalPages();
        makePageList(result.getPageable());

    }
    private void makePageList(Pageable pageable){

        this.page = pageable.getPageNumber() +1;//0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/10.0))*10;
        this.start = tempEnd-9;
        this.end = totalPage> tempEnd? tempEnd:totalPage;

        this.prev = start>1;
        this.next= tempEnd<totalPage;

        this.pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
        //여기서 null을 해줘야하기에 Integer 로 해주었고
        //boxed() 메서드는 int형으로는  Collection으로 담지 못하기 때문에 Integer클래스로 변환해서 List<Integer>로 변환이가능하게 해줍니다.
    }
}
