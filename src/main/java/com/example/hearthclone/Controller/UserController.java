package com.example.hearthclone.Controller;


import com.example.hearthclone.model.User;
import com.example.hearthclone.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getallUsers();
    }
    @PostMapping
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}



//    private final UserService userService;
//
//    public static record RegisterRequest(String username, String password, String displayName) {}
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    // Регистрация: POST /api/users/register
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
//        try {
//            if (req.username() == null || req.username().isBlank()
//                    || req.password() == null || req.password().isBlank()) {
//                return ResponseEntity.badRequest().body("username and password required");
//            }
//            User u = userService.register(req.username(), req.password(), req.displayName());
//            // не возвращаем пароль
//            u.setPassword(null);
//            return ResponseEntity.status(HttpStatus.CREATED).body(u);
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
//        }
//    }
//
//    // Получить всех пользователей (для дев/тестов) GET /api/users
//    @GetMapping
//    public ResponseEntity<List<User>> all() {
//        List<User> list = userService.findAll();
//        list.forEach(u -> u.setPassword(null));
//        return ResponseEntity.ok(list);
//    }
//
//    // Получить пользователя по id GET /api/users/{id}
//    @GetMapping("/{id}")
//    public ResponseEntity<?> byId(@PathVariable Long id) {
//        return userService.findById(id)
//                .map(u -> {
//                    u.setPassword(null);
//                    return ResponseEntity.ok(u);
//                })
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found"));
//    }
//}
