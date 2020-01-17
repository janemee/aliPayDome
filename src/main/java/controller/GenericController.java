package controller;


import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import model.BussinessException;
import model.GenericPo;
import model.ResultEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import util.LogUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * json数据类controller 基础实现
 *
 * @param <PK> 对象主键类型
 * @param <PO> 对象
 */
@Slf4j
public class GenericController<PK, PO extends GenericPo<PK>> {

    /**
     * 操作成功
     */
    protected <T> ResultEntity<T> ok() {
        return result(null, "操作成功", ResultEntity.SUCCESS);
    }

    /**
     * 操作成功
     *
     * @param po 返回数据
     */
    protected <T> ResultEntity<T> ok(T po) {
        return result(po, "操作成功", ResultEntity.SUCCESS);
    }

    /**
     * 操作成功
     *
     * @param po 返回数据
     */
    protected <T> ResultEntity<T> ok(T po, String msg) {
        return result(po, msg, ResultEntity.SUCCESS);
    }

    /**
     * 操作失败
     */
    protected <T> ResultEntity<T> fail(String msg) {
        return result(null, msg, ResultEntity.FAIL);
    }
    /**
     * 操作失败
     */
    protected <T> ResultEntity<T> fail(String msg,int code) {
        return result(null, msg, code);
    }

    /**
     * 操作失败
     */
    protected <T> ResultEntity<T> fail() {
        return result(null, "操作失败", ResultEntity.FAIL);
    }

    /**
     * 操作失败
     */
    protected <T> ResultEntity<T> fail(T po) {
        return result(po, "操作失败", ResultEntity.FAIL);
    }

    /**
     * 返回消息记录
     *
     * @param resultData 返回实体(可空)
     * @param msg        返回消息
     * @param code       成功/错误
     */
    protected <T> ResultEntity<T> result(T resultData, String msg, int code) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setData(resultData);
        resultEntity.setCode(code);
        resultEntity.setMessage(msg);
        return resultEntity;
    }

    protected <T> ResultEntity<T> result(String msg, int code) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setData(null);
        resultEntity.setCode(code);
        resultEntity.setMessage(msg);
        return resultEntity;
    }

    /**
     * 返回消息记录
     *
     * @param resultData 返回实体
     */
    protected <T> ResultEntity<T> result(ResultEntity resultData) {
        if (resultData.getCode() == ResultEntity.SUCCESS) {
            return ok();
        }
        return fail(resultData.getMessage());
    }

    /**
     * 全局异常处理
     */
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResultEntity exception(Exception e) {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setCode(ResultEntity.FAIL);
//        resultEntity.setMessage(e.getMessage());
        if (e instanceof BussinessException) {
            LogUtil.error("Exception", e, GenericController.class);
            if (((BussinessException) e).getCode() == BussinessException.Code.E401.code) {
                resultEntity.setCode(BussinessException.Code.E401.code);
            } else if (((BussinessException) e).getCode() == BussinessException.Code.E402.code) {
                resultEntity.setCode(BussinessException.Code.E402.code);
            } else if (((BussinessException) e).getCode() == BussinessException.Code.E404.code) {
                resultEntity.setCode(BussinessException.Code.E404.code);
            } else if (((BussinessException) e).getCode() == ResultEntity.REALNAME_AUTHING) {
                resultEntity.setCode(ResultEntity.REALNAME_AUTHING);
            } else if (((BussinessException) e).getCode() == ResultEntity.NO_REALNAME_AUTH) {
                resultEntity.setCode(ResultEntity.NO_REALNAME_AUTH);
            } else if (((BussinessException) e).getCode() == ResultEntity.PAY_PASSWOR_NOT_SET) {
                resultEntity.setCode(ResultEntity.PAY_PASSWOR_NOT_SET);
            }
            resultEntity.setMessage(e.getMessage());
        } else if (e instanceof MySQLIntegrityConstraintViolationException) {
            log.error(e.getMessage());
        } else {
            e.printStackTrace();
            log.error("{}", e.getMessage(), e);
        }

        return resultEntity;
    }


    public static HttpServletRequest getRequest() {
        final RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        final ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        return sra.getRequest();
    }

    public static HttpServletResponse getResponse() {
        final RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        final ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        return sra.getResponse();
    }
}
