package com.csc3402.lab.formlogin.controller;

import com.csc3402.lab.formlogin.model.Group;
import com.csc3402.lab.formlogin.model.User;
import com.csc3402.lab.formlogin.repository.UserRepository;
import com.csc3402.lab.formlogin.service.GroupService;
import com.csc3402.lab.formlogin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;
    private final GroupService groupService;
    private final UserRepository userRepository;

    public UserController(UserService userService, GroupService groupService, UserRepository userRepository) {
        this.userService = userService;
        this.groupService = groupService;
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

    //    ---------     TRANSACTION     ----------     //
    @GetMapping("/transaction")
    public String userTransaction() {
        return "transaction";
    }


    //    ---------     BUDGET     ----------     //
    @GetMapping("/budget")
    public String userBudget(Model model) {
//        String email = getCurrentUserEmail();
//        User user = userRepository.findByEmail(email);
//        List<Group> groups = groupService.listGroupsByUserId(user.getUserId());
        model.addAttribute("groups", groupService.listAllGroups());
//        model.addAttribute("groups", groups);
        return "budget";
    }

    @GetMapping("signup")
    public String showAddNewGroupForm(Group group, Model model) {
        return "add-group";
    }

    @PostMapping("add")
    public String addGroup(@Valid Group group, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-group";
        }

        // Extract the month from the startDate or endDate
        String month = group.getStartDate().split("-")[1];

        // Get the list of groups for the same month
        List<Group> allGroups = groupService.listAllGroups();
        List<Group> sameMonthGroups = allGroups.stream()
                .filter(g -> {
                    String groupMonth = g.getStartDate().split("-")[1];
                    return groupMonth.equals(month);
                })
                .collect(Collectors.toList());

        // Check if a group with the same name exists for the specified month
        boolean nameExists = sameMonthGroups.stream()
                .anyMatch(g -> g.getCategory().equalsIgnoreCase(group.getCategory()));
        if (nameExists) {
            return "redirect:/user/signup?error2";
        }

        // Calculate the total budget for that month
        double totalBudget = sameMonthGroups.stream()
                .mapToDouble(Group::getBamount)
                .sum();

        // Check if adding the new group's budget exceeds the limit
        if (totalBudget + group.getBamount() <= 1000) {
            groupService.addNewGroup(group);
            return "redirect:/user/list";
        } else {
            // Redirect to the form with an error message
            return "redirect:/user/signup?error1";
        }
    }

    @GetMapping("update")
    public String showUpdateMainForm(Model model) {
        // Get the list of groups for June (month = 6)
        List<Group> allGroups = groupService.listAllGroups();

        // Filter groups for the month of June (month = 6)
        List<Group> juneGroups = allGroups.stream()
                .filter(g -> {
                    // Extract month from startDate
                    String groupMonth = g.getStartDate().split("-")[1];
                    return groupMonth.equals("06"); // Filter for June (month = 6)
                })
                .collect(Collectors.toList());

        model.addAttribute("groups", juneGroups);
        return "choose-category-to-update";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Group group = groupService.findGroupById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
        model.addAttribute("group", group);
        return "update-category";
    }

    @PostMapping("/update/{id}")
    public String updateGroup(@PathVariable("id") long id, @Valid Group group, BindingResult result, Model model) {
        if (result.hasErrors()) {
            group.setBudgetId(id);
            return "update-category";
        }

        // Extract the month from the startDate or endDate
        String month = group.getStartDate().split("-")[1]; // Assuming your date format allows this

        // Get the list of groups for the same month (June)
        List<Group> allGroups = groupService.listAllGroups();
        List<Group> juneGroups = allGroups.stream()
                .filter(g -> isMonthInRange(g, "6")) // Assuming "6" is June
                .collect(Collectors.toList());

        // Remove the current group being updated from the list
        juneGroups = juneGroups.stream()
                .filter(g -> g.getBudgetId() != id)
                .collect(Collectors.toList());

        // Check if a group with the same name exists for the specified month
        boolean nameExists = juneGroups.stream()
                .anyMatch(g -> g.getCategory().equalsIgnoreCase(group.getCategory()));
        if (nameExists) {
            return "redirect:/user/edit/" + id + "?error2";
        }

        // Calculate the total budget for June
        double totalBudget = juneGroups.stream()
                .mapToDouble(Group::getBamount)
                .sum();

        // Check if adding the new group's budget exceeds the limit
        if (totalBudget + group.getBamount() <= 1000) {
            groupService.updateGroup(group);
            return "redirect:/user/list";
        } else {
            // Redirect to the form with an error message
            return "redirect:/user/edit/" + id + "?error1";
        }
    }

    @GetMapping("delete")
    public String showDeleteMainForm(Model model) {
        // Get the list of groups for June (month = 6)
        List<Group> allGroups = groupService.listAllGroups();

        // Filter groups for the month of June (month = 6)
        List<Group> juneGroups = allGroups.stream()
                .filter(g -> {
                    // Extract month from startDate
                    String groupMonth = g.getStartDate().split("-")[1];
                    return groupMonth.equals("06"); // Filter for June (month = 6)
                })
                .collect(Collectors.toList());

        model.addAttribute("groups", juneGroups);
        return "choose-category-to-delete";
    }

    @GetMapping("delete/{id}")
    public String deleteGroup(@PathVariable("id") long id, Model model) {
        Group group = groupService.findGroupById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));

        groupService.deleteGroup(group);
        return "redirect:/user/list";
    }

    @GetMapping("/category")
    @ResponseBody
    public List<Group> getCategoryData(@RequestParam("month") String month) {
        List<Group> groups = groupService.listAllGroups();
        List<Group> filteredGroups = groups.stream()
                .filter(group -> isMonthInRange(group, month))
                .collect(Collectors.toList());
        return filteredGroups;
    }

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
}
