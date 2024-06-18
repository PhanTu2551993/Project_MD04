package ra.project_md04.model.dto.response.converter;

import ra.project_md04.model.dto.response.WishListResponse;
import ra.project_md04.model.entity.WishList;

public class WishListConverter {
    public static WishListResponse toWishListResponse(WishList wishList) {
        return WishListResponse.builder()
                .wishListId(wishList.getWishListId())
                .userId(wishList.getUser().getUserId())
                .productId(wishList.getProduct().getProductId())
                .wishListProductName(wishList.getProduct().getProductName())
                .build();
    }
}
