package com.abhinavp.webapp.servlet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;

import com.abhinavp.platform.thrift.UserService;
import com.abhinavp.webapp.handler.UserServiceHandler;


/**
 * @author abhinav.prakash
 * 
 */
public class UserServiceServlet extends HttpServlet
{
    private TProcessor processor;

    @Override
    public void init(final ServletConfig config) throws ServletException
    {
    }

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
            IOException
    {
        try
        {
            UserService.Iface handler = new UserServiceHandler();

            processor = new UserService.Processor<UserService.Iface>(handler);

            response.setContentType("application/x-thrift");

            TTransport transport = new TIOStreamTransport(request.getInputStream(), response.getOutputStream());

            TProtocol inProtocol = new TBinaryProtocol(transport);
            TProtocol outProtocol = new TBinaryProtocol(transport);

            processor.process(inProtocol, outProtocol);

        }
        catch (Exception te)
        {
            throw new ServletException(te);
        }

    }

    /*
     * used to test
     */
    private void test(final HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html");
        response.getWriter().print("this is user service");
        // response.getOutputStream().print("this is user service");
        response.setStatus(200);
    }

    @Override
    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
            IOException
    {
        doGet(request, response);

    }

    public static void main(final String[] args) throws IOException
    {
        String text_url = "http://" + "localhost:8080" + "/rest-api/userservice";
        URL url = new URL(text_url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(text_url);
        NameValuePair[] data =
        { new NameValuePair("Content-Type", "application/x-www-form-urlencoded") };

        method.setRequestBody(data);

        client.executeMethod(method);

        String response = method.getResponseBodyAsString();
        System.out.println(response);

    }
}
