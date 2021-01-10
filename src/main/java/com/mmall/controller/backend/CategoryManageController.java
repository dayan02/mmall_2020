package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author: Guoxy
 * @Email: guoxy@primeton.com
 * @create: 2021-01-10 10:42
 **/
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    //新增分类
    @RequestMapping("/add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") Integer parentId){

        //判断登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //判断是否是管理员身份
        if(iUserService.checkAdmin(user).isSuccess()){
            //处理分类
            return iCategoryService.addCategory(parentId,categoryName);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    //更新categoryName
    @RequestMapping("/set_categoryName.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        //判断登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //判断是否是管理员身份
        if(iUserService.checkAdmin(user).isSuccess()){
            //更新categoryName
           return iCategoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return ServerResponse.createByErrorMessage("权限操作，需要管理员权限");
        }
    }

    @RequestMapping("/get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        //判断登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //判断是否是管理员身份
        if(iUserService.checkAdmin(user).isSuccess()){
            //传入一个categoryId,获取当前categoryId所有子节点信息，平级且不递归查询
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("权限操作，需要管理员权限");
        }
    }

    @RequestMapping("/get_deep_category.do")
    @ResponseBody
    public ServerResponse getChildrenAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        //判断登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorCodeAndMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //判断是否是管理员身份
        if(iUserService.checkAdmin(user).isSuccess()){
            //查询当前子节点及递归子节点
           return iCategoryService.selectCategoryAndDeepChildrenById(categoryId);

        }else{
            return ServerResponse.createByErrorMessage("权限操作，需要管理员权限");
        }
    }

}
