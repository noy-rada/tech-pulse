package techpulse.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import techpulse.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "User Authentication", description = "Endpoints for user authentication")
public class AuthController {

    private final UserService userService;


}
