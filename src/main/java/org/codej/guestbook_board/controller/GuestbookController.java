package org.codej.guestbook_board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.codej.dto.GuestbookDTO;
import org.codej.dto.PageRequestDTO;
import org.codej.dto.PageResultDTO;
import org.codej.guestbook_board.entity.Guestbook;
import org.codej.guestbook_board.service.GuestbookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index(){

        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public String list(PageRequestDTO requestDTO, Model model){
        //여기에 PageRequestDTO를 파라미터로 해준다면 page,size라는 파라미터를 전달하면 PageRequestDTO객체로 자동으로 수집됩니다.

        model.addAttribute("result",service.getList(requestDTO));

        return "/guestbook/list";
    }

    @GetMapping("/register")
    public void register(){
        log.info("register");
    }

    @PostMapping("/register")
    public String register(GuestbookDTO guestbookDTO, RedirectAttributes redirectAttributes){

        Long gno = service.register(guestbookDTO);

        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping("/read")
    public void read(long gno, @ModelAttribute("requestDTO")PageRequestDTO requestDTO,Model model){

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto",dto);
    }


}
