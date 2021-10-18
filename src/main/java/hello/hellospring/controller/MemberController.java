package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
/*

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
*/


    @PostConstruct
    public void init() {
        System.out.println("MemberController BEAN 생성");
    }

    @PreDestroy
    public void stop(){
        System.out.println("MemberController BEAN 삭제");
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }


    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        //System.out.println(member.getName());

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

}
