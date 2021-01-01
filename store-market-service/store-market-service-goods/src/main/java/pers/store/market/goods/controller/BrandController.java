package pers.store.market.goods.controller;

import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.store.market.common.pojo.PageResult;
import pers.store.market.common.pojo.Result;
import pers.store.market.common.pojo.StatusCode;
import pers.store.market.goods.domain.pojo.Brand;
import pers.store.market.goods.service.IBrandService;

import java.util.List;
import java.util.Map;

/**
 * @author 高枫
 * @date 2021/1/1 下午8:08
 */
@Api(tags = "品牌服务前端控制器")
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @GetMapping("/findList")
    @ApiOperation(value = "获取品牌列表")
    public Result<List<Brand>> findList() {
        List<Brand> brandList = brandService.findList();
        return new Result<>(true, StatusCode.OK, "查询成功", brandList);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询品牌信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "品牌ID")})
    public Result<Brand> findById(@PathVariable("id") Integer id) {
        Brand brand = brandService.findById(id);
        return new Result<>(true, StatusCode.OK, "查询成功", brand);
    }

    @PostMapping
    @ApiOperation(value = "添加品牌信息")
    public Result add(@RequestBody Brand brand) {
        brandService.add(brand);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "根据ID修改品牌信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "品牌ID")})
    public Result update(@PathVariable("id") Integer id, @RequestBody Brand brand) {
        brand.setId(id);
        brandService.update(brand);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除品牌信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "Integer", required = true, value = "品牌ID")})
    public Result delById(@PathVariable("id") Integer id) {
        brandService.delById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @GetMapping("/search")
    @ApiOperation(value = "组合条件查询品牌列表")
    public Result<List<Brand>> search(@RequestParam Map searchMap) {
        List<Brand> list = brandService.list(searchMap);
        return new Result<>(true, StatusCode.OK, "查询成功", list);
    }

    @GetMapping("/search/{page}/{size}")
    @ApiOperation(value = "分页查询品牌列表")
    public Result findPage(@PathVariable("page") int page, @PathVariable("size") int size) {
        Page<Brand> pageInfo = brandService.findPage(page, size);
        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    @GetMapping("/searchPage/{page}/{size}")
    @ApiOperation(value = "组合条件分页查询品牌列表")
    public Result findPage(@RequestParam Map searchMap, @PathVariable("page") int page, @PathVariable("size") int size) {
        Page pageInfo = brandService.findPage(searchMap, page, size);
        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getResult());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }
}
