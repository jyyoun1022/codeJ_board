package org.codej.guestbook_board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.codej.guestbook_board.entity.Guestbook;
import org.codej.guestbook_board.entity.QGuestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.codej.guestbook_board.entity.QGuestbook.guestbook;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository repository;

    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1,300).forEach(i->{

            Guestbook guestbook = Guestbook.builder()
                    .title("제목_ " + i)
                    .content("내용_ " + i)
                    .writer("작성자_ " + (i % 10))
                    .build();

            System.out.println(repository.save(guestbook));
        });
    }
    @Test
    public void updateTest(){
        Optional<Guestbook> result = repository.findById(1L);

        if(result.isPresent()){
            Guestbook guestbook = result.get();
            guestbook.changeTitle("제목을 바꿔봄");
            guestbook.changeContent("내용을 바꿔봄");

            repository.save(guestbook);

        }
    }
    @Test
    public void testQuery1(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno"));

         String keyword ="1";

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        //where 문에 들어가는 조건들을 넣어주는 컨테이너

        BooleanExpression expression = guestbook.title.contains(keyword);

        booleanBuilder.and(expression);

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        result.stream().forEach(guestbook ->{
            System.out.println(guestbook);
        });


    }
    @Test
    public void testQuery2(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        String keyword = "1";

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        BooleanExpression exTitle = guestbook.title.contains(keyword);
        BooleanExpression exContent = guestbook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);

        booleanBuilder.and(exAll).and(guestbook.gno.gt(0L));

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        result.stream().forEach(guestbook ->{
            System.out.println(guestbook);
        });

    }
}
