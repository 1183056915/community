package cn.lc.community.controller;

import cn.lc.community.dto.AccessTokenDTO;
import cn.lc.community.dto.GithubUser;
import cn.lc.community.mapper.UserMapper;
import cn.lc.community.model.User;
import cn.lc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")

    public String callback(@RequestParam(name = "code")String code, @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
      if(githubUser!=null){
          User user = new User();
         String token = UUID.randomUUID().toString();
          user.setToken(token);
          user.setName(githubUser.getName());
          user.setAccountId(String.valueOf(githubUser.getId()));
          user.setGmtCreate(System.currentTimeMillis());
          user.setGmtModified(user.getGmtCreate());
          user.setAvatarUrl(githubUser.getAvatar_url());
          userMapper.insert(user);
          response.addCookie(new Cookie("token",token));
          //登录成功，写cookie和session
        /*  request.getSession().setAttribute("user",githubUser);*/
          return "redirect:/";
      }else{
          //登录失败，重新登录
          return "redirect:/";

      }

    }
}
