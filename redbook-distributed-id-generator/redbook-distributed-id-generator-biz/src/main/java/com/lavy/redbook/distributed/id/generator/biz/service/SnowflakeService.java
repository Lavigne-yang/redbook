package com.lavy.redbook.distributed.id.generator.biz.service;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lavy.redbook.distributed.id.generator.biz.constant.Constants;
import com.lavy.redbook.distributed.id.generator.biz.core.IDGen;
import com.lavy.redbook.distributed.id.generator.biz.core.common.PropertyFactory;
import com.lavy.redbook.distributed.id.generator.biz.core.common.Result;
import com.lavy.redbook.distributed.id.generator.biz.core.common.ZeroIDGen;
import com.lavy.redbook.distributed.id.generator.biz.core.snowflake.SnowflakeIDGenImpl;
import com.lavy.redbook.distributed.id.generator.biz.exception.InitException;

/**
 * @author <a href="lavyoung1325@outlook.com">lavy</a>
 * @date: 2025/7/14
 * @version: v1.0.0
 * @description:
 */
@Service("SnowflakeService")
public class SnowflakeService {
    private Logger logger = LoggerFactory.getLogger(SnowflakeService.class);

    private IDGen idGen;

    public SnowflakeService() throws InitException {
        Properties properties = PropertyFactory.getProperties();
        boolean flag = Boolean.parseBoolean(properties.getProperty(Constants.LEAF_SNOWFLAKE_ENABLE, "true"));
        if (flag) {
            String zkAddress = properties.getProperty(Constants.LEAF_SNOWFLAKE_ZK_ADDRESS);
            int port = Integer.parseInt(properties.getProperty(Constants.LEAF_SNOWFLAKE_PORT));
            idGen = new SnowflakeIDGenImpl(zkAddress, port);
            if (idGen.init()) {
                logger.info("Snowflake Service Init Successfully");
            } else {
                throw new InitException("Snowflake Service Init Fail");
            }
        } else {
            idGen = new ZeroIDGen();
            logger.info("Zero ID Gen Service Init Successfully");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }
}
