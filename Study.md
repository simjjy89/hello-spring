# hello-spring
스프링 공부 시작

## @Controller, @RestController 차이점
- @Controller는 전통적인 Spring MVC의 컨트롤러. 주로 View를 반환하기 위해 사용됨. Data 반환을 위해서는 @ResponseBody 어노테이션을 추가적으로 상요하여야 함.
- @RestController Spring MVC Controlle에 @ResponseBody가 추가된 것.

## @RequestMapping, @GetMapping, @PostMapping 차이점
- 클랙스 상단에 @RequestMapping("/api") 등으로 쓰이면 url 에 자동으로 붙여줌
- 메소드 상단에 @RequestMapping(value = "/" , method = RequestMethod.GET) 의 형식으로 작성
- 메소드 상단에 @GetMapping("/getParam") 형태로 쓰임
- @PostMapping는 @GetMapping과 형식은 같지만 주소창에 파라미터 표출이 안됨