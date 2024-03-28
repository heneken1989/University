/*
 * package Controller;
 * 
 * import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.core.Authentication; import
 * org.springframework.security.core.context.SecurityContextHolder; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.ModelAttribute; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping;
 * 
 * import service.UserService;
 * 
 * 
 * 
 * @Controller public class UserController {
 * 
 * 
 * @Autowired UserService service;
 * 
 * @PostMapping("/signup")
 * 
 * public String SignUp(@ModelAttribute("username") String email ,
 * 
 * @ModelAttribute("name") String name ,
 * 
 * @ModelAttribute("password") String password,
 * 
 * @ModelAttribute("comfirm_password") String comfrim_password,
 * 
 * @ModelAttribute("phone") String phone, Model model) { UserExample example =
 * new UserExample(); example.createCriteria().andEmailEqualTo(email);
 * List<User> list =userMapper.selectByExample(example); if(list.size()==0) {
 * if(!password.equals(comfrim_password)) { model.addAttribute("Error",
 * "password not match"); //neu muon in nội dung eror ra html th:if="${error}"
 * -->th:text="${error}" System.out.println(model); return "signup"; }
 * 
 * //Băm Password bằng BCrypt String hashedPassword = new
 * BCryptPasswordEncoder().encode(password); User newUser = new User();
 * newUser.setEmail(email); newUser.setName(name);
 * newUser.setPassword(hashedPassword); newUser.setPhone(phone);
 * userMapper.insert(newUser); return "redirect:/index";
 * 
 * } else { model.addAttribute("error","Email da ton tai");
 * 
 * return "signup"; }
 * 
 * 
 * 
 * 
 * }
 * 
 * 
 * 
 * @RequestMapping({"/userdetail"}) public String UserDetail(Model model) {
 * Authentication authentication =
 * SecurityContextHolder.getContext().getAuthentication(); String
 * currentPrincipalName = authentication.getName(); UserExample example = new
 * UserExample();
 * example.createCriteria().andEmailEqualTo(currentPrincipalName); List<User>
 * list =userMapper.selectByExample(example); User userdetail =list.get(0);
 * 
 * model.addAttribute("userdetail", userdetail); return "userdetail"; }
 * 
 * 
 * }
 */


