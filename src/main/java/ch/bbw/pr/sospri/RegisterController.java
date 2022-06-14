package ch.bbw.pr.sospri;

import ch.bbw.pr.sospri.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;

import javax.validation.Valid;
import java.security.SecureRandom;

/**
 * RegisterController
 *
 * @author Peter Rutschmann
 * @version 26.03.2020
 */
@Controller
public class RegisterController {
    @Autowired
    MemberService memberservice;

    @GetMapping("/get-register")
    public String getRequestRegistMembers(Model model) {
        System.out.println("getRequestRegistMembers");
        model.addAttribute("registerMember", new RegisterMember());
        return "register";
    }

    @PostMapping("/get-register")
    public String postRequestRegistMembers(@Valid RegisterMember registerMember, BindingResult bindingResult, Model model) {
        System.out.println("postRequestRegistMembers: registerMember");
        System.out.println(registerMember);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!registerMember.getPassword().equals(registerMember.getConfirmation())) {
            registerMember.setMessage("Make sure your Password match");
            return "register";
        }

        if (memberservice.getByUserName(registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase()) != null) { ;
            registerMember.setMessage("Username " + registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase() + " allready exists");
            return "register";
        }


        Member member = new Member();
        member.setAuthority("member");
        member.setLastname(registerMember.getLastname());
        int strength = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
        String encodedPassword = bCryptPasswordEncoder.encode(registerMember.getPassword());

        member.setPassword(encodedPassword);
        member.setPrename(registerMember.getPrename());
        member.setUsername(registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase());

        memberservice.add(member);
        getRequestRegistered(model, member);

        return "registerconfirmed";
    }

    @GetMapping("/registerconfirmed")
    public String getRequestRegistered(Model model, Member member) {
        System.out.println("getRequestRegistMembers");
        model.addAttribute("member", member);
        return "registerconfirmed";
    }
}
