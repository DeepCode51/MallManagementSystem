package com.huang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author huang
 * @since 2021-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Collection对象", description="")
public class Collection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "我的收藏id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "软件id")
    private Integer commodityId;


}
