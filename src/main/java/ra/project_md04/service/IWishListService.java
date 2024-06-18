package ra.project_md04.service;

import ra.project_md04.model.dto.request.WishListRequest;
import ra.project_md04.model.dto.response.WishListResponse;

import java.util.List;

public interface IWishListService {
    WishListResponse addWishList(WishListRequest wishListRequest);
    List<WishListResponse> getWishList();
    void deleteWishList(Long wishListId);
}
