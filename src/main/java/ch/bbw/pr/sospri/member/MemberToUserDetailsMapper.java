package ch.bbw.pr.sospri.member;
import ch.bbw.pr.sospri.member.Member;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
public class MemberToUserDetailsMapper {
    static public UserDetails toUserDetails(Member member){
        User user = null;
        System.out.println(member);
        if(member != null) {
            Collection<MemberGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new MemberGrantedAuthority(member.getAuthority()));
            user = new User(member.getPrename().toLowerCase()+ "." + member.getLastname().toLowerCase(),
                    member.getPassword(),
                    true,
                    true,
                    true,
                    true,authorities);
        }
        return user;
    }
}