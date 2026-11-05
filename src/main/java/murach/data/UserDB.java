
package murach.data;

import murach.business.User;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserDB {

    private static final List<User> users =
            new CopyOnWriteArrayList<>();

    public static void insert(User user) {
        users.add(user);

        System.out.println(
                "Saved user: " + user.getEmail()
        );
    }
}
