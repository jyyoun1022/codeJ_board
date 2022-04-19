package org.codej.guestbook_board.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.codej.guestbook_board.dto.GuestbookDTO;
import org.codej.guestbook_board.dto.PageRequestDTO;
import org.codej.guestbook_board.dto.PageResultDTO;
import org.codej.guestbook_board.entity.Guestbook;
import org.codej.guestbook_board.entity.QGuestbook;
import org.codej.guestbook_board.repository.GuestbookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

import static org.codej.guestbook_board.entity.QGuestbook.guestbook;

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

        BooleanBuilder builder = getSearch(requestDTO);//검색조건 처리

        Page<Guestbook> result = repository.findAll(builder,pageable);

        Function<Guestbook,GuestbookDTO> fn = (entity ->entityToDto(entity));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {

        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent()? entityToDto(result.get()):null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result = repository.findById(dto.getGno());
        if(result.isPresent()){
            Guestbook guestbook = result.get();
            guestbook.changeTitle(dto.getTitle());
            guestbook.changeContent(dto.getContent());
            repository.save(guestbook);
        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){

        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = guestbook.gno.gt(0L);
        booleanBuilder.and(expression);

        if( type == null || type.trim().length() == 0 ){
            return booleanBuilder;
        }
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")){
            conditionBuilder.or(guestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(guestbook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(guestbook.writer.contains(keyword));
        }
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
