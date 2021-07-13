/*
package woosap.Pepple.security;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    // 인자를 받고 서버에서 토큰을 발행시켜줌
    @GetMapping("/gen/token")
    public Map<String, Object> genToken(@RequestParam(value = "subject") String subject) {
        String token = securityService.createToken(subject);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", token);
        return map;
    }

    // 비밀키로 개인 토큰을 풀어 값을 가져옴
    @ResponseBody
    @GetMapping("/get/token")
    public Map<String, Object> getSubject(@RequestParam("token") String token) {
        String subject = securityService.getSubject(token);
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", subject);
        return map;
    }
}
*/