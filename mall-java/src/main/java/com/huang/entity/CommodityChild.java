package com.huang.entity;

import java.math.BigDecimal;
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
@ApiModel(value="CommodityChild对象", description="")
public class CommodityChild extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "软件id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "软件名")
    private String name;

    @ApiModelProperty(value = "软件描述")
    private String operdescribe;

    @ApiModelProperty(value = "店铺名")
    private String shop;

    @ApiModelProperty(value = "软件图")
    private String image;

    @ApiModelProperty(value = "软件价格")
    private BigDecimal price;


}
