package com.maven.tc.controller;

import com.maven.tc.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    private static final String CAPTCHA_KEY = "captcha_code";

    @GetMapping("/generate")
    @Operation(summary = "生成图片验证码")
    public Result<Map<String, Object>> generate(HttpSession session) {
        String code = generateCode(4);
        session.setAttribute(CAPTCHA_KEY, code);

        Map<String, Object> data = new HashMap<>();
        data.put("code", code);
        data.put("length", 4);
        return Result.success(data);
    }

    @PostMapping("/verify")
    @Operation(summary = "校验验证码")
    public Result<Map<String, Object>> verify(@RequestBody Map<String, String> map, HttpSession session) {
        String inputCode = map.get("code");
        String sessionCode = (String) session.getAttribute(CAPTCHA_KEY);

        Map<String, Object> result = new HashMap<>();

        if (sessionCode == null) {
            result.put("safe", false);
            result.put("message", "验证码已过期，请刷新重试");
            return Result.success(result);
        }

        if (sessionCode.equalsIgnoreCase(inputCode)) {
            session.removeAttribute(CAPTCHA_KEY);
            result.put("safe", true);
            result.put("message", "验证通过，当前环境安全");
        } else {
            result.put("safe", false);
            result.put("message", "验证码错误，请重试");
        }

        return Result.success(result);
    }

    private String generateCode(int length) {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}