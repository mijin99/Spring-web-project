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
@RequestMapping("/board/*") ///WEB-INF/views/board ~ ��δ� �� �� ��Ʈ�ѷ��� ó�� 
@AllArgsConstructor //BoardController�� BoardService�� ���ؼ� ��������. �����ڸ� ����� �ڵ����� !
public class BoardController {
	private BoardService service;
	/*
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list");
		model.addAttribute("list",service.getList());
	}
	
	*/
	//pagingó�� �� list ����
	@GetMapping("/list")
	public void  list(Criteria cri, Model model) {
		log.info("list:"+cri);
		model.addAttribute("list",service.getList(cri));
		int total = service.getTotal(cri);
		log.info("total :"+total);
		model.addAttribute("pageMaker",new PageDTO(cri, total));
	}
	
	
	//insert �� Ű�� �޾ƿ��� 
	@PostMapping("/register")				//�Խù� �߰��� ������� �ٽ� ���ư��� ���� return
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("register :" + board);
		service.register(board);
		rttr.addFlashAttribute("result",board.getBno());
		//redirect: �ۼ��ϸ� ���������� response.sendRedirect()�� ó������
		return "redirect:/board/list";
		
	}
	//������ �Է�,�߰��� ���� ��ȸâ ���� ����
	@GetMapping("/register")
	public void register() {
		
	}
	
	@GetMapping({"/get","/modify"})				 //�ڵ����� Model�� �����͸� ������ �̸����� �����
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
		/* UriComponentsBuilder ��뿹��, �ַ� javascript �� �� ����� ����
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword()); */
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove....."+bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result","success");
		}
		/*
		rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());
		rttr.addAttribute("type",cri.getType());
		rttr.addAttribute("keyword",cri.getKeyword());*/
		return "redirect:/board/list" + cri.getListLink();
	}
}