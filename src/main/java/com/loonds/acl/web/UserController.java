package com.loonds.acl.web;

import com.loonds.acl.model.dto.UserDto;
import com.loonds.acl.model.enums.Role;
import com.loonds.acl.model.payload.CreateEmployeePayload;
import com.loonds.acl.security.AuthenticatedUser;
import com.loonds.acl.security.CurrentUser;
import com.loonds.acl.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Tag(name = "User APIs", description = "API for manage User Operations")
@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public ModelAndView getEmployeeList() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("employee/employee");
        return vi;
    }

    @Operation(description = "Create New User/Employee")
    @PostMapping("/admin/employees")
    UserDto addEmployee(@RequestBody @Valid CreateEmployeePayload payload) {
        return userService.create(payload);
    }

    @GetMapping("/admin")
    void createAdmin(){
        userService.createAdmin();
    }

    @Operation(description = "Update Given Employees Details")
    @PutMapping("/admin/employees/{userId}")
    Optional<UserDto> updateEmployee(@PathVariable String userId,
                                     @RequestBody UserDto userDto) {
        return userService.update(userId, userDto);
    }
    @Operation(description = "Update Given Employees Details")
    @PutMapping( "/employees")
    Optional<UserDto> updateLEmployee(@CurrentUser AuthenticatedUser user,
                                     @RequestBody UserDto userDto) {
        return userService.update(user.getUsername(), userDto);
    }

    @Operation(description = "Update User Role ")
    @PutMapping("/admin/users/{userId}/roles")
    Optional<UserDto> updateUserRole(@PathVariable String userId, @RequestBody Role role){
        return userService.updateRole(userId,role);
    }

    @Operation(description = "Get Details of  Given Employees Details")
    @GetMapping("/admin/employees/{userId}")
    Optional<UserDto> getEmployee(@PathVariable String userId) {
        return userService.get(userId);
    }

    @Operation(description = "Get Details of login User")
    @GetMapping("/me")
    UserDto me(@CurrentUser AuthenticatedUser authenticatedUser) {
        return userService.getMyProfile(authenticatedUser.getUsername());
    }

    @Operation(description = "Get List Of Employees")
    @GetMapping("/admin/employees")
    Page<UserDto> getAllUser() {
        return userService.getAll(Pageable.ofSize(10));
    }

    @Operation(description = "Delete Given User")
    @DeleteMapping("/admin/users/{id}")
    void delete(@PathVariable String id){
        userService.delete(id);
    }
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView previewProfile() {
        ModelAndView vi = new ModelAndView();
        vi.setViewName("profile/user-profile");
        return vi;
    }
}
