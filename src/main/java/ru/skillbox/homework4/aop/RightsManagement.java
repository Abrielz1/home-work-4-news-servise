package ru.skillbox.homework4.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import ru.skillbox.homework4.commentary.repository.CommentaryRepository;
import ru.skillbox.homework4.exception.exceptions.ObjectNotFoundException;
import ru.skillbox.homework4.exception.exceptions.UnsupportedStateException;
import ru.skillbox.homework4.news.repository.NewsRepository;
import ru.skillbox.homework4.user.model.Role;
import ru.skillbox.homework4.user.model.RoleType;
import ru.skillbox.homework4.user.model.User;
import ru.skillbox.homework4.user.repository.UserRepository;
import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RightsManagement {

    private final CommentaryRepository commentaryRepository;

    private final NewsRepository newsRepository;

//    private final UserRepository userRepository;
//
//    @Before(value = "execution(* ru.skillbox.homework4.user.controller.UserController.getById(..))"
//            + " && args(id, ..)", argNames = "id")
//    public void userCheckId(@PathVariable(name = "id") Long id) {
//
//        User user = checkByIdInDb(id);
//
//        for (Role role: user.getRole()) {
//            if (role.getAuthority().equals(RoleType.ROLE_USER)) {
//                if (!userRepository.existsById(id)) {
//                    throw new UnsupportedStateException("You not owner!");
//                }
//            }
//        }
//    }

//    @Before(value = "execution(* ru.skillbox.homework4.user.controller.UserController.updateUserById(..))"
//            + " && args(id, ..)", argNames = "id")
//    public void userCheckUpdate(@PathVariable(name = "id") Long id) {
//
//        User user = checkByIdInDb(id);
//
//        for (Role role: user.getRole()) {
//            if (role.toString().equals(RoleType.ROLE_USER.toString())) {
//                if (!userRepository.existsById(id)) {
//                    throw new UnsupportedStateException("You not owner!");
//                }
//            }
//        }
//    }
//
//    @Before(value = "execution(* ru.skillbox.homework4.user.controller.UserController.delete(..))"
//            + " && args(id, ..)", argNames = "id")
//    public void userCheckDelete(@PathVariable(name = "id") Long id) {
//
//        User user = checkByIdInDb(id);
//
//        for (Role role: user.getRole()) {
//            if (role.toString().equals(RoleType.ROLE_USER.toString())) {
//                if (!userRepository.existsById(id)) {
//                    throw new UnsupportedStateException("You not owner!");
//                }
//            }
//        }
//    }

    @Before(value = "execution(* ru.skillbox.homework4.news.controller.NewsController.updateNewsById(..))" +
            "&& args(newsId, ..)" +
            " && args(userId, ..)", argNames = "newsId,userId")
    public void userRightsManagementOnUpdate(@PathVariable(name = "newsId") Long newsId,
                                             @RequestParam("userId") Long userId) {

        if (!newsRepository.checkRights(userId)) {
            System.out.println("Response form: " + newsRepository.checkRights(userId));
            log.error("User with id: {} trying to reach someone else property with id: {}", userId, newsId);
            throw new UnsupportedStateException("You not owner!");
        }
    }

    @Before(value = "execution(* ru.skillbox.homework4.news.controller.NewsController.deleteNewsById(..))" +
            "&& args(newsId, ..)" +
            " && args(userId, ..)", argNames = "newsId,userId")
    public void userRightsManagementOnDelete(@PathVariable(name = "newsId") Long newsId,
                                             @RequestParam("userId") Long userId) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        String id = request.getParameter("userId");
        userId = Long.valueOf(id);

        if (!newsRepository.existsByIdAndUserId(newsId, userId)) {

            log.error("User with id: {} trying to reach someone else property with id: {}", userId, newsId);
            throw new UnsupportedStateException("You not owner!");
        }
    }

    @Before(value = "execution(* ru.skillbox.homework4.commentary.controller.CommentaryController.updateCommentaryById(..))" +
            "&& args(newsId, ..)" +
            " && args(userId, ..)" +
            " && args(commentaryId, ..)", argNames = "newsId,userId,commentaryId")
    public void userRightsManagementOnUpdateCommentary(@PathVariable(name = "newsId") Long newsId,
                                                       @RequestParam("userId") Long userId,
                                                       @PathVariable Long commentaryId) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        userId = Long.valueOf(request.getParameter("userId"));
        Map<String, String> pathVariables = (Map<String, String>)
                request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        List<String> list = pathVariables.values().stream().toList();
        newsId = Long.parseLong(list.get(0));
        commentaryId = Long.parseLong(list.get(1));

        if (!commentaryRepository.checkRights(commentaryId, userId)) {

            log.error("User with id: {} trying to reach someone else property with id: {}", userId, commentaryId);
            throw new UnsupportedStateException("You not owner!");
        }
    }

    @Before(value = "execution(* ru.skillbox.homework4.commentary.controller.CommentaryController.deleteCommentaryById(..))" +
            "&& args(newsId, ..)" +
            " && args(commentaryId, ..)" +
            " && args(userId, ..)", argNames = "newsId,commentaryId,userId")
    public void userRightsManagementOnDeleteCommentary(@PathVariable(name = "newsId") Long newsId,
                                                       @PathVariable Long commentaryId,
                                                       @RequestParam("userId") Long userId) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        userId = Long.valueOf(request.getParameter("userId"));
        Map<String, String> pathVariables = (Map<String, String>)
                request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        List<String> list = pathVariables.values().stream().toList();
        newsId = Long.parseLong(list.get(0));
        commentaryId = Long.parseLong(list.get(1));

        if (!commentaryRepository.checkRights(commentaryId, userId)) {

            log.error("User with id: {} trying to reach someone else property with id: {}", userId, commentaryId);
            throw new UnsupportedStateException("You not owner!");
        }
    }

//    private User checkByIdInDb(Long id) {
//        return userRepository.findById(id).orElseThrow(() -> {
//            throw new ObjectNotFoundException("User was not found in db");
//
//        });
//    }
}
