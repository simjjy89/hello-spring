package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    @PostConstruct
    public void init() {
        System.out.println("MemberService BEAN 생성");
    }

    @PreDestroy
    public void stop(){
        System.out.println("MemberService BEAN 삭제");
    }

   private final JpaMemberRepository jpaMemberRepository;

    public MemberService(JpaMemberRepository jpaMemberRepository){
        this.jpaMemberRepository = jpaMemberRepository;
    }




    /*
    * 회원가입
    * */

    public Long join(Member member){
        //같은 이름이 있는 중복 회원X

            validateDuplicateMember(member); //중복 회원 검증
        jpaMemberRepository.save(member);
            return member.getId();
     }

    private void validateDuplicateMember(Member member) {
        jpaMemberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /*
    * 전체 회원 조회
    **/
    public List<Member> findMembers(){


            return jpaMemberRepository.findAll();

    }

    public Optional<Member> findOne(Long memberId){
        return jpaMemberRepository.findById(memberId);
    }


}
