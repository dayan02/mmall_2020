package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * @author: Guoxy
 * @Email: guoxy@primeton.com
 * @create: 2021-01-10 10:54
 **/
public interface ICategoryService {

    ServerResponse addCategory(Integer parentId, String categoryName);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse selectCategoryAndDeepChildrenById(Integer categorId);
}
