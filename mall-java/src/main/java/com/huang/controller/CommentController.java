package com.huang.controller;


import com.huang.common.lang.Result;
import com.huang.entity.Comment;
import com.huang.entity.Commodity;
import com.huang.entity.User;
import com.huang.utils.DateUtil;
import com.huang.vo.CommentDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;


@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @ApiOperation("获取某个软件所有评论")
    @GetMapping("/list/{commodityId}")
    public Result list(@PathVariable("commodityId") Integer commodityId){

        List<CommentDto> commentDtoList = new ArrayList<>();

        List<Comment> commentList = commentService.getByCommodityId(commodityId);

        commentList.forEach(comment -> {
            CommentDto commentDto = new CommentDto();
            User user = userService.getById(comment.getUserId());

            commentDto.setId(comment.getId());
            commentDto.setUserName(user.getName());
            commentDto.setCommentTime(comment.getCommentTime());
            commentDto.setContent(comment.getContent());
            commentDtoList.add(commentDto);
        });

        return Result.succ(commentDtoList);
    }

    @ApiOperation("获取所有评论")
    @GetMapping("/lists")
    public Result lists(){

        List<CommentDto> commentDtoList = new ArrayList<>();

        List<Comment> commentList = commentService.list();

        commentList.forEach(comment -> {
            CommentDto commentDto = new CommentDto();
            User user = userService.getById(comment.getUserId());
            Commodity commodity = commodityService.getById(comment.getCommodityId());

            commentDto.setId(comment.getId());
            commentDto.setUserName(user.getName());
            commentDto.setCommodityName(commodity.getName());
            commentDto.setContent(comment.getContent());
            commentDto.setCommentTime(comment.getCommentTime());
            commentDtoList.add(commentDto);
        });

        return Result.succ(commentDtoList);
    }

    @ApiOperation("搜索用户评论接口")
    @GetMapping("/select/{userName}")
    public Result select(@PathVariable("userName") String userName){

        List<CommentDto> commentDtoList = new ArrayList<>();

        User user = userService.getByName(userName);

        List<Comment> commentList = commentService.getByUserId(user.getUserId());

        commentList.forEach(comment -> {
            CommentDto commentDto = new CommentDto();
            Commodity commodity = commodityService.getById(comment.getCommodityId());

            commentDto.setId(comment.getId());
            commentDto.setUserName(user.getName());
            commentDto.setCommodityName(commodity.getName());
            commentDto.setContent(comment.getContent());
            commentDto.setCommentTime(comment.getCommentTime());
            commentDtoList.add(commentDto);
        });

        return Result.succ(commentDtoList);
    }


    @ApiOperation("发布评论接口")
    @PostMapping("/save")
    public Result save(@RequestBody CommentDto commentDto,Principal principal){

        Comment comment = new Comment();
        User user = userService.getByName(principal.getName());

        String time = DateUtil.getJson(new Date());

        comment.setUserId(user.getUserId());
        comment.setCommentTime(time);
        comment.setCommodityId(commentDto.getCommodityId());
        comment.setContent(commentDto.getContent());

        commentService.save(comment);

        return Result.succ("发布成功");
    }

    @ApiOperation("删除评论")
    @PostMapping("/delete")
    public Result deleteByIds(@RequestBody Integer[] ids){

        commentService.removeByIds(Arrays.asList(ids));

        return Result.succ("移除成功");
    }

}
