package com.west.lake.blog.controller;

import com.west.lake.blog.model.SingleValueResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件操作
 *
 * @author futao
 * Created on 2018/9/26.
 */
@Api("文件操作")
@RestController
@RequestMapping(path = "file")
public class FileController {
    /**
     * 上传文件
     *
     * @param file 文件
     * @return
     */
    @ApiOperation("上传文件")
    @PostMapping(path = "upload")
    public SingleValueResult<String> upload(
            @RequestParam(value = "file") MultipartFile file
    ) {
        try {
            System.out.println(file.getSize());
            FileUtils.writeByteArrayToFile(new File(System.getProperty("user.dir") + "/src/main/resources/uploadFiles/" + file.getOriginalFilename()), file.getBytes());
            return new SingleValueResult<>("上传成功!!!");
        } catch (IOException e) {
            e.printStackTrace();
            return new SingleValueResult<>("上传失败!!!" + e.getMessage());
        }
    }


    /**
     * 文件下载
     */
    @ApiOperation("文件下载")
    @GetMapping("download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) {
//        try (InputStream inputStream = new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/uploadFiles/", fileName));
//             OutputStream outputStream = response.getOutputStream()) {
//            response.setContentType("application/x-download");
//            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//            IOUtils.copy(inputStream, outputStream);
//            outputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw LogicException.le(ErrorMessage.LogicErrorMessage.FILE_DOWN_LOAD_FAIL);
//        }

        //下载的文件携带这个名称
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        //文件下载类型--二进制文件
        response.setContentType("application/octet-stream");
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/uploadFiles/" + fileName);
            ;
            byte[] content = new byte[fis.available()];
            fis.read(content);
            fis.close();

            ServletOutputStream sos = response.getOutputStream();
            sos.write(content);

            sos.flush();
            sos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
