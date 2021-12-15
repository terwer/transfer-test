package com.test.transfer.servlet;

import com.test.transfer.factory.BeanFactory;
import com.test.transfer.factory.ProxyFactory;
import com.test.transfer.pojo.Result;
import com.test.transfer.service.TransferService;
import com.test.transfer.utils.JsonUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "transferServlet", value = "/transferServlet")
public class TransferServlet extends HttpServlet {

    // 1. 实例化service层对象
    // private TransferService transferService = new TransferServiceImpl();
    // 利用IOC实例化对象
    // private TransferService transferService = (TransferService) BeanFactory.getBean("transferService");
    // 使用动态代理增强功能，添加事务控制
    // private ProxyFactory proxyFactory = (ProxyFactory) BeanFactory.getBean("proxyFactory");
    // private TransferService transferService = (TransferService) proxyFactory.getCglibProxy(BeanFactory.getBean("transferService"));
    private TransferService transferService = null;

    @Override
    public void init() throws ServletException {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        ProxyFactory proxyFactory = (ProxyFactory) webApplicationContext.getBean("proxyFactory");
        transferService = (TransferService)proxyFactory.getJdkProxy(webApplicationContext.getBean("transferService"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求体的字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);

        Result result = new Result();

        try {

            // 2. 调用service层方法
            transferService.transfer(fromCardNo,toCardNo,money);
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("201");
            result.setMessage(e.toString());
        }

        // 响应
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(JsonUtils.object2Json(result));
    }
}
