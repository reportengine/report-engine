package com.redhat.reportengine.server.restapi.testresult;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/services")
public class UserService {
	@GET
	@Path("/users")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Users getUsers() {
		Users users = new Users();
		List<User> userList = returnData();
		users.setUsers(userList);
		return users;
	}

	private List<User> returnData() {

		ArrayList<User> users = new ArrayList<User>();

		User u1 = new User();
		User u2 = new User();
		User u3 = new User();

		u1.setName("Jeeva");
		u1.setAge(27);

		u2.setName("Jeeva1");
		u2.setAge(25);

		u3.setName("Jeeva2");
		u3.setAge(27);

		users.add(u1);
		users.add(u2);
		users.add(u3);

		return users;
	}
}
