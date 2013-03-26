package com.abhinavp.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
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
    public void init() throws ServletException
    {

    }

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
            IOException
    {
        try
        {
            response.setContentType("application/x-thrift");
            UserService.Iface handler = new UserServiceHandler();

            processor = new UserService.Processor<UserService.Iface>(handler);

            TTransport transport = new TIOStreamTransport(request.getInputStream(), response.getOutputStream());

            TProtocol inProtocol = new TBinaryProtocol(transport);
            TProtocol outProtocol = new TBinaryProtocol(transport);

            processor.process(inProtocol, outProtocol);
        }
        catch (TException te)
        {
            throw new ServletException(te);
        }

    }

}
