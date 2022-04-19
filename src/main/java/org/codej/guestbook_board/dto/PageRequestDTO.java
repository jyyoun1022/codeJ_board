package org.codej.guestbook_board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;



@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page,size;
    private String type,keyword;

    public PageRequestDTO() {
        this.page=1;
        this.size=10;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page-1,size,sort);
        //여기서 Pageable은 data.domain으로 import
    }
}
