package com.abhinavp.webapp.handler;

import com.abhinavp.platform.thrift.TResult;
import com.abhinavp.platform.thrift.TUser;
import com.abhinavp.platform.thrift.TUserStatus;
import com.abhinavp.platform.thrift.UserService;


/**
 * @author abhinav.prakash
 * 
 */
public class UserServiceHandler implements UserService.Iface
{

    public TResult addUser(final TUser user)
    {
        TResult result = new TResult();
        if (user.active)
        {
            result.setStatus(TUserStatus.ACTIVE);
        }
        else
        {
            result.setStatus(TUserStatus.INACTIVE);
        }

        return result;

    }

    /**
     * @param args
     */
    public static void main(final String[] args)
    {
        // TODO Auto-generated method stub

    }

}
