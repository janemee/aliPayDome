package model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Donfy on 2017/8/3
 */
public abstract class GenericPo<PK> implements Serializable, Cloneable {

    @Getter
    @Setter
    /* "id" -> 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected PK id;

    @Getter
    @Setter
    /* uuid */
    protected String uuid;
    @Getter
    @Setter
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    /* "createTime" -> 创建时间 */
    protected Date createTime;

    @Getter
    @Setter
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    /* "updateTime" -> 更新时间 */
    protected Date updateTime;

    @Getter
    @Setter
    /* 删除标志 */
    protected Integer delFlag;
    @Getter
    @Setter
    /* 创建人 */
    protected String creator;

    @Getter
    @Setter
    /* 更新人 */
    protected String updator;

    /**
     * 0,否:no<br>
     * 1,是:yes
     **/
    public enum DELFLAG {
        /**
         * 0,否:no
         **/
        NO("否", 0),

        /**
         * 1,是:yes
         **/
        YES("是", 1);

        public final int code;
        public final String value;
        private static Map<Integer, String> map = new HashMap<>();

        DELFLAG(String value, int code) {
            this.code = code;
            this.value = value;
        }

        public static String getValue(Integer code) {
            if (null == code) {
                return null;
            }
            for (DELFLAG status : DELFLAG.values()) {
                if (status.code == code) {
                    return status.value;
                }
            }
            return null;
        }

        public static Integer getCode(String value) {
            if (null == value || "".equals(value)) {
                return null;
            }
            for (DELFLAG status : DELFLAG.values()) {
                if (status.value.equals(value)) {
                    return status.code;
                }
            }
            return null;
        }

        public static Map<Integer, String> getEnumMap() {
            if (map.size() == 0) {
                for (DELFLAG status : DELFLAG.values()) {
                    map.put(status.code, status.value);
                }
            }
            return map;
        }
    }


}
