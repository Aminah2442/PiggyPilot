package com.csc3402.lab.formlogin.controller;

import com.csc3402.lab.formlogin.model.Group;
import com.csc3402.lab.formlogin.model.User;
import com.csc3402.lab.formlogin.repository.UserRepository;
import com.csc3402.lab.formlogin.service.BudgetService;
import com.csc3402.lab.formlogin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;
    private final BudgetService budgetService;
    private final UserRepository userRepository;

    public UserController(UserService userService, BudgetService budgetService, UserRepository userRepository) {
        this.userService = userService;
        this.budgetService = budgetService;
        this.userRepository = userRepository;
    }

    //    ---------     DASHBOARD     ----------     //
    @GetMapping("/")
    public String dashboard() {
        return "dashboard";
    }

    //    ---------     PROFILE     ----------     //
    @GetMapping("/profile")
    public String userProfile(Model model) {

        // Get the logged-in user's email from the security context
        String email = getCurrentUserEmail();

        // Find the user by email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "redirect:/login?logout"; // Updating email logs user out
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateUserProfile(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

        // Check if the new email already exists in the database
        User existingUserByEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserByEmail != null && !existingUserByEmail.getUserId().equals(user.getUserId())) {
            return "redirect:/user/profile?error";
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "profile";
        }

        // Update user details in the database
        userService.updateUser(user);
        return "redirect:/user/profile?success";
    }

    //    ---------     TRANSACTION     ----------     //
    @GetMapping("/transaction")
    public String userTransaction() {
        return "transaction";
    }


    //    ---------     BUDGET     ----------     //
    @GetMapping("/budget")
    public String userBudget(Model model) {

        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "redirect:/login?logout"; // Log out if the user is not found
        }

        // Get the groups for the current user
        List<Group> groups = budgetService.listGroupsByUserId(user.getUserId());
        model.addAttribute("groups", groups);
        model.addAttribute("user", user);
        return "budget2";
    }

    @GetMapping("/budget/category")
    @ResponseBody
    public List<Group> getCategoryData(@RequestParam("month") String month) {

        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return Collections.emptyList();
        }
        List<Group> groups = budgetService.listGroupsByUserId(user.getUserId());

        // If month is "00", return all groups without filtering
        if ("00".equals(month)) {
            return groups;
        }

        // Filter groups based on the month
        List<Group> filteredGroups = groups.stream()
                .filter(group -> isMonthInRange(group, month))
                .collect(Collectors.toList());

        return filteredGroups;
    }









    //    ---------     METHODS     ----------     //
    private boolean isMonthInRange(Group group, String month) {
        try {
            String startDate = group.getStartDate();
            String endDate = group.getEndDate();
            if (startDate == null || endDate == null) {
                return false;
            }
            int startMonth = Integer.parseInt(startDate.split("-")[1]);
            int endMonth = Integer.parseInt(endDate.split("-")[1]);
            int filterMonth = Integer.parseInt(month);
            return startMonth == filterMonth || endMonth == filterMonth;
        } catch (Exception e) {
            // Log the exception and the problematic group details for debugging
            System.err.println("Error processing group: " + group);
            e.printStackTrace();
            return false;
        }
    }

    // Get the email of the current logged-in user
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}
