package org.codej.guestbook_board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.codej.dto.GuestbookDTO;
import org.codej.dto.PageRequestDTO;
import org.codej.dto.PageResultDTO;
import org.codej.guestbook_board.entity.Guestbook;
import org.codej.guestbook_board.repository.GuestbookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {

        Guestbook entity = dtoToEntity(dto);
        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook,GuestbookDTO> fn = (entity ->entityToDto(entity));

        return new PageResultDTO<>(result,fn);
    }
}
