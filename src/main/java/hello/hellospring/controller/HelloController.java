package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") //컨트롤러 mapping 테스트
    public String hello(Model model){
        model.addAttribute("data","hello!!");

        return "hello"; // /resource/template 에 있는 hello.html을 찾아서 전달
    }

    @GetMapping("hello-mvc") //mvc 테스트
    public String helloMvc(@RequestParam(value="name", required = false, defaultValue = "default") String name, Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }

    @GetMapping("hello-string") //api 테스트
    @ResponseBody //문자를 return 하는 경우 stringConverter가 동작
    public String helloString(@RequestParam(value="name") String name){
         
        return "hello "+name; //view 없이 그대로 넘어감
    }

    @GetMapping("hello-api") //api 테스트
    @ResponseBody //객체를 return 하는 경우  JsonConverter가 동작
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
