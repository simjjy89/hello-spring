package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {


    @Bean
    public TimeTraceAop atimeTraceAop(){
        return new TimeTraceAop();
    }


/*

    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;   //SpringDataJpaMemberRepository interface에서 만들어진 구현체가 Injection 됨
    }
*/


/*
    @PersistenceContext
    private EntityManager em;

    */

/*
    private final DataSource dataSource;
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    */
/*


    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }
*/

/*    @Bean
    public MemberRepository memberRepository(){

        //return new MemoryMemberRepository();
        //return new JdbcTemplateMemberRepository(dataSource);
       // return new JpaMemberRepository(em);
    }*/
/*
    @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }

    */
}
