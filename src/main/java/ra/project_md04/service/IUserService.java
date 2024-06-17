package ra.project_md04.service;

import org.springframework.data.domain.Page;
import ra.project_md04.model.dto.request.AddressRequest;
import ra.project_md04.model.dto.request.UpdateUserRequest;
import ra.project_md04.model.entity.Address;
import ra.project_md04.model.entity.Users;

import java.util.List;

public interface IUserService {
    Page<Users> getUserPaging(String searchName, Integer page, Integer itemPage, String orderBy, String direction);
    Page<Users> getAllUser(Integer page, Integer itemPage, String orderBy, String direction);
    Users getUserById(Long id);
    Users getUserByUserName(String username);
    Users updateUserStatus(Long userId, Boolean status);
    boolean changePassword(String oldPass, String newPass, String confirmNewPass);
    Users updateUser(UpdateUserRequest updateUserRequest);
    Users getCurrentLoggedInUser();
    Address addNewAddress(AddressRequest addressRequest);
}
