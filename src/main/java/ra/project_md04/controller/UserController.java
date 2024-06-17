package ra.project_md04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md04.model.dto.request.AddressRequest;
import ra.project_md04.model.dto.request.UpdateUserRequest;
import ra.project_md04.model.entity.Address;
import ra.project_md04.model.entity.Users;
import ra.project_md04.service.IUserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PutMapping("/account/change-password")
    public ResponseEntity<String> changePassword(@RequestBody UpdateUserRequest updateUserRequest) {
        boolean result = userService.changePassword(updateUserRequest.getOldPass(), updateUserRequest.getNewPass(), updateUserRequest.getConfirmNewPass());

        if (result) {
            return ResponseEntity.ok("Đổi mật khẩu thành công !!");
        } else {
            return ResponseEntity.badRequest().body("Thay đổi mật khẩu thất bại");
        }
    }

    @PutMapping("/account")
    public ResponseEntity<Users> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        Users updatedUser = userService.updateUser(updateUserRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/account")
    public ResponseEntity<Users> getInfoUser() {
        Users infoUser = userService.getCurrentLoggedInUser();
        return new ResponseEntity<>(infoUser, HttpStatus.OK);
    }

    @PostMapping("/account/addresses")
    public ResponseEntity<Address> addNewAddress(@RequestBody AddressRequest addressRequest) {
        Address newAddress = userService.addNewAddress(addressRequest);
        return new ResponseEntity<>(newAddress, HttpStatus.OK);
    }

}

