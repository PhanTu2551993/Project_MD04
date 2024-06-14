package ra.project_md04.service;

import org.springframework.data.domain.Page;
import ra.project_md04.model.entity.Users;

public interface IUserService {
    Page<Users> getUserPaging(String searchName, Integer page, Integer itemPage, String orderBy, String direction);
    Users getAllUser();
    Users getUserById(Long id);
    Users getUserByUserName(Users username);
}
