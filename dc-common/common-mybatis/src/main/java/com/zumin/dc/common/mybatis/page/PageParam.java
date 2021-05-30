package com.zumin.dc.common.mybatis.page;


import java.util.List;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 分页参数
 *
 * @param <T> 源数据类型
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageParam<T> {

  /**
   * 分页查询方法
   */
  private Supplier<List<T>> queryFunc;

  /**
   * 查询的页数
   */
  private Integer page;

  /**
   * 每页个数
   */
  private Integer pageSize;

  public PageParam(Supplier<List<T>> queryFunc) {
    this.queryFunc = queryFunc;
  }
}
