package ${controllerPackagePath};

import com.west.lake.blog.model.SingleValueResult;
import ${entityPackagePath}.${className};
import ${serviceFacePackagePath}.${className}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* ${classDesc}操作接口
*
* @author ${authorName}
* Created on ${createDate}.
*/
@Api("${classDesc}")
@RestController
@RequestMapping(path = "${className?lower_case}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ${className}Controller {

@Resource
private ${className}Service ${className?lower_case}Service;

/**
* 新增${classDesc}
*
* @return
*/
@ApiOperation("新增${classDesc}")
@PostMapping(path = "add")
public ${className} add(
) {
//TODO("not implement")
return ${className?lower_case}Service.add();
}

/**
* 删除${classDesc}
*
* @param id 要删除的${classDesc}的id
* @return
*/
@DeleteMapping("{id}")
public SingleValueResult<String> delete(
    @PathVariable("id") String id
    ) {
    ${className?lower_case}Service.delete(id)
    return new SingleValueResult<>("delete success");
        }

        /**
        * 更新${classDesc}
        *
        * @param id 要更新的${classDesc}的id
        * @return
        */
        @PutMapping("update")
        public ${className} update(
        @RequestParam("id") String id
        ) {
        //TODO("not implement")
        return ${className?lower_case}Service.update(id);
        }


        /**
        * 查询${classDesc}列表
        *
        * @return
        */
        @GetMapping("list")
        public List<${className}> list() {
        return ${className?lower_case}Service.list();
        }

        /**
        * 获取${classDesc}详情
        *
        * @param id ${classDesc}id
        * @return
        */
        @GetMapping("{id}")
        public ${className} byId(@PathVariable("id") String id) {
        return ${className?lower_case}Service.byId(id);
        }
}
