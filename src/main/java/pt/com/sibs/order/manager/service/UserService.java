package pt.com.sibs.order.manager.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;

    @Transactional
    public UserDTO create(UserDTO dto){
        User user = dto.parse();
        return new UserDTO().build(this.repository.save(user));
    }

    @Transactional
    public UserDTO update(Integer userId, UserDTO dto){
        User user = this.getById(userId);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return new UserDTO().build(this.repository.save(user));
    }

    @Transactional
    public User delete(Integer userId){
        User user = this.getById(userId);
        this.repository.delete(user);
        return user;
    }

    public List<UserDTO> getAll(){
        return this.repository
                .findAll()
                .stream()
                .map(i -> new UserDTO().build(i))
                .collect(Collectors.toList());
    }

    public Page<UserDTO> getAllPaged(Pageable page){
        return this.repository
                .findAll(page)
                .map(i -> new UserDTO().build(i));
    }
    private User getById(Integer id){
        return this.repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("User not found!"));
    }

    public User getByEmail(String email){
        return this.repository.findByEmailLike(email)
                .orElseThrow(()-> new EntityNotFoundException("User not found!"));
    }




}
