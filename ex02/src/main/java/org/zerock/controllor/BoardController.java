package org.zerock.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*") ///WEB-INF/views/board ~ 경로는 다 이 컨트롤러로 처리 
@AllArgsConstructor //BoardController는 BoardService에 대해서 의존적임. 생성자를 만들고 자동주입 !
public class BoardController {
	private BoardService service;
	/*
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list");
		model.addAttribute("list",service.getList());
	}
	
	*/
	//paging처리 후 list 수정
	@GetMapping("/list")
	public void  list(Criteria cri, Model model) {
		log.info("list:"+cri);
		model.addAttribute("list",service.getList(cri));
		int total = service.getTotal(cri);
		log.info("total :"+total);
		model.addAttribute("pageMaker",new PageDTO(cri, total));
	}
	
	
	//insert 후 키값 받아오기 
	@PostMapping("/register")				//게시물 추가후 목록으로 다시 돌아가기 위한 return
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("register :" + board);
		service.register(board);
		rttr.addFlashAttribute("result",board.getBno());
		//redirect: 작성하면 내부적으로 response.sendRedirect()를 처리해줌
		return "redirect:/board/list";
		
	}
	//데이터 입력,추가를 위한 조회창 띄우기 위함
	@GetMapping("/register")
	public void register() {
		
	}
	
	@GetMapping({"/get","/modify"})				 //자동으로 Model에 데이터를 지정한 이름으로 담아줌
	public void get(@RequestParam("bno") Long bno,@ModelAttribute("cri") Criteria cri, Model model ) {
		log.info("/get or /modify");
		model.addAttribute("board",service.get(bno));
		
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify:"+board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result","success");
		}
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove....."+bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		return "redirect:/board/list";
	}
}
