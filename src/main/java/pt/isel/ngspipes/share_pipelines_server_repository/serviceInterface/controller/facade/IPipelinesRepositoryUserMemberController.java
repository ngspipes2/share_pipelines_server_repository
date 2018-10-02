package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryUserMember;
import pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config.Routes;

import java.util.Collection;

@CrossOrigin
@RestController
public interface IPipelinesRepositoryUserMemberController {

    @RequestMapping(value = Routes.GET_ALL_USER_MEMBERS, method = RequestMethod.GET)
    ResponseEntity<Collection<PipelinesRepositoryUserMember>> getAllMembers() throws Exception;

    @RequestMapping(value = Routes.GET_USER_MEMBER, method = RequestMethod.GET)
    ResponseEntity<PipelinesRepositoryUserMember> getMember(@PathVariable int memberId) throws Exception;

    @RequestMapping(value = Routes.INSERT_USER_MEMBER, method = RequestMethod.POST)
    ResponseEntity<Integer> insertMember(@RequestBody PipelinesRepositoryUserMember member) throws Exception;

    @RequestMapping(value = Routes.UPDATE_USER_MEMBER, method = RequestMethod.PUT)
    ResponseEntity<Void> updateMember(@RequestBody PipelinesRepositoryUserMember member, @PathVariable int memberId) throws Exception;

    @RequestMapping(value = Routes.DELETE_USER_MEMBER, method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteMember(@PathVariable int memberId) throws Exception;

    @RequestMapping(value = Routes.GET_USER_MEMBERS_WITH_USER, method = RequestMethod.GET)
    ResponseEntity<Collection<PipelinesRepositoryUserMember>> getMembersWithUser(@PathVariable String userName) throws Exception;

    @RequestMapping(value = Routes.GET_USER_MEMBERS_OF_REPOSITORY, method = RequestMethod.GET)
    ResponseEntity<Collection<PipelinesRepositoryUserMember>> getMembersOfRepository(@PathVariable int repositoryId) throws Exception;

}
