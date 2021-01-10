package com.mmall.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author: Guoxy
 * @Email: guoxy@primeton.com
 * @create: 2021-01-10 10:55
 **/
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

        @Autowired
        private CategoryMapper categoryMapper;

        public ServerResponse addCategory(Integer parentId,String categoryName){
            if(parentId == null || StringUtils.isBlank(categoryName)){
                return ServerResponse.createByErrorMessage("添加品类参数错误");
            }
            Category category = new Category();
            category.setName(categoryName);
            category.setParentId(parentId);
            category.setStatus(true);//这个品类是可用的

            int resultCount  = categoryMapper.insert(category);
            if(resultCount > 0){
                return ServerResponse.createBySuccessMessage("添加品类成功");
            }
            return ServerResponse.createByErrorMessage("添加品类失败");
        }

        public ServerResponse updateCategoryName(Integer categoryId,String categoryName){
            if(categoryId == null || StringUtils.isBlank(categoryName)){
                return ServerResponse.createByErrorMessage("更新品类参数错误");
            }
            Category category = new Category();
            category.setId(categoryId);
            category.setName(categoryName);

            int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
            if (rowCount > 0){
                return ServerResponse.createBySuccessMessage("修改品类名字成功");
            }
            return ServerResponse.createByErrorMessage("修改品类名字失败");
        }

        public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
            List<Category> list = categoryMapper.selectChildrenParallelByParentId(categoryId);
            if(CollectionUtils.isEmpty(list)){
                logger.info("未找到当前分类的子分类");
                return ServerResponse.createBySuccessMessage("未找到该品类");
            }
            return ServerResponse.createBySuccess(list);
        }

        public ServerResponse selectCategoryAndDeepChildrenById(Integer categorId){
            Set<Category> categorySet = Sets.newHashSet();
            findChildCategory(categorySet,categorId);
            List<Integer> categoryList = Lists.newArrayList();
            if(categorId != null){
                for (Category categoryItem:categorySet) {
                    categoryList.add(categoryItem.getId());
                }
            }
            return ServerResponse.createBySuccess(categoryList);
        }

         //递归查询,算出子节点
    //Set可以直接排重，需要重写equal和hashCode，因为Category不是基本的对象[Integer、String等]，使用对象的时候需要重写
        private Set<Category> findChildCategory(Set<Category> set,Integer categoryId){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category != null){
                set.add(category);
            }
            //查找子节点，递归要有退出条件
            //mybatis查询集合时，如果查不到，不会返回null对象，所以不用进行空判断
            List<Category> categoryList = categoryMapper.selectChildrenParallelByParentId(categoryId);
            for (Category categoryItem: categoryList) {
                findChildCategory(set,categoryItem.getId());
            }
            return set;
        }

}
