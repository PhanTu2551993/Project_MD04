package ra.project_md04.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@GetMapping
	public ResponseEntity<?> admin() {
		return ResponseEntity.ok().body("đã vào được admin");
	}

}
