package com.example.pipayshopapi.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.Collections;


public class FastAuto1GeneratorTest {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://111.230.16.231:3306/new_pipayShop?characterEncoding=utf-8&userSSL=false", "new_pipayShop", "TEZfswirDJzdc8NJ")
                .globalConfig(builder -> {
                    builder.author("wzx") // 设置作者
//.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\file"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example") // 设置父包名
                            .moduleName("pipayshopapi") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "C:\\file"));
// 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tradin_order") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker
                .execute();
    }
}
