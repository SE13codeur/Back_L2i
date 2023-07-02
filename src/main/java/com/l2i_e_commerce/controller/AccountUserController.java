package com.l2i_e_commerce.controller;

        import com.l2i_e_commerce.dto.ProfileDTO;
        import com.l2i_e_commerce.model.User;
        import com.l2i_e_commerce.service.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.Optional;

@RestController
@RequestMapping("/account/user")
public class AccountUserController {

    @Autowired
    private UserService userService;

    @PutMapping("/profile/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") Long id, @RequestBody ProfileDTO profileDTO) {
        Optional<User> existingUser = userService.findById(id);
        if (existingUser.isPresent()) {
            User modifiedUser = existingUser.get();
            modifiedUser.setUsername(profileDTO.getUsername());
            modifiedUser.setFirstname(profileDTO.getFirstname());
            modifiedUser.setLastname(profileDTO.getLastname());
            modifiedUser.setEmail(profileDTO.getEmail());
            modifiedUser.setPassword(profileDTO.getPassword());
            userService.save(modifiedUser);
            return new ResponseEntity<>(modifiedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
