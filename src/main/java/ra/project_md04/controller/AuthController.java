package ra.project_md04.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md04.constans.EHttpStatus;
import ra.project_md04.model.dto.request.FormLogin;
import ra.project_md04.model.dto.request.FormRegister;
import ra.project_md04.model.dto.response.ResponseWrapper;
import ra.project_md04.service.IAuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@Valid @RequestBody FormLogin formLogin) {
        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .eHttpStatus(EHttpStatus.SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .data(authService.handleLogin(formLogin))
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> handleRegister(@Valid @RequestBody FormRegister formRegister) {
        authService.handleRegister(formRegister);
        return new ResponseEntity<>( ResponseWrapper.builder()
                .eHttpStatus(EHttpStatus.SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .data("Register successfully")
                .build(),
                HttpStatus.CREATED);
    }

}
