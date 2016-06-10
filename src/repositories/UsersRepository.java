package repositories;

import models.User;

public class UsersRepository extends BaseRepository<User> {

    public UsersRepository() {
        super();
        this.tClass = User.class;
    }

    public Boolean checkIfUserExists(User user) {
        User verifyUser = this.getAll().stream().filter(u -> u.getUsername().equals(user.getUsername())).findFirst().orElse(null);
        if (verifyUser == null) {
            return false;
        }

        return true;
    }

    public User getByUsernameAndPassword(String username, String password) {
        return this.getAll().stream().filter((User) -> User.getUsername().equals(username) && User.getPassword().equals(password)).findFirst().orElse(null);
    }
}